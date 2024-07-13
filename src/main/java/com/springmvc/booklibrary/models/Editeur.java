package com.springmvc.booklibrary.models;

import com.springmvc.booklibrary.annotations.Mapping;
import com.springmvc.booklibrary.dao.ModelDao;

@Mapping(table_name = "editeur", id_preffix = "EDI", sequence_name = "editeur_seq")
public class Editeur extends ModelDao {
    private String id;
    private String designation;

    public Editeur() { }

    public Editeur(String designation) {
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
