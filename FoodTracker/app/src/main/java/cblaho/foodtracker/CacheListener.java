package cblaho.foodtracker;

import com.google.gson.JsonArray;

/**
 * Created by maxm on 8/29/15.
 */
public interface CacheListener {
    void onProgressUpdate(int progress);
    void onFoodFound(Food f);
    void onSearchResult(JsonArray results);
}
