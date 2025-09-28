/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.test;

import parking.database.DBConnection;

/**
 *
 * @author ADMIN
 */
public class TESTDB {
   
    public static void main(String[] args) {
        try (java.sql.Connection conn =  DBConnection.getConnection()) {
            if (conn != null) {
                System.out.println("✅ Database connected successfully!");
            } else {
                System.out.println("❌ Failed to connect.");
            }
        } catch (Exception e) {
        }
    }
}
    

