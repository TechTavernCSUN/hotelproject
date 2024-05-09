
package com.mycompany.demoproj;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Room {
    private final IntegerProperty roomId;
    private final StringProperty roomType;
    private final BooleanProperty available;
    private final DoubleProperty pricePerNight;

    public Room(int roomId, String roomType, double pricePerNight, boolean available) {
        this.roomId = new SimpleIntegerProperty(roomId);
        this.roomType = new SimpleStringProperty(roomType);
        this.pricePerNight = new SimpleDoubleProperty(pricePerNight);
        this.available = new SimpleBooleanProperty(available);
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

    public DoubleProperty pricePerNightProperty() {
        return pricePerNight;
    }

    public double getPricePerNight() {
        return pricePerNight.get();
    }

    // New method to access availability status property
    public BooleanProperty availabilityStatusProperty() {
        return available;
    }
}

