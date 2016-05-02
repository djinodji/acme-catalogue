package com.acme.catalogue.model;

/**
 * Created by Djinodji on 3/7/2016.
 */
public class AlbumItem {
    private String title;
    private String description;
    private int id;
    private String photoCount;

    public AlbumItem(int id,String title, String description, String albumCount)
    {
        this.photoCount=albumCount;
        this.description=description;
        this.title=title;
        this.id=id;
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

    public String getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(String photoCount) {
        this.photoCount = photoCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
