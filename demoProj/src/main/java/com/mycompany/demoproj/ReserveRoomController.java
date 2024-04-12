
package com.mycompany.demoproj;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.SpinnerValueFactory;

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
                    int cost = newValue * selectedRoom.getPricePerNight();
                    costLabel.setText("Cost: $" + cost);
                }
            });

            // Initialize cost label text
            int initialCost = valueFactory.getValue() * selectedRoom.getPricePerNight();
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

        // Calculate the cost
        int cost = numberOfDays * selectedRoom.getPricePerNight();

        // Set the cost label
        costLabel.setText("Cost: $" + cost);

        // Update the room's availability status to false (reserved)
        selectedRoom.setAvailable(false);

        // Update the table view in the primary controller
        primaryController.updateTableView();

        // Implement reservation confirmation logic here
        System.out.println("Reservation confirmed for room number: " + selectedRoom.getRoomId() +
                ", Name: " + nameField.getText() +
                ", Email: " + emailField.getText() +
                ", for " + numberOfDays + " days. Total Cost: $" + cost);

        // Close the secondary stage
        ((Stage) roomNumberLabel.getScene().getWindow()).close();
    }
}

