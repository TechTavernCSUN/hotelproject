
package com.mycompany.demoproj;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.Label;


public class CancelReservationController implements Initializable {
    
    @FXML private TextField roomNumberField;
    @FXML private TextField emailField;
    
    @FXML private Label errorLabel;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleCancel() throws IOException {
        String reservationsDbUrl = "jdbc:sqlite:C:\\Users\\ma782165\\Documents\\380\\Project\\hotelproject\\demoProj\\src\\main\\java\\com\\mycompany\\reservations.db";
        String roomsDbUrl = "jdbc:sqlite:C:\\Users\\ma782165\\Documents\\380\\Project\\hotelproject\\demoProj\\src\\main\\java\\com\\mycompany\\hotel_rooms.db";
        
        try (Connection roomsConnection = DriverManager.getConnection(roomsDbUrl);
             Connection reservationsConnection = DriverManager.getConnection(reservationsDbUrl)) {
            
            // Retrieve room number and email from text fields
            int roomNumber = Integer.parseInt(roomNumberField.getText());
            String email = emailField.getText();
            
            boolean success = ReservationManager.cancelReservation(roomsConnection, reservationsConnection, roomNumber, email);
            
            // Display success or failure message
            cancelation(success);
            if (success) {
                // Return to the primary scene
                App.setRoot("primary");
            } else {
                // Update error label
                errorLabel.setText("Failed to cancel reservation.");
            }
        } catch (SQLException e) {
            errorLabel.setText("Database connection error: " + e.getMessage());
            System.err.println("Database connection error: " + e.getMessage());
        }
        
    }
    
    public void cancelation(boolean success) throws IOException {
           
            // Display success or failure message
            if (success) {
                System.out.println("Reservation cancelled successfully.");
            } else {
                System.out.println("Failed to cancel reservation.");
            }
            
    }
    
    @FXML
    private void handleBack() throws IOException {
        App.setRoot("primary");
    }
}