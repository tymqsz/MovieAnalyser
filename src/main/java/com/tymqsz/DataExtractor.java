package com.tymqsz;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.*;

public class DataExtractor {
    private WebDriver driver;
    private List<String> movies;
    private List<MovieData> IMDBdata, RTdata, MCdata;

    public DataExtractor(WebDriver driver, List<String> movieList){
        this.driver = driver;
        movies = movieList;

        IMDBdata = new ArrayList<>();
        RTdata = new ArrayList<>();
        MCdata = new ArrayList<>();

        //extractIMDB();
        
        extractRT();
        
        //extractMC();
    }

    private void extractIMDB(){
        driver.manage().window().maximize();
        driver.get("https://www.imdb.com/");
        clickElement(byText("Decline"), Duration.ofSeconds(3));

        for(String movie: movies){
            try{
                /* search and click movie title */
                writeElement(By.name("q"), movie, Duration.ofSeconds(5));
                clickElement(byText("Exact matches"), Duration.ofSeconds(2));
                clickElement(byText(movie), Duration.ofSeconds(5));
                
                
                String year = readElement(By.xpath("//*[@id='__next']/main/div/section[1]/section/"
                                                    +"div[3]/section/section/div[2]/div[1]/ul/li[1]/a"), Duration.ofSeconds(5));
                String length = readElement(By.xpath("//*[@id='__next']/main/div/section[1]/section/"
                                                    +"div[3]/section/section/div[2]/div[1]/ul/li[3]"), Duration.ofSeconds(5));
        
                String rating = readElement(By.xpath("//*[@id=\"__next\"]/main/div/section[1]/section/div[3]/section/"
                                                    +"section/div[2]/div[2]/div/div[1]/a/span/div/div[2]/div[1]/span[1]"), Duration.ofSeconds(5));
                String genre = readElement(By.xpath("//*[@id=\"__next\"]/main/div/section[1]/section/"
                                                   +"div[3]/section/section/div[3]/div[2]/div[1]/section/div[1]"), Duration.ofSeconds(5));
                String director = readElement(By.xpath("//*[@id=\"__next\"]/main/div/section[1]/section"
                                                      +"/div[3]/section/section/div[3]/div[2]/div[1]/section/div[2]/div/ul/li[1]/div"), Duration.ofSeconds(5));
                String metaRating = readElement(By.xpath("//*[@id=\"__next\"]/main/div/section[1]/section/div[3]/section/"+
                                                         "section/div[3]/div[2]/div[2]/ul/li[3]/a/span/span[1]/span"), Duration.ofSeconds(5));
                String cast = readElement(By.xpath("//*[@id=\"__next\"]/main/div/section[1]/section/div[3]/section/"
                                                  +"section/div[3]/div[2]/div[1]/section/div[2]/div/ul/li[3]/div"), Duration.ofSeconds(5));

                MovieData crtData = new MovieData.Builder()
                                    .setName(movie)
                                    .setCast(cast)
                                    .setDirector(director)
                                    .setGenre(genre)
                                    .setRating(rating)
                                    .setMetarating(metaRating)
                                    .setLength(length)
                                    .setYear(year)
                                    .build();
                
                IMDBdata.add(crtData);                                    
            }
            catch(Exception e){
                System.out.println(String.format("exception during extracting \'%s\': %s", movie, e));
                
                /* add empty movie record */
                MovieData crtData = new MovieData.Builder().setName(movie).build();

                IMDBdata.add(crtData);
            }
                          
        }
        
    }

    private void extractRT(){
        driver.get("https://www.rottentomatoes.com/");
        clickElement(byText("Reject All"), Duration.ofSeconds(5));

        for(String movie: movies){
            /* search movie title */
            writeElement(By.xpath("//*[@aria-label='Search']"), movie, Duration.ofSeconds(5));
                    
            /* click first result */
            clickElement(By.xpath("//*[@id=\"search-results\"]/search-page-result[1]/"
                                                +"ul/search-page-media-row/a[2]"), Duration.ofSeconds(5));

            String rating = readElement(By.xpath("//*[@id=\"modules-wrap\"]/div[1]/media-scorecard/rt-button[2]/rt-text"), Duration.ofSeconds(5));
            String audienceRating = readElement(By.xpath("//*[@id=\"modules-wrap\"]/div[1]/media-scorecard/rt-button[4]/rt-text"), Duration.ofSeconds(5));

            MovieData crtData = new MovieData.Builder().setRating(rating) 
                                                    .setMetarating(audienceRating)
                                                    .build();

            RTdata.add(crtData);
        }
        
    }
    
    private void extractMC(){
        driver.get("https://www.metacritic.com/");
    }
    
    private void clickElement(By to_click, Duration time){
        ExpectedCondition<WebElement> ec = ExpectedConditions.presenceOfElementLocated(to_click);
        WebElement element = new WebDriverWait(driver, time).until(ec);

        element.click();
    }

    private String readElement(By to_find, Duration time){
        ExpectedCondition<WebElement> ec = ExpectedConditions.presenceOfElementLocated(to_find);
        WebElement element = new WebDriverWait(driver, time).until(ec);

        return element.getText();
    }

    private void writeElement(By to_find, String to_write, Duration time){
        ExpectedCondition<WebElement> ec = ExpectedConditions.presenceOfElementLocated(to_find);
        WebElement element = new WebDriverWait(driver, time).until(ec);

        element.sendKeys(to_write);
        element.sendKeys(Keys.RETURN);
    }
    
    private By byText(String text){
        return By.xpath(String.format("//*[contains(text(),\"%s\")]", text));
    }

}
