package cblaho.foodtracker;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 * Created by cblaho on 8/26/15.
 */
public class Ingredient {
    String id;
    String name;
    String group;
    int quantity;
    //map name of nutrient to nutrient tuple of unit:amount
    Map<String, Nutrient> nutrients;
    //map name of conversion to grams, eg. cup:12.0[g], slice:1.0[g]
    Map<String, Double> conversion;

    public Ingredient(String id, Map<String, Double> conversion, String name, int quantity, String group, Map<String, Nutrient> nutrients) {
        //manual constructor, you probably shouldn't use this outright.
        this.id = id;
        this.conversion = conversion;
        this.name = name;
        this.quantity = quantity;
        this.group = group;
        this.nutrients = nutrients;
    }

    public Ingredient(JsonObject response){
        // at this point we've already packaged the JSON reponse into a GSON-comaptible object.
        //this method takes a json object, and returns a full ingredient object

        JsonArray rawConversions = response.getAsJsonArray("conversions");

        for (int i =0; i< rawConversions.size(); i++){
            JsonElement oneConversion = rawConversions.get(i);

        }

        JsonObject rawNutrients = response.getAsJsonObject("nutrients");
        this.name = response.get("name").getAsString();
        this.group = response.get("group_name").getAsString();
        this.id = response.get("id").getAsString();
        this.quantity = 0;



    }



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
