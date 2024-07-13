package com.springmvc.booklibrary.modelsAffichage;

import com.springmvc.booklibrary.annotations.Mapping;
import com.springmvc.booklibrary.models.Livre;

@Mapping(table_name = "v_livre_plus_emprunte", id_preffix = "", sequence_name = "")
public class LivrePlusEmprunte extends Livre {
    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
