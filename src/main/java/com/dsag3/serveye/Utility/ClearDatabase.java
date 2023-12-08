package com.dsag3.serveye.Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ClearDatabase {
    private static final String DB_URL = "jdbc:mysql://database-1.cl85pye4up69.ap-southeast-1.rds.amazonaws.com:3306/alpha_test";
    private static final String USER = "sf_app_admin";
    private static final String PASSWORD = "arfarfwoofwoofmeow";
    public static void clear() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            String sql = "DELETE FROM alpha_table";
            statement.executeUpdate(sql);
            System.out.println("Deletion complete.");

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting records from alpha_table", e);
        }
    }
}
