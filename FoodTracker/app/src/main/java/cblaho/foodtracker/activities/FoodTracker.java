package cblaho.foodtracker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cblaho.foodtracker.data.Food;
import cblaho.foodtracker.data.Ingredient;
import cblaho.foodtracker.data.Recipe;
import cblaho.foodtracker.cache.Cache;

/**
 * Dummy class to start the main list activity.
 */
public class FoodTracker extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ////////////////////BEGIN TEST DATA////////////////////////
        System.out.println("-----GENERATING TEST DATA-----");
        Cache cache = new Cache(null, this.getApplicationContext());
        System.out.println("---MAKING FIRST INGREDIENT-----");
        Map<String,Double> conversions = new HashMap<>();
        conversions.put("cup", 10.0);
        conversions.put("gram", 1.0);
        Map<String,Double> nutrients = new HashMap<>();
        nutrients.put("caffeine", 0.01);
        nutrients.put("sucrose", 0.09);
        nutrients.put("ash", 0.9);
        Ingredient i = new Ingredient(
                "00001",
                conversions,
                "testCheese",
                "cup",
                2.0,
                "testgroup",
                nutrients
        );
        System.out.println("-----SAVING FIRST INGREDIENT-----");
        cache.save(i);
        System.out.println("-----Saved first ingredient-----");
        Map<String,Double> conv2 = new HashMap<>();
        conv2.put("scoop", 200.0);
        Map<String,Double> nut2 = new HashMap<>();
        nut2.put("caffeine", 10.0);
        nut2.put("sucrose", .9);
        nut2.put("ash", 1.1);
        Ingredient i2 = new Ingredient(
                "00002",
                conv2,
                "Food, Dog powdered",
                "scoop",
                3.0,
                "testgroup",
                nut2
        );
        cache.save(i2);
        System.out.println("-----Saved second ingredient-----");
        List<Food> ingredients = new ArrayList<>();
        ingredients.add(i);
        ingredients.add(i2);
        Map<String,Double> rconv = new HashMap<>();
        rconv.put("Servings", 200.0);
        Recipe r = new Recipe(
                "R0",
                "Dog Cheese",
                4.0,
                ingredients,
                rconv,
                "Servings",
                "1. Do a thing\n2. Do another things"
        );
        cache.save(r);
        System.out.println("-----FINISHED GENERATING TEST DATA-----");
        ////////////////////END TEST DATA//////////////////////
        startListActivity();
    }

    /**
     * Starts the list activity.
     */
    public void startListActivity(){
        Intent intent = new Intent(this, RecipeList.class);
        startActivity(intent);
    }

    /**
     * Exits the app on resume so the app exits on backbutton press.
     */
    @Override
    protected void onResume(){
        super.onResume();
        finish();
    }
}
