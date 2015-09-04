package cblaho.foodtracker.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import cblaho.foodtracker.R;
import cblaho.foodtracker.cache.Cache;
import cblaho.foodtracker.cache.CacheListener;
import cblaho.foodtracker.data.Food;
import cblaho.foodtracker.data.Recipe;

public class AddIngredient extends Activity implements CacheListener {
    private Recipe recipe;
    private Cache cache;

    /**
     * Sets up the activity. Unbundles the recipe and sets the actionbar to a user Friendly title.
     * @param savedInstanceState Android OS generated
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        this.cache = new Cache(this, getApplicationContext());
        ActionBar ab = getActionBar();
        if(ab != null) {
            ab.setTitle("Add an Ingredient");
        }
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("recipe");
    }

    /**
     * Onclick handler for the search button in the addIngredient layout.
     * @param view Android OS generated
     */
    public void searchIngredient(View view){
        EditText mEdit = (EditText)findViewById(R.id.add_ingredient_searchbox);
        String query = mEdit.getText().toString();
        cache.searchFood(query);
    }

    /**
     * Listener for the cache. Called if the requested item is specifically found.
     * Starts the AddUnitsQuantity activity, passing a recipe and ingredient object.
     * @param f Food object returned by cache
     */
    @Override
    public void onFoodFound(Food f) {
        Intent intent = new Intent(AddIngredient.this, AddUnitsQuantity.class);
        intent.putExtra("recipe", this.recipe);
        intent.putExtra("ingredient", f);
        startActivity(intent);
    }

    /**
     * Listener for the cache. Called if multiple results are returned.
     * Starts the search results class to prompt the user to select their choice.
     * @param results search results generated by the cache
     */
    @Override
    public void onSearchResult(Map<String, String> results) {
        Intent intent = new Intent(AddIngredient.this, SearchResults.class);
        intent.putExtra("recipe", this.recipe);
        intent.putExtra("results", (HashMap) results);
        startActivity(intent);
    }
}
