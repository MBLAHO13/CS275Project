package cblaho.foodtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RecipeDisplayOne extends Activity {
    private Cache cache;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.cache = new Cache(null, getApplicationContext());
        setContentView(R.layout.activity_recipe_display_one);
        Intent intent = getIntent();
        this.recipe = intent.getParcelableExtra("recipe");
        TextView name = (TextView) this.findViewById(R.id.recipe_display_one_name);
        name.setText(recipe.getName());
    }
}
