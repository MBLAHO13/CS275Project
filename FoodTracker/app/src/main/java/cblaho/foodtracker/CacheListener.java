package cblaho.foodtracker;

import java.util.Map;

/**
 * Created by maxm on 8/29/15.
 */
public interface CacheListener {
    void onFoodFound(Food f);
    void onSearchResult(Map<String,String> results);
}
