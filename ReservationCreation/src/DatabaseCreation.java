import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
<<<<<<< HEAD

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
=======
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatabaseCreation {
    private static final String[][] SAMPLE_GUESTS = new String[][] {
            {"Alice Johnson", "alice.johnson@example.com"},
            {"Bob Smith", "bob.smith@example.com"},
            {"Charlie Brown", "charlie.brown@example.com"},
            {"David Clark", "david.clark@example.com"},
            {"Eva Martin", "eva.martin@example.com"},
            {"Fiona White", "fiona.white@example.com"},
            {"George Harris", "george.harris@example.com"},
            {"Hannah Davis", "hannah.davis@example.com"},
            {"Ian Walker", "ian.walker@example.com"},
            {"Jane Hall", "jane.hall@example.com"},
            {"Kyle Moore", "kyle.moore@example.com"},
            {"Laura King", "laura.king@example.com"},
            {"Michael Young", "michael.young@example.com"},
            {"Nora Baker", "nora.baker@example.com"},
            {"Oliver Wright", "oliver.wright@example.com"},
            {"Patricia Green", "patricia.green@example.com"},
            {"Quinn Lee", "quinn.lee@example.com"},
            {"Rachel Adams", "rachel.adams@example.com"},
            {"Steven Parker", "steven.parker@example.com"},
            {"Tina Turner", "tina.turner@example.com"},
            {"Ulysses Cook", "ulysses.cook@example.com"},
            {"Violet Phillips", "violet.phillips@example.com"},
            {"William Carter", "william.carter@example.com"},
            {"Xavier Scott", "xavier.scott@example.com"},
            {"Yvonne Morgan", "yvonne.morgan@example.com"},
            {"Zachary Bell", "zachary.bell@example.com"},
            {"Liam Anderson", "liam.anderson@example.com"},
            {"Sophia Thompson", "sophia.thompson@example.com"},
            {"Mason Hill", "mason.hill@example.com"},
            {"Isabella Stewart", "isabella.stewart@example.com"}
    };

    public static void main(String[] args) {
        Connection roomsConnection = null;
        Connection reservationsConnection = null;
        Statement roomsStmt = null;
        Statement reservationsStmt = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Class.forName("org.sqlite.JDBC");

            // Open connections to the databases
            roomsConnection = DriverManager.getConnection("jdbc:sqlite:hotel_rooms.db");
            reservationsConnection = DriverManager.getConnection("jdbc:sqlite:reservations.db");
            System.out.println("Opened databases successfully");

            // Create statements for each database
            roomsStmt = roomsConnection.createStatement();
            reservationsStmt = reservationsConnection.createStatement();

            // Drop tables if they exist to start fresh
            roomsStmt.executeUpdate("DROP TABLE IF EXISTS HOTEL_ROOMS");
            reservationsStmt.executeUpdate("DROP TABLE IF EXISTS RESERVATIONS");

            // Create HOTEL_ROOMS table
            String sqlCreateHotelRooms = "CREATE TABLE HOTEL_ROOMS (" +
                    "ROOM_NUMBER INT PRIMARY KEY NOT NULL, " +
                    "ROOM_TYPE TEXT NOT NULL, " +
                    "COST DOUBLE NOT NULL, " +
                    "RESERVED BOOLEAN NOT NULL DEFAULT FALSE)";
            roomsStmt.executeUpdate(sqlCreateHotelRooms);

            // Insert room details
            for (int i = 1; i <= 50; i++) {
                String roomType;
                double cost;
                if (i <= 20) {  // Standard rooms
                    roomType = "Standard";
                    cost = 100.00;
                } else if (i <= 40) {  // Double rooms
                    roomType = "Double";
                    cost = 150.00;
                } else {  // Suites
                    roomType = "Suite";
                    cost = 200.00;
                }
                roomsStmt.executeUpdate("INSERT INTO HOTEL_ROOMS (ROOM_NUMBER, ROOM_TYPE, COST, RESERVED) " +
                        "VALUES (" + i + ", '" + roomType + "', " + cost + ", FALSE);");
            }

            // Create RESERVATIONS table
            String sqlCreateReservations = "CREATE TABLE RESERVATIONS (" +
                    "RESERVATION_ID INT PRIMARY KEY NOT NULL, " +
                    "EMAIL TEXT NOT NULL, " +
                    "NAME TEXT NOT NULL, " +
                    "ROOM_NUMBER INT NOT NULL, " +
                    "PAYMENT TEXT NOT NULL, " +
                    "CHECK_IN DATE NOT NULL, " +
                    "CHECK_OUT DATE NOT NULL)";
            reservationsStmt.executeUpdate(sqlCreateReservations);

            // Define check-in date
            Calendar cal = Calendar.getInstance();
            String checkInDate = dateFormat.format(cal.getTime());
            cal.add(Calendar.DATE, 4);  // Add 4 days for the check-out date
            String checkOutDate = dateFormat.format(cal.getTime());

            // Assign guests to rooms and update reservation status
            for (int i = 0; i < SAMPLE_GUESTS.length; i++) {
                int roomNumber;
                String paymentType;
                if (i < 15) {  // Assign the first 15 to standard rooms
                    roomNumber = i + 1;
                    paymentType = "Cash";  // Payment type for standard rooms
                } else if (i < 25) {  // Assign the next 10 to double rooms
                    roomNumber = 21 + (i - 15);
                    paymentType = "Credit/Debit";  // Payment type for double rooms
                } else if (i < 30) {  // Assign the next 5 to suites
                    roomNumber = 41 + (i - 25);
                    paymentType = "Credit/Debit";  // Payment type for suites
                } else {
                    break;  // No more rooms to assign
                }

                String email = SAMPLE_GUESTS[i][1];
                String name = SAMPLE_GUESTS[i][0];

                // Insert into reservations
                reservationsStmt.executeUpdate("INSERT INTO RESERVATIONS (RESERVATION_ID, EMAIL, NAME, ROOM_NUMBER, PAYMENT, CHECK_IN, CHECK_OUT) " +
                        "VALUES (" + (i + 1) + ", '" + email + "', '" + name + "', " + roomNumber + ", '" + paymentType + "', '" +
                        checkInDate + "', '" + checkOutDate + "');");

                // Update rooms to reserved
                roomsStmt.executeUpdate("UPDATE HOTEL_ROOMS SET RESERVED = TRUE WHERE ROOM_NUMBER = " + roomNumber);
            }

            System.out.println("Tables created and initialized successfully.");

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } finally {
            // Close connections and statements
            try {
                if (roomsStmt != null) roomsStmt.close();
                if (reservationsStmt != null) reservationsStmt.close();
                if (roomsConnection != null) roomsConnection.close();
                if (reservationsConnection != null) reservationsConnection.close();
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }
}
>>>>>>> 101446230f3a5520320996a3e03650f961f8998f
