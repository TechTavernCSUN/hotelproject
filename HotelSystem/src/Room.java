public class Room {
    private int number;
    private RoomType type;
    private boolean available;

    public Room(int number, RoomType type, boolean available) {
        this.number = number;
        this.type = type;
        this.available = available;
    }

    public int getNumber() {
        return number;
    }

    public RoomType getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}