package com.springmvc.booklibrary.modelsAffichage;

import com.springmvc.booklibrary.annotations.Mapping;
import com.springmvc.booklibrary.models.Membre;

@Mapping(table_name = "v_membre", id_preffix = "", sequence_name = "")
public class MembreAffichage extends Membre {
    String type_membre_designation;

    public String getType_membre_designation() {
        return type_membre_designation;
    }

    public void setType_membre_designation(String type_membre_designation) {
        this.type_membre_designation = type_membre_designation;
    }
}
