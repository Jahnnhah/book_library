package com.springmvc.booklibrary.models;

import com.springmvc.booklibrary.annotations.Mapping;
import com.springmvc.booklibrary.dao.JdbcService;
import com.springmvc.booklibrary.dao.ObjectRowMapper;
import com.springmvc.booklibrary.modelsAffichage.LivreAffichage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivreSearch extends Livre{
    private Date date_edition_min;
    private Date date_edition_max;
    private Integer tome_min;
    private Integer tome_max;
    private Integer nombre_pages_min;
    private Integer nombre_pages_max;

    public Date getDate_edition_min() {
        return date_edition_min;
    }

    public void setDate_edition_min(Date date_edition_min) {
        this.date_edition_min = date_edition_min;
    }

    public Date getDate_edition_max() {
        return date_edition_max;
    }

    public void setDate_edition_max(Date date_edition_max) {
        this.date_edition_max = date_edition_max;
    }

    public Integer getTome_min() {
        return tome_min;
    }

    public void setTome_min(Integer tome_min) {
        this.tome_min = tome_min;
    }

    public Integer getTome_max() {
        return tome_max;
    }

    public void setTome_max(Integer tome_max) {
        this.tome_max = tome_max;
    }

    public Integer getNombre_pages_min() {
        return nombre_pages_min;
    }

    public void setNombre_pages_min(Integer nombre_pages_min) {
        this.nombre_pages_min = nombre_pages_min;
    }

    public Integer getNombre_pages_max() {
        return nombre_pages_max;
    }

    public void setNombre_pages_max(Integer nombre_pages_max) {
        this.nombre_pages_max = nombre_pages_max;
    }

    public LivreAffichage[] search(Connection con) throws SQLException {
        try {
            if (con == null) {
                return new LivreAffichage[0];
            }

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM v_livre").append(" WHERE ");
            if (this.getId() != null) {
                sql.append(" id ilike '%").append(this.getId()).append("%' ");
                sql.append(" and ");
            }

            if (this.getTitre() != null) {
                sql.append(" titre ilike '%").append(this.getTitre()).append("%' ");
                sql.append(" and ");
            }

            if (this.getAuteur() != null) {
                sql.append(" auteur_nom ilike '%").append(this.getAuteur()).append("%' ");
                sql.append(" and ");
            }

            if (this.getIsbn() != null) {
                sql.append(" isbn ilike '%").append(this.getIsbn()).append("%' ");
                sql.append(" and ");
            }

            if (this.getNumero_cote() != null) {
                sql.append(" numero_cote ilike '%").append(this.getNumero_cote()).append("%' ");
                sql.append(" and ");
            }

            if (this.getEditeur() != null) {
                sql.append(" editeur_designation ilike '%").append(this.getEditeur()).append("%' ");
                sql.append(" and ");
            }

            if (this.getDate_edition() != null) {
                sql.append(" date_edition = '").append(this.getDate_edition()).append("' ");
                sql.append(" and ");
            } else {
                if (this.getDate_edition_min() != null) {
                    sql.append(" date_edition > '").append(this.getDate_edition_min()).append("' ");
                    sql.append(" and ");
                }
                if (this.getDate_edition_max() != null) {
                    sql.append(" date_edition < '").append(this.getDate_edition_max()).append("' ");
                    sql.append(" and ");
                }
            }

            if (this.getTome() != null) {
                sql.append(" tome = ").append(this.getTome());
                sql.append(" and ");
            } else {
                if (this.getTome_min() != null) {
                    sql.append(" tome > ").append(this.getTome_min());
                    sql.append(" and ");
                }
                if (this.getTome_max() != null) {
                    sql.append(" tome < ").append(this.getTome_max());
                    sql.append(" and ");
                }
            }

            if (this.getCollection() != null) {
                sql.append(" collection_designation ilike '%").append(this.getCollection()).append("%' ");
                sql.append(" and ");
            }

            if (this.getLangue() != null) {
                sql.append(" langue_designation ilike '%").append(this.getLangue()).append("%' ");
                sql.append(" and ");
            }

            if (this.getNombre_pages() != null) {
                sql.append(" tome = ").append(this.getNombre_pages());
                sql.append(" and ");
            } else {
                if (this.getNombre_pages_min() != null) {
                    sql.append(" tome > ").append(this.getNombre_pages_min());
                    sql.append(" and ");
                }
                if (this.getNombre_pages_max() != null) {
                    sql.append(" tome < ").append(this.getNombre_pages_max());
                    sql.append(" and ");
                }
            }

            sql.setLength(sql.length() - 4);
            System.out.println(sql.toString());

            List list = JdbcService.query(con, sql.toString(), new ObjectRowMapper(LivreAffichage.class));
            LivreAffichage[] result = new LivreAffichage[list.size()];
            for (int i = 0; i < list.size(); i++) {
                result[i] = (LivreAffichage) list.get(i);
            }
            return result;

        } catch (Exception e) {
            throw new SQLException("erreur eo amle recherche livre", e);
        }

    }

}
