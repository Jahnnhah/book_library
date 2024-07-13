package com.springmvc.booklibrary.models;

import com.springmvc.booklibrary.annotations.Mapping;
import com.springmvc.booklibrary.dao.ModelDao;

@Mapping(table_name = "collection", id_preffix = "COL", sequence_name = "collection_seq")
public class Collection extends ModelDao {
    private String id;
    private String designation;

    public Collection() { }

    public Collection(String designation) {
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
