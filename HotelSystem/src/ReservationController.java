import java.util.List;
import java.util.Date;

public class ReservationController {
    private DatabaseManager databaseManager;
    //private EmailService emailService; // You'll need to implement this class for sending emails

    public ReservationController(DatabaseManager databaseManager, EmailService emailService) {
        this.databaseManager = databaseManager;
        //this.emailService = emailService;
    }

    // public List<Room> searchAvailableRooms(Date checkInDate, Date checkOutDate, RoomType roomType) {
    //     // Implement logic to search for available rooms based on the provided criteria
    //     return databaseManager.getAvailableRooms(checkInDate, checkOutDate, roomType);
    // }

    public Reservation makeReservation(Client client, Room room, Date checkInDate, Date checkOutDate) {
        // Implement logic to make a reservation
        Reservation reservation = new Reservation(client, room, checkInDate, checkOutDate);
        databaseManager.addReservation(reservation);

        // Send confirmation email to the client
        //emailService.sendConfirmationEmail(client.getEmail(), reservation);

        return reservation;
    }

    public void cancelReservation(Reservation reservation) {
        // Implement logic to cancel a reservation
        databaseManager.deleteReservation(reservation);

        // Send cancellation confirmation email to the client
        //emailService.sendCancellationEmail(reservation.getClient().getEmail(), reservation);
    }

    // public List<Reservation> generateReport() {
    //     // Implement logic to generate a report of current reservations
    //     return databaseManager.getAllReservations();
    // }
}
