package com.mycompany.demoproj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.util.Pair;

/**
 * Class: RevenueCalculator
 * Date: 2024-04-18
 * Programmer: Jacob Spier
 * Description: This class provides functionalities to compute and report the monthly revenue
 * from hotel room reservations stored in a database. It uses SQL queries to retrieve data
 * and Java's LocalDate for date manipulation.
 *
 * Important Functions:
 * - calculateMonthlyRevenue(): Computes and prints the revenue for the current month.
 *   Inputs: None
 *   Outputs: Void (side effects include printing to the console)
 *   Data Structures: Uses SQL queries and processes results using JDBC.
 *   Algorithm: Utilizes STRFTIME to filter database rows by month/year directly in SQL for efficiency.
 *
 * Key Data Structures:
 * - SQL Connection: Managed via JDBC to interface with the database.
 * - LocalDate and DateTimeFormatter: Used for handling and formatting current date.
 *
 * Algorithm Description:
 * - The method calculateMonthlyRevenue uses a PreparedStatement to execute a SQL query
 *   that filters reservation records by the current month and year. This approach was selected
 *   for its direct support in SQL (via STRFTIME) for date-based queries, which reduces the
 *   data processing load on the Java application side.
 */
public class RevenueCalculator {

    private String dbUrl;

    /**
     * Constructs a RevenueCalculator with the specified database URL.
     * @param dbUrl the URL of the database to connect to
     */

    public RevenueCalculator(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    /**
     * Calculates and prints the total rooms booked and total revenue for the current month.
     * This method connects to the database, constructs a query to find the revenue and room count
     * for the current month, executes the query, and prints the results.
     * It handles any SQL exceptions by printing error messages to standard error.
     */

    public Pair<Integer, Double> calculateMonthlyRevenue() {
        // Get current date
        LocalDate currentDate = LocalDate.now();
        // Format the current year and month in 'YYYY-MM' format
        String monthString = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        String sql = "SELECT COUNT(*) AS room_count, SUM(total) AS total_revenue FROM reservations " +
                "WHERE STRFTIME('%Y-%m', check_in) = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, monthString);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int roomCount = rs.getInt("room_count");
                double totalRevenue = rs.getDouble("total_revenue");
                return new Pair<>(roomCount, totalRevenue);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        // Return default values if no data is found
        return new Pair<>(0, 0.0);
    }
}
