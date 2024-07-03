package com.tymqsz;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;



public class App 
{
    public static void main( String[] args )
    {
        WebDriver driver = new ChromeDriver();

        MovieExtractor movieExtractor = new MovieExtractor(driver);
        
        DataExtractor dataExtractor = new DataExtractor(driver, movieExtractor.getMovieList());

        //driver.quit();
    }

}
