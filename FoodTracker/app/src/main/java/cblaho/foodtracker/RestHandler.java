package cblaho.foodtracker;

import android.os.AsyncTask;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by maxm on 8/30/15.
 */
public class RestHandler extends AsyncTask<String,Integer,JsonObject> {
    private static final String dburl = "http://52.88.94.147:5000/rest/api/";
    private CacheListener listener;
    public RestHandler(CacheListener listener) {
        super();
        this.listener = listener;
    }

    @Override
    protected JsonObject doInBackground(String... params) {
        HttpURLConnection request = null;
        try {
            URL url = new URL(dburl + params[0] + "/");
            request = (HttpURLConnection) url.openConnection();
            request.setRequestProperty("request-value", params[1]);
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            return root.getAsJsonObject();
        } catch(IOException e) {
            System.out.println("Failed to connect to the internet somehow");
            e.printStackTrace();
            return null;
        } finally {
            if(request != null) {
                request.disconnect();
            }
        }
    }

    @Override
    protected void onPostExecute(JsonObject root) {
        if(root.get("response").getAsString().equals("search")) {
            // format search results
            Map<String,String> search = new HashMap<>();
            for(JsonElement e : root.get("result").getAsJsonArray()) {
                search.put(
                        e.getAsJsonObject().get("id").getAsString(),
                        e.getAsJsonObject().get("name").getAsString()
                );
            }
            listener.onSearchResult(search);
        } else {
            listener.onFoodFound(new Ingredient(root));
        }
    }
}
