
package com.mycompany.demoproj;

import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Reservation {
    private final SimpleIntegerProperty reservationId;
    private final SimpleStringProperty email;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty roomNumber;
    private final SimpleStringProperty checkIn;
    private final SimpleStringProperty checkOut;
    private final SimpleStringProperty payment;
    private final SimpleDoubleProperty price;
    private final SimpleDoubleProperty total; 

    public Reservation(int reservationId, String email, String name, int roomNumber, String checkIn, String checkOut, String payment, double price, double total) {
        this.reservationId = new SimpleIntegerProperty(reservationId);
        this.email = new SimpleStringProperty(email);
        this.name = new SimpleStringProperty(name);
        this.roomNumber = new SimpleIntegerProperty(roomNumber);
        this.checkIn = new SimpleStringProperty(checkIn);
        this.checkOut = new SimpleStringProperty(checkOut);
        this.payment = new SimpleStringProperty(payment);
        this.price = new SimpleDoubleProperty(price);
        this.total = new SimpleDoubleProperty(total);
    }

    // Getters for the properties to bind to table columns
    public SimpleIntegerProperty reservationIdProperty() { return reservationId; }
    public SimpleStringProperty emailProperty() { return email; }
    public SimpleStringProperty nameProperty() { return name; }
    public SimpleIntegerProperty roomNumberProperty() { return roomNumber; }
    public SimpleStringProperty checkInProperty() { return checkIn; }
    public SimpleStringProperty checkOutProperty() { return checkOut; }
    public SimpleStringProperty paymentProperty() { return payment; }
    public SimpleDoubleProperty priceProperty() { return price; }
    public SimpleDoubleProperty totalProperty() { return total; }
}


