package com.springmvc.booklibrary.models;

import com.springmvc.booklibrary.annotations.Mapping;
import com.springmvc.booklibrary.dao.ModelDao;

@Mapping(table_name = "langue", id_preffix = "", sequence_name = "")
public class Langue extends ModelDao {
    private String code;
    private String designation;

    public Langue() { }

    public Langue(String code, String designation) {
        this.setCode(code);
        this.setDesignation(designation);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
