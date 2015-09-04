package cblaho.foodtracker.cache;

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
import java.util.concurrent.ExecutionException;

import cblaho.foodtracker.data.Food;
import cblaho.foodtracker.data.Ingredient;
import cblaho.foodtracker.data.Recipe;

/**
 * Created by maxm on 8/29/15.
 * Cache handler for interfacing between the UI and the stored information (on the phone and in the
 * REST-accessible database)
 */
public class Cache implements CacheListener {
    private Context context;
    private CacheListener listener;
    private DbHandler database;
    private Map<String,String> ingredients;
    private Map<String,String> recipes;
    private Integer maxId;

    /**
     * Initializes the Cache and loads all resources
     * @param listener Listener for outside requests
     * @param context Activity context
     */
    public Cache(CacheListener listener, Context context) {
        System.out.println("Generating Cache");
        this.context = context;
        this.database = new DbHandler(context);
        this.listener = listener;
        this.maxId = 0;
        this.ingredients = database.getIngredientList();
        this.recipes = this.getRecipeList();
        for(String id : ingredients.keySet()) {
            System.out.println("Known ID: " + id);
        }
        for(String id : recipes.keySet()) {
            System.out.println("Known ID: " + id);
        }
    }

    /**
     * Retrieves the recipe list from the local store
     * @return Map from ids to names of all created recipes
     */
    private Map<String,String> getRecipeList() {
        // Since the Recipes aren't stored in the database, this CSV is used to identify the
        // complete list of recipes and IDs.
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
            Integer id;
            this.maxId = 0;
            while (line != null) {
                System.out.println(line);
                String vals[] = line.split(",");
                r.put(vals[0], vals[1]);
                id = Integer.parseInt(vals[0].replace("R", "").replace("r", ""));
                if(id > this.maxId) {
                    this.maxId = id;
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            return r;
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return r;
    }

    /**
     * Adds recipe to the list on the local store
     * @param id Recipe id
     * @param name Recipe name
     */
    private void addToRecipeList(String id, String name) {
        this.recipes.put(id, name);
        FileOutputStream fos;
        try {
            fos = context.openFileOutput("recipes.csv", Context.MODE_APPEND);
        } catch(FileNotFoundException e) {
            try {
                fos = context.openFileOutput("recipes.csv", Context.MODE_PRIVATE);
            } catch(FileNotFoundException e2) {
                return;
            }
        }
        try {
            fos.write((id + "," + name + "\n").getBytes());
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * Generates the next recipe ID and returns it.
     * @return The next available recipe ID.
     */
    public String getNextRecipeId() {
        maxId++;
        return "R" + maxId.toString();
    }

    /**
     * Retrieves the food item using an ID
     * @param id food ID
     * @return Food matching the given ID
     */
    public Food getFoodById(String id) {
        if(id.startsWith("R") || id.startsWith("r")) {
            return getRecipeById(id);
        } else {
            return getIngredientById(id);
        }
    }

    /**
     * Returns the recipe using an ID
     * @param id Recipe ID
     * @return Recipe matching the given ID (or null if none found)
     */
    public Recipe getRecipeById(String id) {
        System.out.println("Getting by id: " + id);
        // 1. Retrieve Recipe JSON file
        JsonHandler json;
        try {
            json = new JsonHandler(id, context);
        } catch(FileNotFoundException e) {
            return null;
        }
        List<Food> ingredients = new ArrayList<>();
        // 2. Retrieve ingredients from JSON
        Map<String,JsonIngredientQty> jsonIngredients = json.getIngredients();
        for(String ingredientId : jsonIngredients.keySet()) {
            // 3. For each ingredient, load that Food object as well and store it in the Recipe
            Food f = getFoodById(ingredientId);
            f.setQty(jsonIngredients.get(ingredientId).getQty());
            f.setConversion(jsonIngredients.get(ingredientId).getUnit());
            ingredients.add(f);
        }
        // 4. Construct the class out of the list of ingredients and JSON data
        return new Recipe(
                id,
                json.getName(),
                json.getQty(),
                ingredients,
                json.getConversions(),
                json.getConversion(),
                json.getSteps()
        );
    }

    /**
     * Returns the Ingredient using an ID. May possible make a REST request to retrieve the data
     * from the remote store.
     * @param id Ingredient ID
     * @return Ingredient matching the given ID (or null if none found).
     */
    public Ingredient getIngredientById(String id) {
        System.out.println("Getting by id: " + id);
        // 1. Check if the ingredient is stored locally
        if(!ingredients.containsKey(id)) {
            try {
                // 2. if it's not stored locally, retrieve it from the database
                return new Ingredient((new RestHandler(this)).execute("id",id).get());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            // 2. If it is stored locally, get the JSON document
            JsonHandler json;
            try {
                json = new JsonHandler(id, context);
            } catch (FileNotFoundException e) {
                return null;
            }
            // 3. Retrieve nutrients and basic information from the database
            Ingredient res = database.getIngredientById(id);
            // 4. Retrieve conversions from the JSON
            Map<String,Double> conversions = json.getConversions();
            for(String name : conversions.keySet()) {
                res.addConversion(name, conversions.get(name));
            }
            // 5. Construct ingredient out of information
            return res;
        }
    }

    /**
     * Searches for a Food by name and returns the result to the listener by calling onFoodFound
     * or onSearchResult
     * @param name Name of the food to search for
     */
    public void searchFood(String name) {
        // 1. If the name matches a known recipe, return that
        if(recipes.containsValue(name)) {
            for(String id : recipes.keySet()) {
                if(recipes.get(id).equals(name)) {
                    listener.onFoodFound(getRecipeById(id));
                }
            }
        // 2. If the name matches a known ingredient, return that
        } else if(ingredients.containsValue(name)) {
            for(String id : ingredients.keySet()) {
                if(ingredients.get(id).equals(name)) {
                    listener.onFoodFound(getFoodById(id));
                }
            }
        // 3. Can't identify the name, send the name to the REST service to retrieve from remote
        // database or search the USDA NDB
        } else {
            (new RestHandler(listener)).execute("name",name);
        }
    }

    /**
     * Save the recipe and all component ingredients to the local cache for re-use
     * @param r Recipe object to save.
     */
    public void save(Recipe r) {
        System.out.println("Saving " + r.getName());
        for(Food i : r.getIngredients()) {
            if(i.getID().startsWith("r") || i.getID().startsWith("R")) {
                this.save((Recipe) i);
            } else {
                this.save((Ingredient) i);
            }
        }
        (new JsonHandler(r, context)).write();
        if(!recipes.containsKey(r.getID())) {
            addToRecipeList(r.getID(), r.getName());
        }
    }

    /**
     * Save the ingredient to the local cache for re-use
     * @param i Ingredient object to save
     */
    public void save(Ingredient i) {
        System.out.println("Saving " + i.getName());
        ingredients.put(i.getID(), i.getName());
        database.save(i);
        (new JsonHandler(i, context)).write();
    }

    /**
     * Gets the list of recipes created by the user
     * @return Map of IDs to names
     */
    public Map<String,String> getRecipes() {
        return recipes;
    }

    /**
     * Probably unused, disregard
     * @param f Food found by searching
     */
    @Override
    public void onFoodFound(Food f) {
        if(listener != null) {
            listener.onFoodFound(f);
        }
    }

    /**
     * Probably unused, disregard
     * @param results Search result mapping of IDs to Names
     */
    @Override
    public void onSearchResult(Map<String, String> results) {
        if(listener != null) {
            listener.onSearchResult(results);
        }
    }
}
