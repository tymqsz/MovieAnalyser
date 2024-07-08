package com.tymqsz.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;
import java.io.*;


public class DataExtractor {
    private WebDriver driver;
    private List<String> movies;
    private List<MovieRecord> IMDBdata, RTdata, MCdata;

    public DataExtractor(WebDriver driver, List<String> movieList){
        this.driver = driver;
        movies = movieList;

        IMDBdata = new ArrayList<>();
        RTdata = new ArrayList<>();
        MCdata = new ArrayList<>();

        //extractIMDB();
        
        //extractRT();
        
        extractMC();
    }

    private void extractIMDB(){
        driver.manage().window().maximize();
        driver.get("https://www.imdb.com/");
        clickElement(byText("Decline"), Duration.ofSeconds(3));

        BufferedWriter writer = null;
        try{
            File output = new File("/home/tymqsz/movie_analyser/src/main/resources/imdb.txt");
            writer = new BufferedWriter(new FileWriter(output));
        }
        catch(IOException e){
            System.out.println(e);
        }

        for(String movie: movies){
            try{
                /* search and click movie title */
                writeElement(By.name("q"), movie, Duration.ofSeconds(5));
                clickElement(byText("Exact matches"), Duration.ofSeconds(2));
                clickElement(byText(movie), Duration.ofSeconds(5));
                
                String year;
                try{
                    year = readElement(By.xpath("//*[@id='__next']/main/div/section[1]/section/"
                                                    +"div[3]/section/section/div[2]/div[1]/ul/li[1]/a"), Duration.ofSeconds(3));
                }
                catch(TimeoutException | NoSuchElementException e){
                    year = null;
                }

                String length;
                try{
                    length = readElement(By.xpath("//*[@id='__next']/main/div/section[1]/section/"
                                                    +"div[3]/section/section/div[2]/div[1]/ul/li[3]"), Duration.ofSeconds(3));
                }
                catch(TimeoutException | NoSuchElementException e){
                    try{
                        /* may be different setup */
                        length = readElement(By.xpath("//*[@id='__next']/main/div/section[1]/section/"
                                                    +"/div[3]/section/section/div[2]/div[1]/ul/li[2]"), Duration.ofSeconds(3)); 
                    }
                    catch(TimeoutException | NoSuchElementException e1){
                        length = null;
                    }
                }

                String rating;
                try{
                    rating = readElement(By.xpath("//*[@id=\"__next\"]/main/div/section[1]/section/div[3]/section/"
                                                    +"section/div[2]/div[2]/div/div[1]/a/span/div/div[2]/div[1]/span[1]"), Duration.ofSeconds(5));
                }
                catch(TimeoutException | NoSuchElementException e){
                    rating = null;
                }

                String genre;
                try{
                    genre = readElement(By.xpath("//*[@id=\"__next\"]/main/div/section[1]/section/"
                                                   +"div[3]/section/section/div[3]/div[2]/div[1]/section/div[1]"), Duration.ofSeconds(5));
                }
                catch(TimeoutException | NoSuchElementException e){
                    genre = null;
                }

                String director;
                try{
                    director = readElement(By.xpath("//*[@id=\"__next\"]/main/div/section[1]/section"
                                                      +"/div[3]/section/section/div[3]/div[2]/div[1]/section/div[2]/div/ul/li[1]/div"), Duration.ofSeconds(5));
                }
                catch(TimeoutException | NoSuchElementException e){
                    director = null;
                }

                String metaRating;
                try{
                    metaRating = readElement(By.xpath("//*[@id=\"__next\"]/main/div/section[1]/section/div[3]/section/"+
                                                         "section/div[3]/div[2]/div[2]/ul/li[3]/a/span/span[1]/span"), Duration.ofSeconds(5));
                }
                catch(TimeoutException | NoSuchElementException e){
                    metaRating = null;
                }

                String cast;
                try{
                    cast = readElement(By.xpath("//*[@id=\"__next\"]/main/div/section[1]/section/div[3]/section/"
                                                  +"section/div[3]/div[2]/div[1]/section/div[2]/div/ul/li[3]/div"), Duration.ofSeconds(5));
                }
                catch(TimeoutException | NoSuchElementException e){
                    cast = null;
                }

                MovieRecord crtData = new MovieRecord.Builder()
                                    .setTitle(movie)
                                    .setCast(cast)
                                    .setDirector(director)
                                    .setGenre(genre)
                                    .setImdbRating(rating)
                                    .setImdbMetaRating(metaRating)
                                    .setLength(length)
                                    .setYear(year)
                                    .build();
                
                IMDBdata.add(crtData);   
                crtData.dump(writer);                                 
            }
            catch(Exception e){
                System.out.println(String.format("exception during IMDB extracting \'%s\': %s", movie, e));
                
                /* add empty movie record */
                MovieRecord crtData = new MovieRecord.Builder().setTitle(movie).build();

                IMDBdata.add(crtData);
            }
                          
        }
        try{
            writer.close();
        }
        catch(IOException e){}
        
    }

    private void extractRT(){
        driver.manage().window().maximize();
        driver.get("https://www.rottentomatoes.com/");

        /* try to reject cookies (not mandatory) */
        try{
            clickElement(byText("Reject All"), Duration.ofSeconds(3));
        }
        catch(NoSuchElementException | TimeoutException e){
            System.out.println(e);
        }

        BufferedWriter writer = null;
        try{
            File output = new File("/home/tymqsz/movie_analyser/src/main/resources/rt.txt");
            writer = new BufferedWriter(new FileWriter(output));
        }
        catch(IOException e){
            System.out.println(e);
        }

        for(String movie: movies){
            try{
                /* search movie title */
                writeElement(By.xpath("//*[@aria-label='Search']"), movie, Duration.ofSeconds(5));
                        
                /* click first result */
                clickElement(By.xpath("//*[@id=\"search-results\"]/search-page-result[1]/"
                                                    +"ul/search-page-media-row/a[2]"), Duration.ofSeconds(5));

                String rating = readElement(By.xpath("//*[@id=\"modules-wrap\"]/div[1]/media-scorecard/rt-button[2]/rt-text"), Duration.ofSeconds(5));
                String audienceRating = readElement(By.xpath("//*[@id=\"modules-wrap\"]/div[1]/media-scorecard/rt-button[4]/rt-text"), Duration.ofSeconds(5));

                MovieRecord crtData = new MovieRecord.Builder().setTitle(movie)
                                                               .setRtRating(rating) 
                                                               .setRtMetaRating(audienceRating)
                                                               .build();

                RTdata.add(crtData);
                
                crtData.dump(writer);
            }
            catch(Exception e){
                System.out.println(String.format("exception during RT extracting \'%s\': %s", movie, e));
                
                /* add empty movie record */
                MovieRecord crtData = new MovieRecord.Builder().setTitle(movie).build();

                RTdata.add(crtData);

                crtData.dump(writer);
            }
        }
        try{
            writer.close();
        }
        catch(IOException e){}
        
    }
    
    private void extractMC(){
        driver.get("https://www.metacritic.com/");
        driver.manage().window().maximize();
        clickElement(byText("Reject All"), Duration.ofSeconds(3));

        BufferedWriter writer = null;
        try{
            File output = new File("/home/tymqsz/movie_analyser/src/main/resources/mc.txt");
            writer = new BufferedWriter(new FileWriter(output));
        }
        catch(IOException e){
            System.out.println(e);
        }

        for(String movie: movies){
            try{
                writeElement(By.xpath("//input[@placeholder='Search']"),
                             movie, Duration.ofSeconds(3));
                clickElement(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[2]/div[2]/div[1]/a/div[2]/p"), Duration.ofSeconds(3));

                String metaRating = readElement(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[2]/"
                                                    +"div[3]/div[2]/div[1]/div/div[1]/div[2]/div/div/span"), Duration.ofSeconds(3));
                String rating = readElement(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[2]/div[1]/div/div/div[2]/"
                                                        +"div[3]/div[2]/div[2]/div[1]/div[2]/div/div/span"),  Duration.ofSeconds(3));              
                                                        
                MovieRecord crtRecord = new MovieRecord.Builder().setTitle(movie)
                                                                 .setMcRating(rating)
                                                                 .setMcMetaRating(metaRating)
                                                                 .build();
                crtRecord.dump(writer);
            }
            catch(Exception e){
                System.out.println(String.format("exception during FW extracting \'%s\': %s", movie, e));

                MovieRecord crtRecord = new MovieRecord.Builder().setTitle(movie)
                                                                 .build();
                crtRecord.dump(writer);
            }

            try{
                Thread.sleep(1_000);
            }
            catch(InterruptedException e){

            }
            
        }

        try{
            writer.close();
        }
        catch(IOException e){}
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
