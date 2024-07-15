package com.tymqsz;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import com.tymqsz.selenium.*;



public class App 
{
    public static void main( String[] args )
    {
        WebDriver driver = new ChromeDriver();

        /* extract top 250 movie titles */
        TitleExtractor movieExtractor = new TitleExtractor(driver);
        
        /* try to extract movie data from .txt files,
         * if data incomplete - extract from websites
         */
        DataExtractor dataExtractor = new DataExtractor(driver, movieExtractor.getMovieList());
        Database database = new Database();
        try{
            database.readData(Website.IMDB);
            database.readData(Website.RT);
            database.readData(Website.MC);
        }
        catch(IOException e1){
            System.out.println("e1: " + e1);

            dataExtractor.extractIMDB();
            dataExtractor.extractRT();
            dataExtractor.extractMC();

            /* try reading extracted data from file again */
            try{
                database.readData(Website.IMDB);
                database.readData(Website.RT);
                database.readData(Website.MC);
            }
            catch(IOException e2){
                System.out.println("e2: " + e2);

                return;
            }   
        }
        
        List<MovieRecord> mcList = database.getMcList();
        Collections.sort(mcList);
        
        int wrongCnt = 0;
        for(int i = 0; i < 250; i++){
            int nullCnt = 0;
            String str = mcList.get(i).toString();
            for(int j = 0; j < str.length()-4; j++){
                String sub = str.substring(j, j+4);

                if(sub.equals("null")){
                    nullCnt++;
                }
            }

            if(nullCnt == 11){
                wrongCnt++;
                System.out.println(str.substring(0, 20));
            }

        }

        System.out.println(wrongCnt);
        driver.quit();
    }

}
