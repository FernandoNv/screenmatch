package com.example.screenmatch.model;

public enum Category {
    ACTION("Action", "Ação"),
    ADVENTURE("Adventure", "Aventura"),
    DRAMA("Drama", "Drama"),
    HORROR("Horror", "Terror"),
    THRILLER("Thriller", "Suspense"),
    FANTASY("Fantasy", "Fantasia"),
    ROMANCE("Romance", "Romance"),
    CRIME("Crime", "Crime"),
    COMEDY("Comedy", "Comédia");
    private String categoryOMDB;
    private String categoryPortugues;

    Category(String categoryOMDB, String categoryPortugues){
        this.categoryOMDB = categoryOMDB;
        this.categoryPortugues = categoryPortugues;
    }

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.categoryOMDB.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No category found for this value: " + text);
    }

    public static Category fromPortugues(String text) {
        for (Category category : Category.values()) {
            if (category.categoryPortugues.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No category found for this value: " + text);
    }

}
