package cblaho.foodtracker;

/**
 * Created by maxm on 8/30/15.
 */
public class JsonIngredientQty {
    public String unit;
    public Double qty;
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
