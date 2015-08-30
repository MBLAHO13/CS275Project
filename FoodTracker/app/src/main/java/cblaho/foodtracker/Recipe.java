package cblaho.foodtracker;

import java.util.HashMap;
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
    List<Food> ingredients;
    Map<String,Double> conversions;

    public Recipe(String id, String name, Double qty, List<Food> ingredients, Map<String,Double> conversions, String conversion, String steps) {
        this.id = id;
        this.qty = qty;
        this.name = name;
        this.steps = steps;
        this.conversions = conversions;
        this.conversion = conversion;
        this.ingredients = ingredients;
    }

    public void addIngredient(Food f) {
        ingredients.add(f);
    }

    public List<Food> getIngredients() {
        return ingredients;
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
    public String getGroup() {
        return null;
    }

    @Override
    public Map<String, Double> getNutrients() {
        HashMap<String,Double> nutrients = new HashMap<>();
        Double value;
        for(String n : ingredients.get(0).getNutrients().keySet()) {
            value = 0.0;
            for(Food f : ingredients) {
                value += f.getNutrient(n);
            }
            nutrients.put(n, value);
        }
        return nutrients;
    }

    @Override
    public Double getNutrient(String name) {
        Double nutrient = 0.0;
        for(Food f : ingredients) {
            nutrient += f.getNutrient(name);
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
