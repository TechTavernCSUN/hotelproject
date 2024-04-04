import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseCreation {
    public static void main(String[] args) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            // Drop tables if they exist to start fresh
            // Clear existing data (use with caution)
            String sqlClearHotelRooms = "DELETE FROM HOTEL_ROOMS";
            stmt.executeUpdate(sqlClearHotelRooms);

            String sqlClearReservations = "DELETE FROM RESERVATIONS";
            stmt.executeUpdate(sqlClearReservations);

            stmt.executeUpdate("DROP TABLE IF EXISTS HOTEL_ROOMS");
            stmt.executeUpdate("DROP TABLE IF EXISTS RESERVATIONS");

            // Create HOTEL_ROOMS table
            String sqlCreateHotelRooms = "CREATE TABLE IF NOT EXISTS HOTEL_ROOMS " +
                    "(ROOM_NUMBER INT PRIMARY KEY     NOT NULL," +
                    " RESERVED           BOOLEAN NOT NULL, " +
                    " COST               DOUBLE  NOT NULL, " +
                    " ROOM_TYPE          TEXT    NOT NULL)";
            stmt.executeUpdate(sqlCreateHotelRooms);

            // Initialize hotel rooms
            String[] insertRooms = {
                    "INSERT INTO HOTEL_ROOMS (ROOM_NUMBER, RESERVED, COST, ROOM_TYPE) VALUES (101, FALSE, 100.00, 'Single');",
                    "INSERT INTO HOTEL_ROOMS (ROOM_NUMBER, RESERVED, COST, ROOM_TYPE) VALUES (102, FALSE, 150.00, 'Double');",
                    "INSERT INTO HOTEL_ROOMS (ROOM_NUMBER, RESERVED, COST, ROOM_TYPE) VALUES (103, FALSE, 200.00, 'Suite');",
                    "INSERT INTO HOTEL_ROOMS (ROOM_NUMBER, RESERVED, COST, ROOM_TYPE) VALUES (104, FALSE, 100.00, 'Single');",
                    "INSERT INTO HOTEL_ROOMS (ROOM_NUMBER, RESERVED, COST, ROOM_TYPE) VALUES (105, FALSE, 100.00, 'Single');"
            };

            for (String sql : insertRooms) {
                stmt.executeUpdate(sql);
            }

            // Create RESERVATIONS table
            String sqlCreateReservations = "CREATE TABLE IF NOT EXISTS RESERVATIONS " +
                    "(RESERVATION_ID INT PRIMARY KEY     NOT NULL," +
                    " NAME              TEXT    NOT NULL, " +
                    " CHECK_IN          TEXT    NOT NULL, " +
                    " CHECK_OUT         TEXT    NOT NULL, " +
                    " ROOM_NUMBER       INT     NOT NULL, " +
                    " FOREIGN KEY(ROOM_NUMBER) REFERENCES HOTEL_ROOMS(ROOM_NUMBER))";
            stmt.executeUpdate(sqlCreateReservations);

            // Insert reservations for each room
            String[] reservations = {
                    "INSERT INTO RESERVATIONS (RESERVATION_ID, NAME, CHECK_IN, CHECK_OUT, ROOM_NUMBER) VALUES (1, 'John Doe', '2024-04-10', '2024-04-15', 101);",
                    "INSERT INTO RESERVATIONS (RESERVATION_ID, NAME, CHECK_IN, CHECK_OUT, ROOM_NUMBER) VALUES (2, 'Jane Smith', '2024-04-16', '2024-04-20', 102);",
                    "INSERT INTO RESERVATIONS (RESERVATION_ID, NAME, CHECK_IN, CHECK_OUT, ROOM_NUMBER) VALUES (3, 'Bob Brown', '2024-04-21', '2024-04-25', 103);",
                    "INSERT INTO RESERVATIONS (RESERVATION_ID, NAME, CHECK_IN, CHECK_OUT, ROOM_NUMBER) VALUES (4, 'Alice Green', '2024-04-26', '2024-04-30', 104);",
                    //"INSERT INTO RESERVATIONS (RESERVATION_ID, NAME, CHECK_IN, CHECK_OUT, ROOM_NUMBER) VALUES (5, 'Tom White', '2024-05-01', '2024-05-05', 105);"
            };

            for (String sql : reservations) {
                stmt.executeUpdate(sql);
            }

            // Update HOTEL_ROOMS to set RESERVED to TRUE for these room numbers
            String updateRoomsBasedOnReservation = "UPDATE HOTEL_ROOMS SET RESERVED = (SELECT COUNT(*) FROM RESERVATIONS WHERE HOTEL_ROOMS.ROOM_NUMBER = RESERVATIONS.ROOM_NUMBER) > 0";
            stmt.executeUpdate(updateRoomsBasedOnReservation);

            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Tables created, rooms initialized, and reservations added successfully");
    }
}
