import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class Name: TestSystem
 * Date of the Code: 4/11/2024
 * Programmer's Name: Jacob Spier
 * Description: Main test class for a Java application handling hotel room reservations and cancellations,
 *              and calculates revenue. Utilizes JDBC for database connectivity.
 *
 * Important Functions:
 * - main(String[] args): Establishes database connections, processes reservations and cancellations,
 *   and invokes revenue calculation.
 *   Inputs: args (String[]) - command line arguments
 *   Outputs: void
 *
 * Important Data Structures:
 * - Connection (from java.sql): Handles connections to the SQLite databases for rooms and reservations.
 *
 * Algorithms Used:
 * - Uses standard JDBC connectivity procedures for SQLite. Operations include basic CRUD functions
 *   for managing reservations. Specific algorithms for reservation and cancellation logic, and revenue
 *   calculations are implemented in other classes not detailed in this snippet.
 */

public class TestSystem {
    public static void main(String[] args) {
        String roomsDbUrl = "jdbc:sqlite:hotel_rooms.db";
        String reservationsDbUrl = "jdbc:sqlite:reservations.db";



        try (Connection roomsConnection = DriverManager.getConnection(roomsDbUrl);
             Connection reservationsConnection = DriverManager.getConnection(reservationsDbUrl)) {

            String paymentType = "Cash";
            int daysStayed = 4;
            int roomNumber = 19;

            boolean success = ReservationManager.reserveRoom(roomsConnection, reservationsConnection, roomNumber, new String[]{"John Doe", "john.doe@example.com"}, paymentType, daysStayed);
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

            int roomNumber = 2;
            String email = "bob.smith@example.com";
            boolean success = ReservationManager.cancelReservation(roomsConnection, reservationsConnection, roomNumber, email);
            if (success) {
                System.out.println("Reservation cancelled successfully.");
            } else {
                System.out.println("Failed to cancel reservation.");
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }

        RevenueCalculator calculator = new RevenueCalculator(reservationsDbUrl);

        //Calculate revenue for year
        calculator.calculateMonthlyRevenue();
    }
}
