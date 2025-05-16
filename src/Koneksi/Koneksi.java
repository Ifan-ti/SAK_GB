/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author achmad ifan
 */
public class Koneksi {
    public static Connection koneksi(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kasir_v2","root","");
            return conn;
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
    public static void main(String[] args) {
        // Uji koneksi database
        Connection conn = koneksi();
        if (conn != null) {
            JOptionPane.showMessageDialog(null, "Koneksi berhasil!");
        } else {
            JOptionPane.showMessageDialog(null, "Koneksi gagal!");
        }
    }
}
