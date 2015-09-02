package cblaho.foodtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class AddIngredient extends Activity implements CacheListener {
    private Recipe recipe;
    private Cache cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        this.cache = new Cache(this, getApplicationContext());
        getActionBar().setTitle("Add an Ingredient");
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("recipe");
    }

    public void searchIngredient(View view){
        EditText mEdit = (EditText)findViewById(R.id.add_ingredient_searchbox);
        String query = mEdit.getText().toString();
        cache.searchFood(query);
    }

    @Override
    public void onFoodFound(Food f) {
        Intent intent = new Intent(AddIngredient.this, AddUnitsQuantity.class);
        intent.putExtra("recipe", this.recipe);
        intent.putExtra("ingredient", f);
        startActivity(intent);
    }

    @Override
    public void onSearchResult(Map<String, String> results) {
        Intent intent = new Intent(AddIngredient.this, SearchResults.class);
        intent.putExtra("recipe", this.recipe);
        intent.putExtra("results", (HashMap) results);
        startActivity(intent);
    }
}
