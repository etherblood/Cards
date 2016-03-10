package com.etherblood.cardsjmeclient.match.cards.images;

import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.simsilica.lemur.GuiGlobals;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.Raster;
import java.awt.image.ReplicateScaleFilter;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Philipp
 */
public class ImageFactory {

    private final static ImageFactory INSTANCE = new ImageFactory();
    private final HashMap<String, ImageData> dataMap = new HashMap<>();
    private final HashMap<String, BufferedImage> imageMap = new HashMap<>();
    private final HashMap<String, Texture> lastSizeCache = new HashMap<>();

    public static ImageFactory getInstance() {
        return INSTANCE;
    }


    public Texture get(String name, int width, int height) {
        if (width <= 0 || height <= 0) {
            return getDefaultTexture();
        }
        Texture icon = lastSizeCache.get(name);
        if (icon != null && icon.getImage().getWidth() == width && icon.getImage().getHeight() == height) {
            return icon;
        }
        ImageData data = dataMap.get(name);
        if (data == null) {
            return getDefaultTexture();
        }
        BufferedImage img = fromData(data);
        img = getAdjusted(img, data, width, height);

        PaintableImage paintableImage = new PaintableImage(img, true);

        icon = new Texture2D();
//        byte[] data1 = paintableImage.getData();
//        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(data1.length);
//        byteBuffer.put(data1);
        icon.setImage(paintableImage.getImage());
//        BufferedImage bufferedImage = img;//toBufferedImage(new ImageIcon(img).getImage(), width, height);
//
//        Raster data1 = bufferedImage.getData();
//        int[] pixelData = null;
//        pixelData = data1.getPixels(0, 0, width, height, pixelData); //getDataBuffer();
////        int[] pixelData = ((DataBufferInt) dataBuffer).getData();
//        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(pixelData.length * 4);
//        byteBuffer.asIntBuffer().put(IntBuffer.wrap(pixelData));
//        icon.setImage(new Image(Image.Format.RGBA8, width, height, byteBuffer));
        lastSizeCache.put(name, icon);
        return icon;
    }

    private Texture getDefaultTexture() {
        return GuiGlobals.getInstance().loadTexture("./random_sprites___little_red_by_extrahp-d7auma5.gif"
//        return "./agafdg.png";
        , false, false);
    }

    private BufferedImage fromData(ImageData data) {
        BufferedImage image;
        if (data.getPath() != null) {
            image = imageMap.get(data.getPath());
            if (image == null) {
                try {
                    System.out.println("opening image " + data.getPath());
                    image = ImageIO.read(new File(data.getPath()));
                    imageMap.put(data.getPath(), image);
                } catch (IOException ex) {
                    ex.printStackTrace(System.out);
                    return null;
                }
            }
        } else if (data.getUrl() != null) {
            image = imageMap.get(data.getUrl());
            if (image == null) {
                try {
                    System.out.println("downloading image " + data.getUrl());
                    image = ImageIO.read(new URL(data.getUrl()));
                    imageMap.put(data.getUrl(), image);
                } catch (IOException ex) {
                    ex.printStackTrace(System.out);
                    return null;
                }
            }
        } else {
            throw new IllegalStateException();
        }
        return image;
    }

    private BufferedImage getAdjusted(BufferedImage image, ImageData data, int destWidth, int destHeight) {
//        ImageProducer source = image.getSource();
        Rectangle view = data.getView();
        if (view == null) {
            view = new Rectangle(image.getWidth(), image.getHeight());
        }
        return zoomAndCrop(image, view, destWidth, destHeight, data.enableSmoothScale());
//        return new ToolkitImage(source);
    }

    private BufferedImage zoomAndCrop(BufferedImage source, Rectangle view, int destWidth, int destHeight, boolean smooth) {
        int unscaledWidth;
        int unscaledHeight;
        if (view.width * destHeight > destWidth * view.height) {
            unscaledWidth = (view.height * destWidth) / destHeight;
            unscaledHeight = view.height;
        } else {
            unscaledWidth = view.width;
            unscaledHeight = (view.width * destHeight) / destWidth;
        }
        int unscaledX = view.x + (view.width - unscaledWidth) / 2;
        int unscaledY = view.y + (view.height - unscaledHeight) / 2;
        source = source.getSubimage(unscaledX, unscaledY, unscaledWidth, unscaledHeight);

        BufferedImage resized = new BufferedImage(destWidth, destHeight, source.getType());
        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawImage(source, 0, 0, destWidth, destHeight, 0, 0, source.getWidth(), source.getHeight(), null);
        g.dispose();

//        source = (BufferedImage) source.getScaledInstance(destWidth, destHeight, BufferedImage.SCALE_SMOOTH);
//        source = new FilteredImageSource(source, new CropImageFilter(unscaledX, unscaledY, unscaledWidth, unscaledHeight));
//        if (smooth) {
//            source = new FilteredImageSource(source, new AreaAveragingScaleFilter(destWidth, destHeight));
//        } else {
//            source = new FilteredImageSource(source, new ReplicateScaleFilter(destWidth, destHeight));
//        }
        return resized;
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
