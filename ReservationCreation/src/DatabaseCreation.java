import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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

            String sqlCreateHotelRooms = "CREATE TABLE HOTEL_ROOMS " +
                    "(ROOM_NUMBER INT PRIMARY KEY NOT NULL," +
                    " RESERVED BOOLEAN NOT NULL, " + // Removed EMAIL field
                    " COST DOUBLE NOT NULL, " +
                    " ROOM_TYPE TEXT NOT NULL)";
            roomsStmt.executeUpdate(sqlCreateHotelRooms);

            // Create RESERVATIONS table in the reservations database with an email column
            String sqlCreateReservations = "CREATE TABLE RESERVATIONS " +
                    "(RESERVATION_ID INT PRIMARY KEY NOT NULL," +
                    " EMAIL TEXT," + // Added email field
                    " NAME TEXT NOT NULL, " +
                    " PAYMENT TEXT NOT NULL, " +
                    " CHECK_IN TEXT NOT NULL, " +
                    " CHECK_OUT TEXT NOT NULL, " +
                    " ROOM_NUMBER INT NOT NULL)";
            reservationsStmt.executeUpdate(sqlCreateReservations);

            // Generate a set of 30 random room numbers to be reserved
            Random random = new Random();
            Set<Integer> reservedRoomsSet = new HashSet<>();
            while (reservedRoomsSet.size() < 30) {
                int floor = (1 + random.nextInt(5)) * 100; // Generate a floor number (100, 200, 300, 400, 500)
                int room = floor + 1 + random.nextInt(10); // Generate room number (101-110, 201-210, ...)
                reservedRoomsSet.add(room);
            }

            // Further down, when inserting rooms, remove the email from insertion
            for (int floor = 1; floor <= 5; floor++) {
                for (int room = 1; room <= 10; room++) {
                    int roomNumber = floor * 100 + room;
                    String roomType = room % 3 == 0 ? "Suite" : (room % 2 == 0 ? "Double" : "Single");
                    double cost = roomType.equals("Suite") ? 300.00 : (roomType.equals("Double") ? 200.00 : 100.00);
                    boolean reserved = reservedRoomsSet.contains(roomNumber);
                    roomsStmt.executeUpdate("INSERT INTO HOTEL_ROOMS (ROOM_NUMBER, RESERVED, COST, ROOM_TYPE) " +
                            "VALUES (" + roomNumber + ", " + reserved + ", " + cost + ", '" + roomType + "');");
                }
            }



            // Insert reservations for the random reserved rooms
            int reservationId = 1;
            for (int roomNumber : reservedRoomsSet) {
                if (reservationId <= SAMPLE_GUESTS.length) { // Check to avoid ArrayIndexOutOfBoundsException
                    String name = SAMPLE_GUESTS[reservationId - 1][0]; // Get the name from the array
                    String email = SAMPLE_GUESTS[reservationId - 1][1]; // Get the email from the array
                    String paymentType = random.nextBoolean() ? "Credit/Debit" : "Cash"; // Randomly assign payment type
                    reservationsStmt.executeUpdate("INSERT INTO RESERVATIONS (RESERVATION_ID, EMAIL, NAME, PAYMENT, CHECK_IN, CHECK_OUT, ROOM_NUMBER) " +
                            "VALUES (" + reservationId + ", '" + email + "', '" + name + "', '" + paymentType + "', '2024-01-01', '2024-01-05', " + roomNumber + ");");
                    reservationId++;
                } else {
                    // Handle the case where there are more rooms reserved than guests in the sample list
                    // This could involve breaking the loop, or deciding to start over from the first guest, etc.
                    break; // Simplest approach: stop creating more reservations
                }
            }

            System.out.println("Tables created, rooms initialized, and reservations added successfully");

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
