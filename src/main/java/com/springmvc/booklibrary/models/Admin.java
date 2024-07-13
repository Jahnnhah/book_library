package com.springmvc.booklibrary.models;

import com.springmvc.booklibrary.annotations.Mapping;
import com.springmvc.booklibrary.dao.ModelDao;

import java.sql.Connection;
import java.sql.SQLException;

@Mapping(table_name = "admin", id_preffix = "ADM", sequence_name = "admin_seq")
public class Admin extends ModelDao {
    private String id;
    private String email;
    private String password;

    public Admin() {
    }

    public Admin(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String authenticate(Connection con) throws SQLException {
        Admin admin = (Admin) this.get(con);
        if (admin != null) {
            return admin.getId();
        }
        return null;
    }

    public boolean isAdmin(Connection con) throws SQLException {
        this.setPassword(null);
        this.setId(null);
        Admin admin = (Admin) this.get(con);
        if (admin == null) {
            return false;
        }
        return true;
    }
}
