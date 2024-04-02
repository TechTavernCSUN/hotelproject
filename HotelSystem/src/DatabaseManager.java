import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Date;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelDB", "username", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methods for CRUD operations (Create, Read, Update, Delete)

    public List<Room> getAvailableRooms(Date checkInDate, Date checkOutDate, RoomType roomType) {
        // Implement logic to query database for available rooms within the given date range and room type
        // Return a list of available rooms
        // Example SQL query: SELECT * FROM Rooms WHERE available = true AND type = 'SINGLE';
        // You need to perform JOINs and other necessary operations based on your database schema
        // Make sure to handle database exceptions properly
        // Use PreparedStatement to avoid SQL injection
    }

    public void addReservation(Reservation reservation) {
        // Implement logic to add a reservation to the database
        // Example SQL query: INSERT INTO Reservations (client_id, room_id, check_in_date, check_out_date) VALUES (?, ?, ?, ?);
        // Make sure to handle database exceptions properly
        // Use PreparedStatement to avoid SQL injection
    }

    public void deleteReservation(Reservation reservation) {
        // Implement logic to delete a reservation from the database
        // Example SQL query: DELETE FROM Reservations WHERE id = ?;
        // Make sure to handle database exceptions properly
        // Use PreparedStatement to avoid SQL injection
    }

    public List<Reservation> getAllReservations() {
        // Implement logic to retrieve all reservations from the database
        // Example SQL query: SELECT * FROM Reservations;
        // Make sure to handle database exceptions properly
        // Use PreparedStatement to avoid SQL injection
    }

    public Reservation getReservationById(int reservationId) {
        // Implement logic to retrieve a reservation by its ID from the database
        // Example SQL query: SELECT * FROM Reservations WHERE id = ?;
        // Make sure to handle database exceptions properly
        // Use PreparedStatement to avoid SQL injection
    }

    public List<Reservation> getReservationsByClient(Client client) {
        // Implement logic to retrieve all reservations of a client from the database
        // Example SQL query: SELECT * FROM Reservations WHERE client_id = ?;
        // Make sure to handle database exceptions properly
        // Use PreparedStatement to avoid SQL injection
    }

    public void addClient(Client client) {
        // Implement logic to add a new client to the database
    }

    public void updateClient(Client client) {
        // Implement logic to update client information in the database
    }

    public void deleteClient(Client client) {
        // Implement logic to delete a client and associated reservations from the database
    }

    // public void addHotel(Hotel hotel) {
    //     // Implement logic to add a new hotel to the database
    // }

    // public void updateHotel(Hotel hotel) {
    //     // Implement logic to update hotel information in the database
    // }

    // public void deleteHotel(Hotel hotel) {
    //     // Implement logic to delete a hotel, associated rooms, and reservations from the database
    // }

}
