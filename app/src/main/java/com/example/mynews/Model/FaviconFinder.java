package com.example.mynews.Model;

import java.util.List;

public class FaviconFinder {
    private String url;
    private List<Icon> icons;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Icon> getIcons() {
        return icons;
    }

    public void setIcons(List<Icon> icons) {
        this.icons = icons;
    }
}
