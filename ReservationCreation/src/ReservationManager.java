import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * Class: ReservationManager
 * Date: 2024-04-11
 * Programmer: Jacob Spier
 * Description: This class manages room reservations for a hotel. It provides functionalities to
 * reserve and cancel reservations, and to check room availability and prices directly via SQL database
 * interactions.
 *
 * Key Functions:
 * - reserveRoom(): Reserves a room if available and updates reservation details in the database.
 *   Inputs: Connections to room and reservation databases, room number, guest details, payment type, days stayed.
 *   Outputs: boolean (true if reservation succeeds, false otherwise).
 * - cancelReservation(): Cancels an existing reservation based on room number and guest email.
 *   Inputs: Connections to room and reservation databases, room number, guest email.
 *   Outputs: boolean (true if cancellation succeeds, false otherwise).
 *
 * Key Data Structures:
 * - SQL Queries: Uses JDBC to execute SQL queries that interact with the hotel_rooms and reservations databases.
 *
 * Algorithm Description:
 * - The reservation and cancellation algorithms use SQL transactions to ensure data integrity during
 *   insertions and deletions. STRFTIME is used for date manipulation in SQL queries to filter records
 *   by specific months and years.
 */


public class ReservationManager {

    /**
     * Attempts to reserve a room for a specified number of days with given payment details.
     * Checks room availability and price, then inserts a reservation record into the database.
     * Updates the room's reserved status upon successful reservation.
     *
     * @param roomsConnection Connection to the room database
     * @param reservationsConnection Connection to the reservations database
     * @param roomNumber the room number to reserve
     * @param guestDetails array containing guest name and email
     * @param paymentType type of payment (e.g., Credit/Debit, Cash)
     * @param daysStayed number of days of the stay
     * @return true if the reservation is successful, false otherwise
     * @throws SQLException if any SQL errors occur during processing
     */

    public static boolean reserveRoom(Connection roomsConnection, Connection reservationsConnection, int roomNumber, String[] guestDetails, String paymentType, int daysStayed) {
        try {
            if (!isRoomAvailable(roomsConnection, roomNumber)) {
                System.out.println("Room " + roomNumber + " is not available for reservation as it is already reserved.");
                return false;
            }

            double price = getRoomPrice(roomsConnection, roomNumber);  // Ensure this method fetches the price correctly

            int reservationId = getLatestReservationId(reservationsConnection) + 1;
            insertReservation(reservationsConnection, reservationId, guestDetails, roomNumber, price, paymentType, daysStayed);  // Pass paymentType and daysStayed here

            updateRoomStatus(roomsConnection, roomNumber, true);

            return true;
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            return false;
        }
    }


    /**
     * Checks if a specified room is currently available.
     *
     * @param connection Database connection to the room database
     * @param roomNumber The room number to check
     * @return true if the room is available, false otherwise
     * @throws SQLException if a database error occurs
     */
    private static boolean isRoomAvailable(Connection connection, int roomNumber) throws SQLException {
        String sql = "SELECT RESERVED FROM HOTEL_ROOMS WHERE ROOM_NUMBER = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, roomNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return !rs.getBoolean("RESERVED");  // True if room is not reserved
            }
            return false;  // Default return if room does not exist
        }
    }

    /**
     * Retrieves the current price of a specified room from the database.
     *
     * @param connection Database connection to the room database
     * @param roomNumber The room number whose price is being queried
     * @return The price of the room
     * @throws SQLException if the room does not exist or a database error occurs
     */
    private static double getRoomPrice(Connection connection, int roomNumber) throws SQLException {
        String sql = "SELECT PRICE FROM HOTEL_ROOMS WHERE ROOM_NUMBER = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, roomNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("PRICE");
            } else {
                throw new SQLException("No room with the specified number: " + roomNumber);
            }
        }
    }


    /**
     * Inserts a reservation record into the database using details provided.
     *
     * @param connection Database connection to the reservations database
     * @param reservationId Unique ID for the new reservation
     * @param guestDetails Array containing guest's name and email
     * @param roomNumber Room number being reserved
     * @param price Price of the room per night
     * @param paymentType Payment method for the reservation
     * @param daysStayed Duration of the stay in days
     * @throws SQLException if a database error occurs during the insert
     */

    private static void insertReservation(Connection connection, int reservationId, String[] guestDetails, int roomNumber, double price, String paymentType, int daysStayed) throws SQLException {
        double total = price * daysStayed;  // Calculate total based on the stay duration

        // Using LocalDate for current date handling
        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = checkIn.plusDays(daysStayed);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String sql = "INSERT INTO RESERVATIONS (RESERVATION_ID, EMAIL, NAME, PAYMENT, CHECK_IN, CHECK_OUT, ROOM_NUMBER, PRICE, TOTAL) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, reservationId);
            pstmt.setString(2, guestDetails[1]); // Email
            pstmt.setString(3, guestDetails[0]); // Name
            pstmt.setString(4, paymentType); // Payment
            pstmt.setString(5, checkIn.format(formatter)); // Formatted check-in date
            pstmt.setString(6, checkOut.format(formatter)); // Formatted check-out date
            pstmt.setInt(7, roomNumber); // Room Number
            pstmt.setDouble(8, price); // Price per day of room number
            pstmt.setDouble(9, total);  // Set the calculated total
            pstmt.executeUpdate();
        }
    }

    /**
     * Retrieves the maximum current reservation ID from the database to ensure new reservation IDs are unique.
     *
     * @param connection Database connection to the reservations database
     * @return the highest reservation ID present in the database
     * @throws SQLException if a database error occurs during the query
     */
    private static int getLatestReservationId(Connection connection) throws SQLException {
        String sql = "SELECT MAX(RESERVATION_ID) as max_id FROM RESERVATIONS";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("max_id");
            }
            return 0; // Default to 0 if no reservations are found
        }
    }

    /**
     * Cancels a reservation based on room number and guest email. Ensures that both the reservation record is deleted
     * and the room status is updated to not reserved, handling transactions to maintain database integrity.
     *
     * @param roomsConnection Connection to the room database
     * @param reservationsConnection Connection to the reservations database
     * @param roomNumber the room number associated with the reservation
     * @param email the email of the guest whose reservation is to be canceled
     * @return true if the cancellation is successful, false otherwise
     * @throws SQLException if an error occurs during the database transaction
     */
    public static boolean cancelReservation(Connection roomsConnection, Connection reservationsConnection, int roomNumber, String email) {
        try {
            // Open transaction
            roomsConnection.setAutoCommit(false);
            reservationsConnection.setAutoCommit(false);

            // Check if a reservation with the given room number and email exists
            int reservationId = findReservationIdByEmailAndRoom(reservationsConnection, roomNumber, email);
            if (reservationId == -1) {
                System.out.println("No reservation found for the given room number and email.");
                return false;
            }

            // Delete the reservation
            if (!deleteReservation(reservationsConnection, reservationId)) {
                reservationsConnection.rollback();  // Rollback if deletion fails
                System.out.println("Failed to delete reservation.");
                return false;
            }

            // Update the room status to not reserved
            if (!updateRoomStatus(roomsConnection, roomNumber, false)) {
                roomsConnection.rollback();  // Rollback if room update fails
                System.out.println("Failed to update room status.");
                return false;
            }

            // Commit the transaction
            roomsConnection.commit();
            reservationsConnection.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("SQL Error during cancellation: " + e.getMessage());
            try {
                roomsConnection.rollback();
                reservationsConnection.rollback();
            } catch (SQLException se) {
                System.err.println("Error rolling back transaction: " + se.getMessage());
            }
            return false;
        } finally {
            try {
                roomsConnection.setAutoCommit(true);
                reservationsConnection.setAutoCommit(true);
            } catch (SQLException se) {
                System.err.println("Error resetting auto-commit: " + se.getMessage());
            }
        }
    }


    /**
     * Finds the reservation ID associated with a given room number and guest email.
     *
     * @param connection Database connection to the reservations database
     * @param roomNumber Room number to check for reservation
     * @param email Email associated with the reservation
     * @return the reservation ID if found, -1 otherwise
     * @throws SQLException if a database error occurs during the query
     */
    private static int findReservationIdByEmailAndRoom(Connection connection, int roomNumber, String email) throws SQLException {
        String sql = "SELECT RESERVATION_ID FROM RESERVATIONS WHERE ROOM_NUMBER = ? AND EMAIL = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, roomNumber);
            pstmt.setString(2, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("RESERVATION_ID");
                }
            }
        }
        return -1; // Return -1 if no matching reservation is found
    }


    /**
     * Updates the reservation status of a room in the database.
     *
     * @param connection Database connection to the room database
     * @param roomNumber the room number whose status is to be updated
     * @param reserved the new reservation status (true if reserved, false if not)
     * @return true if the update is successful, false otherwise
     * @throws SQLException if a database error occurs during the update
     */
    private static boolean updateRoomStatus(Connection connection, int roomNumber, boolean reserved) throws SQLException {
        String sql = "UPDATE HOTEL_ROOMS SET RESERVED = ? WHERE ROOM_NUMBER = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBoolean(1, reserved);
            pstmt.setInt(2, roomNumber);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Deletes a reservation from the database based on its ID.
     *
     * @param connection Database connection to the reservations database
     * @param reservationId the ID of the reservation to delete
     * @return true if the deletion is successful, false otherwise
     * @throws SQLException if a database error occurs during the deletion
     */
    private static boolean deleteReservation(Connection connection, int reservationId) throws SQLException {
        String sql = "DELETE FROM RESERVATIONS WHERE RESERVATION_ID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, reservationId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}
