package cblaho.foodtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchResults extends Activity implements CacheListener{
    private Cache cache;
    Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        cache = new Cache(this, getApplicationContext());
        setContentView(R.layout.activity_search_results);
        Intent i = getIntent();
        final Recipe recipe = i.getParcelableExtra("recipe");
        Map<String, String> results = (HashMap) i.getSerializableExtra("results");
        ArrayAdapter arrayAdapter;
        ListView listView;
        listView = (ListView) findViewById(R.id.search_results_list_view);
        if(!results.isEmpty()) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position,
                                        long id) {
                    Intent intent = new Intent(SearchResults.this, AddUnitsQuantity.class);
                    RecipePair entry = (RecipePair) a.getItemAtPosition(position);
                    System.out.println((String) entry.getId() + (String) entry.getName());
                    intent.putExtra("ingredient", cache.getIngredientById(entry.getId()));
                    intent.putExtra("recipe", recipe);
                    startActivity(intent);
                }
            });

            ArrayList<RecipePair> SearchPairs = new ArrayList<>();
            for (Map.Entry<String, String> entry : results.entrySet()) {
                SearchPairs.add(new RecipePair(entry.getKey(), entry.getValue()));
            }
            RecipePair[] packedPairs = SearchPairs.toArray(new RecipePair[SearchPairs.size()]);
            arrayAdapter = new SearchResultsAdapter(this, R.layout.search_results_row, packedPairs);
        } else {
            //this shit better not happen
            arrayAdapter = new ArrayAdapter(this,R.layout.search_results_row, R.id.search_result_name, new String[] {"No Results Found"});
        }
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onFoodFound(Food f) {
        Intent intent = new Intent(SearchResults.this, AddUnitsQuantity.class);
        intent.putExtra("recipe", this.recipe);
        intent.putExtra("ingredient", f);
        startActivity(intent);
    }

    @Override
    public void onSearchResult(Map<String, String> results) {}
}
