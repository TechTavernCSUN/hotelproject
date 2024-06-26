import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Name: HotelReservationGUI
 * Date of the Code: 4/4/2024
 * Programmer's Name: Jacob Spier
 * Description: GUI application for managing hotel reservations using JavaFX. It provides interfaces for viewing
 *              hotel rooms and managing reservations, with filters for room types, prices, and availability. It loads in
 *              values from SQLite DB files.
 *
 * Important Functions:
 * - start(Stage primaryStage): Initializes the UI components and loads initial data.
 *   Inputs: primaryStage (Stage) - the primary stage for this application.
 *   Outputs: void
 * - loadData(ListView<String> hotelRoomsList, ListView<String> reservationsList, String roomType, String maxPrice, boolean availableOnly):
 *   Loads data into the hotel rooms and reservations lists based on specified filters.
 *   Inputs: hotelRoomsList (ListView<String>) - UI component for displaying hotel rooms,
 *           reservationsList (ListView<String>) - UI component for displaying reservations,
 *           roomType (String) - filter by room type,
 *           maxPrice (String) - filter by maximum price,
 *           availableOnly (boolean) - filter for only available rooms.
 *   Outputs: void
 *
 * Important Data Structures:
 * - ObservableList<String>: Used to manage lists of strings that are observed by the UI for changes, ensuring the UI updates automatically.
 *
 * Algorithms Used:
 * - SQL queries are constructed dynamically based on user input to filter data. This involves assembling a WHERE clause based on provided filters.
 * - Uses JavaFX for constructing and managing UI components, demonstrating a Model-View-Controller (MVC) architecture typically used in GUI applications.
 */

public class HotelReservationGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Tabs for hotel rooms and reservations
            TabPane tabPane = new TabPane();

            Tab hotelRoomsTab = new Tab("Hotel Rooms");
            hotelRoomsTab.setClosable(false);
            ListView<String> hotelRoomsList = new ListView<>();

            // Filtering UI
            ComboBox<String> roomTypeFilter = new ComboBox<>();
            roomTypeFilter.getItems().addAll("All", "Single", "Double", "Suite");
            roomTypeFilter.getSelectionModel().select("All");

            TextField maxPriceFilter = new TextField();
            maxPriceFilter.setPromptText("Max Price");

            CheckBox availableFilter = new CheckBox("Available Only");

            Button searchButton = new Button("Search");
            searchButton.setOnAction(e -> loadData(hotelRoomsList, null, roomTypeFilter.getValue(), maxPriceFilter.getText(), availableFilter.isSelected()));

            HBox filters = new HBox(10, roomTypeFilter, maxPriceFilter, availableFilter, searchButton);
            filters.setPadding(new Insets(10));

            VBox hotelRoomsLayout = new VBox(10, filters, hotelRoomsList);
            hotelRoomsTab.setContent(hotelRoomsLayout);

            Tab reservationsTab = new Tab("Reservations");
            reservationsTab.setClosable(false);
            ListView<String> reservationsList = new ListView<>();
            reservationsTab.setContent(reservationsList);

            tabPane.getTabs().addAll(hotelRoomsTab, reservationsTab);

            // Load data from database
            loadData(hotelRoomsList, reservationsList, "All", "", false);

            primaryStage.setScene(new Scene(tabPane, 600, 400));
            primaryStage.setTitle("Hotel Reservation System");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData(ListView<String> hotelRoomsList, ListView<String> reservationsList, String roomType, String maxPrice, boolean availableOnly) {
        String hotelRoomsDbUrl = "jdbc:sqlite:hotel_rooms.db";
        String reservationsDbUrl = "jdbc:sqlite:reservations.db";

        List<String> conditions = new ArrayList<>();
        if (!"All".equals(roomType)) {
            conditions.add("ROOM_TYPE = '" + roomType + "'");
        }
        if (!maxPrice.isEmpty()) {
            conditions.add("COST <= " + maxPrice);
        }
        if (availableOnly) {
            conditions.add("RESERVED = FALSE");
        }
        String whereClause = String.join(" AND ", conditions);
        if (!whereClause.isEmpty()) {
            whereClause = "WHERE " + whereClause;
        }

        String sqlSelectHotelRooms = "SELECT * FROM HOTEL_ROOMS " + whereClause;

        try {
            // Load hotel rooms from hotel_rooms.db
            if (hotelRoomsList != null) {
                try (Connection conn = DriverManager.getConnection(hotelRoomsDbUrl);
                     Statement stmt = conn.createStatement()) {
                    ResultSet rsHotelRooms = stmt.executeQuery(sqlSelectHotelRooms);
                    ObservableList<String> hotelRooms = FXCollections.observableArrayList();
                    while (rsHotelRooms.next()) {
                        hotelRooms.add("Room " + rsHotelRooms.getInt("ROOM_NUMBER") +
                                ", " + (rsHotelRooms.getBoolean("RESERVED") ? "Reserved" : "Available") +
                                ", $" + rsHotelRooms.getDouble("PRICE") +
                                ", " + rsHotelRooms.getString("ROOM_TYPE"));
                    }
                    hotelRoomsList.setItems(hotelRooms);
                }
            }

            // Load reservations from reservations.db
            if (reservationsList != null) {
                try (Connection conn = DriverManager.getConnection(reservationsDbUrl);
                     Statement stmt = conn.createStatement()) {
                    ResultSet rsReservations = stmt.executeQuery("SELECT * FROM RESERVATIONS");
                    ObservableList<String> reservations = FXCollections.observableArrayList();
                    while (rsReservations.next()) {
                        reservations.add("Reservation ID: " + rsReservations.getInt("RESERVATION_ID") +
                                ", Room #: " + rsReservations.getInt("ROOM_NUMBER") +
                                ", Email: " + rsReservations.getString("EMAIL") +
                                ", Name: " + rsReservations.getString("NAME") +
                                ", Payment: " + rsReservations.getString("PAYMENT") +
                                ", Check-In: " + rsReservations.getString("CHECK_IN") +
                                ", Check-Out: " + rsReservations.getString("CHECK_OUT") +
                                ", Price: " + rsReservations.getString("PRICE") +
                                ", Total: " + rsReservations.getString("TOTAL"));
                    }
                    reservationsList.setItems(reservations);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
