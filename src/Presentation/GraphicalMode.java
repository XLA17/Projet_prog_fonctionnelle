package Presentation;

import Repositories.RecipeRepo;
import javafx.collections.*;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import models.Recipe;

import java.util.*;

/**
 * Class representing the Graphical Mode of the Recipe Application.
 * This mode displays graphical information about recipes and their properties.
 */
public class GraphicalMode {

    /**
     * The pie chart to display the graphical content.
     */
    private static PieChart pieChart;

    /**
     * Sets up the graphical mode by adding menu items for treatments.
     *
     * @param treatmentsMenu The menu where the graphical treatments will be added.
     * @param centerBox      The VBox where the graphical content will be displayed.
     */
    public static void setGraphicalMode(Menu treatmentsMenu, VBox centerBox) {
        List<MenuItem> treatmentMenuItems = new ArrayList<>();

        // Menu item to get the number of eggs per recipe
        MenuItem getNumberEggsPerRecipe = new MenuItem("7 - Get number eggs per recipe");
        getNumberEggsPerRecipe.setOnAction(e -> {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(); // Creates an observable list of pie chart data.
            // Iterates over the number of eggs per recipe and adds the data to the pie chart.
            for (Map.Entry<Recipe, Integer> entry : RecipeRepo.getNumberEggsPerRecipe().entrySet()) {
                PieChart.Data data = new PieChart.Data(entry.getKey().getTitle(), entry.getValue());
                data.setName(entry.getKey().getTitle() + " : " + entry.getValue());
                pieChartData.add(data);
            }
            // Creates a pie chart with the pie chart data and adds it to the center box.
            pieChart = new PieChart(pieChartData);
            pieChart.setTitle("Number of eggs per recipe :");
            centerBox.getChildren().clear();
            centerBox.getChildren().addAll(pieChart);
        });
        treatmentMenuItems.add(getNumberEggsPerRecipe);

        // Menu item to get the number of ingredients per recipe
        MenuItem getNumberofIngredientsPerRecipe = new MenuItem("16 - Get number of ingredients per recipe");
        getNumberofIngredientsPerRecipe.setOnAction(e -> {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Map.Entry<Recipe, Integer> entry : RecipeRepo.getNumberIngredientsPerRecipe().entrySet()) {
                PieChart.Data data = new PieChart.Data(entry.getKey().getTitle(), entry.getValue());
                data.setName(entry.getKey().getTitle() + " : " + entry.getValue());
                pieChartData.add(data);
            }
            pieChart = new PieChart(pieChartData);
            pieChart.setTitle("Number of ingredients per recipe :");
            centerBox.getChildren().clear();
            centerBox.getChildren().addAll(pieChart);
        });
        treatmentMenuItems.add(getNumberofIngredientsPerRecipe);

        // Menu item to get ingredients and the number of recipes they are used in
        MenuItem getIngredientsAndRecipes = new MenuItem("20 - Get ingredients and recipes");
        getIngredientsAndRecipes.setOnAction(e -> {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Map.Entry<String, List<Recipe>> entry : RecipeRepo.getIngredientsAndRecipes().entrySet()) {
                PieChart.Data data = new PieChart.Data(entry.getKey(), entry.getValue().size());
                data.setName(entry.getKey());
                pieChartData.add(data);
            }
            pieChart = new PieChart(pieChartData);
            pieChart.setTitle("Ingredients and the number of recipes they are used in :");
            centerBox.getChildren().clear();
            centerBox.getChildren().addAll(pieChart);
        });
        treatmentMenuItems.add(getIngredientsAndRecipes);

        // Menu item to get recipes by preparation step
        MenuItem getRecipesByPreparationStep = new MenuItem("21 - Get recipes by preparation step");
        getRecipesByPreparationStep.setOnAction(e -> {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Map.Entry<Integer, List<Recipe>> entry : RecipeRepo.getRecipesByPreparationStep().entrySet()) {
                PieChart.Data data = new PieChart.Data(String.valueOf(entry.getKey()), entry.getValue().size());
                data.setName(String.valueOf(entry.getKey()) + " steps");
                pieChartData.add(data);
            }
            pieChart = new PieChart(pieChartData);
            pieChart.setTitle("Number of preparation steps and number of recipes with this number of steps :");
            centerBox.getChildren().clear();
            centerBox.getChildren().addAll(pieChart);
        });
        treatmentMenuItems.add(getRecipesByPreparationStep);

        // Adding menu items to the treatments menu
        treatmentsMenu.getItems().addAll(treatmentMenuItems);
    }
}
