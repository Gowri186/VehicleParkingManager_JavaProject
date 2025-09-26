/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.db;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ParkingDAO {
    
    // Insert vehicle into current_parking
    public static boolean parkVehicle(String vNo, int slot, String type) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO current_parking(vehicle_no, slot_no, vehicle_type) VALUES(?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, vNo);
            pst.setInt(2, slot);
            pst.setString(3, type);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
        }
        return false;
    }

    // Move vehicle to history table
    public static boolean exitVehicle(String vNo, double payment) {
        try (Connection con = DBConnection.getConnection()) {
            // 1. Get current vehicle
            String q1 = "SELECT * FROM current_parking WHERE vehicle_no=?";
            PreparedStatement pst1 = con.prepareStatement(q1);
            pst1.setString(1, vNo);
            ResultSet rs = pst1.executeQuery();

            if (rs.next()) {
                int slot = rs.getInt("slot_no");
                String type = rs.getString("vehicle_type");
                Timestamp entry = rs.getTimestamp("entry_time");

                // 2. Insert into history
                String q2 = "INSERT INTO parking_history(vehicle_no, slot_no, vehicle_type, entry_time, exit_time, payment) VALUES(?,?,?,?,NOW(),?)";
                PreparedStatement pst2 = con.prepareStatement(q2);
                pst2.setString(1, vNo);
                pst2.setInt(2, slot);
                pst2.setString(3, type);
                pst2.setTimestamp(4, entry);
                pst2.setDouble(5, payment);
                pst2.executeUpdate();

                // 3. Remove from current
                String q3 = "DELETE FROM current_parking WHERE vehicle_no=?";
                PreparedStatement pst3 = con.prepareStatement(q3);
                pst3.setString(1, vNo);
                pst3.executeUpdate();

                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    // Load current table
    public static DefaultTableModel loadCurrentTable() {
        String[] cols = {"Vehicle No", "Slot No", "Type", "Entry Time"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM current_parking")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("vehicle_no"),
                    rs.getInt("slot_no"),
                    rs.getString("vehicle_type"),
                    rs.getTimestamp("entry_time")
                });
            }
        } catch (Exception e) {}
        return model;
    }

    // Load history table
    public static DefaultTableModel loadHistoryTable() {
        String[] cols = {"ID", "Vehicle No", "Slot No", "Type", "Entry Time", "Exit Time", "Payment"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM parking_history")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("vehicle_no"),
                    rs.getInt("slot_no"),
                    rs.getString("vehicle_type"),
                    rs.getTimestamp("entry_time"),
                    rs.getTimestamp("exit_time"),
                    rs.getDouble("payment")
                });
            }
        } catch (Exception e) {}
        return model;
    }
}

    
    

