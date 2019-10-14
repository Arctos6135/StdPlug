package com.arctos6135.stdplug.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.concurrent.Task;
import javafx.scene.image.Image;

/**
 * A Task that receives image data from an MJPEG stream and never finishes unless cancelled.
 */
public class MJPEGStreamViewerTask extends Task<Image> {

    public static final Image NO_CONNECTION_IMG = new Image(MJPEGStreamViewerTask.class.getResourceAsStream("/noconnection.gif"));
    public static final String NO_CONNECTION_STR = "Not Connected";

    // The bytes for the start image and end image tags in a JPEG image
    // This is how we separate each image
    private static final int[] START_IMAGE_BYTES = { 0xFF, 0xD8 };
    private static final int[] END_IMAGE_BYTES = { 0xFF, 0xD9 };

    // Conversion ratio from bytes per second to megabits per second
    private static final double BPS_TO_MBPS = 8.0 / 1024.0 / 1024.0;

    // The URL of the stream
    volatile private String streamURL;

    // Whether or not the URL has been updated
    // If this is true then a new connection has to be opened
    private AtomicBoolean streamURLUpdated = new AtomicBoolean(true);

    // The stream of the connection that we can get image data from
    private InputStream imgStream;

    // The minimum time between two frames
    volatile private long minRefreshInterval = 10;

    /**
     * Creates a new stream viewer thread with the specified URL.
     * @param url The URL of the stream.
     */
    public MJPEGStreamViewerTask(String url) {
        if(url != null) {
            streamURL = url.strip();
        }
    }

    /**
     * Updates the URL of the stream.
     * @param streamURL The new URL of the stream.
     */
    public void updateStreamURL(String streamURL) {
        if(streamURL != null) {
            this.streamURL = streamURL.strip();
        }
        else {
            this.streamURL = null;
        }
        streamURLUpdated.set(true);
    }

    /**
     * Sets the minimum time to wait between two frames, in milliseconds.
     * @param minRefreshInterval The minimum time between frames
     */
    public void setMinRefreshInterval(long minRefreshInterval) {
        this.minRefreshInterval = minRefreshInterval;
    }

    /**
     * Returns whether or not the URL has been updated.
     * 
     * @return If the URL has been updated
     */
    private boolean urlUpdated() {
        if(streamURLUpdated.getAndSet(false)) {
            return true;
        }
        return false;
    }

    /**
     * Opens a connection to the stream URL and returns the stream.
     * 
     * @return The stream
     * @throws InterruptedException If the Task was cancelled
     */
    private InputStream waitForImageStream() throws InterruptedException {
        // Wait forever if not cancelled
        while(!isCancelled()) {
            if(streamURL == null || streamURL == "") {
                updateValue(NO_CONNECTION_IMG);
                updateMessage(NO_CONNECTION_STR);
                
                if(!isCancelled()) {
                    // Wait a second and then retry
                    Thread.sleep(1000);
                }
            }
            else {
                try {
                    URL url = new URL(streamURL);
                    URLConnection conn = url.openConnection();
                    
                    conn.setConnectTimeout(500);
                    conn.setReadTimeout(5000);
                    
                    InputStream stream = conn.getInputStream();
                    System.out.println("Successfully connected to " + streamURL);
                    return stream;
                }
                // Catch any possible exceptions 
                // This thread shouldn't ever die by itself
                catch(Exception e) {
                    System.err.println("Failed to connect to " + streamURL);
                    e.printStackTrace();
                    
                    updateValue(NO_CONNECTION_IMG);
                    updateMessage(NO_CONNECTION_STR);
                    
                    if(!isCancelled()) {
                        // Wait a second and then retry
                        Thread.sleep(1000);
                    }
                }
            }
        }
        return null;
    }

    private static long skipUntil(InputStream stream, int[] indicator) throws IOException {
        long bytesRead = 0;
        for(int i = 0; i < indicator.length; bytesRead++) {
            int b = stream.read();
            if(b == -1) {
                throw new IOException("End of stream reached");
            }
            if(b == indicator[i]) {
                i ++;
            }
            else {
                i = 0;
            }
        }
        return bytesRead;
    }
    
    private static long readUntil(InputStream stream, int[] indicator, ByteArrayOutputStream out) throws IOException {
        long bytesRead = 0;
        for(int i = 0; i < indicator.length; bytesRead ++) {
            int b = stream.read();
            if(b == -1) {
                throw new IOException("End of stream reached");
            }
            
            if(out != null) {
                out.write(b);
            }
            
            if(b == indicator[i]) {
                i ++;
            }
            else {
                i = 0;
            }
        }
        return bytesRead;
    }

    @Override
    protected Image call() throws Exception {
        ByteArrayOutputStream imgBuf = new ByteArrayOutputStream();
        long lastFrame = 0;
        long lastStatCheck = 0;
        int fpsCounter = 0;
        long mbpsCounter = 0;

        // Loop forever while not cancelled
        while(!isCancelled()) {
            // Try to wait for a connection
            try {
                imgStream = waitForImageStream();
            }
            catch(InterruptedException e) {
                // Exit the loop and end this task if interrupted
                break;
            }

            try {
                while(!isCancelled() && !urlUpdated() && imgStream != null) {
                    // Make sure to respect the minimum refresh interval
                    if(System.currentTimeMillis() - lastFrame < minRefreshInterval) {
                        Thread.sleep(minRefreshInterval - (System.currentTimeMillis() - lastFrame));
                    }
                    // Clear the stream
                    // We don't want any old data
                    mbpsCounter += imgStream.available();
                    imgStream.skip(imgStream.available());

                    // Clear the image data
                    imgBuf.reset();
                    // Skip until the start of the actual image
                    mbpsCounter += skipUntil(imgStream, START_IMAGE_BYTES);
                    // Write the image start bytes into the image
                    for(int b : START_IMAGE_BYTES) {
                        imgBuf.write((byte) b);
                    }
                    // Read in the rest
                    mbpsCounter += readUntil(imgStream, END_IMAGE_BYTES, imgBuf);

                    // Calculate FPS and Mbps
                    fpsCounter ++;
                    if(System.currentTimeMillis() - lastStatCheck >= 1000) {
                        updateMessage("FPS: " + fpsCounter + "       Bandwidth: " 
                                + String.format("%.3f", mbpsCounter * BPS_TO_MBPS) + "Mbps");
                        fpsCounter = 0;
                        mbpsCounter = 0;
                        lastStatCheck = System.currentTimeMillis();
                    }

                    lastFrame = System.currentTimeMillis();
                    // Update the image
                    ByteArrayInputStream tmpStream = new ByteArrayInputStream(imgBuf.toByteArray());
                    updateValue(new Image(tmpStream));
                }
            }
            catch(IOException e) {
                updateValue(NO_CONNECTION_IMG);
                updateMessage(NO_CONNECTION_STR);
                System.err.println("Error while reading stream:");
                e.printStackTrace();

                try {
                    // This magical delay here fixes a magical bug where reading from the stream would give -1
                    Thread.sleep(100);
                }
                catch(InterruptedException e1) {
                    break;
                }
            }
            catch(InterruptedException e) {
                break;
            }
            catch(Exception e) {
                // Something really bad just happened
                // Recover anyways - it's critical that the stream never dies
                System.err.println("Error while reading stream:");
                e.printStackTrace();
            }
            finally {
                // Clean up the old stream
                if(imgStream != null) {
                    try {
                        imgStream.close();
                    }
                    catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return null;
    }

}
