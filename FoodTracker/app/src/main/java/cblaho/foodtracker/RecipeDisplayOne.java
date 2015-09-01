package cblaho.foodtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Map;

public class RecipeDisplayOne extends Activity implements CacheListener {
    private Cache cache;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.cache = new Cache(this, getApplicationContext());
        setContentView(R.layout.activity_recipe_display_one);
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("recipe");
        TextView name = (TextView) this.findViewById(R.id.recipe_display_one_name);
        name.setText(recipe.getName());
    }

    @Override
    public void onFoodFound(Food f) {

    }

    @Override
    public void onSearchResult(Map<String, String> results) {

    }
}
