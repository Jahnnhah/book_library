package com.springmvc.booklibrary.models;

import com.springmvc.booklibrary.annotations.Mapping;
import com.springmvc.booklibrary.dao.JdbcService;
import com.springmvc.booklibrary.dao.ModelDao;
import com.springmvc.booklibrary.dao.ObjectRowMapper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Mapping(table_name = "emprunt", id_preffix = "EMP", sequence_name = "emprunt_seq")
public class Emprunt extends ModelDao {
    private String id;
    private String exemplaire;
    private String membre;
    private Date date_emprunt;
    private Date date_echeance;
    private Date date_rendu;

    public Emprunt() {}

    public Emprunt(String exemplaire, String membre, Date date_emprunt, Date date_echeance, Date date_rendu) {
        this.setExemplaire(exemplaire);
        this.setMembre(membre);
        this.setDate_emprunt(date_emprunt);
        this.setDate_echeance(date_echeance);
        this.setDate_rendu(date_rendu);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExemplaire() {
        return exemplaire;
    }

    public void setExemplaire(String exemplaire) {
        this.exemplaire = exemplaire;
    }

    public String getMembre() {
        return membre;
    }

    public void setMembre(String membre) {
        this.membre = membre;
    }

    public Date getDate_emprunt() {
        return date_emprunt;
    }

    public void setDate_emprunt(Date date_emprunt) {
        this.date_emprunt = date_emprunt;
    }

    public Date getDate_echeance() {
        return date_echeance;
    }

    public void setDate_echeance(Date date_echeance) {
        this.date_echeance = date_echeance;
    }

    public Date getDate_rendu() {
        return date_rendu;
    }

    public void setDate_rendu(Date date_rendu) {
        this.date_rendu = date_rendu;
    }

    public void setDate_echeance(int days) {
        Date new_date_echeance = Date.valueOf(this.getDate_emprunt().toLocalDate().plusDays(days));
        this.setDate_echeance(new_date_echeance);
    }

    public int save(Connection con, RegleEmprunt regleEmprunt) throws SQLException {
        int days = regleEmprunt.getLimite_retard();
        this.setDate_echeance(days);
        return super.save(con);
    }

    public static Emprunt[] getNonRendu(Connection con) {
        Emprunt[] result = new Emprunt[0];
        List emprunts = JdbcService.query(con, "SELECT * FROM emprunt WHERE date_rendu IS NULL", new ObjectRowMapper(Emprunt.class));
        if (emprunts.size() > 0) {
            result = (Emprunt[]) emprunts.toArray(new Emprunt[0]);
        }
        return result;
    }

    public boolean isPenalized() {
        return this.getDate_rendu().after(this.getDate_echeance());
    }

    public int getJourRetard() {
        return (int) ChronoUnit.DAYS.between(this.getDate_echeance().toLocalDate(), this.getDate_rendu().toLocalDate());
    }
}
