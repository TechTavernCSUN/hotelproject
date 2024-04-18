package com.mycompany.demoproj;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.time.LocalDate;
import javafx.scene.control.Label;

public class RevenueController implements Initializable {

    @FXML
    private Label dateLabel;

    @FXML
    private Button backButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Get the first day of the current month
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);

        // Get the last day of the current month
        LocalDate lastDayOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        // Set the label text with the date range
        dateLabel.setText("From: " + firstDayOfMonth + "  To: " + lastDayOfMonth);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        // Close the popup
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
