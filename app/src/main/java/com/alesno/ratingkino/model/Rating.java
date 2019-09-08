package com.alesno.ratingkino.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rating")
public class Rating {

    @Element(name = "kp_rating")
    private String ratingKinopoisk;

    @Element(name = "imdb_rating")
    private String ratingIMDb;

    public String getRatingKinopoisk() {
        return ratingKinopoisk;
    }

    public void setRatingKinopoisk(String ratingKinopoisk) {
        this.ratingKinopoisk = ratingKinopoisk;
    }

    public String getRatingIMDb() {
        return ratingIMDb;
    }

    public void setRatingIMDb(String ratingIMDb) {
        this.ratingIMDb = ratingIMDb;
    }
}
