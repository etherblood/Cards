package com.etherblood.cardsjmeclient.match.cards.images;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import com.jme3.texture.Image;
import com.jme3.texture.Image.Format;
import com.jme3.util.BufferUtils;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class PaintableImage1 {

    private int width;
    private int height;
//    private byte[] data;
    private ByteBuffer directBuffer;
    private Image image;

//    public PaintableImage(String imageFilePath){
//        this(imageFilePath, false);
//    }
//    public PaintableImage(String imageFilePath, boolean flipY){
//        BufferedImage loadedImage = FileAssets.getImage(imageFilePath);
//        setSize(loadedImage.getWidth(), loadedImage.getHeight());
//        loadImage(loadedImage, flipY);
//    }
    public PaintableImage1(BufferedImage loadedImage, boolean flipY) {
        this(loadedImage.getWidth(), loadedImage.getHeight());
        loadImage(loadedImage, flipY);
    }

    public PaintableImage1(int width, int height) {
        this.width = width;
        this.height = height;
        //Create black image
        directBuffer = BufferUtils.createByteBuffer(width * height * 4);
//        data = new byte[width * height * 4];       
        //Set data to texture
//        ByteBuffer buffer = BufferUtils.createByteBuffer(data);
        image = new Image(Format.RGBA8, width, height, directBuffer);
//        fill(new Color(0, 0, 0, 255));
    }

    public Image getImage() {
//        ByteBuffer buffer = BufferUtils.createByteBuffer(data);
//        image.setData(buffer);
        return image;
    }

//    public void loadImage(String filePath){
//        loadImage(filePath, false);
//    }
//    public void loadImage(String filePath, boolean flipY){
//        BufferedImage loadedImage = FileAssets.getImage(filePath);
//        loadImage(loadedImage, flipY);
//    }
    public void loadImage(BufferedImage loadedImage, boolean flipY) {
//        int rgb;
//        for(int x=0;x<width;x++){
//            for(int y=0;y<height;y++){
//                rgb = loadedImage.getRGB(x, y);
        int[] intData = loadedImage.getRGB(0, 0, width, height, null, 0, width);
//        if (flipY) {
//            for (int i = width * height - 1; i >= 0; i--) {
//                directBuffer.asIntBuffer().put(intData[width * height - 1 - i]);
//            }
////            for (int i = width * (height - 1); i >= 0; i -= width) {
////                directBuffer.asIntBuffer().put(intData, i, width);
////            }
//        } else {
            directBuffer.asIntBuffer().put(intData);
//        }
//                setPixel(x, (flipY?(height - y):y), ((rgb >> 16) & 0xFF), ((rgb >> 8) & 0xFF), (rgb & 0xFF), ((rgb >> 24) & 0xFF));
//            }
//        }
    }

    public void fill(int rgbaColor) {
        for (int i = 0; i < (width * height * 4); i += 4) {
            directBuffer.putInt(i, rgbaColor);
//            data[i] = (byte) color.getRed();
//            data[i + 1] = (byte) color.getGreen();
//            data[i + 2] = (byte) color.getBlue();
//            data[i + 3] = (byte) color.getAlpha();
        }
    }

    public void setBackground_Alpha(byte colorValue) {
        for (int i = 3; i < (width * height * 4); i += 4) {
            directBuffer.put(i, colorValue);
//            data[i] = (byte) colorValue;
        }
    }

//    public void setPixel(int x, int y, int color){
//        setPixel(x, y, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
//    }
//    public void setPixel(int x, int y, byte red, byte green, byte blue, byte alpha){
//        setPixel_Red(x, y, red);
//        setPixel_Green(x, y, green);
//        setPixel_Blue(x, y, blue);
//        setPixel_Alpha(x, y, alpha);
//    }
//    public void setPixel_Red(int x, int y, byte colorValue){
//        setPixel_ColorValue(x, y, colorValue, 0);
//    }
// 
//    public void setPixel_Green(int x, int y, byte colorValue){
//        setPixel_ColorValue(x, y, colorValue, 1);
//    }
// 
//    public void setPixel_Blue(int x, int y, byte colorValue){
//        setPixel_ColorValue(x, y, colorValue, 2);
//    }
// 
//    public void setPixel_Alpha(int x, int y, byte colorValue){
//        setPixel_ColorValue(x, y, colorValue, 3);
//    }
// 
//    private void setPixel_ColorValue(int x, int y, byte colorValue, int bufferIndexOffset){
//        int index = (bufferIndexOffset + ((x + y * width) * 4));
//        if((index >= 0) && (index < directBuffer.capacity())){
//            directBuffer.put(index, colorValue);
//        }
//    }
//    public Color getPixel(int x, int y){
//        return new Color(getPixel_Red(x, y), getPixel_Green(x, y), getPixel_Blue(x, y), getPixel_Alpha(x, y));
//    }
// 
//    public int getPixel_Red(int x, int y){
//        return getPixel_ColorValue(x, y, 0);
//    }
// 
//    public int getPixel_Green(int x, int y){
//        return getPixel_ColorValue(x, y, 1);
//    }
// 
//    public int getPixel_Blue(int x, int y){
//        return getPixel_ColorValue(x, y, 2);
//    }
// 
//    public int getPixel_Alpha(int x, int y){
//        return getPixel_ColorValue(x, y, 3);
//    }
// 
//    private int getPixel_ColorValue(int x, int y, int bufferIndexOffset){
//        int i = (x + y * width) * 4;
//        return directBuffer.get(i + bufferIndexOffset) & 0xFF;//(data[i + bufferIndexOffset] & 0xFF);
//    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

//    public byte[] getData(){
//        return data;
//    }
//    public void setData(byte[] data){
//        System.arraycopy(data, 0, this.data, 0, this.data.length);
//    }
}
