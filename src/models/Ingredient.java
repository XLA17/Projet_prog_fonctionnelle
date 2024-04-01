package models;

/**
 * This abstract class represents an Ingredient, which serves as a base class for different types of ingredients.
 */
public abstract class Ingredient {
    protected String name;

    /**
     * Constructs an Ingredient with the specified name.
     *
     * @param name The name of the ingredient.
     */
    public Ingredient(String name) {
        this.name = name;
    }

    // Getters and Setters

    /**
     * Retrieves the name of the ingredient.
     *
     * @return The name of the ingredient.
     */
    public String getName() {return name;}

    /**
     * Returns a string representation of the ingredient.
     *
     * @return A string containing the name of the ingredient.
     */
    @Override
    public String toString() {
        return "Ingredient : " + name;
    }
}
