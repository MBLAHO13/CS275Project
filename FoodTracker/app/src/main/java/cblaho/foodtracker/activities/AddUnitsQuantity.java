package cblaho.foodtracker.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import cblaho.foodtracker.R;
import cblaho.foodtracker.data.Food;
import cblaho.foodtracker.data.Recipe;

public class AddUnitsQuantity extends Activity implements AdapterView.OnItemSelectedListener {
    private Recipe recipe;
    private Food ingredient;

    /**
     * Sets the dropdown menu to the available units. Unpacks the recipe and ingredient.
     * @param savedInstanceState Android OS generated
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_units_quantity);
        Intent intent = getIntent();
        this.recipe = intent.getParcelableExtra("recipe");
        this.ingredient = intent.getParcelableExtra("ingredient");
        ActionBar ab = getActionBar();
        if(ab != null) {
            ab.setTitle(ingredient.getName());
        }
        Spinner spinner = (Spinner) findViewById(R.id.add_units_quantity_dropdown);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new ArrayList<>(ingredient.getConversions().keySet())
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * Adds an ingredient to a recipe, along with the quantity. Called by both onClick handlers.
     */
    private void addIngredientToRecipe() {
        ingredient.setQty(Double.parseDouble(((EditText) findViewById(R.id.add_units_quantity_quantity)).getText().toString()));
        recipe.addIngredient(ingredient);
    }

    /**
     * Onclick handler for the "Done" button on the addUnitsQuantity layout.
     * Adds and ingredient and starts the Recipe Steps activity.
     * @param view Android OS generated
     */
    public void onDoneIngredients(View view) {
        addIngredientToRecipe();
        Intent intent = new Intent(AddUnitsQuantity.this, RecipeSteps.class);
        intent.putExtra("recipe", this.recipe);
        startActivity(intent);
    }

    /**
     * Onclick handler for the "Add Another" button on the addUnitsQuantity layout.
     * Adds and ingredient and restarts the AddIngredient activity.
     * @param view Android OS generated
     */
    public void onAddAnother(View view) {
        addIngredientToRecipe();
        Intent intent = new Intent(AddUnitsQuantity.this, AddIngredient.class);
        intent.putExtra("recipe", this.recipe);
        startActivity(intent);
    }

    /**
     * Onclick handler for the spinner. Sets the conversion on the ingredient.
     * @param parent Android OS generated
     * @param view Android OS generated
     * @param position position of item clicked
     * @param id Android OS generated
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.ingredient.setConversion((String) parent.getItemAtPosition(position));
    }

    /**
     * Handler for none selected. Sets the Conversion to null.
     * @param parent Android OS generated
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        this.ingredient.setConversion(null);
    }
}
