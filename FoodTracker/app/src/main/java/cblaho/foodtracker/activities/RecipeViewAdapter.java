package cblaho.foodtracker.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import cblaho.foodtracker.R;

/**
 * Created by cblaho on 8/30/15.
 * View adapter for Recipes in the main recipe list
 */
public class RecipeViewAdapter extends ArrayAdapter<RecipePair> {
    public RecipeViewAdapter(Context context, int layoutResourceId, RecipePair[] data) {
        super(context, layoutResourceId, data);
    }

    /**
     * Adapter for the recipe List. Sets the text field to the name of the Recipe Pair object..
     * @param position generated by Android OS
     * @param convertView generated by Android OS
     * @param parent generated by Android OS
     * @return the view we have created for the list
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.recipe_list_element, parent, false);
            }

            TextView name = (TextView) convertView.findViewById(R.id.recipe_list_element_name);

            name.setText(this.getItem(position).getName());

            return convertView;
    }

}

//I'm declaring two classes in one file, fight meeee

/**
 * Object to keep id and name together in the listView. Kludgy, but it keeps the data together.
 * Java doesn't have a tuple class, which means I had to write this crap.
 */
class RecipePair{
    String id;
    String name;

    public RecipePair(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
