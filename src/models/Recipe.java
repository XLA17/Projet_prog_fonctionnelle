package models;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a recipe.
 */
public class Recipe {
    // Attributes
    private String id;
    private String title;
    private String date;
    private List<Ingredient> ingredients;
    private List<String> preparationSteps;
    private String comment;
    private Map<String, String> nutrition;
    private String related_ref;
    private String related_str;

    /**
     * Constructs a Recipe object with the provided parameters.
     *
     * @param id The ID of the recipe.
     * @param title The title of the recipe.
     * @param date The date of the recipe.
     * @param ingredients The list of ingredients required for the recipe.
     * @param preparationSteps The list of preparation steps for the recipe.
     * @param comment Additional comments or notes about the recipe.
     * @param nutrition Nutritional information for the recipe.
     * @param related_ref Reference of related recipes.
     * @param related_str Sentence about related recipes.
     */
    public Recipe(String id, String title, String date, List<Ingredient> ingredients, List<String> preparationSteps, String comment, Map<String, String> nutrition, String related_ref, String related_str) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.ingredients = ingredients;
        this.preparationSteps = preparationSteps;
        this.comment = comment;
        this.nutrition = nutrition;
        this.related_ref = related_ref;
        this.related_str = related_str;
    }

    // Getters

    /**
     * @return The ID of the recipe.
     */
    public String getId() {return id;}
    /**
     * @return The title of the recipe.
     */
    public String getTitle() {return title;}
    /**
     * @return The date of the recipe.
     */
    public String getDate() {return date;}
    /**
     * @return The list of ingredients required for the recipe.
     */
    public List<Ingredient> getIngredients() {return ingredients;}
    /**
     * @return The list of preparation steps for the recipe.
     */
    public List<String> getPreparationSteps() {return preparationSteps;}
    /**
     * @return Additional comments or notes about the recipe.
     */
    public String getComment() {return comment;}
    /**
     * @return Nutritional information for the recipe.
     */
    public Map<String, String> getNutrition() {return nutrition;}
    /**
     * @return Reference of related recipes.
     */
    public String getRelated_ref() {return related_ref;}
    /**
     * @return Sentence about related recipes.
     */
    public String getRelated_str() {return related_str;}

    /**
     * Retrieves all preparation steps for the recipe, including steps from nested recipes.
     *
     * @return The list of all preparation steps for the recipe.
     */
    public List<String> getAllPreparationSteps(){
        // Create a new list of preparation steps and add the current list of preparation steps
        List<String> preparationSteps = new ArrayList<>();
        preparationSteps.addAll(this.preparationSteps);

        return getAllPreparationSteps(preparationSteps, this.ingredients); // Call the private method with the list of ingredients
    }

    /**
     * Private and recursive method to retrieve all preparation steps for the recipe
     *
     * @param preparationSteps The current list of preparation steps for the recipe.
     * @param ingredients The list of ingredients to scan for nested preparation steps.
     * @return The list of all preparation steps for the recipe.
     */

    private List<String> getAllPreparationSteps(List<String> preparationSteps, List<Ingredient> ingredients){
        // Retrieve ingredients contains in ingredients of type Ingredient_recipe
        List<Ingredient> new_ingredients = ingredients.stream()
                .filter(ingredient -> ingredient instanceof Ingredient_recipe)
                .map(i -> (Ingredient_recipe)i)
                .map(Ingredient_recipe::getIngredients)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        if (new_ingredients.isEmpty()) {
            // If there are no more ingredients of type Ingredient_recipe, return the list of preparation steps
            return preparationSteps;
        }
        else {
            // Else, retrieve the preparation steps of the ingredients of type Ingredient_recipe
            List<String> steps = ingredients.stream()
                    .filter(ingredient -> ingredient instanceof Ingredient_recipe)
                    .map(i -> (Ingredient_recipe)i)
                    .map(Ingredient_recipe::getPreparationSteps)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());

            // Concatenate the preparation steps of the ingredients of type Ingredient_recipe with the current list of preparation steps
            List<String> listSteps = Stream.concat(preparationSteps.stream(), steps.stream())
                    .collect(Collectors.toList());

            // Call the method recursively with the new list of ingredients of type Ingredient_recipe
            return getAllPreparationSteps(listSteps, new_ingredients);
        }
    }

    /**
     * Retrieves all ingredients required for the recipe, including ingredients from nested recipes.
     *
     * @return The list of all ingredients required for the recipe.
     */
    public List<Ingredient_simple> getAllIngredients(){
        return getAllIngredients(ingredients); // Call the private method with the list of ingredients
    }

    /**
     * Private and recursive method to retrieve all ingredients required for the recipe
     *
     * @param ingredients ingredients The list of ingredients to scan for nested ingredients.
     * @return The list of all ingredients required for the recipe.
     */
    private List<Ingredient_simple> getAllIngredients(List<Ingredient> ingredients){
        // Retrieve ingredients of type Ingredient_simple
        List<Ingredient_simple> ingredientSimples = ingredients.stream()
                .filter(ingredient -> ingredient instanceof Ingredient_simple)
                .map(i -> (Ingredient_simple)i)
                .collect(Collectors.toList());

        // Retrieve ingredients contains in ingredients of type Ingredient_recipe
        List<Ingredient> new_ingredients = ingredients.stream()
                .filter(ingredient -> ingredient instanceof Ingredient_recipe)
                .map(i -> (Ingredient_recipe)i)
                .map(Ingredient_recipe::getIngredients)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        if (new_ingredients.isEmpty()) {
            // If there are no more ingredients of type Ingredient_recipe, return the list of ingredients of type Ingredient_simple
            return ingredientSimples;
        }
        else {
            // Else, concatenate the list of ingredients of type Ingredient_simple with the list of ingredients of type Ingredient_recipe returns by the method recursively
            return Stream.concat(ingredientSimples.stream(), getAllIngredients(new_ingredients).stream())
                    .collect(Collectors.toList());
        }
    }

    /**
     * Checks if the recipe contains a specific ingredient.
     *
     * @param ingredientName The name of the ingredient to check for.
     * @return True if the ingredient is found, false otherwise.
     */
    public boolean contains(String ingredientName){
        return getAllIngredients().stream()
                .anyMatch(i -> i.getName().equals(ingredientName));
    }

    /**
     * Retrieves the total number of ingredients with the specified name in the recipe.
     *
     * @param ingredientName The name of the ingredient.
     * @return The total number of ingredients with the specified name.
     */
    public double getNumberIngredients(String ingredientName){
        String unit = getAllIngredients().stream()
                .filter(i -> i.getName().contains(ingredientName))
                .map(Ingredient_simple::getUnit)
                .findFirst()
                .orElse(null);

        // return true if all ingredients with the specified name have the same unit
        boolean isSameUnit = getAllIngredients().stream()
                .filter(i -> i.getName().contains(ingredientName))
                .allMatch(i -> i.getUnit().equals(unit));

        if (isSameUnit){
            // return the sum of the amount of all ingredients with the specified name
            return getAllIngredients().stream()
                    .filter(i -> i.getName().contains(ingredientName))
                    .map(i -> Double.parseDouble(i.getAmount()))
                    .reduce(0.0, Double::sum);
        }
        else {
            // return 0 and print an error message if the units are not the same
            try {
                throw new Exception("IMPOSSIBLE ! Units are not the same.");
            } catch (Exception e) {
                e.printStackTrace();
                return 0.0;
            }
        }
    }

    /**
     * Checks if the recipe contains at least one ingredient from the provided list of Ingredient_simple.
     *
     * @param ingredientsSimple The list of Ingredient_simple objects to check against.
     * @return True if the recipe contains at least one ingredient from the provided list, otherwise false.
     */
    public boolean containsOne(List<Ingredient_simple> ingredientsSimple){
        return ingredientsSimple.stream()
                .anyMatch(i -> getAllIngredients().stream()
                        .anyMatch(i2 -> i2.getName().equals(i.getName())));
    }

    /**
     * Returns a string representation of the recipe.
     *
     * @return A string containing the recipe's ID and title.
     */
    @Override
    public String toString() {
        return "Recipe " + id + ": " + title;
    }
}
