package com.example.fyp;

public class Product {
    private int id;
    private String name;
    private String shortdesc;
    private double rating;
    private double price;
    private String image;

    public Product(int id, String name, String shortdesc, double rating, double price, String image) {
        this.id = id;
        this.name = name;
        this.shortdesc = shortdesc;
        this.rating = rating;
        this.price = price;
        this.image = image;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public double getRating() {
        return rating;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
