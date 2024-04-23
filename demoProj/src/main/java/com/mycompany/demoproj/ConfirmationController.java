/**
 * Class Name: ConfirmationController
 * Date of Code: 4/23/24
 * Name of Coder: Christopher Lagos
 * Description: Controller class for handling confirmation message UI and returning to the main menu.
 */
package com.mycompany.demoproj;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConfirmationController {
    @FXML
    private Label confirmationMessageLabel;
/**
     * Sets the reservation details in the confirmation message.
     * @param room The room for which the reservation is made.
     * @param numberOfDays The number of days for the reservation.
     * @param cost The total cost of the reservation.
     */
    public void setReservationDetails(Room room, int numberOfDays, int cost) {
        confirmationMessageLabel.setText("Your reservation for Room " + room.getRoomId() +
                " for " + numberOfDays + " day(s) has been confirmed. Total cost: $" + cost + ".");
    }
/**
     * Returns to the main menu by closing the confirmation pop-up.
     */
    @FXML
    private void returnToMainMenu() {
        // Close the confirmation pop-up
        confirmationMessageLabel.getScene().getWindow().hide();
    }
}
