package com.springmvc.booklibrary.models;

import com.springmvc.booklibrary.annotations.Mapping;
import com.springmvc.booklibrary.dao.ModelDao;

import java.sql.Date;
import java.time.LocalDate;

@Mapping(table_name = "penalite", id_preffix = "PEN", sequence_name = "penalite_seq")
public class Penalite extends ModelDao {
    private String id;
    private String membre;
    private Date date_debut;
    private Date date_fin;

    public Penalite(String id, String membre, Date date_debut, Date date_fin) {
        this.setId(id);
        this.setMembre(membre);
        this.setDate_debut(date_debut);
        this.setDate_fin(date_fin);
    }

    public Penalite() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMembre() {
        return membre;
    }

    public void setMembre(String membre) {
        this.membre = membre;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public void setDate_fin(int days) {
        Date new_date_fin = Date.valueOf(this.getDate_debut().toLocalDate().plusDays(days));
        this.setDate_fin(new_date_fin);
    }

    public boolean estEnCours() {
        Date currentDate = Date.valueOf(LocalDate.now());
        if (currentDate.after(this.getDate_debut()) && currentDate.before(this.getDate_fin())) {
            return true;
        }
        return false;
    }
}
