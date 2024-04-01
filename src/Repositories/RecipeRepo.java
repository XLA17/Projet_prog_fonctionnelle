package Repositories;

import models.*;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class represents a repository for managing Recipe objects.
 */
public class RecipeRepo {
    private static List<Recipe> recipes = new ArrayList<>();

    /**
     * Initializes the recipe repository by parsing data from an XML file.
     *
     * @param file The XML file containing recipe data.
     * @throws Exception If an error occurs during parsing.
     */
    public static void init(File file) throws Exception {
        // Create a Document object representing the XML file's data as a hierarchy of Node objects.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        doc.getDocumentElement().normalize();

        // Find all <recipe> elements
        NodeList recipeNodeList = doc.getElementsByTagName("rcp:recipe");

        // Iterate over <recipe> elements
        for (int i = 0; i < recipeNodeList.getLength(); i++) {
            Node recipeNode = recipeNodeList.item(i);

            if (recipeNode.getNodeType() == Node.ELEMENT_NODE) { // Checks if the current node is an Element node.
                Element recipeElement = (Element)recipeNode;     // Casts the node to an Element.

                String id = recipeElement.getAttribute("id"); // Retrieves the value of the "id" attribute of the <recipe> element.

                NodeList recipeChildNodes = recipeNode.getChildNodes(); // Retrieves a NodeList of child nodes of the <recipe> element.

                String title = null;
                String date = null;
                List<Ingredient> ingredients = new ArrayList<>();
                List<String> preparationSteps = new ArrayList<>();
                String comment = null;
                Map<String, String> nutrition = new HashMap<>();
                String related_ref = null;
                String related_str = null;

                // Iterate over child elements of <recipe>
                for (int j = 0; j < recipeChildNodes.getLength(); j++){
                    if (recipeChildNodes.item(j).getNodeType() == Node.ELEMENT_NODE){       // Checks if the current child node is an Element node.
                        Element recipeChildElement = (Element) recipeChildNodes.item(j);    // Casts the child node to an Element.
                        switch(recipeChildElement.getTagName()){                            // Switch statement based on the tag name of the child element.
                            case "rcp:title":
                                title = recipeChildElement.getTextContent();
                                break;
                            case "rcp:date":
                                date = recipeChildElement.getTextContent();
                                break;
                            case "rcp:ingredient":
                                // Add ingredients to the recipe
                                addIngredient(recipeChildElement, ingredients);
                                break;
                            case "rcp:preparation":
                                // Add preparation steps to the recipe
                                NodeList stepsNodeList = recipeChildElement.getElementsByTagName("rcp:step"); // Retrieves a NodeList of <rcp:step> elements.
                                for (int k = 0; k < stepsNodeList.getLength(); k++) {                         // Iterates through each <rcp:step> element.
                                    preparationSteps.add(stepsNodeList.item(k).getTextContent());             // Adds the text content of each <rcp:step> element to the preparationSteps list.
                                }
                                break;
                            case "rcp:nutrition":
                                // Extract nutrition information
                                NamedNodeMap attributesList = recipeChildElement.getAttributes();     // Retrieves a NamedNodeMap of attributes of the <rcp:nutrition> element.
                                for (int k = 0; k < attributesList.getLength(); k++) {                // Iterates through each attribute of the <rcp:nutrition> element.
                                    Node attribute = attributesList.item(k);                          // Retrieves the current attribute node.
                                    nutrition.put(attribute.getNodeName(), attribute.getNodeValue()); // Adds the attribute name-value pair to the nutrition map.
                                }
                                break;
                            case "rcp:comment":
                                comment = recipeChildElement.getTextContent();
                                break;
                            case "rcp:related":
                                related_ref = recipeChildElement.getAttribute("ref"); // Retrieves the value of the "ref" attribute of the <rcp:related> element.
                                related_str = recipeChildElement.getTextContent();          // Retrieves the text content of the <rcp:related> element.
                                break;
                            default:
                                break;
                        }
                    }
                }

                // Create a Recipe object and add it to the repository
                Recipe recipe = new Recipe(id, title, date, ingredients, preparationSteps, comment, nutrition, related_ref, related_str);
                recipes.add(recipe);
            }
        }
    }

    /**
     * Adds an ingredient to the list of ingredients for a recipe.
     * This method recursively adds nested ingredients and their preparation steps.
     *
     * @param ingredientElement The XML element representing the ingredient.
     * @param ingredientsList   The list of ingredients to which the ingredient will be added.
     */
    private static void addIngredient(Element ingredientElement, List<Ingredient> ingredientsList){
        String name = ingredientElement.getAttribute("name"); // Retrieves the name of the ingredient.

        if (ingredientElement.hasChildNodes()) {
            // If the ingredient has child elements, it is an Ingredient_recipe

            List<Ingredient> ingredients = new ArrayList<>();
            List<String> preparationSteps = new ArrayList<>();

            NodeList ingredientChildNodes = ingredientElement.getChildNodes(); // Retrieves a NodeList of child nodes of the ingredient element.

            // Iterate over child nodes of the ingredient
            for (int i = 0; i < ingredientChildNodes.getLength(); i++) {
                if (ingredientChildNodes.item(i).getNodeType() == Node.ELEMENT_NODE){        // Checks if the current child node is an Element node.
                    Element ingredientChildElement = (Element) ingredientChildNodes.item(i); // Casts the child node to an Element.
                    switch (ingredientChildElement.getTagName()) {                           // Switch statement based on the tag name of the child element.
                        case "rcp:ingredient":
                            // Recursively add nested ingredients
                            addIngredient(ingredientChildElement, ingredients);
                            break;
                        case "rcp:preparation":
                            // Add preparation steps for the ingredient
                            NodeList stepsNodeList = ingredientChildElement.getElementsByTagName("rcp:step"); // Retrieves a NodeList of <rcp:step> elements.
                            for (int j = 0; j < stepsNodeList.getLength(); j++) {                             // Iterates through each <rcp:step> element.
                                preparationSteps.add(stepsNodeList.item(j).getTextContent());                 // Adds the text content of each <rcp:step> element to the preparationSteps list.
                            }
                            break;
                        default:
                            break;
                    }
                }
            }

            // Create an Ingredient_recipe object and add it to the list of ingredients
            Ingredient_recipe ingredientRecipe = new Ingredient_recipe(name, ingredients, preparationSteps);
            ingredientsList.add(ingredientRecipe);
        }
        else {
            // If the ingredient has no child elements, it is an Ingredient_simple
            String amount = ingredientElement.getAttribute("amount");
            String unit = ingredientElement.getAttribute("unit");

            // Create an Ingredient_simple object and add it to the list of ingredients
            Ingredient_simple ingredientSimple = new Ingredient_simple(name, amount, unit);
            ingredientsList.add(ingredientSimple);
        }
    }

    /**
     * Retrieves the titles of all recipes in the repository.
     *
     * @return A list of recipe titles.
     */
    public static List<String> getRecipeTitles(){
        return recipes.stream()
                .map(Recipe::getTitle)
                .collect(Collectors.toList());
    }

    /**
     * Calculates the total number of eggs used in all recipes.
     *
     * @return The total number of eggs.
     */
    public static int getTotalEggs() {
        return recipes.stream()
                .map(Recipe::getAllIngredients)
                .flatMap(List::stream)
                .filter(i -> i.getName().contains("egg"))
                .map(i -> Integer.parseInt(i.getAmount()))
                .reduce(0, Integer::sum);
    }

    /**
     * Retrieves recipes containing olive oil as an ingredient.
     *
     * @return A list of recipes containing olive oil.
     */
    public static List<Recipe> getRecipesWithOliveOil() {
        return recipes.stream()
                .filter(r -> r.contains("olive oil"))
                .collect(Collectors.toList());
    }

    /**
     * Calculates the number of eggs used per recipe.
     *
     * @return A map containing each recipe and the corresponding number of eggs used.
     */
    public static Map<Recipe, Integer> getNumberEggsPerRecipe(){
        return recipes.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        r -> (int) r.getNumberIngredients("egg")
                ));
    }

    /**
     * Retrieves recipes with less than 500 calories.
     *
     * @return A list of recipes with less than 500 calories.
     */
    public static List<Recipe> getRecipesWithLessThan500Calories(){
        return recipes.stream()
                .filter(r -> r.getNutrition().containsKey("calories") && Integer.parseInt(r.getNutrition().get("calories")) < 500)
                .collect(Collectors.toList());
    }


    /**
     * Retrieves the quantity of sugar used in the recipe "Zuppa Inglese".
     *
     * @return The quantity of sugar used.
     */
    public static double getSugarQuantityOfZuppaInglese(){
        return recipes.stream()
                .filter(r -> r.getTitle().equals("Zuppa Inglese"))
                .map(r -> r.getNumberIngredients("sugar"))
                .findFirst().orElse(0.0);
    }

    /**
     * Retrieves the unit of sugar used in the recipe "Zuppa Inglese".
     *
     * @return The unit of sugar used.
     */
    public static String getSugarUnitOfZuppaInglese(){
        return recipes.stream()
                .filter(r -> r.getTitle().equals("Zuppa Inglese"))
                .map(Recipe::getAllIngredients)
                .flatMap(List::stream)
                .filter(i -> i.getName().contains("sugar"))
                .map(Ingredient_simple::getUnit)
                .findFirst()
                .orElse("");
    }

    /**
     * Retrieves the first two preparation steps of the recipe "Zuppa Inglese".
     *
     * @return A list containing the first two preparation steps.
     */
    public static List<String> get2FirstStepsOfZuppaInglese(){
        return recipes.stream()
                .filter(r -> r.getTitle().equals("Zuppa Inglese"))
                .map(Recipe::getPreparationSteps)
                .flatMap(List::stream)
                .limit(2)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves recipes with more than 5 preparation steps.
     *
     * @return A list of recipes with more than 5 preparation steps.
     */
    public static List<Recipe> getRecipesWithMoreThan5PreparationSteps(){
        return recipes.stream()
                .filter(r -> r.getAllPreparationSteps().size() > 5)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves recipes without butter as an ingredient.
     *
     * @return A list of recipes without butter.
     */
    public static List<Recipe> getRecipesWithoutButter(){
        return recipes.stream()
                .filter(r -> !r.contains("butter"))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves recipes containing at least one ingredient used in the recipe "Zuppa Inglese".
     *
     * @return A list of recipes containing at least one ingredient used in "Zuppa Inglese".
     */
    public static List<Recipe> getRecipesWithIngredientOfZuppaInglese(){
        // Retrieves the ingredients used in "Zuppa Inglese".
        List<Ingredient_simple> zuppaIngleseIngredients = recipes.stream()
                .filter(r -> r.getTitle().equals("Zuppa Inglese"))
                .map(Recipe::getAllIngredients)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        // Returns recipes containing at least one ingredient used in "Zuppa Inglese".
        return recipes.stream()
                .filter(r -> !r.getTitle().equals("Zuppa Inglese"))
                .filter(r -> r.containsOne(zuppaIngleseIngredients)) // Filters recipes containing at least one ingredient used in "Zuppa Inglese".
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the recipe with the highest calorie content.
     *
     * @return The recipe with the highest calorie content.
     */
    public static Recipe getRecipeWithMostCalories(){
        return recipes.stream()
                .filter(r -> r.getNutrition().containsKey("calories"))
                .max(Comparator.comparing(r -> r.getNutrition().get("calories")))
                .orElse(null);
    }

    /**
     * Retrieves the most frequently used unit among all recipes' ingredients.
     *
     * @return The most frequently used unit.
     */
    public static String getMostFrequentUnit(){
        // Retrieves the units of all ingredients and counts their occurrences.
        Map<String, Long> map = recipes.stream()
                .map(Recipe::getAllIngredients)
                .flatMap(List::stream)
                .map(Ingredient_simple::getUnit)
                .filter(i -> i != "")// Filters out empty units.
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        // Returns the unit with the maximum count.
        return map.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    /**
     * Retrieves the number of ingredients for each recipe.
     *
     * @return A map containing each recipe and the corresponding number of ingredients.
     */
    public static Map<Recipe, Integer> getNumberIngredientsPerRecipe(){
        return recipes.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        r -> r.getAllIngredients().size()
                ));
    }

    /**
     * Retrieves the recipe with the highest fat content.
     *
     * @return The recipe with the highest fat content.
     */
    public static Recipe getRecipeWithMostFat(){
        return recipes.stream()
                .filter(r -> r.getNutrition().containsKey("fat"))
                .max(Comparator.comparing(r -> Integer.parseInt(r.getNutrition().get("fat").replace("%", "")))) // Finds the recipe with the maximum fat content.
                .orElse(null);
    }

    /**
     * Retrieves the most frequently used ingredient among all recipes.
     *
     * @return The most frequently used ingredient.
     */
    public static String getIngredientMostUsed(){
        // Retrieves the names of all ingredients and counts their occurrences.
        Map<String, Long> map = recipes.stream()
                .map(Recipe::getAllIngredients)
                .flatMap(List::stream)
                .map(Ingredient::getName)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        return map.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    /**
     * Retrieves recipes sorted by the number of ingredients they contain.
     *
     * @return A list of recipes sorted by the number of ingredients.
     */
    public static List<Recipe> getRecipesSortedByNumberIngredients(){
        return recipes.stream()
                .sorted(Comparator.comparing(r -> r.getAllIngredients().size()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a map where each ingredient is associated with the recipes that use it.
     *
     * @return A map containing each ingredient and the corresponding recipes.
     */
    public static Map<String, List<Recipe>> getIngredientsAndRecipes(){
        return recipes.stream()
                .map(Recipe::getAllIngredients)
                .flatMap(List::stream)
                .map(Ingredient::getName)
                .distinct()
                .collect(Collectors.toMap(// Constructs a map of ingredients and associated recipes.
                        Function.identity(),
                        i -> recipes.stream()
                                .filter(r -> r.contains(i))
                                .collect(Collectors.toList())
                ));
    }

    /**
     * Retrieves recipes grouped by the number of preparation steps they have.
     *
     * @return A map containing each number of preparation steps and the corresponding recipes.
     */
    public static Map<Integer, List<Recipe>> getRecipesByPreparationStep(){
        return recipes.stream()
                .map(r -> r.getAllPreparationSteps().size())
                .distinct()
                .collect(Collectors.toMap( // Constructs a map of step counts and associated recipes.
                        Function.identity(),
                        i -> recipes.stream()
                                .filter(r -> r.getAllPreparationSteps().size() == i)
                                .collect(Collectors.toList())
                ));
    }

    /**
     * Retrieves the recipe with the fewest preparation steps.
     *
     * @return The recipe with the fewest preparation steps.
     */
    public static Recipe getEasiestRecipe(){
        return recipes.stream()
                .min(Comparator.comparing(r -> r.getAllPreparationSteps().size()))
                .orElse(null);
    }

}
