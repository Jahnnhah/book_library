package com.springmvc.booklibrary.models;

import com.springmvc.booklibrary.annotations.Mapping;
import com.springmvc.booklibrary.dao.ModelDao;
import org.springframework.stereotype.Service;

@Service
@Mapping(table_name = "auteur", id_preffix = "AUT", sequence_name = "auteur_seq")
public class Auteur extends ModelDao {
    private String id;
    private String nom;

    public Auteur() {
    }

    public Auteur(String nom) {
        this.setNom(nom);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
