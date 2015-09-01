package cblaho.foodtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

public class RecipeList extends FragmentActivity {
    private Cache cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);
        ListView listView;
        this.cache = new Cache(null, getApplicationContext());
        final Map<String, String> recipes = cache.getRecipes();
        ArrayAdapter arrayAdapter;
        listView = (ListView) findViewById(R.id.recipeList);
        if(!recipes.isEmpty()) {
            listView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position,
                                        long id) {
                    Intent intent = new Intent(RecipeList.this, RecipeDisplayOne.class);
                    RecipePair entry = (RecipePair) a.getItemAtPosition(position);
                    intent.putExtra("recipe", cache.getRecipeById(entry.getId()));
                    startActivity(intent);
                }
            });

            ArrayList<RecipePair> recipePairs = new ArrayList<>();
            for (Map.Entry<String, String> entry : recipes.entrySet()) {
                recipePairs.add(new RecipePair(entry.getKey(), entry.getValue()));
            }
            RecipePair[] packedPairs = recipePairs.toArray(new RecipePair[recipePairs.size()]);
            arrayAdapter = new RecipeViewAdapter(this, R.layout.recipe_list_element, packedPairs);
        } else {
            arrayAdapter = new ArrayAdapter(this,R.layout.recipe_list_element, R.id.recipe_list_element_name, new String[] {"No Recipes Found"});
            //todo: make this add a new recipe
        }
        listView.setAdapter(arrayAdapter);
    }

    public void startRecipeAdd(){
        Intent intent = new Intent(RecipeList.this, RecipeAdd.class);
        intent.putExtra("recipe", new Recipe(cache.getNextRecipeId()));
        startActivity(intent);
    }
}
