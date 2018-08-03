package com.example.nev.toppizza.models;

import java.util.Arrays;

public class Pizza {

    private  String name;
    private String summary;
    private String type;
    private String[] price= new String[3];
    private String offer;

    public Pizza(String name, String summary, String type, String[] price, String offer) {
         setName( name);
        setSummary(summary);
        setType(type);
        setPrice( price);
        setOffer(offer);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getPrice() {
        return price;
    }

    public void setPrice(String[] price) {
        this.price = price;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }


    @Override
    public String toString() {
        return "Pizza{" +
                "name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", type='" + type + '\'' +
                ", price=" + Arrays.toString(price) +
                ", offer=" + offer +
                '}';
    }
}
