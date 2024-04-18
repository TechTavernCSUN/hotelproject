import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
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
                    "PRICE DOUBLE NOT NULL, " +
                    "RESERVED BOOLEAN NOT NULL DEFAULT FALSE)";
            roomsStmt.executeUpdate(sqlCreateHotelRooms);

            // Insert room details
            for (int i = 1; i <= 50; i++) {
                String roomType;
                double price;
                if (i <= 20) {  // Standard rooms
                    roomType = "Standard";
                    price = 100.00;
                } else if (i <= 40) {  // Double rooms
                    roomType = "Double";
                    price = 150.00;
                } else {  // Suites
                    roomType = "Suite";
                    price = 200.00;
                }
                roomsStmt.executeUpdate("INSERT INTO HOTEL_ROOMS (ROOM_NUMBER, ROOM_TYPE, PRICE, RESERVED) " +
                        "VALUES (" + i + ", '" + roomType + "', " + price + ", FALSE);");
            }

            // Create RESERVATIONS table
            String sqlCreateReservations = "CREATE TABLE RESERVATIONS (" +
                    "RESERVATION_ID INT PRIMARY KEY NOT NULL, " +
                    "EMAIL TEXT NOT NULL, " +
                    "NAME TEXT NOT NULL, " +
                    "ROOM_NUMBER INT NOT NULL, " +
                    "PAYMENT TEXT NOT NULL, " +
                    "CHECK_IN DATE NOT NULL, " +
                    "CHECK_OUT DATE NOT NULL, " +
                    "PRICE DOUBLE NOT NULL, " +
                    "TOTAL DOUBLE NOT NULL)";

            reservationsStmt.executeUpdate(sqlCreateReservations);

            // Define check-in date
            Calendar cal = Calendar.getInstance();
            String checkInDate = dateFormat.format(cal.getTime());
            int stay_time = 4;
            cal.add(Calendar.DATE, stay_time);  // Add 4 days for the check-out date
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

                ResultSet priceResult = roomsStmt.executeQuery("SELECT PRICE FROM HOTEL_ROOMS WHERE ROOM_NUMBER = " + roomNumber);
                double pricePerDay = 0;
                if (priceResult.next()) {
                    pricePerDay = priceResult.getDouble("PRICE");
                }

                double totalPrice = pricePerDay * stay_time; // Calculate total price based on stay duration

                // Insert into reservations
                reservationsStmt.executeUpdate("INSERT INTO RESERVATIONS (RESERVATION_ID, EMAIL, NAME, ROOM_NUMBER, PAYMENT, CHECK_IN, CHECK_OUT, PRICE, TOTAL) " +
                        "VALUES (" + (i + 1) + ", '" + email + "', '" + name + "', " + roomNumber + ", '" + paymentType + "', '" +
                        checkInDate + "', '" + checkOutDate + "', " + pricePerDay + ", " + totalPrice + ");");


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