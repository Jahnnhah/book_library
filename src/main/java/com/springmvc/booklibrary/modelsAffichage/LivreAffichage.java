package com.springmvc.booklibrary.modelsAffichage;

import com.springmvc.booklibrary.annotations.Mapping;
import com.springmvc.booklibrary.models.Livre;

@Mapping(table_name = "v_livre", id_preffix = "", sequence_name = "")
public class LivreAffichage extends Livre {
    String auteur_nom;
    String editeur_designation;
    String collection_designation;
    String langue_designation;

    public String getAuteur_nom() {
        return auteur_nom;
    }

    public void setAuteur_nom(String auteur_nom) {
        this.auteur_nom = auteur_nom;
    }

    public String getEditeur_designation() {
        return editeur_designation;
    }

    public void setEditeur_designation(String editeur_designation) {
        this.editeur_designation = editeur_designation;
    }

    public String getCollection_designation() {
        return collection_designation;
    }

    public void setCollection_designation(String collection_designation) {
        this.collection_designation = collection_designation;
    }

    public String getLangue_designation() {
        return langue_designation;
    }

    public void setLangue_designation(String langue_designation) {
        this.langue_designation = langue_designation;
    }
}
