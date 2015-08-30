package cblaho.foodtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by cblaho on 8/30/15.
 */
public class RecipeViewAdapter extends ArrayAdapter<RecipePair> {
    public RecipeViewAdapter(Context context, int layoutResourceId, RecipePair[] data) {
        super(context, layoutResourceId, data);
    }


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
