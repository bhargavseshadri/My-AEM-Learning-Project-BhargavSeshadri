package com.aem.geeks.core.utils.CfRecipeRLTD;

import java.util.List;

public class Recipe {

    private int id;
    private String name;
    private int servings;
    private String difficulty;
    private String cuisine;
    private List<String> mealType;

    public Recipe() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getServings() {
        return servings;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getCuisine() {
        return cuisine;
    }

    public List<String> getMealType() {
        return mealType;
    }

}
