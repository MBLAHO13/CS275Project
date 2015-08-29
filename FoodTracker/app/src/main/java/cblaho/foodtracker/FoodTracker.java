package cblaho.foodtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class FoodTracker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracker);
        NavDrawerItem yourRecipe = new NavDrawerItem(R.drawable.yourRecipe, "Your Recipes");
        NavDrawerItem addRecipe = new NavDrawerItem(R.drawable.addRecipe, "Add Recipe");
        NavDrawerItem addIngredient = new NavDrawerItem(R.drawable.addIngredient, "Add Ingredients");
        NavDrawerItem[] navDrawerItems = new NavDrawerItem[]{yourRecipe, addRecipe, addIngredient};
        ListView mDrawerList = (ListView) findViewById(R.id.navigation_list);
        mDrawerList.setAdapter(new NavAdapter(this, R.layout.navrow, navDrawerItems));
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
