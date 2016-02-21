package com.etherblood.cardsswingdisplay.images;

import java.awt.Rectangle;

/**
 *
 * @author Philipp
 */
public class ImageData {
    private String name, url, path;
    private Rectangle view;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Rectangle getView() {
        return view;
    }

    public void setView(Rectangle view) {
        this.view = view;
    }
    
    public boolean enableSmoothScale() {
        if(path != null) {
            return !path.endsWith(".gif");
        }
        return !url.endsWith(".gif");
    }
}
