package cblaho.foodtracker.cache;

/**
 * Created by maxm on 8/30/15.
 * Storage class for ingredient quantities from JSON handler
 * For example, it could be stored as "3 cups" which would be qty=3, unit=cups
 */
public class JsonIngredientQty {
    public String unit;
    public Double qty;

    /**
     * Instantiate the class with a unit and quantity
     * @param unit Food quantity unit
     * @param qty Food quantity
     */
    public JsonIngredientQty(String unit, Double qty) {
        this.unit = unit;
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public Double getQty() {
        return qty;
    }
}
