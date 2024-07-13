package com.springmvc.booklibrary.models;

public class Categorie {
    private String id;
    private String designation;

    public Categorie() {
    }

    public Categorie(String designation) {
        this.setDesignation(designation);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
