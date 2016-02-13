package com.etherblood.cardsswingdisplay;

import java.awt.Toolkit;
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
        ToolkitImage img = imageMap.get(name);
        if(img == null) {
            return null;
        }
        img = adjust(img, width, height);
        icon = new ImageIcon(img);
        lastSizeCache.put(name, icon);
        return icon;
    }
    
    private ToolkitImage adjust(ToolkitImage image, int width, int height) {
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();
        
        int tWidth;
        int tHeight;
        if(imgWidth * height > width * imgHeight) {
            tWidth = (imgHeight * width) / height;
            tHeight = imgHeight;
        } else {
            tWidth = imgWidth;
            tHeight = (int) (imgWidth * height / width);
        }
        int tX = (imgWidth - tWidth) / 2;
        int tY = (imgHeight - tHeight) / 2;
        
        ImageProducer imageSource = image.getSource();
        imageSource = new FilteredImageSource(imageSource, new CropImageFilter(tX, tY, tWidth, tHeight));
        imageSource = new FilteredImageSource(imageSource, new ReplicateScaleFilter(width, height));
//        imageSource = new FilteredImageSource(imageSource, new AreaAveragingScaleFilter(width, height));//does not support gifs
        return new ToolkitImage(imageSource);
    }

    public void registerPath(String name, String path) {
        ToolkitImage image = (ToolkitImage)Toolkit.getDefaultToolkit().getImage(path);
        imageMap.put(name, image);
    }
    
    public void registerUrl(String name, String url) throws MalformedURLException {
        ToolkitImage image = (ToolkitImage)Toolkit.getDefaultToolkit().getImage(new URL(url));
        imageMap.put(name, image);
    }
}
