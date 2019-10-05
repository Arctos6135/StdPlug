package com.arctos6135.stdplug.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * This thread runs in the background and receives image data from an MJPEG stream.
 */
public class MJPEGStreamViewerThread extends Thread {

    // The bytes for the start image and end image tags in a JPEG image
    // This is how we separate each image
    private static final int[] START_IMAGE_BYTES = { 0xFF, 0xD8 };
    private static final int[] END_IMAGE_BYTES = { 0xFF, 0xD9 };

    // Other code can bind to this property to be updated when a new frame arrives
    private ObjectProperty<BufferedImage> imgProperty = new SimpleObjectProperty<>(null);

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
        this.streamURL = streamURL;
    }

    /**
     * Updates the URL of the stream.
     * @param streamURL The new URL of the stream.
     */
    public void updateStreamURL(String streamURL) {
        this.streamURL = streamURL;
        streamURLUpdated = true;
    }

    /**
     * Retrieves the property containing the current frame.
     * 
     * @return The property containing the current frame.
     */
    public ObjectProperty<BufferedImage> getImageProperty() {
        return imgProperty;
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
                
                imgProperty.set(null);
                try {
                    // Wait half a second before retrying
                    Thread.sleep(500);
                }
                catch(InterruptedException e1) {
                    // Copied from SmartDashboard source code
                    // Not sure what this does but too scared to remove
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e1);
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

    private static void skipUntil(InputStream stream, int[] indicator) throws IOException {
        for(int i = 0; i < indicator.length; ) {
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
    }
    
    private static void readUntil(InputStream stream, int[] indicator, ByteArrayOutputStream out) throws IOException {
        for(int i = 0; i < indicator.length; ) {
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
    }

    @Override
    public void run() {
        ByteArrayOutputStream imgBuf = new ByteArrayOutputStream();
        long lastFrame = 0;

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
                    imgStream.skip(imgStream.available());

                    // Clear the image data
                    imgBuf.reset();
                    // Skip until the start of the actual image
                    skipUntil(imgStream, START_IMAGE_BYTES);
                    // Write the image start bytes into the image
                    for(int b : START_IMAGE_BYTES) {
                        imgBuf.write((byte) b);
                    }
                    // Read in the rest
                    readUntil(imgStream, END_IMAGE_BYTES, imgBuf);

                    // TODO: Bandwidth and FPS calculation

                    lastFrame = System.currentTimeMillis();
                    // Update the image
                    ByteArrayInputStream tmpStream = new ByteArrayInputStream(imgBuf.toByteArray());
                    imgProperty.set(ImageIO.read(tmpStream));
                }
            }
            catch(IOException e) {
                imgProperty.set(null);
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
