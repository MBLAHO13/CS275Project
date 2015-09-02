package cblaho.foodtracker;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import java.util.Map;

public class RecipeAdd extends Activity implements CacheListener {
    Recipe data;
    private Cache cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.cache = new Cache(this, getApplicationContext());
        setContentView(R.layout.addingredient);
        ActionBar ab = getActionBar();
        ab.setTitle("Add Ingredients");
        //todo: sort out the steps
        ab.setSubtitle("Step One of Two");
        Intent intent = getIntent();
        //data = intent.getParcelableExtra(MainActivity.ADDRESS_DATA);
        //TextView title = (TextView) findViewById(R.id.titleText);
    }

    public void searchIngredient(){
        EditText mEdit   = (EditText)findViewById(R.id.recipe_add_searchbox);
        String query = mEdit.getText().toString();
        cache.searchFood(query);

    }

    @Override
    public void onFoodFound(Food f) {
        //pass information to add unit/quantity activity
    }

    @Override
    public void onSearchResult(Map<String, String> results) {
        //pass information to the list view thing
    }
}
