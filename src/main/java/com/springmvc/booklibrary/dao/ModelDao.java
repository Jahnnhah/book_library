package com.springmvc.booklibrary.dao;

import com.springmvc.booklibrary.annotations.Mapping;
import com.springmvc.booklibrary.models.*;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ModelDao {

    public ModelDao() {
    }

    private void addOrder(String sql) {
        if (this instanceof Penalite) {
            sql = sql + " order by date_fin desc";
        }
        if (this instanceof Emprunt) {
            sql = sql + " order by date_emprunt";
        }
        if (this instanceof Livre || this instanceof Exemplaire || this instanceof Membre) {
            sql = sql + " order by id";
        }
    }

    public boolean isEmpty() throws IllegalAccessException {
        Field[] parentFields = null;
        if (this.getClass().getSuperclass() != null) {
            parentFields = this.getClass().getSuperclass().getDeclaredFields();
            for (Field field : parentFields) {
                field.setAccessible(true);
                if (field.get(this) != null) {
                    return false;
                }
            }
        }

        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(this) != null) {
                return false;
            }
        }
        return true;
    }

    public List<Object> findAll(Connection con) {
        String sql = "SELECT * FROM " + this.getClass().getAnnotation(Mapping.class).table_name();
        addOrder(sql);
        return JdbcService.query(con, sql, null, new ObjectRowMapper(this.getClass()));
    }

    public List<Object> find(Connection con) throws SQLException {
        try {
            if (con == null) {
                return new ArrayList<>();
            }

            Field[] fields = this.getClass().getDeclaredFields();
            Field[] parent_fields = this.getClass().getSuperclass().getDeclaredFields();
            List<Field> setted_fields = new ArrayList<>();
            List<Field> parent_setted_fields = new ArrayList<>();
            List<Object> values = new ArrayList<>();
            StringBuilder sql = new StringBuilder();

            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(this) != null) {
                    setted_fields.add(field);
                }
            }
            for (Field field : parent_fields) {
                field.setAccessible(true);
                if (field.get(this) != null) {
                    parent_setted_fields.add(field);
                }
            }

            sql.append("SELECT * FROM ").append(this.getClass().getAnnotation(Mapping.class).table_name()).append(" WHERE ");
            for (int i = 0; i < setted_fields.size(); i++) {
                setted_fields.get(i).setAccessible(true);
                sql.append(setted_fields.get(i).getName()).append(" = ? AND ");
                values.add(setted_fields.get(i).get(this));
            }
            for (int i = 0; i < parent_setted_fields.size(); i++) {
                parent_setted_fields.get(i).setAccessible(true);
                sql.append(parent_setted_fields.get(i).getName()).append(" = ? AND ");
                values.add(parent_setted_fields.get(i).get(this));
            }
            sql.setLength(sql.length() - 4); // manala ny "AND " am farany

            String finalSql = sql.toString();
            addOrder(finalSql);
            System.out.println(finalSql);
            return JdbcService.query(con, finalSql, values.toArray(), new ObjectRowMapper(this.getClass()));

        } catch (Exception e) {
            throw new SQLException("erreur eo amle find" + this.getClass().getName(), e);
        }
    }

    public Object get(Connection con) throws SQLException {
        try {
            if (con == null) {
                throw new SQLException("connection null");
            }

            List<Object> all = this.find(con);
            if (all.size() > 0) {
                return all.get(0);
            }
            return null;

        } catch (Exception e) {
            throw new SQLException("erreur eo amle get" + this.getClass().getName(), e);
        }
    }

    public int save(Connection con) throws SQLException {
        try {
            if (con == null) {
                return 0;
            }

            Field[] fields = this.getClass().getDeclaredFields();
            List<Object> values = new ArrayList<>();
            StringBuilder sql = new StringBuilder();

            Field idField = null;
            for (Field field : fields) {
                if (field.getName().equalsIgnoreCase("id")) {
                    idField = field;
                    break;
                }
            }
            String id_value = "CONCAT('"+ this.getClass().getAnnotation(Mapping.class).id_preffix() +"', LPAD(CAST(NEXTVAL('"+ this.getClass().getAnnotation(Mapping.class).sequence_name() +"') AS TEXT), 3, '0'))";

            if (idField != null) {
                idField.setAccessible(true);
                Object idValue = idField.get(this);

                if (idValue == null) { // INSERT
                    sql.append("INSERT INTO ").append(this.getClass().getAnnotation(Mapping.class).table_name()).append(" (");
                    for (Field field : fields) {
                        field.setAccessible(true);
                        sql.append(field.getName()).append(", ");
                        if (!field.equals(idField)) {
                            values.add(field.get(this));
                        }
                    }
                    sql.setLength(sql.length() - 2); // manala ny ", " am farany
                    sql.append(") VALUES (");
                    sql.append(id_value);
                    sql.append(", ");
                    sql.append("?, ".repeat(values.size()));
                    sql.setLength(sql.length() - 2);
                    sql.append(")");

                } else { // UPDATE rehefa tsy misy ny ao amn id
                    sql.append("UPDATE ").append(this.getClass().getAnnotation(Mapping.class).table_name()).append(" SET ");
                    for (Field field : fields) {
                        if (!field.equals(idField)) {
                            field.setAccessible(true);
                            sql.append(field.getName()).append(" = ?, ");
                            values.add(field.get(this));
                        }
                    }
                    sql.setLength(sql.length() - 2);
                    sql.append(" WHERE ").append(idField.getName()).append(" = ?");
                    values.add(idValue);
                }

            } else {
                throw new SQLException("No field named 'id' found in class " + this.getClass().getName());
            }

            System.out.println(sql.toString());

            return JdbcService.update(con, sql.toString(), values.toArray());
        } catch (Exception e) {
            throw new SQLException("Failed to save instance of " + this.getClass().getName(), e);
        }
    }

    public int delete(Connection con) throws SQLException {
        try {
            if (con == null) {
                return 0;
            }

            Field[] fields = this.getClass().getDeclaredFields();

            Field idField = null;
            for (Field field : fields) {
                if (field.getName().equalsIgnoreCase("id")) {
                    idField = field;
                    break;
                }
            }
            if (idField != null) {
                idField.setAccessible(true);
                Object[] values = new Object[1];
                values[0] = idField.get(this).toString();
                String sql = "DELETE FROM " + this.getClass().getAnnotation(Mapping.class).table_name() + " WHERE id = ?";
                return JdbcService.update(con, sql, values);
            }
            return 0;
        } catch (Exception e) {
            throw new SQLException("Tsy vaofafa le " + this.getClass().getName());
        }
    }

}