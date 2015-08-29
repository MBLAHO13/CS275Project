package cblaho.foodtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class RecipeList extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);


        ListView listView;

        //Todo: Actually input recipe data
        String[] from = { "mashed potatoes","sandwich","chips","beer" };

        ArrayAdapter arrayAdapter;

        listView = (ListView) findViewById(R.id.RecipeView);

        arrayAdapter = new ArrayAdapter(this,R.layout.recipedisplay, R.id.recipeName, from);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {

                Intent intent = new Intent(RecipeList.this, RecipeView.class);
                String entry =  (String) a.getItemAtPosition(position);
                String recipeId = "r0";
                intent.putExtra("identifier", recipeId );

                startActivity(intent);
            }
        });

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
