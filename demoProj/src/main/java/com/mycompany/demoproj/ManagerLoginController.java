package com.mycompany.demoproj;

import java.io.IOException;
import javafx.fxml.FXML;

public class ManagerLoginController {

    // Inject the PrimaryController instance
    @FXML
    private PrimaryController primaryController;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void handleManagerSignIn() throws IOException {
        // Check if primaryController is not null before accessing its methods
        if (primaryController != null) {
            // This method should handle the logic for manager sign in
            // If sign in is successful, update the visibility of generate buttons
            primaryController.setGenerateReportButtonVisible(true);
            primaryController.setGenerateRevenueButtonVisible(true);
            primaryController.setManagerSignInButton("Manager Signed In");
            
            // Switch back to primary page
            App.setRoot("primary");
        }
    }

    @FXML
    private void handleBack() throws IOException {
        App.setRoot("primary");
    }
}
