package com.tymqsz.sql_server_connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Connection;

public class Connector {
    private Connection connection;
    private String dbURL = "jdbc:sqlserver://xxx:1433;trustServerCertificate=true";
    private String user = "test";
    private String pass = "test";
    
    public Connector(){
        try{
            connection = DriverManager.getConnection(dbURL, user, pass);
        }
        catch(SQLException e){
            System.out.println(e);
            return;
        }        
    }

    public ResultSet getTopMovies(int count, String genre, String director, String max_minutes_length){
        ResultSet resultSet = null;
        try{
            Statement statement = connection.createStatement();
            String query = String.format("SELECT TOP %d title, year, avgRating "
                                        +"FROM MOVIES "
                                        +"WHERE genre LIKE CONCAT('%%', '%s', '%%') AND "
                                        +"director LIKE CONCAT('%%', '%s', '%%') AND "
                                        +"[length (minutes)] < %s"
                                        +"ORDER BY avgRating DESC;", count, genre, director, max_minutes_length);
            resultSet = statement.executeQuery(query);            
        }
        catch(SQLException e){
            System.out.println(e);
        }

        return resultSet;
    }

    public ResultSet getTopMoviesOfDirector(int count, String director){
        ResultSet resultSet = null;
        try{
            Statement statement = connection.createStatement();
            String query = String.format("SELECT TOP %d title, avgRating "
                                        +"FROM MOVIES "
                                        +"WHERE director = '%s' "
                                        +"ORDER BY avgRating DESC;", count, director);
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
            }
            
        }
        catch(SQLException e){
            System.out.println(e);
        }

        return resultSet;
    }

    public ResultSet execQuery(String query){
        ResultSet resultSet = null;
        try{
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        }
        catch(SQLException e){
            System.out.println(e);
        }

        return resultSet;
    }
}
