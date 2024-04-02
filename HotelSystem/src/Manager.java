import java.util.List;

public class Manager {
    private String name;
    private String email;

    public Manager(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // Method to generate report using ReservationController's generateReport() method
    public void getReport(ReservationController reservationController) {
        List<Reservation> report = reservationController.generateReport();
        System.out.println("Report generated by " + name + ":");
        for (Reservation reservation : report) {
            System.out.println("Client: " + reservation.getClient().getName() +
                    ", Room Number: " + reservation.getRoom().getNumber() +
                    ", Check-in Date: " + reservation.getCheckInDate() +
                    ", Check-out Date: " + reservation.getCheckOutDate());
        }
    }
}