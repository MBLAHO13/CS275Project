package cblaho.foodtracker;

/**
 * Created by cblaho on 8/26/15.
 */
public class Ingredient {
    //TODO: Revise basic properties
    String currentUnit;
    String name;
    double quantity;

    public Ingredient(String name, String currentUnit, double quantity)
    {
        this.name = name;
        this.currentUnit = currentUnit;
        this.quantity = quantity;
    }

    public String getName()
    {
        return this.name;
    }

    public String getCurrentUnit()
    {
        return this.currentUnit;
    }

    public double getQuantity()
    {
        return this.quantity;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setCurrentUnit(String currentUnit)
    {
        this.currentUnit = currentUnit;
    }
    public void setQuantity(double quantity)
    {
        this.quantity = quantity;
    }


}
