package com.tymqsz.selenium;

import java.io.*;
import java.util.List;


import java.util.ArrayList;
import java.util.Collections;


public class Database {
    

    private List<MovieRecord> imdbList, rtList, mcList, fullList;

    public Database(){
        fullList = new ArrayList<>();

        imdbList= new ArrayList<>();
        //rtList= new ArrayList<>();
        //mcList= new ArrayList<>();

        try{
            readData("imdb.txt", imdbList);
            //readData("rt.txt", rtList);
            //readData("mc.txt", mcList);
        }
        catch(IOException e){
            System.out.println(e);

            return;
        }
        //mergeData();
    }

    private void readData(String input_file, List<MovieRecord> list) throws IOException{
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

    public void mergeData(){
        Collections.sort(imdbList);
        Collections.sort(rtList);
        Collections.sort(mcList);

        for(int i = 0; i < 250; i++){
            System.out.println(imdbList.get(i));
        }
    }
}