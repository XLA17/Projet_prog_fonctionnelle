package Presentation;

import Repositories.RecipeRepo;

import java.io.File;

public class MainConsole {

    public static void main(String[] args) throws Exception {
        File xmlFile = new File("src/recipes.xml");

        RecipeRepo.init(xmlFile);

        System.out.println("4 - List of recipe's titles :");
        RecipeRepo.getRecipeTitles().forEach(r -> System.out.println("- " + r));

        System.out.println("\n5 - Total number of eggs used : " + RecipeRepo.getTotalEggs());

        System.out.println("\n6 - Recipes with olive oil :");
        RecipeRepo.getRecipesWithOliveOil().forEach(r -> System.out.println("- " + r));

        System.out.println("\n7 - Number of eggs per recipe :");
        RecipeRepo.getNumberEggsPerRecipe().forEach((k, v) -> System.out.println("- " + k.getTitle() + " : " + v));

        System.out.println("\n8 - Recipes with less than 500 calories :");
        RecipeRepo.getRecipesWithLessThan500Calories().forEach(r -> System.out.println("- " + r));

        System.out.println("\n9 - Sugar quantity of Zuppa Inglese : " + RecipeRepo.getSugarQuantityOfZuppaInglese() + " " + RecipeRepo.getSugarUnitOfZuppaInglese());

        System.out.println("\n10 - Two first steps of Zuppa Inglese :");
        RecipeRepo.get2FirstStepsOfZuppaInglese().forEach(System.out::println);

        System.out.println("\n11 - Recipes with more than 5 preparation steps :");
        RecipeRepo.getRecipesWithMoreThan5PreparationSteps().forEach(r -> System.out.println("- " + r));

        System.out.println("\n12 - Recipes without butter :");
        RecipeRepo.getRecipesWithoutButter().forEach(r -> System.out.println("- " + r));

        System.out.println("\n13 - Recipes with ingredient of Zuppa Inglese :");
        RecipeRepo.getRecipesWithIngredientOfZuppaInglese().forEach(r -> System.out.println("- " + r));

        System.out.println("\n14 - Recipe with the most calories : " + RecipeRepo.getRecipeWithMostCalories());

        System.out.println("\n15 - Unit the most frequent : " + RecipeRepo.getMostFrequentUnit());

        System.out.println("\n16 - Number of ingredients per recipe :");
        RecipeRepo.getNumberIngredientsPerRecipe().forEach((k, v) -> System.out.println("- " + k.getTitle() + " : " + v));

        System.out.println("\n17 - Recipe with the most fat : " + RecipeRepo.getRecipeWithMostFat());

        System.out.println("\n18 - Ingredient the most used : " + RecipeRepo.getIngredientMostUsed());

        System.out.println("\n19 - Recipes sorted by number of ingredients :");
        RecipeRepo.getRecipesSortedByNumberIngredients().forEach(r -> System.out.println("- " + r));

        System.out.println("\n20 - Ingredients and the recipes they are used in :");
        RecipeRepo.getIngredientsAndRecipes().forEach((k, v) -> System.out.println("- " + k + " : " + v));

        System.out.println("\n21 - Number of preparation steps and recipes with this number of steps :");
        RecipeRepo.getRecipesByPreparationStep().forEach((k, v) -> System.out.println("- " + k + " : " + v));

        System.out.println("\n22 - The easiest recipe : " + RecipeRepo.getEasiestRecipe());
    }
}