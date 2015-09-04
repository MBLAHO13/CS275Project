package cblaho.foodtracker.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import cblaho.foodtracker.R;
import cblaho.foodtracker.cache.Cache;
import cblaho.foodtracker.data.Recipe;

public class RecipeSteps extends Activity {
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("recipe");
        ActionBar ab = getActionBar();
        if(ab != null) {
            ab.setTitle("Finish Recipe Details");
        }
    }

    public void onFinish(View view) {
        recipe.setName(((EditText) findViewById(R.id.recipe_steps_name)).getText().toString());
        Double servings = Double.parseDouble(((EditText) findViewById(R.id.recipe_steps_servings)).getText().toString());
        recipe.addConversion("servings", recipe.getGrams()/servings);
        recipe.setConversion("servings");
        recipe.setQty(servings);
        recipe.setSteps(((EditText) findViewById(R.id.recipe_steps_steps)).getText().toString());
        Cache cache = new Cache(null, getApplicationContext());
        cache.save(recipe);
        Intent intent = new Intent(RecipeSteps.this, RecipeDisplayOne.class);
        intent.putExtra("recipe", recipe);
        startActivity(intent);
    }
}
