package Presentation;

import Repositories.RecipeRepo;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.*;

/**
 * Class representing the Textual Mode of the Recipe Application.
 * This mode displays textual information about recipes and their properties.
 */
public class TextualMode {
    // Menu items for treatments

    /**
     * The text area to display the textual content.
     */
    private static TextArea textArea;

    /**
     * The label to display the textual content.
     */
    private static Label label;

    /**
     * Sets up the textual mode by adding menu items for treatments.
     *
     * @param treatmentsMenu The menu where the textual treatments will be added.
     * @param centerBox      The VBox where the textual content will be displayed.
     */
    public static void setTextualMode(Menu treatmentsMenu, VBox centerBox) {
        List<MenuItem> treatmentMenuItems = new ArrayList<>();

        // Menu item to get the list of recipe's titles
        MenuItem getRecipeTitles = new MenuItem("4 - Get recipe titles");
        getRecipeTitles.setOnAction(e -> {
            updateLabel("List of recipe's titles :");
            display(RecipeRepo.getRecipeTitles());
        });
        treatmentMenuItems.add(getRecipeTitles);

        // Menu item to get the total number of eggs used
        MenuItem getTotalEggs = new MenuItem("5 - Get total eggs");
        getTotalEggs.setOnAction(e -> {
            updateLabel("Total number of eggs used :");
            display(RecipeRepo.getTotalEggs());;
        });
        treatmentMenuItems.add(getTotalEggs);

        // Menu item to get recipes with olive oil
        MenuItem getRecipesWithOliveOil = new MenuItem("6 - Get recipes with olive oil");
        getRecipesWithOliveOil.setOnAction(e -> {
            updateLabel("Recipes with olive oil :");
            display(RecipeRepo.getRecipesWithOliveOil());
        });
        treatmentMenuItems.add(getRecipesWithOliveOil);

        // Menu item to get the number of eggs per recipe
        MenuItem getNumberEggsPerRecipe = new MenuItem("7 - Get number eggs per recipe");
        getNumberEggsPerRecipe.setOnAction(e -> {
            updateLabel("Number of eggs per recipe :");
            display(RecipeRepo.getNumberEggsPerRecipe());
        });
        treatmentMenuItems.add(getNumberEggsPerRecipe);

        // Menu item to get recipes with less than 500 calories
        MenuItem getRecipesWithLessThan500Calories = new MenuItem("8 - Get recipes with less than 500 calories");
        getRecipesWithLessThan500Calories.setOnAction(e -> {
            updateLabel("Recipes with less than 500 calories :");
            display(RecipeRepo.getRecipesWithLessThan500Calories());
        });
        treatmentMenuItems.add(getRecipesWithLessThan500Calories);

        // Menu item to get sugar quantity of Zuppa Inglese
        MenuItem getSugarQuantityOfZuppaInglese = new MenuItem("9 - Get sugar quantity of Zuppa Inglese");
        getSugarQuantityOfZuppaInglese.setOnAction(e -> {
            updateLabel("Sugar quantity of Zuppa Inglese :");
            String sugarQuantity = RecipeRepo.getSugarQuantityOfZuppaInglese() + " " + RecipeRepo.getSugarUnitOfZuppaInglese();
            display(sugarQuantity);
        });
        treatmentMenuItems.add(getSugarQuantityOfZuppaInglese);

        // Menu item to get 2 first steps of Zuppa Inglese
        MenuItem get2FirstStepsOfZuppaInglese = new MenuItem("10 - Get 2 first steps of Zuppa Inglese");
        get2FirstStepsOfZuppaInglese.setOnAction(e -> {
            updateLabel("Two first steps of Zuppa Inglese :");
            display(RecipeRepo.get2FirstStepsOfZuppaInglese());
        });
        treatmentMenuItems.add(get2FirstStepsOfZuppaInglese);

        // Menu item to get recipes with more than 5 preparation steps
        MenuItem getRecipesWithMoreThan5PreparationSteps = new MenuItem("11 - Get recipes with more than 5 preparation steps");
        getRecipesWithMoreThan5PreparationSteps.setOnAction(e -> {
            updateLabel("Recipes with more than 5 preparation steps :");
            display(RecipeRepo.getRecipesWithMoreThan5PreparationSteps());
        });
        treatmentMenuItems.add(getRecipesWithMoreThan5PreparationSteps);

        // Menu item to get recipes without butter
        MenuItem getRecipesWithoutButter = new MenuItem("12 - Get recipes without butter");
        getRecipesWithoutButter.setOnAction(e -> {
            updateLabel("Recipes without butter :");
            display(RecipeRepo.getRecipesWithoutButter());
        });
        treatmentMenuItems.add(getRecipesWithoutButter);

        // Menu item to get recipes with ingredients of Zuppa Inglese
        MenuItem getRecipesWithIngredientOfZuppaInglese = new MenuItem("13 - Get recipes with ingredients of Zuppa Inglese");
        getRecipesWithIngredientOfZuppaInglese.setOnAction(e -> {
            updateLabel("Recipes with ingredient of Zuppa Inglese :");
            display(RecipeRepo.getRecipesWithIngredientOfZuppaInglese());
        });
        treatmentMenuItems.add(getRecipesWithIngredientOfZuppaInglese);

        // Menu item to get recipe with most calories
        MenuItem getRecipeWithMostCalories = new MenuItem("14 - Get recipe with most calories");
        getRecipeWithMostCalories.setOnAction(e -> {
            updateLabel("Recipe with the most calories :");
            display(RecipeRepo.getRecipeWithMostCalories());
        });
        treatmentMenuItems.add(getRecipeWithMostCalories);

        // Menu item to get most frequent unit
        MenuItem getMostFrequentUnit = new MenuItem("15 - Get most frequent unit");
        getMostFrequentUnit.setOnAction(e -> {
            updateLabel("Unit the most frequent :");
            display(RecipeRepo.getMostFrequentUnit());
        });
        treatmentMenuItems.add(getMostFrequentUnit);

        // Menu item to get the number of ingredients per recipe
        MenuItem getNumberofIngredientsPerRecipe = new MenuItem("16 - Get number of ingredients per recipe");
        getNumberofIngredientsPerRecipe.setOnAction(e -> {
            updateLabel("Number of ingredients per recipe :");
            display(RecipeRepo.getNumberIngredientsPerRecipe());
        });
        treatmentMenuItems.add(getNumberofIngredientsPerRecipe);

        // Menu item to get recipe with most fat
        MenuItem getRecipeWithMostFat = new MenuItem("17 - Get recipe with most fat");
        getRecipeWithMostFat.setOnAction(e -> {
            updateLabel("Recipe with the most fat :");
            display(RecipeRepo.getRecipeWithMostFat());
        });
        treatmentMenuItems.add(getRecipeWithMostFat);

        // Menu item to get ingredient most used
        MenuItem getIngredientMostUsed = new MenuItem("18 - Get ingredient most used");
        getIngredientMostUsed.setOnAction(e -> {
            updateLabel("Ingredient the most used :");
            display(RecipeRepo.getIngredientMostUsed());
        });
        treatmentMenuItems.add(getIngredientMostUsed);

        // Menu item to get recipes sorted by number of ingredients
        MenuItem getRecipesSortedByNumberIngredients = new MenuItem("19 - Get recipes sorted by number ingredients");
        getRecipesSortedByNumberIngredients.setOnAction(e -> {
            updateLabel("Recipes sorted by number of ingredients :");
            display(RecipeRepo.getRecipesSortedByNumberIngredients());
        });
        treatmentMenuItems.add(getRecipesSortedByNumberIngredients);

        // Menu item to get ingredients and recipes
        MenuItem getIngredientsAndRecipes = new MenuItem("20 - Get ingredients and recipes");
        getIngredientsAndRecipes.setOnAction(e -> {
            updateLabel("Ingredients and the recipes they are used in :");
            display(RecipeRepo.getIngredientsAndRecipes());
        });
        treatmentMenuItems.add(getIngredientsAndRecipes);

        // Menu item to get recipes by preparation step
        MenuItem getRecipesByPreparationStep = new MenuItem("21 - Get recipes by preparation step");
        getRecipesByPreparationStep.setOnAction(e -> {
            updateLabel("Number of preparation steps and recipes with this number of steps :");
            display(RecipeRepo.getRecipesByPreparationStep());
        });
        treatmentMenuItems.add(getRecipesByPreparationStep);

        // Menu item to get recipe with most calories
        MenuItem getEasiestRecipe = new MenuItem("22 - Get easiest recipe");
        getEasiestRecipe.setOnAction(e -> {
            updateLabel("The easiest recipe :");
            display(RecipeRepo.getEasiestRecipe());
        });
        treatmentMenuItems.add(getEasiestRecipe);

        // Add the menu items to the treatments menu
        treatmentsMenu.getItems().addAll(treatmentMenuItems);

        // Set the center box
        setCenterBox(centerBox);
    }

    /**
     * Sets up the center box with label and text area for displaying textual content.
     *
     * @param centerBox The VBox where textual content will be displayed.
     */
    private static void setCenterBox(VBox centerBox) {
        label = new Label("Choose a treatment");
        textArea = new TextArea();
        textArea.setEditable(false);
        centerBox.getChildren().addAll(label, textArea);
    }

    /**
     * Updates the label with the given text.
     *
     * @param text The text to be displayed in the label.
     */
    private static void updateLabel(String text) {
        label.setText(text);
    }

    // Display methods for different types of data

    /**
     * Displays a single object in the text area.
     *
     * @param o The object to be displayed.
     */
    private static void display(Object o) {
        textArea.clear();
        textArea.appendText(o + "\n");
    }

    /**
     * Displays a list of objects in the text area.
     *
     * @param list The list of objects to be displayed.
     */
    private static void display(List list) {
        textArea.clear();
        list.forEach(e -> textArea.appendText(e + "\n"));
    }

    /**
     * Displays a map of objects in the text area.
     *
     * @param map The map of objects to be displayed.
     */
    private static void display(Map map) {
        textArea.clear();
        map.forEach((k, v) -> textArea.appendText(k + " : " + v + "\n"));
    }
}
