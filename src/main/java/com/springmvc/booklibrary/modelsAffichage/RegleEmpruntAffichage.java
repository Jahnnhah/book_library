package com.springmvc.booklibrary.modelsAffichage;

import com.springmvc.booklibrary.annotations.Mapping;
import com.springmvc.booklibrary.models.RegleEmprunt;

@Mapping(table_name = "v_regle_emprunt", id_preffix = "", sequence_name = "")
public class RegleEmpruntAffichage extends RegleEmprunt {
    String type_membre_designation;
    String livre_titre;

    public String getType_membre_designation() {
        return type_membre_designation;
    }

    public void setType_membre_designation(String type_membre_designation) {
        this.type_membre_designation = type_membre_designation;
    }

    public String getLivre_titre() {
        return livre_titre;
    }

    public void setLivre_titre(String livre_titre) {
        this.livre_titre = livre_titre;
    }
}
