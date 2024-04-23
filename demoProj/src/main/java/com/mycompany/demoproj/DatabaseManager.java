package com.mycompany.demoproj;

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

public class DatabaseManager {
    
    
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
