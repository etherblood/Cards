package com.etherblood.cardsswingdisplay.images;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.ReplicateScaleFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import javax.swing.ImageIcon;
import sun.awt.image.ToolkitImage;

/**
 *
 * @author Philipp
 */
public class ImageFactory {
    public final static ImageFactory INSTANCE = new ImageFactory();
    private final HashMap<String, ImageData> dataMap = new HashMap<>();
    private final HashMap<String, ToolkitImage> imageMap = new HashMap<>();
    private final HashMap<String, ImageIcon> lastSizeCache = new HashMap<>();
    
    public ImageIcon get(String name, int width, int height) {
        if(width <= 0 || height <= 0) {
            return null;
        }
        ImageIcon icon = lastSizeCache.get(name);
        if(icon != null && icon.getIconWidth() == width && icon.getIconHeight() == height) {
            return icon;
        }
        ImageData data = dataMap.get(name);
        if(data == null) {
            return null;
        }
        ToolkitImage img = fromData(data);
        img = getAdjusted(img, data, width, height);
        icon = new ImageIcon(img);
        lastSizeCache.put(name, icon);
        return icon;
    }
    
    private ToolkitImage fromData(ImageData data) {
        ToolkitImage image;
        if(data.getPath() != null) {
            image = imageMap.get(data.getPath());
            if(image == null) {
                System.out.println("opening image " + data.getPath());
                image = (ToolkitImage)Toolkit.getDefaultToolkit().getImage(data.getPath());
                imageMap.put(data.getPath(), image);
            }
        } else if(data.getUrl() != null) {
            image = imageMap.get(data.getUrl());
            if(image == null) {
                System.out.println("downloading image " + data.getUrl());
                image = (ToolkitImage)Toolkit.getDefaultToolkit().getImage(toUrl(data.getUrl()));
                imageMap.put(data.getUrl(), image);
            }
        } else {
            throw new IllegalStateException();
        }
        return image;
    }
    
    private ToolkitImage getAdjusted(ToolkitImage image, ImageData data, int destWidth, int destHeight) {
        ImageProducer source = image.getSource();
        Rectangle view = data.getView();
        if(view == null) {
            view = new Rectangle(image.getWidth(), image.getHeight());
        }
        source = zoomAndCrop(source, view, destWidth, destHeight, data.enableSmoothScale());
        return new ToolkitImage(source);
    }
    
    private ImageProducer zoomAndCrop(ImageProducer source, Rectangle view, int destWidth, int destHeight, boolean smooth) {
        int unscaledWidth;
        int unscaledHeight;
        if(view.width * destHeight > destWidth * view.height) {
            unscaledWidth = (view.height * destWidth) / destHeight;
            unscaledHeight = view.height;
        } else {
            unscaledWidth = view.width;
            unscaledHeight = (view.width * destHeight) / destWidth;
        }
        int unscaledX = view.x + (view.width - unscaledWidth) / 2;
        int unscaledY = view.y + (view.height - unscaledHeight) / 2;
        source = new FilteredImageSource(source, new CropImageFilter(unscaledX, unscaledY, unscaledWidth, unscaledHeight));
        if(smooth) {
            source = new FilteredImageSource(source, new AreaAveragingScaleFilter(destWidth, destHeight));
        } else {
            source = new FilteredImageSource(source, new ReplicateScaleFilter(destWidth, destHeight));
        }
        return source;
    }

    public void register(ImageData data) {
        dataMap.put(data.getName(), data);
    }

    private URL toUrl(String url) throws RuntimeException {
        try {
            return new URL(url);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
}
