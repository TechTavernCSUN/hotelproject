
package com.mycompany.demoproj;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.SpinnerValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import java.io.IOException;
import javafx.scene.control.RadioButton;


import java.sql.Connection;
import java.sql.DriverManager;
import java.io.IOException;
import java.sql.SQLException;

public class ReserveRoomController {
    @FXML
    private Label roomNumberLabel;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private Spinner<Integer> daysSpinner;
    
    @FXML
    private RadioButton cashRadioButton;

    @FXML
    private RadioButton cardRadioButton;

    @FXML
    private Label costLabel;

    private Room selectedRoom;
    private BookRoomController primaryController; // Add reference to PrimaryController

    public void initialize() {
        if (selectedRoom != null) {
            roomNumberLabel.setText("Room Number: " + selectedRoom.getRoomId());

            // Set spinner value factory to allow only positive integers
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1);
            daysSpinner.setValueFactory(valueFactory);

            // Listener to update cost label when spinner value changes
            daysSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    double cost = newValue * selectedRoom.getPricePerNight();
                    costLabel.setText("Cost: $" + cost);
                }
            });

            // Initialize cost label text
            double initialCost = valueFactory.getValue() * selectedRoom.getPricePerNight();
            costLabel.setText("Cost: $" + initialCost);
        }
    }

    public void setSelectedRoom(Room room) {
        this.selectedRoom = room;
    }

    // Method to set the primary controller
    public void setPrimaryController(BookRoomController primaryController) {
        this.primaryController = primaryController;
    }

    @FXML
    private void confirmReservation() {
        // Get the selected number of days from the spinner
        int numberOfDays = daysSpinner.getValue();

        // Get name and email from text fields
        String name = nameField.getText();
        String email = emailField.getText();

        // Ensure name and email are not empty
        if (name.isEmpty() || email.isEmpty()) {
            // Show an error message or handle empty fields as needed
            System.out.println("Name and email fields are required.");
            return;
        }
        
        // Get the selected payment option
        String paymentOption = cashRadioButton.isSelected() ? "Cash" : "Credit/Debit";

        // Calculate the cost
        double cost = selectedRoom.getPricePerNight() * numberOfDays;

        // Set the cost label
        costLabel.setText("Cost: $" + cost);

        // Reserve the room
        boolean success = reserveRoom(selectedRoom.getRoomId(), name, email, numberOfDays, paymentOption);

        if (success) {
            // Update the room's availability status to false (reserved)
            selectedRoom.setAvailable(false);

            // Update the table view in the primary controller
            primaryController.updateTableView();

            // Close the current pop-up
            Stage currentStage = (Stage) roomNumberLabel.getScene().getWindow();
            currentStage.close();

            // Open the confirmation pop-up
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Confirmation.fxml"));
            Parent root;
            try {
                root = loader.load();
                ConfirmationController controller = loader.getController();
                controller.setReservationDetails(selectedRoom, numberOfDays, cost); // Pass reservation details to the confirmation controller
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to reserve room.");
            // Show an error message or handle the failure as needed
        }
    }

    private boolean reserveRoom(int roomNumber, String name, String email, int numberOfDays, String paymentOption) {
        String reservationsDbUrl = "jdbc:sqlite:reservations.db";
        String roomsDbUrl = "jdbc:sqlite:hotel_rooms.db";
        

        try (Connection roomsConnection = DriverManager.getConnection(roomsDbUrl);
             Connection reservationsConnection = DriverManager.getConnection(reservationsDbUrl)) {

            boolean success = ReservationManager.reserveRoom(roomsConnection, reservationsConnection, roomNumber, new String[]{name, email}, paymentOption, numberOfDays);
            if (success) {
                System.out.println("Room reserved successfully.");
                return true;
            } else {
                System.out.println("Failed to reserve room.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
        return false;
    }
    
}


