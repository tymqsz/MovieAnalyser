package com.tymqsz;

import java.io.BufferedWriter;
import java.nio.Buffer;

public class MovieData implements Comparable<MovieData>{
    private final String name;
    private final String year;
    private final String genre;
    private final String length;
    private final String director;
    private final String cast;
    private final String rating;
    private final String metarating;

    private MovieData(Builder builder) {
        this.name = builder.name;
        this.year = builder.year;
        this.genre = builder.genre;
        this.length = builder.length;
        this.director = builder.director;
        this.cast = builder.cast;
        this.rating = builder.rating;
        this.metarating = builder.metarating;
    }

    @Override
    public int compareTo(MovieData other){
        return this.name.compareTo(other.name);
    }

    public void dump(BufferedWriter writer){
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(";");
        builder.append(year).append(";");
        builder.append(genre).append(";");
        builder.append(length).append(";");
        builder.append(rating).append(";");
        builder.append(metarating).append(";");
        builder.append(director).append(";");
        builder.append(cast).append(";");
    }
    
    public static class Builder {
        private String name;
        private String year;
        private String genre;
        private String length;
        private String director;
        private String cast;
        private String rating;
        private String metarating;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setYear(String year) {
            this.year = year;
            return this;
        }

        public Builder setGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder setLength(String length) {
            this.length = length;
            return this;
        }

        public Builder setDirector(String director) {
            this.director = director;
            return this;
        }

        public Builder setCast(String cast) {
            this.cast = cast;
            return this;
        }

        public Builder setRating(String rating) {
            this.rating = rating;
            return this;
        }

        public Builder setMetarating(String metarating) {
            this.metarating = metarating;
            return this;
        }

        public MovieData build() {
            return new MovieData(this);
        }
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public String getLength() {
        return length;
    }

    public String getDirector() {
        return director;
    }

    public String getCast() {
        return cast;
    }

    public String getRating() {
        return rating;
    }

    public String getMetarating() {
        return metarating;
    }
}
