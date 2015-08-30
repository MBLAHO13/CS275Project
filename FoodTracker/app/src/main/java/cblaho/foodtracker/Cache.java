package cblaho.foodtracker;

import android.content.Context;

import java.util.List;
import java.util.Map;

/**
 * Created by maxm on 8/29/15.
 */
public class Cache {
    CacheListener listener;
    DbHandler database;
    Map<String,String> ingredients;
    Map<String,String> recipes;

    public Cache(CacheListener listener, Context context) {
        this.database = new DbHandler(context);
        this.listener = listener;
        this.ingredients = database.getIngredientList();
    }

    public Food getFoodById(String id) {
        if(id.startsWith("R") || id.startsWith("r")) {
            return getRecipeById(id);
        } else {
            return getIngredientById(id);
        }
    }

    public Recipe getRecipeById(String id) {
        return null;
    }

    public Ingredient getIngredientById(String id) {
        Ingredient res = database.getIngredientById(id);
        // Add conversions from csv
        return res;
    }

    public void searchFood(String name) {
        Food f;
        // 1. Check recipes map
        //  i. if there, dur
        // 2. Check ingredients map
        //  i. if there, knock on db's door and say hello
        // 3. make Json request
    }

    public void save(Food f) {
        List<Food> foodIngredients = f.getIngredients();
        if(foodIngredients != null) {
            // If it has ingredients, it must be a recipe
            for(Food i : foodIngredients) {
                this.save(i);
            }
            // Then do local caching stuff
        } else {
            database.save(f);
        }
    }

    public Map<String,String> getIngredients() {
        return ingredients;
    }

    public Map<String,String> getRecipes() {
        return recipes;
    }
}
