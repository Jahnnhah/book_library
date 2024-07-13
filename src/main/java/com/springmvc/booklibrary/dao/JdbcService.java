package com.springmvc.booklibrary.dao;

import org.springframework.jdbc.core.RowMapper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcService {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/book_library";
            String user = "postgres";
            String password = "root";
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return con;
    }

    public static List query(Connection con, String sql, Object[] values, RowMapper rowMapper) {
        List results = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = con.prepareStatement(sql);

            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    preparedStatement.setObject(i + 1, values[i]);
                }
            }

            resultSet = preparedStatement.executeQuery();
            int rowNum = 0;
            while (resultSet.next()) {
                results.add(rowMapper.mapRow(resultSet, rowNum++));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return results;

    }
    public static List query(Connection con, String sql, RowMapper rowMapper) {
        List<Object> result = new ArrayList();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = con.createStatement();
            resultSet = statement.executeQuery(sql);
            int rowNum = 0;
            while (resultSet.next()) {
                result.add(rowMapper.mapRow(resultSet, rowNum++));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;

    }
    public static Object query(Connection con, String sql) {
        Object result = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = con.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                result = resultSet.getObject(1);  // Retrieves the first column's value
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static int update(Connection con, String sql, Object[] values) {
        PreparedStatement preparedStatement = null;
        int affectedRows = 0;

        try {
            preparedStatement = con.prepareStatement(sql);

            for (int i = 0; i < values.length; i++) {
                preparedStatement.setObject(i + 1, values[i]);
            }

            affectedRows = preparedStatement.executeUpdate();        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return affectedRows;

    }

}
