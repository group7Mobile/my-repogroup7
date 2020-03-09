package com.example.artemis.backend;

public class Favourite {
    private String title;
    private String url;
    private boolean homepage;

    public Favourite(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isHomepage() {
        return homepage;
    }

    public void setHomepage(boolean homepage) {
        this.homepage = homepage;
    }
}