package cblaho.foodtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class FoodTracker extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.recipe_list);
        Intent intent = new Intent(this, RecipeList.class);
        startActivity(intent);
//        NavDrawerItem yourRecipe = new NavDrawerItem(R.drawable.yourrecipe, "Your Recipes");
//        NavDrawerItem addRecipe = new NavDrawerItem(R.drawable.addrecipe, "Add Recipe");
//        NavDrawerItem addIngredient = new NavDrawerItem(R.drawable.addingredient, "Add Ingredient");
//        NavDrawerItem[] navDrawerItems = new NavDrawerItem[]{yourRecipe, addRecipe, addIngredient};
//        ListView mDrawerList = (ListView) findViewById(R.id.navigation_list);
//        final NavAdapter adapter = new NavAdapter(this,R.layout.navrow, navDrawerItems);
//        if (mDrawerList == null || adapter == null){
//            System.err.print("Someone's null.");
//        }
//        mDrawerList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_tracker, menu);
        return true;
    }

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
}
