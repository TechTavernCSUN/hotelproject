//Class Name: BookRoomController
//Date of Code: 4/23/24
//Name of Coder: Christopher Lagos
//Description: Handles code for UI elements and the booking of rooms

package com.mycompany.demoproj;

import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookRoomController {
    @FXML
    private TextField searchField;
    @FXML
    private CheckBox showAvailableCheckBox;
    @FXML
    private TableView<Room> roomTable;
    @FXML
    private TableColumn<Room, Number> roomIdColumn;
    @FXML
    private TableColumn<Room, String> roomTypeColumn;
    @FXML
    private TableColumn<Room, Number> priceColumn;
    @FXML
    private TableColumn<Room, String> availabilityColumn;
    @FXML
    private TableColumn<Room, Void> reserveColumn;
    @FXML
    private Label label;

    private FilteredList<Room> filteredRooms;

    public void initialize() {
        // Initialize the columns
        roomIdColumn.setCellValueFactory(cellData -> cellData.getValue().roomIdProperty());
        roomTypeColumn.setCellValueFactory(cellData -> cellData.getValue().roomTypeProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().pricePerNightProperty());
        availabilityColumn.setCellValueFactory(cellData -> {
            BooleanProperty available = cellData.getValue().availabilityStatusProperty();
            return Bindings.when(available)
                    .then("Available")
                    .otherwise("Reserved");
        });

        loadRoomData();    
        
        // Initialize reserve button in columns
        reserveColumn.setCellFactory(param -> new TableCell<>() {
            private final Button reserveButton = new Button("Reserve");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Room room = getTableView().getItems().get(getIndex());
                    if (room.isAvailable()) {
                        setGraphic(new HBox(reserveButton));
                        reserveButton.setOnAction(event -> reserveRoom(room));
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });

        // Initialize filtered list
        filteredRooms = new FilteredList<>(roomTable.getItems(), p -> true);

        // Add listener to search field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredRooms.setPredicate(room -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchText = newValue.toLowerCase();
                return String.valueOf(room.getRoomId()).toLowerCase().contains(searchText);
            });
        });
        roomTable.setItems(filteredRooms);
        //If "Show Available Rooms Only" is selected only show open rooms
        showAvailableCheckBox.setOnAction(event -> filterRooms());

        // Check if label is not null before setting font
        if (label != null) {
            Font font = Font.font("System Bold", 20);
            label.setFont(font);
        }
    }
    //Loading of room database
    private void loadRoomData() {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        String url = "jdbc:sqlite:C:\\Users\\ma782165\\Documents\\380\\Project\\hotelproject\\demoProj\\src\\main\\java\\com\\mycompany\\hotel_rooms.db";
        String query = "SELECT * FROM HOTEL_ROOMS";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                rooms.add(new Room(
                    rs.getInt("ROOM_NUMBER"),
                    rs.getString("ROOM_TYPE"),
                    rs.getDouble("PRICE"),
                    !rs.getBoolean("RESERVED")
                ));
            }
            roomTable.setItems(rooms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleBack() throws IOException {
        App.setRoot("primary");
    }

    // Filter rooms based on availability
    private void filterRooms() {
        if (showAvailableCheckBox.isSelected()) {
            filteredRooms.setPredicate(Room::isAvailable);
        } else {
            filteredRooms.setPredicate(p -> true);
        }
    }

    // Handle reserve button action
    private void reserveRoom(Room room) {
        // Open reservation menu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReserveRoom.fxml"));
        Parent root;
        try {
            root = loader.load();
            ReserveRoomController controller = loader.getController();
            controller.setSelectedRoom(room);
            controller.setPrimaryController(this);
            controller.initialize();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Updates table after making reservation.
    public void updateTableView() {
        roomTable.refresh();
    }
}

