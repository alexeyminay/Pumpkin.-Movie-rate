package com.alesno.ratingkino.model;

public class Movie {
    private String name;
    private String years;

    public Movie(String name, String years) {
        this.name = name;
        this.years = years;
    }

    public String getName() {
        return name;
    }

    public String getYears() {
        return years;
    }
}
