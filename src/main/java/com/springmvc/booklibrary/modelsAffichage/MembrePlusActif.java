package com.springmvc.booklibrary.modelsAffichage;

import com.springmvc.booklibrary.annotations.Mapping;
import com.springmvc.booklibrary.models.Membre;

@Mapping(table_name = "v_membre_plus_actif", id_preffix = "", sequence_name = "")
public class MembrePlusActif extends Membre {
    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
