package com.mnayef.library.model;

import java.io.Serializable;

/**
 * Created by Mohamed Hamdan on 2017-Jun-02.
 * mohamed.nayef95@gmail.com
 */
public class Link implements Serializable {

    private String title;
    private String url;
    private String description;
    private String image;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
