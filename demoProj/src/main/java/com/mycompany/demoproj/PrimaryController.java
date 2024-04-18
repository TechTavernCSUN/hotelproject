package com.mycompany.demoproj;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class PrimaryController {

    @FXML
    private Button generateReportButton;

    @FXML
    private Button generateRevenueButton;

    @FXML
    private Button managerSignInButton;

    // Add an action handler for the manager sign in button
    @FXML
    private void handleSignIn() throws IOException {
        App.setRoot("ManagerLogin");
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
    
    // Add setters for the buttons
    public void setGenerateReportButtonVisible(boolean visible) {
        generateReportButton.setVisible(visible);
    }

    public void setGenerateRevenueButtonVisible(boolean visible) {
        generateRevenueButton.setVisible(visible);
    }
    
    // Add setter for manager sign-in button text
    public void setManagerSignInButton(String text) {
        managerSignInButton.setText(text);
    }
}
