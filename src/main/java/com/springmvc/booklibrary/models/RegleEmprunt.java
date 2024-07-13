package com.springmvc.booklibrary.models;

import com.springmvc.booklibrary.annotations.Mapping;
import com.springmvc.booklibrary.dao.ModelDao;

@Mapping(table_name = "regle_emprunt", id_preffix = "REM", sequence_name = "rglemprunt_seq")
public class RegleEmprunt extends ModelDao {
    private String id;
    private String livre;
    private String type_membre;
    private Boolean peut_emprunter;
    private Boolean peut_emmener_maison;
    private Integer limite_age;
    private Integer limite_retard;

    public RegleEmprunt() {}

    public RegleEmprunt(String livre, String type_membre, Boolean peut_emprunter, Boolean peut_emmener_maison, Integer limite_age, Integer limite_retard) {
        this.setLivre(livre);
        this.setType_membre(type_membre);
        this.setPeut_emprunter(peut_emprunter);
        this.setPeut_emmener_maison(peut_emmener_maison);
        this.setLimite_age(limite_age);
        this.setLimite_retard(limite_retard);
    }

    public String getLivre() {
        return livre;
    }

    public void setLivre(String livre) {
        this.livre = livre;
    }

    public String getType_membre() {
        return type_membre;
    }

    public void setType_membre(String type_membre) {
        this.type_membre = type_membre;
    }

    public Boolean isPeut_emprunter() {
        return peut_emprunter;
    }

    public void setPeut_emprunter(Boolean peut_emprunter) {
        this.peut_emprunter = peut_emprunter;
    }

    public Boolean isPeut_emmener_maison() {
        return peut_emmener_maison;
    }

    public void setPeut_emmener_maison(Boolean peut_emmener_maison) {
        this.peut_emmener_maison = peut_emmener_maison;
    }

    public Integer getLimite_age() {
        return limite_age;
    }

    public void setLimite_age(Integer limite_age) {
        this.limite_age = limite_age;
    }

    public Integer getLimite_retard() {
        return limite_retard;
    }

    public void setLimite_retard(Integer limite_retard) {
        this.limite_retard = limite_retard;
    }
}
