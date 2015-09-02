package cblaho.foodtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
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
            fqty.setText((Integer.valueOf(f.getQty().intValue())).toString());
            fname.setTextSize(16);
            funit.setTextSize(16);
            fqty.setTextSize(16);
            fname.setPadding(0, 0, 20, 0);
            funit.setPadding(0,0,20,0);
            fqty.setPadding(0,0,20,0);
            tr.addView(fqty);
            tr.addView(funit);
            tr.addView(fname);
            TableRow.LayoutParams p = new TableRow.LayoutParams();
            p.rightMargin = 10;
            tr.setLayoutParams(p);
            ingredients.addView(tr);
        }
        TextView steps = (TextView) this.findViewById(R.id.recipe_display_one_steps);
        steps.setText(recipe.getSteps());

        View hBar;
        RelativeLayout.LayoutParams hParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics())
        );

        TableLayout nutrients = (TableLayout) this.findViewById(R.id.recipe_display_one_nutrients);
        for(Map.Entry<String,Double> nutrient : recipe.getNutrients().entrySet()) {
            hBar = new View(this.getApplicationContext());
            hBar.setLayoutParams(hParams);
            hBar.setBackgroundColor(getResources().getColor(R.color.primary));
            TableRow tr = new TableRow(this);
            TextView nname = new TextView(this);
            TextView nqty = new TextView(this);
            nname.setText(nutrient.getKey());
            nqty.setText(nutrient.getValue().toString());
            nname.setTextSize(16);
            nqty.setTextSize(16);
            nname.setPadding(0,0,20,0);
            nqty.setPadding(0,0,20,0);
            tr.addView(nname);
            tr.addView(nqty);
            nutrients.addView(tr);
            nutrients.addView(hBar);
        }
    }
}
