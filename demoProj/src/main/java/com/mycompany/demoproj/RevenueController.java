package com.mycompany.demoproj;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Pair;

public class RevenueController implements Initializable {

    @FXML
    private Label dateLabel;

    @FXML
    private Label totalRoomsLabel;

    @FXML
    private Label totalRevenueLabel;

    private RevenueCalculator revenueCalculator;

    @FXML
    private Button backButton;

    String reservationsDbUrl = "jdbc:sqlite:reservations.db";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Get the first day of the current month
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);

        // Get the last day of the current month
        LocalDate lastDayOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        // Set the label text with the date range
        dateLabel.setText("From: " + firstDayOfMonth + "  To: " + lastDayOfMonth);

        // Instantiate the RevenueCalculator with the database URL
        revenueCalculator = new RevenueCalculator(reservationsDbUrl);

        // Call the method to calculate monthly revenue and update labels
        updateRevenueDetails();
    }

    private void updateRevenueDetails() {
        // Call the calculateMonthlyRevenue method to get the revenue details
        Pair<Integer, Double> revenueDetails = revenueCalculator.calculateMonthlyRevenue();

        // Get the total rooms booked and total revenue from the Pair
        int totalRoomsBooked = revenueDetails.getKey();
        double totalRevenue = revenueDetails.getValue();

        // Update the labels in the UI with these values
        totalRoomsLabel.setText("Total Rooms Booked: " + totalRoomsBooked);
        totalRevenueLabel.setText("Total Revenue: $" + totalRevenue);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        // Close the popup
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
