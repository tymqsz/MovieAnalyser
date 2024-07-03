package com.tymqsz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
    public static void main(String[] args) {
        // Database URL
        String url = "jdbc:sqlserver://192.168.0.109\\SQLEXPRESS:1433;databaseName=master;trustServerCertificate=true";
        // Username
        String username = "remote";
        // Password
        String password = "niggerballs";

        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to SQL Server successfully.");

            statement = connection.createStatement();
            String query = "SELECT * FROM dbo.TEST";
            resultSet = statement.executeQuery(query);

            // Process the result set
            while (resultSet.next()) {
                // Retrieve data by column name
                int id = resultSet.getInt("a");
                System.out.println("ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }
}
