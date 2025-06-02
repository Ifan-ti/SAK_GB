/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Owner_Laporan;
/**
 *
 * @author Lenovo
 */
import com.toedter.calendar.JDateChooser;
import java.sql.*;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;
import Koneksi.Koneksi;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import Aset.roundednew;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class Laporan_Laba extends roundednew {
    Connection conn = (Connection) Koneksi.koneksi();
    int i = 0;
    public Laporan_Laba() {
       
    
        initComponents();
        loadDataLaba();
        
        
    }
    
    //LOAD DATA
    private void loadDataLaba(){
        try {
           String sql = "SELECT * FROM view_lap_laba ";
           PreparedStatement pst = conn.prepareStatement(sql);
         
           ResultSet rs = pst.executeQuery();
           DefaultTableModel model = new DefaultTableModel(new String[]{"Tanggal","Laba Kotor", "Biaya Pengeluaran", "Laba Bersih"}, 0);
           jTable1.setModel(model);
           
           while (rs.next()) {
                Object[] row = {
                  rs.getString("Tanggal"),
                  rs.getString("Laba_Kotor"),
                  rs.getString("Biaya_Pengeluaran"),
                  rs.getString("Laba_Bersih")
                };
                model.addRow(row);
             }
        } catch (Exception e) {
        }
    }
    private void loadDataStok(){
        try {
        String sql = "SELECT * FROM view_laporan_stok_bahanbaku";
        PreparedStatement pst = conn.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Nama Stok", "Tanggal Masuk", "Tanggal Keluar", "Total Masuk","Total Keluar","Stok Tersedia","Harga Stok"}, 0);
        jTable1.setModel(model);


        while (rs.next()) {

            Object[] row = {
                rs.getString("Nama_Bahanbaku"),
                rs.getString("Tanggal_Masuk"),
                rs.getString("Tanggal_Keluar"),
                rs.getString("Total_Masuk"),
                rs.getString("Total_Keluar"),
                rs.getString("Stok_Tersedia"),
                rs.getString("Harga_Stok")
            };
            model.addRow(row);
        }
        } catch (Exception e) {
        }
    }
    
    private void loadDataPengeluaran(){
        try {
        String sql = "SELECT * FROM view_laporan_pengeluaran";
        PreparedStatement pst = conn.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Tanggal", "Nama", "Keterangan", "Jumlah","Biaya","Total"}, 0);
        jTable1.setModel(model);


        while (rs.next()) {

            Object[] row = {
                rs.getString("Tanggal"),
                rs.getString("Nama_Pengeluaran"),
                rs.getString("Keterangan"),
                rs.getString("Jumlah"),
                rs.getString("Biaya"),
                rs.getString("Total")
                
            };
            model.addRow(row);
        } 
        } catch (Exception e) {
        }
    }
    
    private void loadDataPresensi(){
        try {
        String sql = "SELECT * FROM view_laporan_presensi";
        PreparedStatement pst = conn.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Nama Karyawan","Petugas","Tanggal", "Waktu Masuk", "Waktu Pulang","Keterangan"}, 0);
        jTable1.setModel(model);


        while (rs.next()) {

            Object[] row = {
                rs.getString("Nama_Karyawan"),
                rs.getString("Role"),
                rs.getString("Tanggal"),
                rs.getString("Waktu_Masuk"),
                rs.getString("Waktu_Pulang"),
                rs.getString("Keterangan")
                
            };
            model.addRow(row);
        } 
        } catch (Exception e) {
        }
    }
    
    private void loadDataOpname(){
        try {
            String sql = "SELECT * FROM view_stokopname";
            PreparedStatement pst = conn.prepareStatement(sql);
            
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = new DefaultTableModel(new String[]{"Nama Bahan","Petugas","Tanggal","Harga Beli","Stok Sistem","Stok Fisik","Selisih","Keterangan"},0);
            jTable1.setModel(model);
            
            while (rs.next()){
                Object[] row ={
                    rs.getString("Nama_BahanBaku"),
                    rs.getString("Petugas"),
                    rs.getString("Tanggal"),
                    rs.getString("Harga_Beli_Terakhir"),
                    rs.getString("Stok_Sistem"),
                    rs.getString("Stok_Fisik"),
                    rs.getString("Selisih"),
                    rs.getString("Keterangan")
                };
                model.addRow(row);
            }
        } catch (Exception e) {
        }
    }
    
    //FUNGSI KONFIRMASI

    private void lapLaba(){
        java.util.Date Awal = tglAwal.getDate();
        java.util.Date Akhir = tglAkhir.getDate();

    if (Awal == null || Akhir == null) {
        JOptionPane.showMessageDialog(this, "Silakan pilih tanggal awal dan akhir.");
        return;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String tanggalAwal = sdf.format(Awal);
    String tanggalAkhir = sdf.format(Akhir);

    try {
        String sql = "SELECT * FROM view_lap_laba WHERE Tanggal BETWEEN ? AND ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, tanggalAwal);
        pst.setString(2, tanggalAkhir);

        ResultSet rs = pst.executeQuery();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Tanggal", "Laba Kotor", "Biaya Pengeluaran", "Laba Bersih"}, 0);
        jTable1.setModel(model);

        double totalLabaBersih = 0.0;

        while (rs.next()) {
            double labaBersih = rs.getDouble("Laba_Bersih");
            totalLabaBersih += labaBersih;

            Object[] row = {
                rs.getString("Tanggal"),
                rs.getString("Laba_Kotor"),
                rs.getString("Biaya_Pengeluaran"),
                rs.getString("Laba_Bersih")
            };
            model.addRow(row);
        }
        DecimalFormat df = new DecimalFormat("#,##0");
        txt_harga.setText("Rp " + df.format(totalLabaBersih));
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
    }
        
  }
    
    private void lapStok(){
        java.util.Date Awal = tglAwal.getDate();
        java.util.Date Akhir = tglAkhir.getDate();

    if (Awal == null || Akhir == null) {
        JOptionPane.showMessageDialog(this, "Silakan pilih tanggal awal dan akhir.");
        return;
    }


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String tanggalAwal = sdf.format(Awal);
    String tanggalAkhir = sdf.format(Akhir);
    try {
        String sql = "SELECT * FROM view_laporan_stok_bahanbaku WHERE (Tanggal_Masuk BETWEEN ? AND ?) OR (Tanggal_Keluar BETWEEN ? AND ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        
        pst.setString(1, tanggalAwal);
        pst.setString(2, tanggalAkhir);
        
        ResultSet rs = pst.executeQuery();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Tanggal Masuk", "Tanggal Keluar", "Nama Stok", "Total Masuk","Total Keluar","Stok Tersedia","Harga Stok"}, 0);
        jTable1.setModel(model);

        double totalHargaStok= 0.0;

        while (rs.next()) {
            double hargaStok = rs.getDouble("Harga_Stok");
            totalHargaStok += hargaStok;

            Object[] row = {
                rs.getString("Nama_Bahanbaku"),
                rs.getString("Tanggal_Masuk"),
                rs.getString("Tanggal_Keluar"),
                rs.getString("Total_Masuk"),
                rs.getString("Total_Keluar"),
                rs.getString("Stok_Tersedia"),
                rs.getString("Harga_Stok")
            };
            model.addRow(row);
        }
        DecimalFormat df = new DecimalFormat("#,##0");
        txt_harga.setText("Rp " + df.format(totalHargaStok));
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
    }
        
    }
    
    private void lapPl(){
        java.util.Date awal = tglAwal.getDate();
    java.util.Date akhir = tglAkhir.getDate();

    if (awal == null || akhir == null) {
        JOptionPane.showMessageDialog(this, "Silakan pilih tanggal awal dan tanggal akhir.");
        return;
    }

    // Format tanggal sesuai format di database, misal yyyy-MM-dd
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String tanggalAwal = sdf.format(awal);
    String tanggalAkhir = sdf.format(akhir);

    try {
        String sql = "SELECT * FROM view_laporan_pengeluaran WHERE Tanggal BETWEEN ? AND ?";
        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, tanggalAwal);
        pst.setString(2, tanggalAkhir);

        ResultSet rs = pst.executeQuery();

        // Buat model tabel baru dengan kolom sesuai data
        DefaultTableModel model = new DefaultTableModel(new String[]{
            "Tanggal", "Nama", "Keterangan", "Jumlah", "Biaya", "Total"
        }, 0);

        jTable1.setModel(model);

        double totalBiaya = 0.0;

        while (rs.next()) {
            Object[] row = {
                rs.getString("Tanggal"),
                rs.getString("Nama"),
                rs.getString("Keterangan"),
                rs.getString("Jumlah"),
                rs.getString("Biaya"),
                rs.getString("Total")
            };
            model.addRow(row);

            totalBiaya += rs.getDouble("Total");
        }

        // Format total biaya misal dengan pemisah ribuan
        DecimalFormat df = new DecimalFormat("#,###");
        txt_harga.setText("Rp " + df.format(totalBiaya));
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
    }
        
    }
    
    private void lapPre(){
        java.util.Date Awal = tglAwal.getDate();
        java.util.Date Akhir = tglAkhir.getDate();

    if (Awal == null || Akhir == null) {
        JOptionPane.showMessageDialog(this, "Silakan pilih tanggal awal dan akhir.");
        return;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String tanggalAwal = sdf.format(Awal);
    String tanggalAkhir = sdf.format(Akhir);
    
        try {
        String sql = "SELECT * FROM view_laporan_presensi WHERE Tanggal BETWEEN ? AND ?";
        
        PreparedStatement pst = conn.prepareStatement(sql);
         pst.setString(1, tanggalAwal);
         pst.setString(2, tanggalAkhir);
         
        ResultSet rs = pst.executeQuery();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Nama Karyawan","Petugas","Tanggal", "Waktu Masuk", "Waktu Pulang","Keterangan"}, 0);
        jTable1.setModel(model);


        while (rs.next()) {

            Object[] row = {
                rs.getString("Nama_Karyawan"),
                rs.getString("Role"),
                rs.getString("Tanggal"),
                rs.getString("Waktu_Masuk"),
                rs.getString("Waktu_Pulang"),
                rs.getString("Keterangan")
                
            };
            model.addRow(row);
        } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
        }

    }
    
    private void lapopname(){
         java.util.Date Awal = tglAwal.getDate();
        java.util.Date Akhir = tglAkhir.getDate();

    if (Awal == null || Akhir == null) {
        JOptionPane.showMessageDialog(this, "Silakan pilih tanggal awal dan akhir.");
        return;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String tanggalAwal = sdf.format(Awal);
    String tanggalAkhir = sdf.format(Akhir);

    try {
        String sql = "SELECT * FROM view_stokopname WHERE Tanggal BETWEEN ? AND ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, tanggalAwal);
        pst.setString(2, tanggalAkhir);

         ResultSet rs = pst.executeQuery();
            DefaultTableModel model = new DefaultTableModel(new String[]{"Nama Bahan","Petugas","Tanggal","Harga Beli","Stok Sistem","Stok Fisik","Selisih","Keterangan"},0);
            jTable1.setModel(model);
            
            while (rs.next()){
                Object[] row ={
                    rs.getString("Nama_BahanBaku"),
                    rs.getString("Petugas"),
                    rs.getString("Tanggal"),
                    rs.getString("Harga_Beli_Terakhir"),
                    rs.getString("Stok_Sistem"),
                    rs.getString("Stok_Fisik"),
                    rs.getString("Selisih"),
                    rs.getString("Keterangan")
                };
                model.addRow(row);
            }
   
        txt_harga.setText("");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
    }
    }
    
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundednew1 = new Aset.roundednew();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        roundednew2 = new Aset.roundednew();
        judul = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnkonfir = new Aset.button();
        txt_harga = new Aset.RoundedTextField();
        tglAkhir = new com.toedter.calendar.JDateChooser();
        tglAwal = new com.toedter.calendar.JDateChooser();
        roundednew3 = new Aset.roundednew();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btnprintlaporan = new Aset.button();
        btnlapstok = new Aset.button();
        btnlappengeluaran = new Aset.button();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnpresensi = new Aset.button();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        btnlapopname = new Aset.button();

        setBackground(new java.awt.Color(221, 221, 221));
        setColorend(new java.awt.Color(238, 238, 238));
        setColorstar(new java.awt.Color(238, 238, 238));
        setPreferredSize(new java.awt.Dimension(1180, 740));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        roundednew1.setColorend(new java.awt.Color(170, 215, 217));
        roundednew1.setColorstar(new java.awt.Color(170, 215, 217));
        roundednew1.setPreferredSize(new java.awt.Dimension(590, 740));
        roundednew1.setRoundedkananatas(92);
        roundednew1.setRoundedkananbawah(92);
        roundednew1.setRoundedkiriatas(92);
        roundednew1.setRoundedkiribawah(92);
        roundednew1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tanggal", "Pendapatan Laba Kotor", "Biaya Pengeluaran", "Pendapatan Laba Bersih"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        roundednew1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 860, 270));

        add(roundednew1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 990, 350));

        roundednew2.setColorend(new java.awt.Color(170, 215, 217));
        roundednew2.setColorstar(new java.awt.Color(170, 215, 217));
        roundednew2.setPreferredSize(new java.awt.Dimension(590, 740));
        roundednew2.setRoundedkananatas(92);
        roundednew2.setRoundedkananbawah(92);
        roundednew2.setRoundedkiriatas(92);
        roundednew2.setRoundedkiribawah(92);
        roundednew2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        judul.setFont(new java.awt.Font("Poppins", 1, 30)); // NOI18N
        judul.setForeground(new java.awt.Color(90, 142, 149));
        judul.setText("Filter Tanggal");
        roundednew2.add(judul, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, -1, 31));

        jLabel3.setFont(new java.awt.Font("Poppins", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(90, 142, 149));
        jLabel3.setText("Total Laba Bersih");
        roundednew2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, 31));

        jLabel8.setFont(new java.awt.Font("Poppins", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(90, 142, 149));
        jLabel8.setText("Tanggal akhir");
        roundednew2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, -1, 31));

        jLabel13.setFont(new java.awt.Font("Poppins", 0, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(90, 142, 149));
        jLabel13.setText("Tanggal awal");
        roundednew2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, 31));

        btnkonfir.setBackground(new java.awt.Color(229, 225, 218));
        btnkonfir.setForeground(new java.awt.Color(90, 142, 149));
        btnkonfir.setText("KONFIMASI");
        btnkonfir.setColor(new java.awt.Color(229, 225, 218));
        btnkonfir.setColorborder(new java.awt.Color(229, 225, 218));
        btnkonfir.setColorclic(new java.awt.Color(229, 225, 218));
        btnkonfir.setColorover(new java.awt.Color(229, 225, 218));
        btnkonfir.setFont(new java.awt.Font("Poppins", 1, 20)); // NOI18N
        btnkonfir.setRadius(15);
        btnkonfir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkonfirActionPerformed(evt);
            }
        });
        roundednew2.add(btnkonfir, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 360, 50));

        txt_harga.setBackground(new java.awt.Color(242, 242, 242));
        txt_harga.setBorder(null);
        txt_harga.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_harga.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        txt_harga.setRoundedkananatas(15);
        txt_harga.setRoundedkananbawah(15);
        txt_harga.setRoundedkiriatas(15);
        txt_harga.setRoundedkiribawah(15);
        roundednew2.add(txt_harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 360, 40));
        roundednew2.add(tglAkhir, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, 170, 40));
        roundednew2.add(tglAwal, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 170, 40));

        add(roundednew2, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, 440, 350));

        roundednew3.setColorend(new java.awt.Color(170, 215, 217));
        roundednew3.setColorstar(new java.awt.Color(170, 215, 217));
        roundednew3.setPreferredSize(new java.awt.Dimension(590, 740));
        roundednew3.setRoundedkananatas(92);
        roundednew3.setRoundedkananbawah(92);
        roundednew3.setRoundedkiriatas(92);
        roundednew3.setRoundedkiribawah(92);
        roundednew3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(90, 142, 149));
        jLabel7.setText("Laporan");
        roundednew3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, -1, 31));

        jLabel5.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(90, 142, 149));
        jLabel5.setText("Pengeluaran");
        roundednew3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 170, -1, 31));

        jLabel4.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(90, 142, 149));
        jLabel4.setText("Print");
        roundednew3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, -1, 31));

        jLabel6.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(90, 142, 149));
        jLabel6.setText("Stok");
        roundednew3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, -1, 31));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Box (1).png"))); // NOI18N
        roundednew3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, -1, -1));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/File.png"))); // NOI18N
        roundednew3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, -1, -1));

        jLabel10.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(90, 142, 149));
        jLabel10.setText("Laporan");
        roundednew3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, -1, 31));

        jLabel11.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(90, 142, 149));
        jLabel11.setText("Laporan");
        roundednew3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, -1, 31));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Card Down.png"))); // NOI18N
        roundednew3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 150, -1, -1));

        btnprintlaporan.setRadius(25);
        btnprintlaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintlaporanActionPerformed(evt);
            }
        });
        roundednew3.add(btnprintlaporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 130, 220));

        btnlapstok.setRadius(25);
        btnlapstok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlapstokActionPerformed(evt);
            }
        });
        roundednew3.add(btnlapstok, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 220, 80));

        btnlappengeluaran.setRadius(25);
        btnlappengeluaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlappengeluaranActionPerformed(evt);
            }
        });
        roundednew3.add(btnlappengeluaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, 220, 80));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Card Down.png"))); // NOI18N
        roundednew3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 240, -1, -1));

        jLabel15.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(90, 142, 149));
        jLabel15.setText("Laporan");
        roundednew3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 230, -1, 31));

        jLabel16.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(90, 142, 149));
        jLabel16.setText("Presensi");
        roundednew3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 260, -1, 31));

        btnpresensi.setRadius(25);
        btnpresensi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpresensiActionPerformed(evt);
            }
        });
        roundednew3.add(btnpresensi, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 220, 80));

        jLabel18.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(90, 142, 149));
        jLabel18.setText("Laporan");
        roundednew3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, -1, 31));

        jLabel19.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(90, 142, 149));
        jLabel19.setText("Opname");
        roundednew3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 280, -1, 31));

        btnlapopname.setRadius(25);
        btnlapopname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlapopnameActionPerformed(evt);
            }
        });
        roundednew3.add(btnlapopname, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 130, 80));

        add(roundednew3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 470, 350));
    }// </editor-fold>//GEN-END:initComponents

    private void btnkonfirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkonfirActionPerformed
        switch (i) {
            case 0:
                lapLaba();
                break;
            case 1:
                lapStok();
                break;
            case 2:
                lapPl();
                break;
            case 3:
                lapPre();
                break;
            case 4 :
                 lapopname();
                break;
                
        }
    }//GEN-LAST:event_btnkonfirActionPerformed

    private void btnprintlaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintlaporanActionPerformed
        // TODO add your handling code here:
        switch (i) {
            case 0:
                 try {
                 String reportPath = "src/ServiceReport/laporanlaba.jasper";
                 HashMap<String, Object> parameters = new HashMap<>();
                 
                 java.util.Date tanggalAwal = tglAwal.getDate(); // Contoh input awal
                 java.util.Date tanggalAkhir = tglAkhir.getDate(); // Contoh input akhir

                 // Kirim parameter ke laporan
                 parameters.put("tanggal_awal", tanggalAwal);
                 parameters.put("tanggal_akhir", tanggalAkhir);
                 
                 JasperPrint print = JasperFillManager.fillReport(reportPath, parameters, conn);
                 JasperViewer viewer = new JasperViewer(print,false);
                 viewer.setVisible(true);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
           
            break;
            
            case 1 :
                 try {
                 String reportPath = "src/ServiceReport/laporanstok.jasper";
                 HashMap<String, Object> parameters = new HashMap<>();
                 
                 java.util.Date tanggalAwal = tglAwal.getDate(); // Contoh input awal
                 java.util.Date tanggalAkhir = tglAkhir.getDate(); // Contoh input akhir

                 // Kirim parameter ke laporan
                 parameters.put("tanggal_awal", tanggalAwal);
                 parameters.put("tanggal_akhir", tanggalAkhir);
                 
                 JasperPrint print = JasperFillManager.fillReport(reportPath, parameters, conn);
                 JasperViewer viewer = new JasperViewer(print,false);
                 viewer.setVisible(true);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
            break;
            
            case 2:
                try {
                 String reportPath = "src/ServiceReport/laporanpengeluaran.jasper";
                 HashMap<String, Object> parameters = new HashMap<>();
                 
                 java.util.Date tanggalAwal = tglAwal.getDate(); // Contoh input awal
                 java.util.Date tanggalAkhir = tglAkhir.getDate(); // Contoh input akhir

                 // Kirim parameter ke laporan
                 parameters.put("tanggal_awal", tanggalAwal);
                 parameters.put("tanggal_akhir", tanggalAkhir);
                 
                 JasperPrint print = JasperFillManager.fillReport(reportPath, parameters, conn);
                 JasperViewer viewer = new JasperViewer(print,false);
                 viewer.setVisible(true);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
                break;
            case 3:
                 try {
                 String reportPath = "src/ServiceReport/laporanpresensi.jasper";
                 HashMap<String, Object> parameters = new HashMap<>();
                 
                 java.util.Date tanggalAwal = tglAwal.getDate(); // Contoh input awal
                 java.util.Date tanggalAkhir = tglAkhir.getDate(); // Contoh input akhir

                 // Kirim parameter ke laporan
                 parameters.put("tanggal_awal", tanggalAwal);
                 parameters.put("tanggal_akhir", tanggalAkhir);
                 
                 JasperPrint print = JasperFillManager.fillReport(reportPath, parameters, conn);
                 JasperViewer viewer = new JasperViewer(print,false);
                 viewer.setVisible(true);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
                break;
            case 4:
                 try {
                 String reportPath = "src/ServiceReport/laporanopname.jasper";
                 HashMap<String, Object> parameters = new HashMap<>();
                 
                 java.util.Date tanggalAwal = tglAwal.getDate(); // Contoh input awal
                 java.util.Date tanggalAkhir = tglAkhir.getDate(); // Contoh input akhir

                 // Kirim parameter ke laporan
                 parameters.put("tanggal_awal", tanggalAwal);
                 parameters.put("tanggal_akhir", tanggalAkhir);
                 
                 JasperPrint print = JasperFillManager.fillReport(reportPath, parameters, conn);
                 JasperViewer viewer = new JasperViewer(print,false);
                 viewer.setVisible(true);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
                break;
        }
//        try {
//           String reportPath = "src/ServiceReport/laporanlaba.jasper";
//           HashMap<String, Object> parameters = new HashMap<>();
//            JasperPrint print = JasperFillManager.fillReport(reportPath, parameters, conn);
//            JasperViewer viewer = new JasperViewer(print,false);
//            viewer.setVisible(true);
//        } catch (Exception e) {
//             e.printStackTrace();
//        }
    }//GEN-LAST:event_btnprintlaporanActionPerformed

    private void btnlapstokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlapstokActionPerformed
        int b;
        
        if (i == 1) {
            i = 0;
        }else{
            i = 1;
        }
        switch (i) {
            case 0:
               
               loadDataLaba();
                break;
            case 1:
             
                jLabel3.setText("Total Harga Stok");
                loadDataStok();
                break;
            case 2 :
                
                jLabel3.setText("Total Biaya Pengeluaran");
                loadDataPengeluaran();
                break;
            case 3 : 
            
                jLabel13.setVisible(false);
                txt_harga.setVisible(false);
                loadDataPresensi();
                break;
            case 4 :
               
                break;
        }
    }//GEN-LAST:event_btnlapstokActionPerformed

    private void btnlappengeluaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlappengeluaranActionPerformed
        int b;
        
        if (i == 1) {
            i = 0;
        }else{
            i = 1;
        }
        switch (i) {
            case 0:
            
                loadDataLaba();
                break;
            case 1:
                
                jLabel3.setText("Total Biaya Pengeluaran");
               loadDataPengeluaran();
                break;
            case 2 :
                
                jLabel3.setText("Total Harga Stok");
                loadDataStok();
                break;
            case 3 : 
                
                jLabel3.setVisible(false);
                txt_harga.setVisible(false);
                loadDataPresensi();
                break;
            case 4 :
                
                break;
            case 5:
                break;
        }
    }//GEN-LAST:event_btnlappengeluaranActionPerformed

    private void btnpresensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpresensiActionPerformed
        // TODO add your handling code here:
       int b;
        
        if (i == 1) {
            i = 0;
        }else{
            i = 1;
        }
         switch (i) {
            case 0:
                loadDataLaba();
                break;
            case 1:
                jLabel3.setVisible(false);
                txt_harga.setVisible(false);
                loadDataPresensi();
                break;
            case 2:
                jLabel3.setText("Total Harga Stok");
                loadDataStok();
                break;
            case 3:
                jLabel3.setText("Total Biaya Pengeluaran");
                loadDataPengeluaran();
                break;
            case 4:
                
                break;
            case 5:
                break;
                
         }    
    }//GEN-LAST:event_btnpresensiActionPerformed

    private void btnlapopnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlapopnameActionPerformed
        // TODO add your handling code here:
        
         int b;
        
        if (i == 1) {
            i = 0;
        }else{
            i = 1;
        }
         switch (i) {
            case 0:
                loadDataLaba();
                break;
            case 1:
               loadDataOpname();
                break;
         }
    }//GEN-LAST:event_btnlapopnameActionPerformed

         

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Aset.button btnkonfir;
    private Aset.button btnlapopname;
    private Aset.button btnlappengeluaran;
    private Aset.button btnlapstok;
    private Aset.button btnpresensi;
    private Aset.button btnprintlaporan;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel judul;
    private Aset.roundednew roundednew1;
    private Aset.roundednew roundednew2;
    private Aset.roundednew roundednew3;
    private com.toedter.calendar.JDateChooser tglAkhir;
    private com.toedter.calendar.JDateChooser tglAwal;
    private Aset.RoundedTextField txt_harga;
    // End of variables declaration//GEN-END:variables
}
