package cblaho.foodtracker;

import java.util.Map;

/**
 * Created by cblaho on 8/26/15.
 */
public class Ingredient {
    String id;
    String name;
    String group;
    int quantity;
    Map<String, Nutrient> nutrients;

    public Ingredient(String id, Map<String, Double> conversion, String name, int quantity, String group, Map<String, Nutrient> nutrients) {
        this.id = id;
        this.conversion = conversion;
        this.name = name;
        this.quantity = quantity;
        this.group = group;
        this.nutrients = nutrients;
    }

    Map<String, Double> conversion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Double> getConversion() {
        return conversion;
    }

    public void setConversion(Map<String, Double> conversion) {
        this.conversion = conversion;
    }

    public Map<String, Nutrient> getNutrients() {
        return nutrients;
    }

    public void setNutrients(Map<String, Nutrient> nutrients) {
        this.nutrients = nutrients;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
