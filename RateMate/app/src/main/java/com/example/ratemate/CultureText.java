package com.example.ratemate;

public class CultureText {
    public String name;
    public float rate;
    public String category ;
    public String release_date;
    public String rate_date;
    public String description;
    public String review;

    public CultureText(String name, float rate, String category, String release_date, String rate_date, String description, String review)
    {
        this.name = name;
        this.rate = rate;
        this.category = category;
        this.release_date = release_date;
        this.rate_date = rate_date;
        this.description = description;
        this.review = review;
    }
}
