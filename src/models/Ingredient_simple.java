package models;

/**
 * This class represents an Ingredient_simple, which is a type of ingredient with a specified amount and unit.
 */
public class Ingredient_simple extends Ingredient {
    private String amount;
    private String unit;

    /**
     * Constructs an Ingredient_simple with the provided parameters.
     *
     * @param name The name of the ingredient.
     * @param amount The amount of the ingredient.
     * @param unit The unit of measurement for the ingredient.
     */
    public Ingredient_simple(String name, String amount, String unit) {
        super(name);
        this.amount = amount;
        this.unit = unit;
    }

    // Getters

    /**
     * Retrieves the amount of the ingredient.
     *
     * @return The amount of the ingredient.
     */
    public String getAmount() {return amount;}

    /**
     * Retrieves the unit of measurement for the ingredient.
     *
     * @return The unit of measurement for the ingredient.
     */
    public String getUnit() {return unit;}

    /**
     * Returns a string representation of the Ingredient_simple.
     *
     * @return A string containing the name of the ingredient and specifying it as a simple ingredient.
     */
    @Override
    public String toString() {
        return super.toString() + " (simple)";
    }
}
