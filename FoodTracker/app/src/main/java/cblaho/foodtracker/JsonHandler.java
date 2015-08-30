package cblaho.foodtracker;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by maxm on 8/30/15.
 */
public class JsonHandler {
    private Context context;
    private String filename;
    private JsonObject root;
    public JsonHandler(String id, Context context) throws FileNotFoundException {
        this.filename = id + ".json";
        this.context = context;
        this.root = (new JsonParser())
                .parse(new InputStreamReader(context.openFileInput(filename)))
                .getAsJsonObject();
    }

    public JsonHandler(Recipe r, Context context) {
        this.context = context;
        this.filename = r.getID() + ".json";
        this.root = new JsonObject();
        this.root.addProperty("name", r.getName());
        this.root.addProperty("steps", r.getSteps());
        JsonArray conversions = new JsonArray();
        for(Map.Entry<String,Double> conversion : r.getConversions().entrySet()) {
            JsonObject conv = new JsonObject();
            conv.addProperty("unit", conversion.getKey());
            conv.addProperty("grams", conversion.getValue());
            conversions.add(conv);
        }
        this.root.add("conversions", conversions);
        JsonArray ingredients = new JsonArray();
        for(Food ingredient : r.getIngredients()) {
            JsonObject i = new JsonObject();
            i.addProperty("id", ingredient.getID());
            i.addProperty("unit", ingredient.getConversion());
            i.addProperty("qty", ingredient.getQty());
            ingredients.add(i);
        }
        this.root.add("ingredients", ingredients);
    }

    public JsonHandler(Ingredient i, Context context) {
        this.context = context;
        this.filename = i.getID() + ".json";
        this.root = new JsonObject();
        this.root.addProperty("name", i.getName());
        JsonArray conversions = new JsonArray();
        for(Map.Entry<String,Double> conversion : i.getConversions().entrySet()) {
            JsonObject conv = new JsonObject();
            conv.addProperty("unit", conversion.getKey());
            conv.addProperty("grams", conversion.getValue());
            conversions.add(conv);
        }
        this.root.add("conversions", conversions);
    }

    public void write() {
        FileOutputStream fos;
        try {
            fos = context.openFileOutput(filename, context.MODE_APPEND);
        } catch(FileNotFoundException e) {
            try {
                fos = context.openFileOutput(filename, context.MODE_PRIVATE);
            } catch(FileNotFoundException e2) {
                return;
            }
        }
        try {
            fos.write((new Gson().toJson(root)).getBytes());
        } catch(IOException e) {
        } finally {
            try {
                fos.close();
            } catch (IOException e2) {}
        }
    }

    // Ingredient Methods
    public Map<String,Double> getConversions() {
        HashMap<String,Double> conversions = new HashMap<>();
        for(JsonElement e : root.get("conversions").getAsJsonArray()) {
            JsonObject o = e.getAsJsonObject();
            conversions.put(o.get("unit").getAsString(), o.get("grams").getAsDouble());
        }
        return conversions;
    }

    // Recipe Methods
    public Map<String, JsonIngredientQty> getIngredients() {
        Map<String,JsonIngredientQty> ingredients = new HashMap<>();
        for(JsonElement e : root.get("ingredients").getAsJsonArray()) {
            String id = e.getAsJsonObject().get("id").getAsString();
            JsonIngredientQty qty = new JsonIngredientQty(
                    e.getAsJsonObject().get("unit").getAsString(),
                    e.getAsJsonObject().get("qty").getAsDouble()
            );
            ingredients.put(id, qty);
        }
        return ingredients;
    }

    public String getSteps() {
        return root.get("steps").getAsString();
    }

    public String getName() {
        return root.get("name").getAsString();
    }
}
