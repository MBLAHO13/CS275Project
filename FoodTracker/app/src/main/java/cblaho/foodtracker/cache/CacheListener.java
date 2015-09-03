package cblaho.foodtracker.cache;

import java.util.Map;

import cblaho.foodtracker.data.Food;

/**
 * Created by maxm on 8/29/15.
 * Listener for receiving cache data
 */
public interface CacheListener {
    void onFoodFound(Food f);
    void onSearchResult(Map<String,String> results);
}
