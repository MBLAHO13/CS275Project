package cblaho.foodtracker.cache;

import java.util.Map;

import cblaho.foodtracker.data.Food;

/**
 * Created by maxm on 8/29/15.
 * Listener for receiving cache data
 */
public interface CacheListener {
    /**
     * Called if a Food object is successfully found by the Cache
     * @param f Food object created by the cache
     */
    void onFoodFound(Food f);

    /**
     * Called if nothing was found by the Cache and a Search was conducted
     * @param results Mapping of IDs to Names of ingredients
     */
    void onSearchResult(Map<String,String> results);
}
