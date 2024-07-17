package com.tymqsz.data_extraction;

import java.io.IOException;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;



public class Main 
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
        
        driver.quit();
    }

}
