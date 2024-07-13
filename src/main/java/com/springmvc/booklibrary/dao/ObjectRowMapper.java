package com.springmvc.booklibrary.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;
import java.sql.SQLException;

public class ObjectRowMapper implements RowMapper {
    private Class objectClass;
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {

            // malaka anle constructor
            Constructor[] classConstructors = this.getObjectClass().getConstructors();
            Constructor constructor = classConstructors[0];
            for (int i = 0; i < classConstructors.length; i++) {
                if (classConstructors[i].getParameterCount() == 0) {
                    constructor = classConstructors[i];
                }
            }

            // mamorona anle instance de mi set ny valeur amin'ny ireo fields
            Object instance = constructor.newInstance();
            Field[] fields = this.getObjectClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = rs.getObject(field.getName());
                field.set(instance, value);
            }
            Field[] parent_fields = this.getObjectClass().getSuperclass().getDeclaredFields();
            if (parent_fields != null) {
                for (Field field : parent_fields) {
                    field.setAccessible(true);
                    Object value = rs.getObject(field.getName());
                    field.set(instance, value);
                }
            }

            return instance;

        } catch (Exception e) {
            throw new SQLException("Failed to map row to instance of " + this.getObjectClass().getName(), e);
        }
    }

    public ObjectRowMapper(Class objectClass) {
        this.setObjectClass(objectClass);
    }

    public Class getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(Class objectClass) {
        this.objectClass = objectClass;
    }
}
