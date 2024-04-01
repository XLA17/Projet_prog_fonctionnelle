package Presentation;

import Repositories.RecipeRepo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;

public class RecipePrincipal extends Application {

    /**
     * The menu where the treatments will be added.
     */
    private Menu treatmentsMenu;

    /**
     * The VBox where the content will be displayed.
     */
    private VBox centerBox;

    /**
     * The presentation mode of the application (textual or graphical).
      */
    private boolean isTextual = true;

    /**
     * The entry point of the application.
     *
     * @param stage The primary stage for this application, onto which the application scene can be set.
     */
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane(); // Creates a BorderPane to organize the layout of the application.
        Scene scene = new Scene(root, 800, 600);

        // Menu setup
        MenuBar menuBar = new MenuBar();
        Menu presentationMenu = new Menu("Presentation");
        treatmentsMenu = new Menu("Treatments");
        menuBar.getMenus().addAll(presentationMenu, treatmentsMenu);

        // Presentation mode options
        MenuItem textualItem = new MenuItem("Textual");
        textualItem.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have chosen TEXTUAL presentation mode");
            alert.show();
            isTextual = true;
            updatePresentationMode();
        });
        MenuItem graphicalItem = new MenuItem("Graphical");
        graphicalItem.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have chosen GRAPHICAL presentation mode");
            isTextual = false;
            alert.show();
            isTextual = false;
            updatePresentationMode();
        });
        presentationMenu.getItems().addAll(textualItem, graphicalItem);

        // Center layout setup
        centerBox = new VBox(10);
        centerBox.setPadding(new Insets(10));

        // Root layout setup
        root.setTop(menuBar);
        root.setCenter(centerBox);

        // Initial presentation mode
        updatePresentationMode();

        stage.setScene(scene);
        stage.setTitle("Recipe Application");
        stage.show();
    }

    /**
     * Updates the presentation mode based on the user's selection (textual or graphical).
     */
    private void updatePresentationMode() {
        if (isTextual) {
            treatmentsMenu.getItems().clear();
            centerBox.getChildren().clear();
            TextualMode.setTextualMode(treatmentsMenu, centerBox);
        } else {
            treatmentsMenu.getItems().clear();
            centerBox.getChildren().clear();
            GraphicalMode.setGraphicalMode(treatmentsMenu, centerBox);
        }
    }

    /**
     * The main method responsible for initializing the application and loading recipe data from an XML file.
     *
     * @param args The command-line arguments.
     * @throws Exception If an error occurs during the initialization process.
     */
    public static void main(String[] args) throws Exception {
        File xmlFile = new File("src/data/recipes.xml");

        RecipeRepo.init(xmlFile);

        launch(args);
    }
}
