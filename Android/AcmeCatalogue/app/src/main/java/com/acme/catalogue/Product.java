package com.acme.catalogue;

/**
 * Created by Djinodji on 3/4/2016.
 */
public class Product {

    private String nom;
    private String description;
    private int id;
    private int albums_count;
    private int statut;

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

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

    public int getAlbums_count() {
        return albums_count;
    }

    public void setAlbums_count(int albums_count) {
        this.albums_count = albums_count;
    }
}
