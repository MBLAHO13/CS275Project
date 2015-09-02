package cblaho.foodtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddUnitsQuantity extends Activity {
    private Recipe recipe;
    private Food ingredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_units_quantity);
        Intent intent = getIntent();
        this.recipe = intent.getParcelableExtra("recipe");
        this.ingredient = intent.getParcelableExtra("ingredient");
        getActionBar().setTitle(ingredient.getName());
    }

    public void onDoneIngredients(View view) {
        Intent intent = new Intent(AddUnitsQuantity.this, RecipeSteps.class);
    }

    public void onAddAnother(View view) {
        Intent intent = new Intent(AddUnitsQuantity.this, AddIngredient.class);
        intent.putExtra("recipe", this.recipe);
        startActivity(intent);
    }
}
