package cblaho.foodtracker;

/**
 * Created by Karishma on 28-08-2015.
 */
public class UnitConverter {

    private Ingredient ingredient;
    private String desiredUnit;

    public UnitConverter(Ingredient ingredient, String desiredUnits)
    {
        this.ingredient = ingredient;
        this.desiredUnit = desiredUnits;
    }

    public double convert()
    {

        return 0;
    }

    /**
     *
     * @return Ingredient
     */
    public Ingredient getIngredient()
    {
        return this.ingredient;
    }

    /**
     *
     * @param ingredient
     */
    public void setIngredient(Ingredient ingredient)
    {
        this.ingredient = ingredient;
    }

    /**
     *
     * @return String
     */
    public  String getDesiredUnit()
    {
        return this.desiredUnit;
    }

    /**
     *
     * @param desiredUnit
     */
    public void setDesiredUnit(String desiredUnit)
    {
        this.desiredUnit = desiredUnit;
    }


}
