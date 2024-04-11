package com.mycompany.demoproj;

import java.io.IOException;
import javafx.fxml.FXML;

public class ManagerLoginController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    
    @FXML
    private void handleManagerSignIn() throws IOException {
        App.setRoot("primary");
    }
    
    @FXML
    private void handleBack() throws IOException {
        App.setRoot("primary");
    }
}