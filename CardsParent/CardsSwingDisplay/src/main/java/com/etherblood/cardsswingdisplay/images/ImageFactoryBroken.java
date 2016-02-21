//package com.etherblood.cardsswingdisplay.images;
//
//import java.awt.Image;
//import java.awt.Rectangle;
//import java.awt.Toolkit;
//import java.awt.image.BufferedImage;
//import java.awt.image.CropImageFilter;
//import java.awt.image.FilteredImageSource;
//import java.awt.image.ImageProducer;
//import java.awt.image.ReplicateScaleFilter;
//import java.io.File;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.imageio.ImageIO;
//import javax.swing.ImageIcon;
//import sun.awt.image.ToolkitImage;
//
///**
// *
// * @author Philipp
// */
//public class ImageFactoryBroken {
//
//    public final static ImageFactoryBroken INSTANCE = new ImageFactoryBroken();
//    private final HashMap<String, ImageData> dataMap = new HashMap<>();
//    private final HashMap<Object, ImageIcon> imageMap = new HashMap<>();
//    private final HashMap<String, ImageIcon> lastSizeCache = new HashMap<>();
//
//    public ImageIcon get(String name, int width, int height) {
//        if (width <= 0 || height <= 0) {
//            return null;
//        }
//        ImageIcon icon = lastSizeCache.get(name);
//        if (icon != null && icon.getIconWidth() == width && icon.getIconHeight() == height) {
//            return icon;
//        }
//        ImageData data = dataMap.get(name);
//        if (data == null) {
//            return null;
//        }
//        ImageIcon img = fromData(data);
//        icon = getAdjusted(img, data, width, height);
//        lastSizeCache.put(name, icon);
//        return icon;
//    }
//
//    private ImageIcon fromData(ImageData data) {
//        ImageIcon image;
//            if (data.getPath() != null) {
//                image = imageMap.get(data.getPath());
//                if (image == null) {
//                    image = new ImageIcon(data.getPath());//(ToolkitImage)Toolkit.getDefaultToolkit().getImage(data.getPath());
//                    imageMap.put(data.getPath(), image);
//                }
//            } else if (data.getUrl() != null) {
//                URL url = toUrl(data);
//                image = imageMap.get(url);
//                if (image == null) {
//                    image = new ImageIcon(url);//(ToolkitImage)Toolkit.getDefaultToolkit().getImage(url);
//                    imageMap.put(url, image);
//                }
//            } else {
//                throw new IllegalStateException();
//            }
//        return image;
//    }
//
//    private ImageIcon getAdjusted(ImageIcon image, ImageData data, int destWidth, int destHeight) {
////        ImageProducer source = image.getSource();
//        Rectangle view = data.getView();
//        if (view == null) {
//            view = new Rectangle(image.getIconWidth(), image.getIconHeight());
//        }
//        image = zoomAndCrop(image, view, destWidth, destHeight);
//        return image;
//    }
//
//    private ImageIcon zoomAndCrop(ImageIcon source, Rectangle view, int destWidth, int destHeight) {
//        int unscaledWidth;
//        int unscaledHeight;
//        if (view.width * destHeight > destWidth * view.height) {
//            unscaledWidth = (view.height * destWidth) / destHeight;
//            unscaledHeight = view.height;
//        } else {
//            unscaledWidth = view.width;
//            unscaledHeight = (view.width * destHeight) / destWidth;
//        }
//        int unscaledX = view.x + (view.width - unscaledWidth) / 2;
//        int unscaledY = view.y + (view.height - unscaledHeight) / 2;
//        ImageProducer producer = source.getImage().getSource();
//        producer = new FilteredImageSource(producer, new CropImageFilter(unscaledX, unscaledY, unscaledWidth, unscaledHeight));
//        producer = new FilteredImageSource(producer, new ReplicateScaleFilter(destWidth, destHeight));
//        return new ImageIcon(new ToolkitImage(producer));
//        
////        source = source.getSubimage(unscaledX, unscaledY, unscaledWidth, unscaledHeight);
////        BufferedImage result = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_ARGB);
////        result.getGraphics().drawImage(source.getScaledInstance(destWidth, destHeight, Image.SCALE_SMOOTH), 0, 0, null);
////        return result;
//        
////        source = new FilteredImageSource(source, new CropImageFilter(unscaledX, unscaledY, unscaledWidth, unscaledHeight));
////        source = new FilteredImageSource(source, new ReplicateScaleFilter(destWidth, destHeight));
//////        source = new FilteredImageSource(source, new AreaAveragingScaleFilter(destWidth, destHeight));//does not support gifs
////        return source;
//    }
//
//    public void register(ImageData data) {
//        dataMap.put(data.getName(), data);
//    }
//
//    private URL toUrl(ImageData data) throws RuntimeException {
//        try {
//            return new URL(data.getUrl());
//        } catch (MalformedURLException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//}
