package cblaho.foodtracker;

import java.util.List;
import java.util.Map;

/**
 * Created by mmattes on 8/29/15.
 */
public class Recipe implements Food {
    String id;
    String name;
    Double qty;
    String conversion;
    String steps;
    List<Ingredient> ingredients;
    Map<String,Double> conversions;

    public Recipe(String id, String name, Double qty, List<Ingredient> ingredients, Map<String,Double> conversions, String conversion, String steps) {
        this.id = id;
        this.qty = qty;
        this.name = name;
        this.steps = steps;
        this.conversions = conversions;
        this.conversion = conversion;
        this.ingredients = ingredients;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Double getNutrient(String name) {
        Double nutrient = 0.0;
        for(Ingredient i : ingredients) {
            nutrient += i.getNutrient(name);
        }
        return nutrient*qty;
    }

    @Override
    public Double getQty() {
        return qty;
    }

    @Override
    public void setQty(Double qty) {
        this.qty = qty;
    }

    @Override
    public Map<String, Double> getConversions() {
        return conversions;
    }

    @Override
    public String getConversion() {
        return conversion;
    }

    @Override
    public void setConversion(String name) {
        conversion = name;
    }

    @Override
    public void addConversion(String name, Double grams) {
        conversions.put(name, grams);
    }
}
