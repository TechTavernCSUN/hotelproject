import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
<<<<<<< HEAD
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
=======
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
>>>>>>> 101446230f3a5520320996a3e03650f961f8998f
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.List;
>>>>>>> 101446230f3a5520320996a3e03650f961f8998f

public class HotelReservationGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Tabs for hotel rooms and reservations
            TabPane tabPane = new TabPane();

            Tab hotelRoomsTab = new Tab("Hotel Rooms");
            hotelRoomsTab.setClosable(false);
            ListView<String> hotelRoomsList = new ListView<>();
<<<<<<< HEAD
            hotelRoomsTab.setContent(hotelRoomsList);
=======

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
>>>>>>> 101446230f3a5520320996a3e03650f961f8998f

            Tab reservationsTab = new Tab("Reservations");
            reservationsTab.setClosable(false);
            ListView<String> reservationsList = new ListView<>();
            reservationsTab.setContent(reservationsList);

            tabPane.getTabs().addAll(hotelRoomsTab, reservationsTab);

            // Load data from database
<<<<<<< HEAD
            loadData(hotelRoomsList, reservationsList);
=======
            loadData(hotelRoomsList, reservationsList, "All", "", false);
>>>>>>> 101446230f3a5520320996a3e03650f961f8998f

            primaryStage.setScene(new Scene(tabPane, 600, 400));
            primaryStage.setTitle("Hotel Reservation System");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
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
=======
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
                                ", $" + rsHotelRooms.getDouble("COST") +
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
                                ", Check-Out: " + rsReservations.getString("CHECK_OUT"));
                    }
                    reservationsList.setItems(reservations);
                }
            }
>>>>>>> 101446230f3a5520320996a3e03650f961f8998f

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
=======

>>>>>>> 101446230f3a5520320996a3e03650f961f8998f
    public static void main(String[] args) {
        launch(args);
    }
}
