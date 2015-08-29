package cblaho.foodtracker;

import java.util.Map;

/**
 * Created by maxm on 8/29/15.
 */
public class Cache {
    CacheListener listener;
    Map<String,String> ingredients;
    Map<String,String> recipes;

    public Cache(CacheListener listener) {
        this.listener = listener;
    }

    public Food getFoodById(String id) {
        if(id.startsWith("R") || id.startsWith("r")) {
            return getRecipeById(id);
        } else {
            // return ingredient from database
        }
    }

    public Recipe getRecipeById(String id) {

    }

    public void searchFood(String name) {

    }

    public void save(Recipe r) {

    }

    public void save(Ingredient i) {

    }

    public Map<String,String> getIngredients() {
        return ingredients;
    }

    public Map<String,String> getRecipes() {
        return recipes;
    }
}
