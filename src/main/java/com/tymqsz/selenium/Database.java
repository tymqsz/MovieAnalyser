package com.tymqsz.selenium;

import java.io.*;
import java.util.List;


import java.util.ArrayList;


public class Database {
    
    private List<MovieRecord> imdbList, rtList, mcList;

    public Database(){
        imdbList= new ArrayList<>();
        rtList= new ArrayList<>();
        mcList= new ArrayList<>();
    }

    public void readData(Website website) throws IOException{
        String input_file;
        List<MovieRecord> list;
        switch (website) {
            case IMDB:
                input_file = "imdb.txt";
                list = imdbList;
                break;
            case RT:
                input_file = "rt.txt";
                list = rtList;
                break;
            case MC:
                input_file = "mc.txt";
                list = mcList;
                break;
            default:
                throw new IOException("improper website");
        }

        InputStream stream = Database.class.getResourceAsStream("/"+input_file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        if(stream == null || reader == null)
            throw new IOException("InputStreamException");
        
        String line;
        String[] split;
        MovieRecord record;
        while((line = reader.readLine()) != null){
            split = line.split(";");

            if(split.length != 12)
                throw new IOException(String.format("Improper data structure at line:\n %s", line));

            record = new MovieRecord.Builder()
                                    .setTitle(split[0])
                                    .setYear(split[1])
                                    .setGenre(split[2])
                                    .setLength(split[3])
                                    .setImdbRating(split[4])
                                    .setImdbMetaRating(split[5])
                                    .setRtRating(split[6])
                                    .setRtMetaRating(split[7])
                                    .setMcRating(split[8])
                                    .setMcMetaRating(split[9])
                                    .setDirector(split[10])
                                    .setCast(split[11])
                                    .build();
            
            list.add(record);
        }
        
        if(list.size() != 250)
            throw new IOException(String.format("Incorrect data size: %d", list.size()));
    }

    public List<MovieRecord> getImdbList(){
        return imdbList;
    }

    public List<MovieRecord> getRtList(){
        return rtList;
    }

    public List<MovieRecord> getMcList(){
        return mcList;
    }
}