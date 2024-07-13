package com.springmvc.booklibrary.models;

import com.springmvc.booklibrary.annotations.Mapping;
import com.springmvc.booklibrary.dao.ModelDao;

import java.sql.Connection;
import java.sql.SQLException;

@Mapping(table_name = "exemplaire", id_preffix = "EXP", sequence_name = "exemplaire_seq")
public class Exemplaire extends ModelDao {
    private String id;
    private String livre;
    private Boolean disponible;

    public Exemplaire(String livre, Boolean disponible) {
        this.setLivre(livre);
        this.setDisponible(disponible);
    }

    public Exemplaire() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLivre() {
        return livre;
    }

    public void setLivre(String livre) {
        this.livre = livre;
    }

    public Boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Boolean makeNotDisponible(Connection con) throws SQLException {
        this.setDisponible(false);
        if (this.save(con) == 1) {
            return true;
        }
        return false;
    }
}
