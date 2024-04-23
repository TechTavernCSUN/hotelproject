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

public class ReportController implements Initializable {

    @FXML private TableView<Reservation> tableView;
    @FXML private TableColumn<Reservation, Number> columnReservationId;
    @FXML private TableColumn<Reservation, String> columnEmail;
    @FXML private TableColumn<Reservation, String> columnName;
    @FXML private TableColumn<Reservation, Number> columnRoomNumber;
    @FXML private TableColumn<Reservation, String> columnCheckIn;
    @FXML private TableColumn<Reservation, String> columnCheckOut;
    @FXML private TableColumn<Reservation, String> columnPayment;
    @FXML private TableColumn<Reservation, Number> columnPrice;
    @FXML private TableColumn<Reservation, Number> columnTotal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnReservationId.setCellValueFactory(cellData -> cellData.getValue().reservationIdProperty());
        columnEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        columnRoomNumber.setCellValueFactory(cellData -> cellData.getValue().roomNumberProperty());
        columnCheckIn.setCellValueFactory(cellData -> cellData.getValue().checkInProperty());
        columnCheckOut.setCellValueFactory(cellData -> cellData.getValue().checkOutProperty());
        columnPayment.setCellValueFactory(cellData -> cellData.getValue().paymentProperty());
        columnPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        columnTotal.setCellValueFactory(cellData -> cellData.getValue().totalProperty());

        loadReservationData();
    }

    private void loadReservationData() {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList();
        String url = "jdbc:sqlite:C:\\Users\\ma782165\\Documents\\380\\Project\\hotelproject\\demoProj\\src\\main\\java\\com\\mycompany\\reservations.db";
        String query = "SELECT * FROM RESERVATIONS";

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
            tableView.setItems(reservations);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() throws IOException {
        // Replace "App.setRoot("primary")" with your own method to change scenes or close the window
        System.out.println("Back button pressed - implement scene change or window close.");
    }
}