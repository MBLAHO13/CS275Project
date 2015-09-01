package cblaho.foodtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class RecipeAdd extends Activity {
    Recipe data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.addingredient);
            Intent intent = getIntent();
            //data = intent.getParcelableExtra(MainActivity.ADDRESS_DATA);
            //TextView title = (TextView) findViewById(R.id.titleText);
    }
}
