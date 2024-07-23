package com.tymqsz.gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.tymqsz.sql_server_connection.Connector;
public class FilterVerifier {
    private Set<String> directorSet;

    private Connector connector;

    public FilterVerifier(Connector connector){
        this.connector = connector;

        directorSet = new HashSet<>();
        extractDirectorSet();
    }

    private void extractDirectorSet(){
        ResultSet result = connector.execQuery("SELECT director FROM MOVIES;");

        try{
            while(result.next()){
                directorSet.add(result.getString(1));
            }
        }
        catch(SQLException e){
            System.out.println(e);
            return;
        }
    }

    public boolean directorOK(String director){
        return directorSet.contains(director) || director.equals("");
    }

    public boolean lengthOK(String length){
        if(length.equals("")) return true;
        try{
            Integer value = Integer.parseInt(length);
            if(value < 0) return false;
        }
        catch(NumberFormatException e){
            return false;
        }
        return true;
    } 

    public boolean countOK(String count){
        if(count.equals("")) return false;
        return true;
    } 

    
}