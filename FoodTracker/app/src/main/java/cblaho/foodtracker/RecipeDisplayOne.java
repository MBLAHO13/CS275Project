package cblaho.foodtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Map;

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
        TextView servings = (TextView) this.findViewById(R.id.recipe_display_one_servings);
        servings.setText(recipe.getQty().toString());
        TableLayout ingredients = (TableLayout) this.findViewById(R.id.recipe_display_one_ingredients);
        for(Food f : recipe.getIngredients()) {
            TableRow tr = new TableRow(this);
            TextView fname = new TextView(this);
            TextView funit = new TextView(this);
            TextView fqty = new TextView(this);
            fname.setText(f.getName());
            funit.setText(f.getConversion());
            fqty.setText(f.getQty().toString());
            tr.addView(fname);
            tr.addView(funit);
            tr.addView(fqty);
            ingredients.addView(tr);
        }
        TextView steps = (TextView) this.findViewById(R.id.recipe_display_one_steps);
        steps.setText(recipe.getSteps());
        TableLayout nutrients = (TableLayout) this.findViewById(R.id.recipe_display_one_nutrients);
        for(Map.Entry<String,Double> nutrient : recipe.getNutrients().entrySet()) {
            TableRow tr = new TableRow(this);
            TextView nname = new TextView(this);
            TextView nqty = new TextView(this);
            nname.setText(nutrient.getKey());
            nqty.setText(nutrient.getValue().toString());
            tr.addView(nname);
            tr.addView(nqty);
            nutrients.addView(tr);
        }
    }
}
