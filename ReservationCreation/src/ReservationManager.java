import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class ReservationManager {

    public static boolean reserveRoom(Connection roomsConnection, Connection reservationsConnection, int roomNumber, String[] guestDetails) {
        try {
            int reservationId = getLatestReservationId(reservationsConnection) + 1;
            insertReservation(reservationsConnection, reservationId, guestDetails, roomNumber);
            updateRoomStatus(roomsConnection, roomNumber, true);
            return true;
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            return false;
        }
    }

    private static int getLatestReservationId(Connection connection) throws SQLException {
        String sql = "SELECT MAX(RESERVATION_ID) as max_id FROM RESERVATIONS";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("max_id");
            }
            return 0; // Default to 0 if no reservations are found
        }
    }

    private static void insertReservation(Connection connection, int reservationId, String[] guestDetails, int roomNumber) throws SQLException {
        String sql = "INSERT INTO RESERVATIONS (RESERVATION_ID, EMAIL, NAME, PAYMENT, CHECK_IN, CHECK_OUT, ROOM_NUMBER) " +
                "VALUES (?, ?, ?, ?, '2024-01-01', '2024-01-05', ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, reservationId);
            pstmt.setString(2, guestDetails[1]); // Email
            pstmt.setString(3, guestDetails[0]); // Name
            pstmt.setString(4, new Random().nextBoolean() ? "Credit/Debit" : "Cash");
            pstmt.setInt(5, roomNumber);
            pstmt.executeUpdate();
        }
    }

    public static boolean cancelReservation(Connection roomsConnection, Connection reservationsConnection, int reservationId) {
        try {
            // Disable auto-commit for transactional integrity
            roomsConnection.setAutoCommit(false);
            reservationsConnection.setAutoCommit(false);

            // First, get the room number from the reservation to be canceled
            int roomNumber = getRoomNumberForReservation(reservationsConnection, reservationId);
            if (roomNumber == -1) {
                System.out.println("No reservation found with ID: " + reservationId);
                return false;
            }

            // Delete the reservation
            if (!deleteReservation(reservationsConnection, reservationId)) {
                reservationsConnection.rollback();  // Rollback reservation changes
                System.out.println("Failed to delete reservation.");
                return false;
            }

            // Update the room status
            if (!updateRoomStatus(roomsConnection, roomNumber, false)) {
                roomsConnection.rollback();  // Rollback room status changes
                System.out.println("Failed to update room status.");
                return false;
            }

            // Commit transactions if all operations are successful
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

    private static boolean updateRoomStatus(Connection connection, int roomNumber, boolean reserved) throws SQLException {
        String sql = "UPDATE HOTEL_ROOMS SET RESERVED = ? WHERE ROOM_NUMBER = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBoolean(1, reserved);
            pstmt.setInt(2, roomNumber);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    private static boolean deleteReservation(Connection connection, int reservationId) throws SQLException {
        String sql = "DELETE FROM RESERVATIONS WHERE RESERVATION_ID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, reservationId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    private static int getRoomNumberForReservation(Connection connection, int reservationId) throws SQLException {
        String sql = "SELECT ROOM_NUMBER FROM RESERVATIONS WHERE RESERVATION_ID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, reservationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ROOM_NUMBER");
                }
            }
        }
        return -1; // Return -1 if no reservation is found
    }
}
