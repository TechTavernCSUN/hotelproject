package com.mycompany.demoproj;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/demoproj/primary.fxml"));
            Parent root = loader.load();
            scene = new Scene(root, 800, 600);
            PrimaryController primaryController = loader.getController();
            scene.setUserData(primaryController);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Print error message and stack trace
            e.printStackTrace();
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load FXML file");
            alert.setContentText("An error occurred while loading the FXML file. Please check if the file exists and is accessible.");
            alert.showAndWait();
        }
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}

