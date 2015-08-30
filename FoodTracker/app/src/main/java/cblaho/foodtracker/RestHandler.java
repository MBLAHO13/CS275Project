package cblaho.foodtracker;

import android.os.AsyncTask;

import java.util.Map;

/**
 * Created by maxm on 8/30/15.
 */
public class RestHandler extends AsyncTask<String,Integer,Map<String,String>> {
    private CacheListener listener;
    public RestHandler(CacheListener listener) {
        super();
        this.listener = listener;
    }

    @Override
    protected Map<String, String> doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Map<String,String> m) {
        listener.onSearchResult(m);
    }
}
