package cblaho.foodtracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mmattes on 8/29/15.
 */
public class Recipe implements Food {
    private String id;
    private String name;
    private Double qty;
    private String conversion;
    private String steps;
    private List<Food> ingredients;
    private Map<String,Double> conversions;

    public Recipe(String id, String name, String steps, Map<String,Double> conversions, List<Food> ingredients) {
        this.id = id;
        this.qty = 1.0;
        this.name = name.replace(",","");
        this.steps = steps;
        this.conversions = conversions;
        this.conversion = null;
        this.ingredients = ingredients;
    }

    public Recipe(String id) {
        this.id = id;
        this.qty = 1.0;
        this.name = null;
        this.steps = null;
        this.conversions = new HashMap<>();
        this.conversion = null;
        this.ingredients = new ArrayList<>();
    }

    public Recipe(String id, String name, Double qty, List<Food> ingredients, Map<String,Double> conversions, String conversion, String steps) {
        this.id = id;
        this.qty = qty;
        this.name = name.replace(",","");
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

    public String getSteps() {
        return steps;
    }

    public void setName(String name) {
        this.name = name.replace(",","");
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
