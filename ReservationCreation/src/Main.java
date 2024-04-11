import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String roomsDbUrl = "jdbc:sqlite:hotel_rooms.db";
        String reservationsDbUrl = "jdbc:sqlite:reservations.db";

        try (Connection roomsConnection = DriverManager.getConnection(roomsDbUrl);
             Connection reservationsConnection = DriverManager.getConnection(reservationsDbUrl)) {

            boolean success = ReservationManager.reserveRoom(roomsConnection, reservationsConnection, 16, new String[]{"John Doe", "john.doe@example.com"});
            if (success) {
                System.out.println("Room reserved successfully.");
            } else {
                System.out.println("Failed to reserve room.");
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }

        try (Connection roomsConnection = DriverManager.getConnection(roomsDbUrl);
             Connection reservationsConnection = DriverManager.getConnection(reservationsDbUrl)) {

            int reservationId = 1; // Example reservation ID to cancel
            boolean success = ReservationManager.cancelReservation(roomsConnection, reservationsConnection, reservationId);
            if (success) {
                System.out.println("Reservation cancelled successfully.");
            } else {
                System.out.println("Failed to cancel reservation.");
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }
}

