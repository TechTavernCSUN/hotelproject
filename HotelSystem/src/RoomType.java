public class RoomType {
    private String typeName;
    private double pricePerNight;

    public RoomType(String typeName, double pricePerNight) {
        this.typeName = typeName;
        this.pricePerNight = pricePerNight;
    }

    public String getTypeName() {
        return typeName;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }
}
