package cblaho.foodtracker;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cblaho and mmattes on 8/29/15.
 */
public class Ingredient implements Food {
    private String id;
    private String name;
    private String group;
    private Double quantity;
    private String conversion;
    //map name of nutrient to nutrient tuple of unit:amount
    private Map<String, Double> nutrients;
    //map name of conversion to grams, eg. cup:12.0[g], slice:1.0[g]
    private Map<String, Double> conversions;

    public Ingredient(String id, Map<String, Double> conversions, String name, String conversion, Double quantity, String group, Map<String, Double> nutrients) {
        //manual constructor, you probably shouldn't use this outright.
        this.id = id;
        this.name = name;
        this.group = group;
        this.nutrients = nutrients;
        this.conversions = conversions;
        this.conversion = conversion;
        this.quantity = quantity;
    }

    public Ingredient(String id, String name, String group, Map<String,Double> nutrients) {
        this.id = id;
        this.name = name;
        this.group = group;
        this.nutrients = nutrients;
        this.conversions = new HashMap<>();
        this.conversion = null;
        this.quantity = 1.0;
    }

    public Ingredient(JsonObject response){
        // at this point we've already packaged the JSON reponse into a GSON-comaptible object.
        //this method takes a json object, and returns a full ingredient object
        JsonArray jsonConversions = response.getAsJsonArray("conversions");
        for(JsonElement c : jsonConversions){
            JsonObject jsonConversion = c.getAsJsonObject();
            this.addConversion(
                    jsonConversion.get("unit").getAsString(),
                    jsonConversion.get("grams").getAsDouble()
            );
        }
        this.name = response.get("name").getAsString();
        this.group = response.get("group_name").getAsString();
        this.id = response.get("id").getAsString();
        this.quantity = 0.0;
        JsonArray jsonNutrients = response.getAsJsonObject("nutrients").getAsJsonArray();
        for(JsonElement n : jsonNutrients) {
            JsonObject jsonNutrient = n.getAsJsonObject();
            nutrients.put(
                    jsonNutrient.get("name").getAsString(),
                    jsonNutrient.get("val").getAsDouble()
            );
        }
        this.conversion = null;
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
        return group;
    }

    @Override
    public Map<String, Double> getNutrients() {
        return nutrients;
    }

    @Override
    public Double getNutrient(String name) {
        if(this.conversion == null) {
            return nutrients.get(name)*quantity;
        } else {
            return nutrients.get(name)*quantity*conversions.get(conversion);
        }
    }

    @Override
    public Double getQty() {
        return quantity;
    }

    @Override
    public void setQty(Double qty) {
        quantity = qty;
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
