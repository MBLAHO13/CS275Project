package cblaho.foodtracker.cache;

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

import cblaho.foodtracker.data.Ingredient;

/**
 * Created by maxm on 8/30/15.
 * Handles REST requests to and from the server
 */
public class RestHandler extends AsyncTask<String,Integer,JsonObject> {
    private static final String dburl = "http://52.88.94.147:5000/rest/api/";
    private CacheListener listener;

    /**
     * Instantiate the RestHandler
     * @param listener listener to send REST results to
     */
    public RestHandler(CacheListener listener) {
        super();
        this.listener = listener;
    }

    /**
     * Contact the server and send the request for either ID or Name search, then return the JSON response
     * @param params "id" or "name" followed by the id or name to search for
     * @return JSON response from the server
     */
    @Override
    protected JsonObject doInBackground(String... params) {
        HttpURLConnection request = null;
        System.out.println("Making REST Request");
        try {
            URL url = new URL(dburl + params[0] + "/");
            request = (HttpURLConnection) url.openConnection();
            request.setRequestProperty("request-value", params[1]);
            request.connect();
            System.out.println("Connected");
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            System.out.println("Got JSON");
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

    /**
     * Calls the appropriate listener function based on the JSON response
     * @param root JSON response from the server
     */
    @Override
    protected void onPostExecute(JsonObject root) {
        if(root.get("response").getAsString().equals("search")) {
            System.out.println("Calling onSearchResult");
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
            System.out.println("Calling onFoodFound");
            listener.onFoodFound(new Ingredient(root));
        }
    }
}
