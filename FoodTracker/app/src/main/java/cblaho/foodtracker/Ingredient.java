package cblaho.foodtracker;

/**
 * Created by cblaho on 8/26/15.
 */
public class Ingredient {
    //TODO: Revise basic properties
    String currentUnit;
    String name;
    String id;
    double quantity;

    public Ingredient(String name, String id, String currentUnit, double quantity)
    {
        this.name = name;
        this.currentUnit = currentUnit;
        this.quantity = quantity;
        this.id = id;
    }

    /**
     *
     * @return String
     */
    public String getName()
    {
        return this.name;
    }

    /**
     *
     * @return String
     */
    public String getCurrentUnit()
    {
        return this.currentUnit;
    }

    /**
     *
     * @return double
     */
    public double getQuantity()
    {
        return this.quantity;
    }

    /**
     *
     * @return String
     */
    public String getId()
    {
        return this.id;
    }

    /**
     *
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     *
     * @param currentUnit
     */
    public void setCurrentUnit(String currentUnit)
    {
        this.currentUnit = currentUnit;
    }

    /**
     *
     * @param quantity
     */
    public void setQuantity(double quantity)
    {
        this.quantity = quantity;
    }

    public void setId(String id)
    {
        this.id = id;
    }


}
