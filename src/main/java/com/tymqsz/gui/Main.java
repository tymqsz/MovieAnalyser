package com.tymqsz.gui;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.tymqsz.sql_server_connection.Connector;

public class Main {
    public static void main(String[] args) {
        Connector connector = new Connector();

        ResultSet result; /* = connector.getTopMovies(3, "Comedy"); */
        /*try{
            while(result.next()){
                System.out.println(result.getString(1) + " " + result.getString(2));
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }*/

        result = connector.getTopMoviesOfDirector(3, "Christopher Nolan");
        try{
            while(result.next()){
                System.out.println(result.getString(1) + " " + result.getString(2));
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        
    }
}
