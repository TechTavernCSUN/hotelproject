/**
 * Class Name: Room
 * Date of Code: 4/23/24
 * Name of Coder: Christopher Lagos
 * Description: Room entity class representing a room in a hotel.
 */
package com.mycompany.demoproj;

import javafx.beans.property.*;

public class Room {
    private final IntegerProperty roomId;
    private final StringProperty roomType;
    private final BooleanProperty available;
    private final DoubleProperty pricePerNight;
    /**
     * Constructor for creating a Room object.
     * @param roomId The unique ID of the room.
     * @param roomType The type of the room.
     * @param pricePerNight The price per night of the room.
     * @param available The availability status of the room.
     */

    public Room(int roomId, String roomType, double pricePerNight, boolean available) {
        this.roomId = new SimpleIntegerProperty(roomId);
        this.roomType = new SimpleStringProperty(roomType);
        this.pricePerNight = new SimpleDoubleProperty(pricePerNight);
        this.available = new SimpleBooleanProperty(available);
    }
    /**
     * Getter for roomIdProperty.
     * @return The property representing the room ID.
     */

    public IntegerProperty roomIdProperty() {
        return roomId;
    }
/**
     * Getter for roomId.
     * @return The ID of the room.
     */
    public int getRoomId() {
        return roomId.get();
    }
/**
     * Getter for roomTypeProperty.
     * @return The property representing the room type.
     */
    public StringProperty roomTypeProperty() {
        return roomType;
    }
/**
     * Getter for roomType.
     * @return The type of the room.
     */
    public String getRoomType() {
        return roomType.get();
    }
/**
     * Getter for availableProperty.
     * @return The property representing the availability status.
     */
    public BooleanProperty availableProperty() {
        return available;
    }
/**
     * Getter for isAvailable.
     * @return The availability status of the room.
     */
    public boolean isAvailable() {
        return available.get();
    }
/**
     * Setter for available.
     * @param available The new availability status of the room.
     */
    public void setAvailable(boolean available) {
        this.available.set(available);
    }
/**
     * Getter for pricePerNightProperty.
     * @return The property representing the price per night.
     */
    public DoubleProperty pricePerNightProperty() {
        return pricePerNight;
    }
/**
     * Getter for getPricePerNight.
     * @return The price per night of the room.
     */
    public double getPricePerNight() {
        return pricePerNight.get();
    }
    
     // New method to access availability status property
    /**
     * Getter for availabilityStatusProperty.
     * @return The property representing the availability status.
     */
    public BooleanProperty availabilityStatusProperty() {
        return available;
    }
}

