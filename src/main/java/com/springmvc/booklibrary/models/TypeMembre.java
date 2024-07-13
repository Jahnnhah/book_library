package com.springmvc.booklibrary.models;

import com.springmvc.booklibrary.annotations.Mapping;
import com.springmvc.booklibrary.dao.ModelDao;

@Mapping(table_name = "type_membre", id_preffix = "", sequence_name = "")
public class TypeMembre extends ModelDao {
    private String id;
    private String designation;
    private Integer coeff_retard;

    public TypeMembre() {}

    public TypeMembre(String designation, Integer coeff_retard) {
        this.setDesignation(designation);
        this.setCoeff_retard(coeff_retard);
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

    public Integer getCoeff_retard() {
        return coeff_retard;
    }

    public void setCoeff_retard(int coeff_retard) {
        this.coeff_retard = coeff_retard;
    }
}
