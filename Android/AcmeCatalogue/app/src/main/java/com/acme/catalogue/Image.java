package com.acme.catalogue;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Djinodji on 3/8/2016.
 */
@Root(strict=false)
public class Image {
    @Element
    private String nom;
    @Element
    private String description;
    @Element
    private int id;
    @Element
    private  int order;
    @Element
    private String path;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
