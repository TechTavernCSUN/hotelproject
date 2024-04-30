
package com.mycompany.demoproj;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConfirmationController {
    @FXML
    private Label confirmationMessageLabel;

    public void setReservationDetails(Room room, int numberOfDays, double cost) {
        confirmationMessageLabel.setText("Your reservation for Room " + room.getRoomId() +
                " for " + numberOfDays + " day(s) has been confirmed. Total cost: $" + cost + ".");
    }

    @FXML
    private void returnToMainMenu() {
        // Close the confirmation pop-up
        confirmationMessageLabel.getScene().getWindow().hide();
    }
}
