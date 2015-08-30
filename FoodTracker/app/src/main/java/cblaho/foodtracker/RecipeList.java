package cblaho.foodtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecipeList extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);


        ListView listView;


        //todo: uncomment for cache class
        //final Map<String, String> recipes = Cache.getRecipes();

        final Map<String, String> recipes = new HashMap<>();
//        recipes.put("0", "mashed potatoes");
//        recipes.put("1", "mashed beer");
//        recipes.put("2", "beer potatoes");


        ArrayAdapter arrayAdapter = null;

        listView = (ListView) findViewById(R.id.recipeList);

        if(!recipes.isEmpty()) {

            listView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position,
                                        long id) {

                    Intent intent = new Intent(RecipeList.this, RecipeDisplayOne.class);
                    RecipePair entry = (RecipePair) a.getItemAtPosition(position);
                    String recipeId = entry.getId();
                    intent.putExtra("identifier", recipeId);

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_recipe_list, menu);
//        return true;
   // }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startRecipeAdd(){
        Intent intent = new Intent(RecipeList.this, RecipeAdd.class);

    }
}
