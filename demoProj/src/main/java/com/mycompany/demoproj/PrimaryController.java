package com.mycompany.demoproj;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class PrimaryController {

    @FXML
    private Button generateReportButton;

    @FXML
    private Button generateRevenueButton;

    @FXML
    private Button managerSignInButton;
    

    private static boolean managerLoggedIn = false;

    @FXML
    public void initialize() {
        // Set the initial manager login status
        setManagerLoggedIn(managerLoggedIn);
    }
    
    // Add an action handler for the manager sign in button
    @FXML
    private void handleSignIn() throws IOException {
        if (managerLoggedIn) {
            managerLoggedIn = false;
            managerSignInButton.setText("Manager Sign In");
            generateReportButton.setVisible(false);
            generateRevenueButton.setVisible(false);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerLogin.fxml"));
            Parent root = loader.load();
            ManagerLoginController managerLoginController = loader.getController();
            managerLoginController.setPrimaryController(this); // Inject PrimaryController instance
            App.setRoot("ManagerLogin");
        }
    }
    
    public void setManagerLoggedIn(boolean loggedIn) {
        managerLoggedIn = loggedIn;
        if (loggedIn) {
            managerSignInButton.setText("Log Out");
            generateReportButton.setVisible(true);
            generateRevenueButton.setVisible(true);
        } else {
            managerSignInButton.setText("Manager Sign In");
            generateReportButton.setVisible(false);
            generateRevenueButton.setVisible(false);
        }
    }
    
    @FXML
    private void handleBookRoom() throws IOException {
        App.setRoot("BookRoom");
    }
    
    @FXML
    private void handleCancelReservation() throws IOException {
        App.setRoot("CancelReservation");
    }
    
    @FXML
    private void handleYourReservations() throws IOException {
        App.setRoot("ClientReservation");
    }
    
    @FXML
    private void handleGenerateReport() throws IOException {
        App.setRoot("Report");
    }
    
    @FXML
    private void handleGenerateRevenue() throws IOException {
        // Load the FXML file for the revenue page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Revenue.fxml"));
        Parent root = loader.load();
        
        // Create a new stage for the revenue page
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Generate Revenue");
        stage.setScene(new Scene(root));
        stage.show();
    }
    
}
