import java.util.Date;

public class Reservation {
    private Client client;
    private Room room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(Client client, Room room, Date checkInDate, Date checkOutDate) {
        this.client = client;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Client getClient() {
        return client;
    }

    public Room getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }
}