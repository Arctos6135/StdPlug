package com.arctos6135.stdplug.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

/**
 * This thread runs in the background and receives image data from an MJPEG stream.
 */
public class MJPEGStreamViewerThread extends Thread {

    public static final Image NO_CONNECTION_IMG = new Image(MJPEGStreamViewerThread.class.getResourceAsStream("/noconnection.gif"));

    // The bytes for the start image and end image tags in a JPEG image
    // This is how we separate each image
    private static final int[] START_IMAGE_BYTES = { 0xFF, 0xD8 };
    private static final int[] END_IMAGE_BYTES = { 0xFF, 0xD9 };

    // Conversion ratio from bytes per second to megabits per second
    private static final double BPS_TO_MBPS = 8.0 / 1024.0 / 1024.0;

    // Other code can bind to this property to be updated when a new frame arrives
    private ObjectProperty<Image> imgProperty = new SimpleObjectProperty<>(null);
    // Other code can bind to this property to be updated when the FPS is calculated
    private IntegerProperty fpsProperty = new SimpleIntegerProperty(-1);
    // Other code can bind to this property to be updated when the bandwidth is calculated
    private DoubleProperty mbpsProperty = new SimpleDoubleProperty(-1);

    // The URL of the stream
    private String streamURL;

    // Whether or not the URL has been updated
    // If this is true then a new connection has to be opened
    private boolean streamURLUpdated = true;

    // The stream of the connection that we can get image data from
    private InputStream imgStream;

    // The minimum time between two frames
    private long minRefreshInterval = 10;

    /**
     * Creates a new stream viewer thread with the specified URL.
     * @param streamURL The URL of the stream.
     */
    public MJPEGStreamViewerThread(String streamURL) {
        super();
        // Make this a daemon thread to prevent it from keeping the VM alive
        setDaemon(true);
        if(streamURL != null) {
            this.streamURL = streamURL.strip();
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
        streamURLUpdated = true;
    }

    /**
     * Retrieves the property containing the current frame.
     * 
     * @return The property containing the current frame.
     */
    public ObjectProperty<Image> getImageProperty() {
        return imgProperty;
    }

    /**
     * Retrieves the property containing the FPS.
     * 
     * @return The property containing the FPS.
     */
    public IntegerProperty getFPSProperty() {
        return fpsProperty;
    }

    /**
     * Retrieves the property containing the bandwidth in Mbps.
     * 
     * @return The property containing the bandwidth
     */
    public DoubleProperty getMbpsProperty() {
        return mbpsProperty;
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
        if(streamURLUpdated) {
            streamURLUpdated = false;
            return true;
        }
        return false;
    }

    /**
     * Opens a connection to the stream URL and returns the stream.
     * 
     * @return The stream
     */
    private InputStream waitForImageStream() {
        // Wait forever if not interrupted
        while(!interrupted()) {
            if(streamURL == null || streamURL == "") {
                imgProperty.set(NO_CONNECTION_IMG);
                fpsProperty.set(-1);
                mbpsProperty.set(-1);
                try {
                    // Wait a second before retrying
                    Thread.sleep(1000);
                }
                catch(InterruptedException e1) {
                    // Copied from SmartDashboard source code
                    // Not sure what this does but too scared to remove
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e1);
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
                catch(IOException e) {
                    System.err.println("Failed to connect to " + streamURL);
                    e.printStackTrace();
                    
                    imgProperty.set(NO_CONNECTION_IMG);
                    fpsProperty.set(-1);
                    mbpsProperty.set(-1);
                    try {
                        // Wait a second before retrying
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException e1) {
                        // Copied from SmartDashboard source code
                        // Not sure what this does but too scared to remove
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e1);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void interrupt() {
        // Do some cleanup
        if(imgStream != null) {
            try {
                imgStream.close();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
        super.interrupt();
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
    public void run() {
        ByteArrayOutputStream imgBuf = new ByteArrayOutputStream();
        long lastFrame = 0;
        long lastStatCheck = 0;
        int fpsCounter = 0;
        long mbpsCounter = 0;

        while(!interrupted()) {
            imgStream = waitForImageStream();

            try {
                while(!interrupted() && !urlUpdated() && imgStream != null) {
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
                        fpsProperty.set(fpsCounter);
                        fpsCounter = 0;
                        mbpsProperty.set(mbpsCounter * BPS_TO_MBPS);
                        mbpsCounter = 0;
                        lastStatCheck = System.currentTimeMillis();
                    }

                    lastFrame = System.currentTimeMillis();
                    // Update the image
                    ByteArrayInputStream tmpStream = new ByteArrayInputStream(imgBuf.toByteArray());
                    imgProperty.set(new Image(tmpStream));
                }
            }
            catch(IOException e) {
                imgProperty.set(NO_CONNECTION_IMG);
                fpsProperty.set(-1);
                mbpsProperty.set(-1);
                System.err.println("Error while reading stream:");
                e.printStackTrace();
            }
            catch(InterruptedException e) {
                // Copied from SmartDashboard source code
                // Not sure what this does but too scared to remove
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
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
    }
}
