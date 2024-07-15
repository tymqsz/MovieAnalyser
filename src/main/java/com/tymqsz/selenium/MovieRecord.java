package com.tymqsz.selenium;

import java.io.BufferedWriter;
import java.io.IOException;

public class MovieRecord implements Comparable<MovieRecord> {
    private final String title;
    private final String year;
    private final String genre;
    private final String length;
    private final String director;
    private final String cast;
    private final String imdbRating, rtRating, mcRating;
    private final String imdbMetaRating, rtMetaRating, mcMetaRating;

    private MovieRecord(Builder builder) {
        this.title = builder.title;
        this.year = builder.year;
        this.genre = builder.genre;
        this.length = builder.length;
        this.director = builder.director;
        this.cast = builder.cast;
        this.imdbRating = builder.imdbRating;
        this.rtRating = builder.rtRating;
        this.mcRating = builder.mcRating;
        this.imdbMetaRating = builder.imdbMetaRating;
        this.rtMetaRating = builder.rtMetaRating;
        this.mcMetaRating = builder.mcMetaRating;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append("title: ").append(title).append("; ");
        builder.append("year: ").append(year).append("; ");
        builder.append("genre: ").append(genre).append("; ");
        builder.append("length: ").append(length).append("; ");
        builder.append("director: ").append(director).append("; ");
        builder.append("cast: ").append(cast).append("; ");
        builder.append("imdbRating: ").append(imdbRating).append("; ");
        builder.append("rtRating: ").append(rtRating).append("; ");
        builder.append("mcRating: ").append(mcRating).append("; ");
        builder.append("imdbMetaRating: ").append(imdbMetaRating).append("; ");
        builder.append("rtMetaRating: ").append(rtMetaRating).append("; ");
        builder.append("mcMetaRating: ").append(mcMetaRating).append("; ");
        
        return builder.toString();
    }

    @Override
    public int compareTo(MovieRecord other) {
        return this.title.compareTo(other.title);
    }

    public void dump(BufferedWriter writer) {
        String fixedGenre = genre != null ? genre.replace("\n", " ") : null;

        StringBuilder builder = new StringBuilder();
        builder.append("\"").append(title).append("\"").append(";");
        builder.append("\"").append(year).append("\"").append(";");
        builder.append("\"").append(fixedGenre).append("\"").append(";");
        builder.append("\"").append(length).append("\"").append(";");
        builder.append("\"").append(imdbRating).append("\"").append(";");
        builder.append("\"").append(imdbMetaRating).append("\"").append(";");
        builder.append("\"").append(rtRating).append("\"").append(";");
        builder.append("\"").append(rtMetaRating).append("\"").append(";");
        builder.append("\"").append(mcRating).append("\"").append(";");
        builder.append("\"").append(mcMetaRating).append("\"").append(";");
        builder.append("\"").append(director).append("\"").append(";");
        builder.append("\"").append(cast).append("\"").append("\n");

        try {
            writer.write(builder.toString());
            writer.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static class Builder {
        private String title;
        private String year;
        private String genre;
        private String length;
        private String director;
        private String cast;
        private String imdbRating;
        private String rtRating;
        private String mcRating;
        private String imdbMetaRating;
        private String rtMetaRating;
        private String mcMetaRating;

        public Builder setTitle(String title) {
            this.title = title;
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

        public Builder setImdbRating(String imdbRating) {
            this.imdbRating = imdbRating;
            return this;
        }

        public Builder setRtRating(String rtRating) {
            this.rtRating = rtRating;
            return this;
        }

        public Builder setMcRating(String mcRating) {
            this.mcRating = mcRating;
            return this;
        }

        public Builder setImdbMetaRating(String imdbMetaRating) {
            this.imdbMetaRating = imdbMetaRating;
            return this;
        }

        public Builder setRtMetaRating(String rtMetaRating) {
            this.rtMetaRating = rtMetaRating;
            return this;
        }

        public Builder setMcMetaRating(String mcMetaRating) {
            this.mcMetaRating = mcMetaRating;
            return this;
        }

        public MovieRecord build() {
            return new MovieRecord(this);
        }
    }

    public String getTitle() {
        return title;
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

    public String getImdbRating() {
        return imdbRating;
    }

    public String getRtRating() {
        return rtRating;
    }

    public String getMcRating() {
        return mcRating;
    }

    public String getImdbMetaRating() {
        return imdbMetaRating;
    }

    public String getRtMetaRating() {
        return rtMetaRating;
    }

    public String getMcMetaRating() {
        return mcMetaRating;
    }
}
