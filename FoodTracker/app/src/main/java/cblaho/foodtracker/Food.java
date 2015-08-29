package cblaho.foodtracker;

import java.util.Map;

/**
 * Created by maxm on 8/29/15.
 */
public interface Food {
    String getID();
    String getName();
    Double getNutrient(String name);
    Double getQty();
    void setQty(Double qty                                       );
    Map<String, Double> getConversions();
    String getConversion();
    void setConversion(String name);
    void addConversion(String name, Double grams);
}
