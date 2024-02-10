package com.example.screenmatch.model;

public enum Category {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    DRAMA("Drama"),
    HORROR("Horror"),
    THRILLER("Thriller"),
    FANTASY("Fantasy"),
    ROMANCE("Romance"),
    CRIME("Crime"),
    COMEDY("Comedy");
    private String categoryOMDB;

    Category(String categoryOMDB){
        this.categoryOMDB = categoryOMDB;
    }

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.categoryOMDB.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No category found for this value: " + text);
    }

}
