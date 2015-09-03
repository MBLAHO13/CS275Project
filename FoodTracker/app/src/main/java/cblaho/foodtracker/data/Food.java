package cblaho.foodtracker.data;

import android.os.Parcelable;

import java.util.Map;

/**
 * Created by maxm on 8/29/15.
 * Common interface to be able to handle Ingredients and Recipes generically
 */
public interface Food extends Parcelable {
    String getID();
    String getName();
    String getGroup();
    Map<String,Double> getNutrients();
    Double getNutrient(String name);
    Double getQty();
    Double getGrams();
    void setQty(Double qty);
    Map<String, Double> getConversions();
    String getConversion();
    void setConversion(String name);
    void addConversion(String name, Double grams);
}
