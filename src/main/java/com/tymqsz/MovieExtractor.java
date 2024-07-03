package com.tymqsz;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.*;
import java.io.*;


public class MovieExtractor {
    private WebDriver driver;
    private List<String> movieList;    

    public MovieExtractor(WebDriver driver){
        this.driver = driver;
        movieList = new ArrayList<>();
        

        try{
            extractFromFile();
        }
        catch(Exception e){
            System.out.println(e);
            extractFromSite();
        }
    }

    private void extractFromFile() throws Exception{
        InputStream inputStream = MovieExtractor.class.getResourceAsStream("/movie_list.txt");
        if(inputStream == null)
            throw new Exception("NoInputStreamException");

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while((line = reader.readLine()) != null){
            movieList.add(line);
        }
    }

    private void extractFromSite(){
        driver.get("https://www.imdb.com/chart/top/?ref_=nv_mv_250");

        clickElement(byText("Decline"), Duration.ofSeconds(10));


        String xpath, text;
        for(int i = 1; i <= 250; i++){
            xpath = String.format("//*[@id='__next']/main/div/div[3]/section/div/div[2]/div/ul/li[%d]/div[2]/div/div/div[1]/a/h3", i);

            text = readElement(xpath, Duration.ofSeconds(2));

            /* remove number from title */
            String[] split = text.split(". ", 2);
            movieList.add(split[1]);
        }

        try{
            File outputFile = new File("/home/tymqsz/movie_rater/movie_scraper/src/main/resources/movie_list.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            StringBuilder builder = new StringBuilder();

            for(String title: movieList){
                builder.append(title).append("\n");
            }

            writer.write(builder.toString());
            writer.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    public List<String> getMovieList(){
        return movieList;
    }

    private void clickElement(String xpath, Duration time){
        ExpectedCondition<WebElement> ec = ExpectedConditions.presenceOfElementLocated(
                                                              By.xpath(xpath));
        WebElement element = new WebDriverWait(driver, time).until(ec);

        element.click();
    }

    private String readElement(String xpath, Duration time){
        ExpectedCondition<WebElement> ec = ExpectedConditions.presenceOfElementLocated(
                                                              By.xpath(xpath));
        WebElement element = new WebDriverWait(driver, time).until(ec);

        return element.getText();
    }
    
    private String byText(String text){
        return "//*[text()='" + text + "']";
    }
    
}
