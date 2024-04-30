package com.mycompany.demoproj;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;

public class ClientReservationController implements Initializable {

    @FXML private TableView<Reservation> reservationTable;
    @FXML private TableColumn<Reservation, String> emailColumn;
    @FXML private TableColumn<Reservation, String> nameColumn;
    @FXML private TableColumn<Reservation, Integer> roomNumberColumn;
    @FXML private TableColumn<Reservation, String> checkInColumn;
    @FXML private TableColumn<Reservation, String> checkOutColumn;
    @FXML private TableColumn<Reservation, Double> priceColumn;
    @FXML private TextField emailField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        roomNumberColumn.setCellValueFactory(cellData -> cellData.getValue().roomNumberProperty().asObject());
        checkInColumn.setCellValueFactory(cellData -> cellData.getValue().checkInProperty());
        checkOutColumn.setCellValueFactory(cellData -> cellData.getValue().checkOutProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        // Add listener to email field to update reservation data when email is entered
        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            //loadReservationData(newValue);
            // Clear the table if the email field is empty
            if(newValue.isEmpty()) {
                reservationTable.getItems().clear();
            }
        });
    }
    
    @FXML
    private void handleSearch() {
        String email = emailField.getText();
        if (!email.isEmpty() && email.contains("@")) {
            loadReservationData(email);
        }
    }

    private void loadReservationData(String email) {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList();
        String url = "jdbc:sqlite:C:\\Users\\ma782165\\Documents\\380\\Project\\hotelproject\\demoProj\\src\\main\\java\\com\\mycompany\\reservations.db";
        String query = "SELECT * FROM RESERVATIONS WHERE EMAIL LIKE '%" + email + "%'";
        
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                reservations.add(new Reservation(
                    rs.getInt("RESERVATION_ID"),
                    rs.getString("EMAIL"),
                    rs.getString("NAME"),
                    rs.getInt("ROOM_NUMBER"),
                    rs.getString("CHECK_IN"),
                    rs.getString("CHECK_OUT"),
                    rs.getString("PAYMENT"),
                    rs.getDouble("PRICE"),
                    rs.getDouble("TOTAL")
                ));
            }
            reservationTable.setItems(reservations);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() throws IOException {
        App.setRoot("primary");
    }
}
