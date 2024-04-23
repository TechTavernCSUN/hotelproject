import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class Name: ReservationSystemTest
 * Date of the Code: Not specified
 * Programmer's Name: Not specified
 * Description: Test class for the ReservationSystem using JUnit to ensure that reservation and cancellation functionalities
 *              work as expected under various conditions.
 *
 * Important Functions:
 * - setUp(): Sets up database connections before each test.
 *   Inputs: None
 *   Outputs: void
 * - tearDown(): Closes database connections after each test to clean up resources.
 *   Inputs: None
 *   Outputs: void
 * - testReserveRoomSuccess(): Tests successful room reservation.
 *   Inputs: room number, guest details, payment type, days stayed
 *   Outputs: Asserts true if reservation is successful
 * - testReserveRoomFailure(): Tests room reservation failure due to room unavailability.
 *   Inputs: room number, guest details, payment type, days stayed
 *   Outputs: Asserts false if reservation fails
 * - testCancelReservationSuccess(): Tests successful cancellation of an existing reservation.
 *   Inputs: room number, guest email
 *   Outputs: Asserts true if cancellation is successful
 * - testCancelReservationFailure(): Tests failure in canceling a non-existing reservation.
 *   Inputs: room number, guest email
 *   Outputs: Asserts false if cancellation fails
 *
 * Important Data Structures:
 * - Connection (from java.sql): Manages connections to the SQLite databases for rooms and reservations.
 *
 * Algorithms Used:
 * - Utilizes JDBC standard procedures for managing database connections and transactions. Specific logic for reservation and
 *   cancellation is tested based on the implementation provided by ReservationManager class, assuming it handles SQL transactions.
 */
public class ReservationSystemTest {
    private Connection roomsConnection;
    private Connection reservationsConnection;
    private String roomsDbUrl = "jdbc:sqlite:hotel_rooms.db";
    private String reservationsDbUrl = "jdbc:sqlite:reservations.db";

    @Before
    public void setUp() throws SQLException {
        roomsConnection = DriverManager.getConnection(roomsDbUrl);
        reservationsConnection = DriverManager.getConnection(reservationsDbUrl);
        // Additional setup like preparing mock data can be done here
    }

    @After
    public void tearDown() throws SQLException {
        // Clean up databases or close connections if necessary
        if (roomsConnection != null) {
            roomsConnection.close();
        }
        if (reservationsConnection != null) {
            reservationsConnection.close();
        }
    }

    @Test
    public void testReserveRoomSuccess() throws SQLException {
        int roomNumber = 16; // Ensure this room is available in your test setup
        String[] guestDetails = {"John Doe", "john.doe@example.com"};
        String paymentType = "Cash";
        int daysStayed = 4;

        boolean success = ReservationManager.reserveRoom(roomsConnection, reservationsConnection, roomNumber, guestDetails, paymentType, daysStayed);
        Assert.assertTrue("Room should be reserved successfully", success);
    }

    @Test
    public void testReserveRoomFailure() throws SQLException {
        int roomNumber = 15; // Ensure this room is not available in your test setup
        String[] guestDetails = {"Alice Johnson", "alice.johnson@example.com"};
        String paymentType = "Credit/Debit";
        int daysStayed = 3;

        boolean success = ReservationManager.reserveRoom(roomsConnection, reservationsConnection, roomNumber, guestDetails, paymentType, daysStayed);
        Assert.assertFalse("Room should fail to be reserved as it is not available", success);
    }

    @Test
    public void testCancelReservationSuccess() throws SQLException {
        int roomNumber = 2; // Ensure this room has an existing reservation in your test setup
        String email = "bob.smith@example.com";

        boolean success = ReservationManager.cancelReservation(roomsConnection, reservationsConnection, roomNumber, email);
        Assert.assertTrue("Reservation should be cancelled successfully", success);
    }

    @Test
    public void testCancelReservationFailure() throws SQLException {
        int roomNumber = 3; // Ensure this room does not have a reservation in your test setup
        String email = "nonexistent@example.com";

        boolean success = ReservationManager.cancelReservation(roomsConnection, reservationsConnection, roomNumber, email);
        Assert.assertFalse("Cancellation should fail as no such reservation exists", success);
    }
}
