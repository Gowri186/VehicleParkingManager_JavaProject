/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.db;
import java.sql.*;
/**
 *
 * @author ADMIN
 */
public class DBConnection {
  
    private static final String URL = "jdbc:mysql://localhost:3306/parkingdb";
    private static final String USER = "root";   // change if your MySQL user is different
    private static final String PASSWORD = "";   // put your MySQL password

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    
}
