package com.mnayef.linkpreview;

import com.infoedge.jrandomizer.annotations.URL;

/**
 * Created by Mohamed Hamdan on 2017-Jun-04.
 * mohamed.nayef95@gmail.com
 */
public class LinkModel {

    @URL
    private String mLink;

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        this.mLink = link;
    }
}
