package parking.backend;
import parking.database.DBConnection;


import java.sql.*;
import java.time.LocalTime;


public class ParkingDAO {

    // Park Vehicle → insert into current_vehicles
    public static boolean parkVehicle(String vehicleNo, int slotNo, String type, LocalTime entryTime) {
        String sql = "INSERT INTO current_vehicles (vehicle_no, slot_no, vehicle_type, entry_time) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vehicleNo);
            ps.setInt(2, slotNo);
            ps.setString(3, type);
            ps.setTime(4, Time.valueOf(entryTime));

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("Error in parkVehicle: " + e.getMessage());
            return false;
        }
    }
    public static boolean clearAllData() {
    // Logic to execute DELETE FROM CurrentParkingTable;
    // Logic to execute DELETE FROM ParkingHistoryTable;
    // Return true if successful, false otherwise.
    return true; // Placeholder
}

    // Exit Vehicle → move to history + delete from current
    public static boolean exitVehicle(String vehicleNo, LocalTime exitTime, int fee) {
        String selectSQL = "SELECT * FROM current_vehicles WHERE vehicle_no = ?";
        String insertSQL = "INSERT INTO vehicle_history (vehicle_no, slot_no, vehicle_type, entry_time, exit_time, fee) VALUES (?, ?, ?, ?, ?, ?)";
        String deleteSQL = "DELETE FROM current_vehicles WHERE vehicle_no = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement selectPS = conn.prepareStatement(selectSQL);
             PreparedStatement insertPS = conn.prepareStatement(insertSQL);
             PreparedStatement deletePS = conn.prepareStatement(deleteSQL)) {

            // 1. Get data from current_vehicles
            selectPS.setString(1, vehicleNo);
            ResultSet rs = selectPS.executeQuery();

            if (rs.next()) {
                int slot = rs.getInt("slot_no");
                String type = rs.getString("vehicle_type");
                Time entryTime = rs.getTime("entry_time");

                // 2. Insert into vehicle_history
                insertPS.setString(1, vehicleNo);
                insertPS.setInt(2, slot);
                insertPS.setString(3, type);
                insertPS.setTime(4, entryTime);
                insertPS.setTime(5, Time.valueOf(exitTime));
                insertPS.setInt(6, fee);
                insertPS.executeUpdate();

                // 3. Delete from current_vehicles
                deletePS.setString(1, vehicleNo);
                deletePS.executeUpdate();

                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error in exitVehicle: " + e.getMessage());
        }
        return false;
    }
}
