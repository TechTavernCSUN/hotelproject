import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HotelReservationGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Tabs for hotel rooms and reservations
            TabPane tabPane = new TabPane();

            Tab hotelRoomsTab = new Tab("Hotel Rooms");
            hotelRoomsTab.setClosable(false);
            ListView<String> hotelRoomsList = new ListView<>();
            hotelRoomsTab.setContent(hotelRoomsList);

            Tab reservationsTab = new Tab("Reservations");
            reservationsTab.setClosable(false);
            ListView<String> reservationsList = new ListView<>();
            reservationsTab.setContent(reservationsList);

            tabPane.getTabs().addAll(hotelRoomsTab, reservationsTab);

            // Load data from database
            loadData(hotelRoomsList, reservationsList);

            primaryStage.setScene(new Scene(tabPane, 600, 400));
            primaryStage.setTitle("Hotel Reservation System");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData(ListView<String> hotelRoomsList, ListView<String> reservationsList) {
        String dbUrl = "jdbc:sqlite:test.db";

        // Load hotel rooms
        String sqlSelectHotelRooms = "SELECT * FROM HOTEL_ROOMS";
        // Load reservations
        String sqlSelectReservations = "SELECT * FROM RESERVATIONS";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement()) {

            ResultSet rsHotelRooms = stmt.executeQuery(sqlSelectHotelRooms);
            ObservableList<String> hotelRooms = FXCollections.observableArrayList();
            while (rsHotelRooms.next()) {
                hotelRooms.add("Room " + rsHotelRooms.getInt("ROOM_NUMBER") +
                        ", " + (rsHotelRooms.getBoolean("RESERVED") ? "Reserved" : "Available") +
                        ", $" + rsHotelRooms.getDouble("COST") +
                        ", " + rsHotelRooms.getString("ROOM_TYPE"));
            }
            hotelRoomsList.setItems(hotelRooms);

            ResultSet rsReservations = stmt.executeQuery(sqlSelectReservations);
            ObservableList<String> reservations = FXCollections.observableArrayList();
            while (rsReservations.next()) {
                reservations.add("Reservation ID: " + rsReservations.getInt("RESERVATION_ID") +
                        ", Name: " + rsReservations.getString("NAME") +
                        ", Check-In: " + rsReservations.getString("CHECK_IN") +
                        ", Check-Out: " + rsReservations.getString("CHECK_OUT") +
                        ", Room #: " + rsReservations.getInt("ROOM_NUMBER"));
            }
            reservationsList.setItems(reservations);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
