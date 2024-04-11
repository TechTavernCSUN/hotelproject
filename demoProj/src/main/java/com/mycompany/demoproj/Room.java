
package com.mycompany.demoproj;

import javafx.beans.property.*;

public class Room {
    private final IntegerProperty roomId;
    private final StringProperty roomType;
    private final BooleanProperty available;
    private final IntegerProperty pricePerNight;

    public Room(int roomId, String roomType, boolean available, int pricePerNight) {
        this.roomId = new SimpleIntegerProperty(roomId);
        this.roomType = new SimpleStringProperty(roomType);
        this.available = new SimpleBooleanProperty(available);
        this.pricePerNight = new SimpleIntegerProperty(pricePerNight);
    }

    public IntegerProperty roomIdProperty() {
        return roomId;
    }

    public int getRoomId() {
        return roomId.get();
    }

    public StringProperty roomTypeProperty() {
        return roomType;
    }

    public String getRoomType() {
        return roomType.get();
    }

    public BooleanProperty availableProperty() {
        return available;
    }

    public boolean isAvailable() {
        return available.get();
    }

    public void setAvailable(boolean available) {
        this.available.set(available);
    }

    public IntegerProperty pricePerNightProperty() {
        return pricePerNight;
    }

    public int getPricePerNight() {
        return pricePerNight.get();
    }
    
    // New method to access availability status property
    public BooleanProperty availabilityStatusProperty() {
        return available;
    }
}

