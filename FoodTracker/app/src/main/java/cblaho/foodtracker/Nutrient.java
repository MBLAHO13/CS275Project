package cblaho.foodtracker;

/**
 * Created by cblaho on 8/27/15.
 */
public class Nutrient {
    public String getUnit() {
        return unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    String unit;
    int quantity;

    public Nutrient(String unit, int quantity){
        this.unit = unit;
        this.quantity = quantity;
    }


}
