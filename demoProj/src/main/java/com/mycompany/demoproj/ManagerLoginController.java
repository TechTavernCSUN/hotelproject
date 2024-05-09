package com.mycompany.demoproj;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class ManagerLoginController {

    @FXML
    private PrimaryController primaryController;

    @FXML
    private BorderPane managerLoginRoot;

    // map to store email and password pairs for manager accounts
    private final Map<String, String> managerAccounts;

    // Inject the email and password fields from the FXML
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;


    public void setPrimaryController(PrimaryController primaryController) {
        this.primaryController = primaryController;
    }

    // Initialize the map with the predefined accounts
    public ManagerLoginController() {
        managerAccounts = new HashMap<>();
        
        managerAccounts.put("Michael@email.com", "password");
        managerAccounts.put("Jacob@email.com", "password");
        managerAccounts.put("Joshua@email.com", "password");
        managerAccounts.put("Chris@email.com", "password");
    }

    @FXML
    private void handleManagerSignIn() throws IOException {
        // Retrieve email and password entered by the manager
        String email = emailField.getText();
        String password = passwordField.getText();

        // Check if the entered email exists in the manager accounts map
        if (managerAccounts.containsKey(email)) {
            // If the email exists, check if the entered password matches
            if (managerAccounts.get(email).equals(password)) {
                // If both email and password match, proceed with sign in
                // Handle successful sign-in here
                Scene scene = managerLoginRoot.getScene();
                PrimaryController primaryController = (PrimaryController) scene.getUserData();

                primaryController.setManagerLoggedIn(true);
                System.out.println("Manager signed in successfully");
                App.setRoot("primary");
            } else {
                // If password does not match, handle incorrect password scenario
                // (e.g., display an error message)
                System.out.println("Incorrect password");
                // If password does not match, update error label
                errorLabel.setText("Incorrect password");
            }
        } else {
            // If email does not exist in the map, handle unknown email scenario
            // (e.g., display an error message)
            System.out.println("Unknown email");
            // If email does not exist in the map, update error label
            errorLabel.setText("Unknown email");
        }
    }

    @FXML
    private void handleBack() throws IOException {
        App.setRoot("primary");
    }
}
