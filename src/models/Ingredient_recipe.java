package models;

import java.util.*;

/**
 * This class represents an Ingredient_recipe, which is a type of ingredient that consists of a list of ingredients and preparation steps.
 */
public class Ingredient_recipe extends Ingredient {
    private List<Ingredient> ingredients;
    private List<String> preparationSteps;

    /**
     * Constructs an Ingredient_recipe with the provided parameters.
     *
     * @param name The name of the ingredient.
     * @param ingredients The list of ingredients.
     * @param preparationSteps The list of preparation steps.
     */
    public Ingredient_recipe(String name, List<Ingredient> ingredients, List<String> preparationSteps) {
        super(name);
        this.ingredients = ingredients;
        this.preparationSteps = preparationSteps;
    }

    // Getters

    /**
     * Retrieves the list of ingredients contained in this Ingredient_recipe.
     *
     * @return The list of ingredients.
     */
    public List<Ingredient> getIngredients() {return ingredients;}

    /**
     * Retrieves the list of preparation steps for this Ingredient_recipe.
     *
     * @return The list of preparation steps.
     */
    public List<String> getPreparationSteps() {return preparationSteps;}

    /**
     * Returns a string representation of the Ingredient_recipe.
     *
     * @return A string containing the name of the ingredient and specifying it as a recipe.
     */
    @Override
    public String toString() {
        return super.toString() + " (Recipe)";
    }
}
