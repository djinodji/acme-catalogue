package com.acme.catalogue;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Djinodji on 3/9/2016.
 */
@Root (strict=false)
public class AlbumSerialisable {
    @Element(required=false)
    private String nom;
    @Element(required=false)
    private String last_update_time;
    @Element(required=false)
    private int id;
    @Element(required=false)
    private int photos_count;
    @Element(required=false)
    private int statut;
    @ElementList(inline=true, required = false)
    private List<Image> photos;

    public AlbumSerialisable(){}
    public  AlbumSerialisable(Album al, List<Image> images){
        this.id=al.getId();
        this.nom=al.getNom();
        this.last_update_time=al.getLast_update_time();
        this.statut=al.getStatut();
        this.photos_count=al.getPhotos_count();
        this.photos= new ArrayList<Image>();
        for (int x=0; x<images.size();x++)
        {
            this.photos.add(images.get(x));
        }
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLast_update_time() {
        return last_update_time;
    }

    public void setLast_update_time(String last_update_time) {
        this.last_update_time = last_update_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhotos_count() {
        return photos_count;
    }

    public void setPhotos_count(int photos_count) {
        this.photos_count = photos_count;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public List<Image> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Image> photos) {
        this.photos = photos;
    }
}
