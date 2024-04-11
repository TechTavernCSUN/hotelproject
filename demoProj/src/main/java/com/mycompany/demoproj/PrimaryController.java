package com.mycompany.demoproj;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("ManagerLogin");
    }
    
    @FXML
    private void handleBookRoom() throws IOException {
        App.setRoot("BookRoom");
    }
    
    @FXML
    private void handleSignIn() throws IOException {
        App.setRoot("ManagerLogin");
    }
    
    @FXML
    private void handleCancelReservation() throws IOException {
        App.setRoot("CancelReservation");
    }
    
    @FXML
    private void handleYourReservations() throws IOException {
        App.setRoot("primary");
    }
    
    @FXML
    private void handleGenerateReport() throws IOException {
        App.setRoot("primary");
    }
    
    @FXML
    private void handleGenerateRevenue() throws IOException {
        App.setRoot("primary");
    }
}
