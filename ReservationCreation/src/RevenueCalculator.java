import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RevenueCalculator {

    private String dbUrl;

    public RevenueCalculator(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public void calculateMonthlyRevenue(int year, int month) {
        // Corrected column name from COST to total as per the given schema information
        String sql = "SELECT COUNT(*) AS room_count, SUM(total) AS total_revenue FROM reservations " +
                "WHERE STRFTIME('%Y-%m', check_in) = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Format the year and month in 'YYYY-MM' format
            String monthString = String.format("%04d-%02d", year, month);
            pstmt.setString(1, monthString);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int roomCount = rs.getInt("room_count");
                double totalRevenue = rs.getDouble("total_revenue");
                System.out.println("Total rooms booked in " + monthString + ": " + roomCount);
                System.out.println("Total revenue for " + monthString + ": $" + totalRevenue);
            } else {
                System.out.println("No reservations found for " + monthString);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }
}
