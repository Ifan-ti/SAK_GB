/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Kasir.DAO;

import Koneksi.Koneksi;
import ServiceReport.CetakNota;
import java.awt.BorderLayout;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import java.sql.*;

/**
 *
 * @author achmad ifan
 */
public class Nota implements CetakNota{

    Connection conn = (Connection) Koneksi.koneksi();

    @Override
    public void cetakNota(JPanel pn, String Barcode) {
        try {
        String reportPath = "src/Kasir_Produk/Nota.jasper";
        HashMap<String, Object> parameters = new HashMap<>();
        if (Barcode != null && !Barcode.trim().isEmpty()) {
            parameters.put("id", Barcode);
        }
        JasperPrint print = JasperFillManager.fillReport(reportPath, parameters, conn);
        // tampil panel
        pn.setLayout(new BorderLayout());
        pn.repaint();
        pn.add(new JRViewer(print));
        pn.revalidate();
    } catch (Exception e) {
        JOptionPane.showConfirmDialog(null, e.getMessage());
    }
    }
    
}
