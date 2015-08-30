package cblaho.foodtracker;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maxm on 8/29/15.
 */
public class Cache {
    private Context context;
    private CacheListener listener;
    private DbHandler database;
    private Map<String,String> ingredients;
    private Map<String,String> recipes;

    public Cache(CacheListener listener, Context context) {
        this.context = context;
        this.database = new DbHandler(context);
        this.listener = listener;
        this.ingredients = database.getIngredientList();
        this.recipes = this.getRecipeList();
    }

    private Map<String,String> getRecipeList() {
        Map<String,String> r = new HashMap<>();
        FileInputStream fis;
        try {
            fis = context.openFileInput("recipes.csv");
        } catch (FileNotFoundException e) {
            return r;
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line = reader.readLine();
            while (line != null) {
                String vals[] = line.split(",");
                r.put(vals[0], vals[1]);
            }
        } catch (IOException e) {
            return r;
        } finally {
            try {
                fis.close();
            } catch (IOException e) {}
        }
        return r;
    }

    private void addToRecipeList(String id, String name) {
        this.recipes.put(id, name);
        FileOutputStream fos;
        try {
            fos = context.openFileOutput("recipes.csv", context.MODE_APPEND);
        } catch(FileNotFoundException e) {
            try {
                fos = context.openFileOutput("recipes.csv", context.MODE_PRIVATE);
            } catch(FileNotFoundException e2) {
                return;
            }
        }
        try {
            fos.write((id + "," + name + "\n").getBytes());
        } catch(IOException e) {
        } finally {
            try {
                fos.close();
            } catch (IOException e2) {}
        }
    }

    public Food getFoodById(String id) {
        if(id.startsWith("R") || id.startsWith("r")) {
            return getRecipeById(id);
        } else {
            return getIngredientById(id);
        }
    }

    public Recipe getRecipeById(String id) {
        JsonHandler json;
        try {
            json = new JsonHandler(id, context);
        } catch(FileNotFoundException e) {
            return null;
        }
        List<Food> ingredients = new ArrayList<>();
        Map<String,JsonIngredientQty> jsonIngredients = json.getIngredients();
        for(String ingredientId : jsonIngredients.keySet()) {
            Food f = getFoodById(ingredientId);
            f.setQty(jsonIngredients.get(ingredientId).getQty());
            f.setConversion(jsonIngredients.get(ingredientId).getUnit());
            ingredients.add(f);
        }
        return new Recipe(id, json.getName(), json.getSteps(), json.getConversions(), ingredients);
    }

    public Ingredient getIngredientById(String id) {
        JsonHandler json;
        try {
            json = new JsonHandler(id, context);
        } catch (FileNotFoundException e) {
            return null;
        }
        Ingredient res = database.getIngredientById(id);
        Map<String,Double> conversions = json.getConversions();
        for(String name : conversions.keySet()) {
            res.addConversion(name, conversions.get(name));
        }
        return res;
    }

    public void searchFood(String name) {
        if(recipes.containsValue(name)) {
            for(String id : recipes.keySet()) {
                if(recipes.get(id).equals(name)) {
                    listener.onFoodFound(getRecipeById(id));
                }
            }
        } else if(ingredients.containsValue(name)) {
            for(String id : ingredients.keySet()) {
                if(ingredients.get(id).equals(name)) {
                    listener.onFoodFound(getFoodById(id));
                }
            }
        } else {
            (new RestHandler(listener)).execute(name);
        }
    }

    public void save(Recipe r) {
        for(Food i : r.getIngredients()) {
            if(i.getID().startsWith("r") || i.getID().startsWith("R")) {
                this.save((Recipe) i);
            } else {
                this.save((Ingredient) i);
            }
        }
        (new JsonHandler(r, context)).write();
        addToRecipeList(r.getID(), r.getName());
    }

    public void save(Ingredient i) {
        ingredients.put(i.getID(), i.getName());
        database.save(i);
        (new JsonHandler(i, context)).write();
    }

    public Map<String,String> getIngredients() {
        return ingredients;
    }

    public Map<String,String> getRecipes() {
        return recipes;
    }
}
