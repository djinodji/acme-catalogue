package com.acme.catalogue.model;

/**
 * Created by Djinodji on 3/1/2016.
 */
public class ProductItem {

    private String title;

    private int id;

    public ProductItem(int id, String title, String description, String albumCount)
    {
        this.id=id;
        this.albumCount=albumCount;
        this.description=description;
        this.title=title;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlbumCount() {
        return albumCount;
    }

    public void setAlbumCount(String albumCount) {
        this.albumCount = albumCount;
    }

    private String description;

    private String albumCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
