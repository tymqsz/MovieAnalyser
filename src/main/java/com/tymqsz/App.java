package com.tymqsz;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import com.tymqsz.selenium.DataExtractor;
import com.tymqsz.selenium.Database;
import com.tymqsz.selenium.TitleExtractor;



public class App 
{
    public static void main( String[] args )
    {
        WebDriver driver = new ChromeDriver();

        TitleExtractor movieExtractor = new TitleExtractor(driver);
        
        DataExtractor dataExtractor = new DataExtractor(driver, movieExtractor.getMovieList());

        //Database db = new Database();
        
        //driver.quit();
    }

}
