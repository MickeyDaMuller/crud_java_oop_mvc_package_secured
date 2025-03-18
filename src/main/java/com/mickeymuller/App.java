package com.mickeymuller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Connected to the database successfully!");
        } else {
            System.err.println("Failed to establish a database connection.");
        }
    }

    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/mickeymuller_java?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "Opeyemioluwa_0316";
        Connection conn = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Add MySQL Connector/J to the classpath.---" + e);
        } catch (SQLException e) {
            System.err.println("Connection failed! Error: " + e.getMessage());
        }

        return conn; // Ensure the method always returns a Connection (even if null)
    }
}
