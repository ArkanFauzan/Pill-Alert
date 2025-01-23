package com.example.pillalert;

public class CardDiseaseModel {
    private String name;
    private String description;

    private String date;

    public CardDiseaseModel(String name, String description, String date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

}
