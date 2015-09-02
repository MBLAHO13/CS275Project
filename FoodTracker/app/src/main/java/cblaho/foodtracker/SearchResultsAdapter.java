package cblaho.foodtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by cblaho on 9/2/15.
 */
public class SearchResultsAdapter extends ArrayAdapter<RecipePair> {
    public SearchResultsAdapter(Context context, int layoutResourceId, RecipePair[] data) {
        super(context, layoutResourceId, data);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.search_results_row, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.search_result_name);

        name.setText(this.getItem(position).getName());

        return convertView;
    }

}
