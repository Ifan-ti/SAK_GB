/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Kasir_status;

import Kasir_Produk.*;
import Aset.roundednew;
import Koneksi.Koneksi;
import com.mysql.cj.jdbc.PreparedStatementWrapper;
import java.awt.Color;
import java.awt.print.PrinterException;
import raven.glasspanepopup.GlassPanePopup;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.lang.String;
import java.util.Collections;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author achmad ifan
 */
public class Menu_status extends roundednew {

    

    /**
     * Creates new form Menu
     */
    public Menu_status() {
        initComponents();
        setOpaque(false);
        pn_detailPesanan.setVisible(false);
        ambilIdPelanggan();
        viewdata(0);
        txt_namaRoti.setEditable(false);
        jc_status.setEditable(false);
        txt_tanggalSelesai.setEditable(false);
        txt_jumlahPesanan.setEditable(false);
    }
    
    
    Connection conn = (Connection) Koneksi.koneksi();
    ArrayList<String> idPelanggan = new ArrayList<>();
    ArrayList<JLabel> Username = new ArrayList<>();
    ArrayList<JLabel> status = new ArrayList<>();
    ArrayList<JLabel> txtId = new ArrayList<>();
    ArrayList<JPanel> Displey = new ArrayList<>();
    
    String idDetail =  "";
    String idPesanan =  "";
    
    int tambah = 0;
    int a = 0;

    
    // mendapatkan data jam, menit, detik
    DateTimeFormatter format_ft = DateTimeFormatter.ofPattern("HH:mm:ss ");
    String fulltime = LocalDateTime.now().format(format_ft);
    
    // deklarasi bulan, hari, dan tahun
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter format_tanggal = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String tanggal = now.format(format_tanggal); 
    
    LocalDate tanggalSekarang=  now.toLocalDate(); // Mengambil bagian tanggal dari LocalDateTime
    LocalDate tanggalBesok = tanggalSekarang.plusDays(1);
    String tanggalSelesai = tanggalBesok.format(format_tanggal);
    
    //mendapat data bulan
    DateTimeFormatter foemat_bulan = DateTimeFormatter.ofPattern("MM");
    String bulan = now.format(foemat_bulan); // Mengubah LocalDateTime menjadi string dengan format yyyy-MM-dd
    
    //mendapat tangall
    DateTimeFormatter format_hari = DateTimeFormatter.ofPattern("dd");
    String hari = now.format(format_hari); // Mengubah LocalDateTime menjadi string dengan format yyyy-MM-dd
   
    
    
    
    
    private void fungsiedit(boolean penentuan){
        jc_status.setEnabled(penentuan);
        txt_tanggalSelesai.setEditable(penentuan);
        txt_jumlahPesanan.setEditable(penentuan);
        
    }
    private void ambilIdPelanggan(){
        String sql ="SELECT DISTINCT Id_Pelanggan FROM `detail_pesanan`";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
            idPelanggan.add(rs.getString("Id_Pelanggan"));
   
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Menu_status.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void ambilIdPesanan(){
        String sql ="SELECT DISTINCT pesanan.Id_Pesanan FROM pesanan, pelanggan, detail_pesanan, produk\n" +
"                    WHERE detail_pesanan.Id_Pelanggan = pelanggan.Id_Pelanggan AND\n" +
"                    detail_pesanan.Id_Pesanan = pesanan.Id_Pesanan \n" +
"                    AND pesanan.Id_Produk = produk.Id_Produk AND\n" +
"                    pelanggan.Id_Pelanggan = ? AND produk.Nama_produk = ? ";;
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, idDetail);
            st.setString(2, (String) jc_produk.getSelectedItem());
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                idPesanan = rs.getString("Id_Pesanan");
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Menu_status.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private void viewdata(int tambah){
        Collections.addAll(Username, lb_pelanggan1, lb_pelanggan2, lb_pelanggan3, lb_pelanggan4);
        Collections.addAll(txtId, txt_id1, txt_id2, txt_id3, txt_id4);
        Collections.addAll(status, lb_status1, lb_status2, lb_status3, lb_status4);
        Collections.addAll(Displey, displey_1, displey_2, displey_3, displey_4);
        String sql ="SELECT DISTINCT pelanggan.Id_Pelanggan, pelanggan.Nama_Pelanggan, pesanan.Status FROM pesanan, pelanggan, detail_pesanan\n" +
                    "WHERE detail_pesanan.Id_Pelanggan = pelanggan.Id_Pelanggan AND\n" +
                    "detail_pesanan.Id_Pesanan = pesanan.Id_Pesanan AND pelanggan.Id_Pelanggan = ?";
        
            for (int i = 0; i < 4; i++) {
                Displey.get(i).setVisible(false);
                
            
            }
        int perulangan = 0+tambah;
        if(perulangan >=  0 ){
            
            
   
            int j = 0;
        for (int i = perulangan; i <= 3+perulangan; i++) {
            
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, idPelanggan.get(i));
            String ip = idPelanggan.get(i);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
               Username.get(j).setText(rs.getString("Nama_Pelanggan"));
               status.get(j).setText(rs.getString("Status"));
               txtId.get(j).setText(rs.getString("Id_Pelanggan"));
               
                              

                Displey.get(j).setVisible(true);
            }
            
           
            
            if(j<=3){
                ++j;
            }else{
                j=0;
            }
         
        } catch (Exception e) {
        }
}
        }
        
    }  
   private void search(){
       String sql ="SELECT DISTINCT pelanggan.Id_Pelanggan, pelanggan.Nama_Pelanggan, pesanan.Status FROM pesanan, pelanggan, detail_pesanan\n" +
                    "WHERE detail_pesanan.Id_Pelanggan = pelanggan.Id_Pelanggan AND\n" +
                    "detail_pesanan.Id_Pesanan = pesanan.Id_Pesanan AND pelanggan.Nama_Pelanggan = ?";
       
                    for (int i = 0; i < 4; i++) {
                Displey.get(i).setVisible(false);
            }
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, txt_search.getText());
            ResultSet rs = st.executeQuery();
            for (int i = 0; i < 3; i++) {
                
            if(rs.next()){
                Username.get(i).setText(rs.getString("Nama_Pelanggan"));
               status.get(i).setText(rs.getString("Status"));
               txtId.get(i).setText(rs.getString("Id_Pelanggan"));

               Displey.get(i).setVisible(true);
            }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Menu_status.class.getName()).log(Level.SEVERE, null, ex);
        }
   }  
   private void datadiri(int urutan){
       String sql = "SELECT DISTINCT pelanggan.Nama_Pelanggan, pesanan.Tanggal_Pesanan, pelanggan.Alamat FROM pesanan, pelanggan, detail_pesanan\n" +
                    "WHERE detail_pesanan.Id_Pelanggan = pelanggan.Id_Pelanggan AND\n" +
                    "detail_pesanan.Id_Pesanan = pesanan.Id_Pesanan AND pelanggan.Id_Pelanggan = ?";
       try {
           PreparedStatement st = conn.prepareStatement(sql);
           st.setString(1, txtId.get(urutan).getText());
           
           ResultSet rs = st.executeQuery();
           
           if(rs.next()){
               txt_nama.setText(rs.getString("Nama_Pelanggan"));
               txt_tanggal.setText(rs.getString("Tanggal_Pesanan"));
               txt_alamat.setText(rs.getString("Alamat"));
           }
       } catch (Exception e) {
       }
       
   }
   private void daftarProduk(int urutan){
       try {
            
            
            String sql = "SELECT DISTINCT produk.Nama_produk FROM pesanan, pelanggan, detail_pesanan, produk\n" +
            "WHERE detail_pesanan.Id_Pelanggan = pelanggan.Id_Pelanggan AND\n" +
            "pesanan.Id_Produk = produk.Id_Produk AND\n" +
            "detail_pesanan.Id_Pesanan = pesanan.Id_Pesanan AND pelanggan.Id_Pelanggan = ?"; // Ganti dengan query Anda
            jc_produk.removeAllItems();
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, txtId.get(urutan).getText());
           
            ResultSet rs = st.executeQuery();

            // 4. Mengiterasi hasil query dan menambahkannya ke JComboBox
            while (rs.next()) {
                String namaKolom = rs.getString("Nama_produk"); // Ganti nama_kolom
                jc_produk.addItem(namaKolom);
                idDetail = txtId.get(urutan).getText();
            }
          

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal mengambil data dari database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
       
   }   
   private void detailPesanan(){
       pn_detailPesanan.setVisible(true);
       pn_view.setVisible(false);
       String sql = "SELECT DISTINCT produk.Nama_produk, pesanan.Status, pesanan.Jumlah_Pesanan, pesanan.Tanggal_Selesai FROM pesanan, pelanggan, detail_pesanan, produk\n" +
                    "WHERE detail_pesanan.Id_Pelanggan = pelanggan.Id_Pelanggan AND\n" +
                    "pesanan.Id_Produk = produk.Id_Produk AND\n" +
                    "detail_pesanan.Id_Pesanan = pesanan.Id_Pesanan AND produk.Nama_produk = ? AND pelanggan.Id_Pelanggan = ?"; // Ganti dengan query Anda
           
        try{
            
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, (String) jc_produk.getSelectedItem());
            st.setString(2, idDetail);
           
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                txt_namaRoti.setText(rs.getString("Nama_produk"));
                jc_status.setSelectedItem(rs.getString("Status"));
                txt_jumlahPesanan.setText(rs.getString("Jumlah_Pesanan"));
                txt_tanggalSelesai.setText(rs.getString("Tanggal_Selesai"));
            }
          

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal mengambil data dari database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
       
   }
   private void getIdPesanan(String id){
       pn_detailPesanan.setVisible(true);
       String sql = "SELECT DISTINCT pesanan.Id_Pesanan FROM pesanan, pelanggan, detail_pesanan, produk\n" +
                    "WHERE detail_pesanan.Id_Pelanggan = pelanggan.Id_Pelanggan AND\n" +
                    "pesanan.Id_Produk = produk.Id_Produk AND\n" +
                    "detail_pesanan.Id_Pesanan = pesanan.Id_Pesanan AND produk.Nama_produk = ? AND pelanggan.Id_Pelanggan = ?"; // Ganti dengan query Anda
           
        try{
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, (String) jc_produk.getSelectedItem());
            st.setString(2, id);
           
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                idPesanan = rs.getString("Id_Pesanan");
            }
          

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal mengambil data dari database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
       
   }
   
     private void Update () {
        try {
            String query = "UPDATE pesanan SET Jumlah_Pesanan = ? ,Tanggal_Selesai = ? ,Status = ?  WHERE Id_Pesanan= ? ";
            PreparedStatement pst = conn.prepareStatement(query);
             pst.setString(1, txt_jumlahPesanan.getText());
             pst.setString(2, txt_tanggalSelesai.getText());
             pst.setString(3, (String) jc_status.getSelectedItem());
             pst.setString(4, idPesanan);
             
             int row = pst.executeUpdate();
             
             if (row>0){
                 JOptionPane.showMessageDialog(null, "Produk Berhasil Di Update", "Update", 1);
             }else{
                 JOptionPane.showMessageDialog(null, "Produk Gagal Di Update", "Update", 0);

             }
             
        detailPesanan();
            
            
            
            
         
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting data: " + e.getMessage());
        }
       
     }
   
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_detailPesanan = new Aset.roundednew();
        txt_judul = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_tanggalSelesai = new Aset.RoundedTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_namaRoti = new Aset.RoundedTextField();
        txt_jumlahPesanan = new Aset.RoundedTextField();
        btn_kembali = new Aset.button();
        btn_edit = new Aset.button();
        jc_status = new Aset.Jcomboboxcustom();
        pn_view = new Aset.roundednew();
        button1 = new Aset.button();
        addpesanan = new Aset.button();
        txt_search = new Aset.RoundedTextField();
        addpesanan1 = new Aset.button();
        displey_2 = new Aset.roundednew();
        lb_status2 = new javax.swing.JLabel();
        lb_pelanggan2 = new javax.swing.JLabel();
        btn_detail2 = new Aset.button();
        jLabel2 = new javax.swing.JLabel();
        txt_id2 = new javax.swing.JLabel();
        displey_1 = new Aset.roundednew();
        jLabel1 = new javax.swing.JLabel();
        lb_status1 = new javax.swing.JLabel();
        lb_pelanggan1 = new javax.swing.JLabel();
        btn_detail1 = new Aset.button();
        txt_id1 = new javax.swing.JLabel();
        displey_3 = new Aset.roundednew();
        lb_status3 = new javax.swing.JLabel();
        lb_pelanggan3 = new javax.swing.JLabel();
        btn_detail3 = new Aset.button();
        jLabel3 = new javax.swing.JLabel();
        txt_id3 = new javax.swing.JLabel();
        displey_4 = new Aset.roundednew();
        lb_status4 = new javax.swing.JLabel();
        lb_pelanggan4 = new javax.swing.JLabel();
        btn_detail4 = new Aset.button();
        jLabel4 = new javax.swing.JLabel();
        txt_id4 = new javax.swing.JLabel();
        roundednew4 = new Aset.roundednew();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        judul = new javax.swing.JLabel();
        txt_nama = new Aset.RoundedTextField();
        txt_tanggal = new Aset.RoundedTextField();
        txt_alamat = new Aset.RoundedTextField();
        roundednew5 = new Aset.roundednew();
        judul1 = new javax.swing.JLabel();
        jc_produk = new Aset.Jcomboboxcustom();
        addpesanan2 = new Aset.button();
        jLabel7 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(242, 242, 242));
        setColorend(new java.awt.Color(242, 242, 242));
        setColorstar(new java.awt.Color(242, 242, 242));
        setRoundedkananatas(94);
        setRoundedkananbawah(94);
        setRoundedkiriatas(94);
        setRoundedkiribawah(94);
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pn_detailPesanan.setColorend(new java.awt.Color(170, 215, 217));
        pn_detailPesanan.setColorstar(new java.awt.Color(170, 215, 217));
        pn_detailPesanan.setRoundedkananatas(50);
        pn_detailPesanan.setRoundedkananbawah(50);
        pn_detailPesanan.setRoundedkiriatas(50);
        pn_detailPesanan.setRoundedkiribawah(50);
        pn_detailPesanan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_judul.setFont(new java.awt.Font("Poppins", 1, 30)); // NOI18N
        txt_judul.setForeground(new java.awt.Color(90, 142, 149));
        txt_judul.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txt_judul.setText("Detail Pesanan");
        pn_detailPesanan.add(txt_judul, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 400, 31));

        jLabel9.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(90, 142, 149));
        jLabel9.setText("Nama Roti");
        pn_detailPesanan.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, 31));

        txt_tanggalSelesai.setBackground(new java.awt.Color(251, 249, 241));
        txt_tanggalSelesai.setBorder(null);
        txt_tanggalSelesai.setForeground(new java.awt.Color(153, 153, 153));
        txt_tanggalSelesai.setCaretColor(new java.awt.Color(242, 242, 242));
        txt_tanggalSelesai.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        txt_tanggalSelesai.setRoundedkananatas(15);
        txt_tanggalSelesai.setRoundedkananbawah(15);
        txt_tanggalSelesai.setRoundedkiriatas(15);
        txt_tanggalSelesai.setRoundedkiribawah(15);
        pn_detailPesanan.add(txt_tanggalSelesai, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 240, 350, 50));

        jLabel10.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(90, 142, 149));
        jLabel10.setText("Tanggal Selsai");
        pn_detailPesanan.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 200, -1, 31));

        jLabel11.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(90, 142, 149));
        jLabel11.setText("Status");
        pn_detailPesanan.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 80, -1, 31));

        jLabel12.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(90, 142, 149));
        jLabel12.setText("Jumlah Pesanan");
        pn_detailPesanan.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, 31));

        txt_namaRoti.setBackground(new java.awt.Color(251, 249, 241));
        txt_namaRoti.setBorder(null);
        txt_namaRoti.setForeground(new java.awt.Color(153, 153, 153));
        txt_namaRoti.setCaretColor(new java.awt.Color(242, 242, 242));
        txt_namaRoti.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        txt_namaRoti.setRoundedkananatas(15);
        txt_namaRoti.setRoundedkananbawah(15);
        txt_namaRoti.setRoundedkiriatas(15);
        txt_namaRoti.setRoundedkiribawah(15);
        pn_detailPesanan.add(txt_namaRoti, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 350, 50));

        txt_jumlahPesanan.setBackground(new java.awt.Color(251, 249, 241));
        txt_jumlahPesanan.setBorder(null);
        txt_jumlahPesanan.setForeground(new java.awt.Color(153, 153, 153));
        txt_jumlahPesanan.setCaretColor(new java.awt.Color(242, 242, 242));
        txt_jumlahPesanan.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        txt_jumlahPesanan.setRoundedkananatas(15);
        txt_jumlahPesanan.setRoundedkananbawah(15);
        txt_jumlahPesanan.setRoundedkiriatas(15);
        txt_jumlahPesanan.setRoundedkiribawah(15);
        pn_detailPesanan.add(txt_jumlahPesanan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 350, 50));

        btn_kembali.setBackground(new java.awt.Color(229, 225, 218));
        btn_kembali.setForeground(new java.awt.Color(90, 142, 149));
        btn_kembali.setText("KEMBALI");
        btn_kembali.setColor(new java.awt.Color(229, 225, 218));
        btn_kembali.setColorborder(new java.awt.Color(229, 225, 218));
        btn_kembali.setColorclic(new java.awt.Color(229, 225, 218));
        btn_kembali.setColorover(new java.awt.Color(229, 225, 218));
        btn_kembali.setFont(new java.awt.Font("Poppins", 1, 25)); // NOI18N
        btn_kembali.setRadius(15);
        btn_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kembaliActionPerformed(evt);
            }
        });
        pn_detailPesanan.add(btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 240, 320, 50));

        btn_edit.setBackground(new java.awt.Color(229, 225, 218));
        btn_edit.setForeground(new java.awt.Color(90, 142, 149));
        btn_edit.setText("EDIT");
        btn_edit.setColor(new java.awt.Color(229, 225, 218));
        btn_edit.setColorborder(new java.awt.Color(229, 225, 218));
        btn_edit.setColorclic(new java.awt.Color(229, 225, 218));
        btn_edit.setColorover(new java.awt.Color(229, 225, 218));
        btn_edit.setFont(new java.awt.Font("Poppins", 1, 25)); // NOI18N
        btn_edit.setRadius(15);
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });
        pn_detailPesanan.add(btn_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 120, 320, 50));

        jc_status.setBackground(new java.awt.Color(251, 249, 241));
        jc_status.setForeground(new java.awt.Color(153, 153, 153));
        jc_status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "proses", "selesai" }));
        jc_status.setToolTipText("");
        jc_status.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        pn_detailPesanan.add(jc_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 120, 350, 50));

        add(pn_detailPesanan, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 1140, 350));

        pn_view.setColorend(new java.awt.Color(170, 215, 217));
        pn_view.setColorstar(new java.awt.Color(170, 215, 217));
        pn_view.setRoundedkananatas(50);
        pn_view.setRoundedkananbawah(50);
        pn_view.setRoundedkiriatas(50);
        pn_view.setRoundedkiribawah(50);
        pn_view.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        button1.setBackground(new java.awt.Color(251, 249, 241));
        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Search.png"))); // NOI18N
        button1.setColorborder(new java.awt.Color(251, 249, 241));
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        pn_view.add(button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 30, 50, 50));

        addpesanan.setBackground(new java.awt.Color(229, 225, 218));
        addpesanan.setForeground(new java.awt.Color(90, 142, 149));
        addpesanan.setText("KEMBALI");
        addpesanan.setColor(new java.awt.Color(229, 225, 218));
        addpesanan.setColorborder(new java.awt.Color(229, 225, 218));
        addpesanan.setColorclic(new java.awt.Color(229, 225, 218));
        addpesanan.setColorover(new java.awt.Color(229, 225, 218));
        addpesanan.setFont(new java.awt.Font("Poppins", 1, 25)); // NOI18N
        addpesanan.setRadius(15);
        addpesanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addpesananActionPerformed(evt);
            }
        });
        pn_view.add(addpesanan, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 30, 200, 50));

        txt_search.setBackground(new java.awt.Color(251, 249, 241));
        txt_search.setBorder(null);
        txt_search.setForeground(new java.awt.Color(153, 153, 153));
        txt_search.setCaretColor(new java.awt.Color(242, 242, 242));
        txt_search.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        txt_search.setRoundedkananatas(15);
        txt_search.setRoundedkananbawah(15);
        txt_search.setRoundedkiriatas(15);
        txt_search.setRoundedkiribawah(15);
        pn_view.add(txt_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 600, 50));

        addpesanan1.setBackground(new java.awt.Color(229, 225, 218));
        addpesanan1.setForeground(new java.awt.Color(90, 142, 149));
        addpesanan1.setText("LANJUT");
        addpesanan1.setColor(new java.awt.Color(229, 225, 218));
        addpesanan1.setColorborder(new java.awt.Color(229, 225, 218));
        addpesanan1.setColorclic(new java.awt.Color(229, 225, 218));
        addpesanan1.setColorover(new java.awt.Color(229, 225, 218));
        addpesanan1.setFont(new java.awt.Font("Poppins", 1, 25)); // NOI18N
        addpesanan1.setRadius(15);
        addpesanan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addpesanan1ActionPerformed(evt);
            }
        });
        pn_view.add(addpesanan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 30, 200, 50));

        displey_2.setColorend(new java.awt.Color(251, 249, 241));
        displey_2.setColorstar(new java.awt.Color(251, 249, 241));
        displey_2.setRoundedkananatas(30);
        displey_2.setRoundedkananbawah(30);
        displey_2.setRoundedkiriatas(30);
        displey_2.setRoundedkiribawah(30);
        displey_2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lb_status2.setFont(new java.awt.Font("Poppins", 1, 17)); // NOI18N
        lb_status2.setForeground(new java.awt.Color(90, 142, 149));
        lb_status2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_status2.setText("Status : Proses");
        displey_2.add(lb_status2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 245, -1));

        lb_pelanggan2.setFont(new java.awt.Font("Poppins", 1, 17)); // NOI18N
        lb_pelanggan2.setForeground(new java.awt.Color(90, 142, 149));
        lb_pelanggan2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_pelanggan2.setText("Pelangggan");
        displey_2.add(lb_pelanggan2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 115, 245, -1));

        btn_detail2.setForeground(new java.awt.Color(251, 249, 241));
        btn_detail2.setText("Detail");
        btn_detail2.setColor(new java.awt.Color(170, 215, 217));
        btn_detail2.setColorborder(new java.awt.Color(170, 215, 217));
        btn_detail2.setColorclic(new java.awt.Color(170, 215, 217));
        btn_detail2.setColorover(new java.awt.Color(170, 215, 217));
        btn_detail2.setFont(new java.awt.Font("Poppins", 1, 15)); // NOI18N
        btn_detail2.setIconTextGap(15);
        btn_detail2.setRadius(10);
        btn_detail2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_detail2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_detail2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_detail2MouseExited(evt);
            }
        });
        btn_detail2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_detail2ActionPerformed(evt);
            }
        });
        displey_2.add(btn_detail2, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 170, 100, 30));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/icon_status_profil.png"))); // NOI18N
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        displey_2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 20, 88, 100));

        txt_id2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txt_id2.setForeground(new java.awt.Color(251, 249, 241));
        txt_id2.setText("jLabel13");
        displey_2.add(txt_id2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 40));

        pn_view.add(displey_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(305, 110, 245, 210));

        displey_1.setColorend(new java.awt.Color(251, 249, 241));
        displey_1.setColorstar(new java.awt.Color(251, 249, 241));
        displey_1.setRoundedkananatas(30);
        displey_1.setRoundedkananbawah(30);
        displey_1.setRoundedkiriatas(30);
        displey_1.setRoundedkiribawah(30);
        displey_1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/icon_status_profil.png"))); // NOI18N
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        displey_1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 20, 88, 100));

        lb_status1.setFont(new java.awt.Font("Poppins", 1, 17)); // NOI18N
        lb_status1.setForeground(new java.awt.Color(90, 142, 149));
        lb_status1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_status1.setText("Status : Proses");
        displey_1.add(lb_status1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 245, -1));

        lb_pelanggan1.setFont(new java.awt.Font("Poppins", 1, 17)); // NOI18N
        lb_pelanggan1.setForeground(new java.awt.Color(90, 142, 149));
        lb_pelanggan1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_pelanggan1.setText("Pelangggan");
        displey_1.add(lb_pelanggan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 115, 245, -1));

        btn_detail1.setForeground(new java.awt.Color(251, 249, 241));
        btn_detail1.setText("Detail");
        btn_detail1.setColor(new java.awt.Color(170, 215, 217));
        btn_detail1.setColorborder(new java.awt.Color(170, 215, 217));
        btn_detail1.setColorclic(new java.awt.Color(170, 215, 217));
        btn_detail1.setColorover(new java.awt.Color(170, 215, 217));
        btn_detail1.setFont(new java.awt.Font("Poppins", 1, 15)); // NOI18N
        btn_detail1.setIconTextGap(15);
        btn_detail1.setRadius(10);
        btn_detail1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_detail1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_detail1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_detail1MouseExited(evt);
            }
        });
        btn_detail1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_detail1ActionPerformed(evt);
            }
        });
        displey_1.add(btn_detail1, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 170, 100, 30));

        txt_id1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txt_id1.setForeground(new java.awt.Color(251, 249, 241));
        txt_id1.setText("jLabel13");
        displey_1.add(txt_id1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 40));

        pn_view.add(displey_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 245, 210));

        displey_3.setColorend(new java.awt.Color(251, 249, 241));
        displey_3.setColorstar(new java.awt.Color(251, 249, 241));
        displey_3.setRoundedkananatas(30);
        displey_3.setRoundedkananbawah(30);
        displey_3.setRoundedkiriatas(30);
        displey_3.setRoundedkiribawah(30);
        displey_3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lb_status3.setFont(new java.awt.Font("Poppins", 1, 17)); // NOI18N
        lb_status3.setForeground(new java.awt.Color(90, 142, 149));
        lb_status3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_status3.setText("Status : Proses");
        displey_3.add(lb_status3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 245, -1));

        lb_pelanggan3.setFont(new java.awt.Font("Poppins", 1, 17)); // NOI18N
        lb_pelanggan3.setForeground(new java.awt.Color(90, 142, 149));
        lb_pelanggan3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_pelanggan3.setText("Pelangggan");
        displey_3.add(lb_pelanggan3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 115, 245, -1));

        btn_detail3.setForeground(new java.awt.Color(251, 249, 241));
        btn_detail3.setText("Detail");
        btn_detail3.setColor(new java.awt.Color(170, 215, 217));
        btn_detail3.setColorborder(new java.awt.Color(170, 215, 217));
        btn_detail3.setColorclic(new java.awt.Color(170, 215, 217));
        btn_detail3.setColorover(new java.awt.Color(170, 215, 217));
        btn_detail3.setFont(new java.awt.Font("Poppins", 1, 15)); // NOI18N
        btn_detail3.setIconTextGap(15);
        btn_detail3.setRadius(10);
        btn_detail3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_detail3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_detail3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_detail3MouseExited(evt);
            }
        });
        btn_detail3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_detail3ActionPerformed(evt);
            }
        });
        displey_3.add(btn_detail3, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 170, 100, 30));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/icon_status_profil.png"))); // NOI18N
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        displey_3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 20, 88, 100));

        txt_id3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txt_id3.setForeground(new java.awt.Color(251, 249, 241));
        txt_id3.setText("jLabel13");
        displey_3.add(txt_id3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 40));

        pn_view.add(displey_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 110, 245, 210));

        displey_4.setColorend(new java.awt.Color(251, 249, 241));
        displey_4.setColorstar(new java.awt.Color(251, 249, 241));
        displey_4.setRoundedkananatas(30);
        displey_4.setRoundedkananbawah(30);
        displey_4.setRoundedkiriatas(30);
        displey_4.setRoundedkiribawah(30);
        displey_4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lb_status4.setFont(new java.awt.Font("Poppins", 1, 17)); // NOI18N
        lb_status4.setForeground(new java.awt.Color(90, 142, 149));
        lb_status4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_status4.setText("Status : Proses");
        displey_4.add(lb_status4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 245, -1));

        lb_pelanggan4.setFont(new java.awt.Font("Poppins", 1, 17)); // NOI18N
        lb_pelanggan4.setForeground(new java.awt.Color(90, 142, 149));
        lb_pelanggan4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_pelanggan4.setText("Pelangggan");
        displey_4.add(lb_pelanggan4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 115, 245, -1));

        btn_detail4.setForeground(new java.awt.Color(251, 249, 241));
        btn_detail4.setText("Detail");
        btn_detail4.setColor(new java.awt.Color(170, 215, 217));
        btn_detail4.setColorborder(new java.awt.Color(170, 215, 217));
        btn_detail4.setColorclic(new java.awt.Color(170, 215, 217));
        btn_detail4.setColorover(new java.awt.Color(170, 215, 217));
        btn_detail4.setFont(new java.awt.Font("Poppins", 1, 15)); // NOI18N
        btn_detail4.setIconTextGap(15);
        btn_detail4.setRadius(10);
        btn_detail4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_detail4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_detail4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_detail4MouseExited(evt);
            }
        });
        btn_detail4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_detail4ActionPerformed(evt);
            }
        });
        displey_4.add(btn_detail4, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 170, 100, 30));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/icon_status_profil.png"))); // NOI18N
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        displey_4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 20, 88, 100));

        txt_id4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txt_id4.setForeground(new java.awt.Color(251, 249, 241));
        txt_id4.setText("jLabel13");
        displey_4.add(txt_id4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 40));

        pn_view.add(displey_4, new org.netbeans.lib.awtextra.AbsoluteConstraints(865, 110, 245, 210));

        add(pn_view, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 1140, 350));

        roundednew4.setColorend(new java.awt.Color(170, 215, 217));
        roundednew4.setColorstar(new java.awt.Color(170, 215, 217));
        roundednew4.setRoundedkananatas(50);
        roundednew4.setRoundedkananbawah(50);
        roundednew4.setRoundedkiriatas(50);
        roundednew4.setRoundedkiribawah(50);
        roundednew4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(90, 142, 149));
        jLabel8.setText("Tanggal");
        roundednew4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, -1, 31));

        jLabel5.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(90, 142, 149));
        jLabel5.setText("Alamat");
        roundednew4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, 31));

        jLabel6.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(90, 142, 149));
        jLabel6.setText("Nama Pelanggan");
        roundednew4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, 31));

        judul.setFont(new java.awt.Font("Poppins", 1, 30)); // NOI18N
        judul.setForeground(new java.awt.Color(90, 142, 149));
        judul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        judul.setText("Data Diri Pelanggan");
        roundednew4.add(judul, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 490, 31));

        txt_nama.setBackground(new java.awt.Color(251, 249, 241));
        txt_nama.setBorder(null);
        txt_nama.setForeground(new java.awt.Color(153, 153, 153));
        txt_nama.setCaretColor(new java.awt.Color(242, 242, 242));
        txt_nama.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        txt_nama.setRoundedkananatas(15);
        txt_nama.setRoundedkananbawah(15);
        txt_nama.setRoundedkiriatas(15);
        txt_nama.setRoundedkiribawah(15);
        roundednew4.add(txt_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 280, 50));

        txt_tanggal.setBackground(new java.awt.Color(251, 249, 241));
        txt_tanggal.setBorder(null);
        txt_tanggal.setForeground(new java.awt.Color(153, 153, 153));
        txt_tanggal.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_tanggal.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        txt_tanggal.setRoundedkananatas(15);
        txt_tanggal.setRoundedkananbawah(15);
        txt_tanggal.setRoundedkiriatas(15);
        txt_tanggal.setRoundedkiribawah(15);
        roundednew4.add(txt_tanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 120, 180, 50));

        txt_alamat.setBackground(new java.awt.Color(251, 249, 241));
        txt_alamat.setBorder(null);
        txt_alamat.setForeground(new java.awt.Color(153, 153, 153));
        txt_alamat.setCaretColor(new java.awt.Color(242, 242, 242));
        txt_alamat.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        txt_alamat.setRoundedkananatas(15);
        txt_alamat.setRoundedkananbawah(15);
        txt_alamat.setRoundedkiriatas(15);
        txt_alamat.setRoundedkiribawah(15);
        roundednew4.add(txt_alamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 490, 50));

        add(roundednew4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 415, 550, 330));

        roundednew5.setColorend(new java.awt.Color(170, 215, 217));
        roundednew5.setColorstar(new java.awt.Color(170, 215, 217));
        roundednew5.setRoundedkananatas(50);
        roundednew5.setRoundedkananbawah(50);
        roundednew5.setRoundedkiriatas(50);
        roundednew5.setRoundedkiribawah(50);
        roundednew5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        judul1.setFont(new java.awt.Font("Poppins", 1, 30)); // NOI18N
        judul1.setForeground(new java.awt.Color(90, 142, 149));
        judul1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        judul1.setText("Daftar Pesanan");
        roundednew5.add(judul1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 490, 30));

        jc_produk.setBackground(new java.awt.Color(251, 249, 241));
        jc_produk.setForeground(new java.awt.Color(153, 153, 153));
        jc_produk.setToolTipText("");
        jc_produk.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        roundednew5.add(jc_produk, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 500, 50));

        addpesanan2.setBackground(new java.awt.Color(229, 225, 218));
        addpesanan2.setForeground(new java.awt.Color(90, 142, 149));
        addpesanan2.setText("KONFIMASI");
        addpesanan2.setColor(new java.awt.Color(229, 225, 218));
        addpesanan2.setColorborder(new java.awt.Color(229, 225, 218));
        addpesanan2.setColorclic(new java.awt.Color(229, 225, 218));
        addpesanan2.setColorover(new java.awt.Color(229, 225, 218));
        addpesanan2.setFont(new java.awt.Font("Poppins", 1, 25)); // NOI18N
        addpesanan2.setRadius(15);
        addpesanan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addpesanan2ActionPerformed(evt);
            }
        });
        roundednew5.add(addpesanan2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 500, 50));

        jLabel7.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(90, 142, 149));
        jLabel7.setText("Nama Roti");
        roundednew5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, 31));

        add(roundednew5, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 415, 560, 330));
    }// </editor-fold>//GEN-END:initComponents

    private void addpesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addpesananActionPerformed
        tambah -=4;
        if(tambah<0){
            tambah = 0;
        }        
        viewdata(tambah);
    }//GEN-LAST:event_addpesananActionPerformed
    
    private void addpesanan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addpesanan1ActionPerformed
        int tambahnew = tambah;
        tambah +=4;
        if(tambah>idPelanggan.toArray().length){
            tambah =tambahnew;
        }
        viewdata(tambah);
    }//GEN-LAST:event_addpesanan1ActionPerformed

    private void btn_detail1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_detail1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_detail1MouseClicked

    private void btn_detail1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_detail1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_detail1MouseEntered

    private void btn_detail1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_detail1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_detail1MouseExited

    private void btn_detail1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_detail1ActionPerformed
        datadiri(0);
        daftarProduk(0);
    }//GEN-LAST:event_btn_detail1ActionPerformed

    private void btn_detail2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_detail2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_detail2MouseClicked

    private void btn_detail2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_detail2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_detail2MouseEntered

    private void btn_detail2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_detail2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_detail2MouseExited

    private void btn_detail2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_detail2ActionPerformed
        datadiri(1);
        daftarProduk(1);

    }//GEN-LAST:event_btn_detail2ActionPerformed

    private void btn_detail3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_detail3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_detail3MouseClicked

    private void btn_detail3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_detail3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_detail3MouseEntered

    private void btn_detail3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_detail3MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_detail3MouseExited

    private void btn_detail3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_detail3ActionPerformed
               datadiri(2);
               daftarProduk(2);
               
    }//GEN-LAST:event_btn_detail3ActionPerformed

    private void btn_detail4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_detail4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_detail4MouseClicked

    private void btn_detail4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_detail4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_detail4MouseEntered

    private void btn_detail4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_detail4MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_detail4MouseExited

    private void btn_detail4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_detail4ActionPerformed
                datadiri(3);
                daftarProduk(3);

    }//GEN-LAST:event_btn_detail4ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        search();
    }//GEN-LAST:event_button1ActionPerformed

    private void addpesanan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addpesanan2ActionPerformed
        detailPesanan();
        fungsiedit(false);
        ambilIdPesanan();

    }//GEN-LAST:event_addpesanan2ActionPerformed

    private void btn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kembaliActionPerformed
        
         if(a==1){
            fungsiedit(false);
            a=0;
            btn_edit.setText("EDIT");
            btn_kembali.setText("KEMBALI");
            txt_judul.setText(" Detail Pesanan");
        }else if (a==0){
            fungsiedit(false);
            a=1;
            pn_detailPesanan.setVisible(false);
            pn_view.setVisible(true);
        }
        
        
    }//GEN-LAST:event_btn_kembaliActionPerformed
    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        
        if(a==0){
            fungsiedit(true);
            a=1;
            btn_edit.setText("SELESAI");
            btn_kembali.setText("BATAL");
            txt_judul.setText("Edit Detail Pesanan");
            
        }else if (a==1){
            fungsiedit(false);
            a=0;
            btn_edit.setText("EDIT");
            btn_kembali.setText("KEMBALI");
            txt_judul.setText(" Detail Pesanan");

//            Update();
        }
        
    }//GEN-LAST:event_btn_editActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Aset.button addpesanan;
    private Aset.button addpesanan1;
    private Aset.button addpesanan2;
    private Aset.button btn_detail1;
    private Aset.button btn_detail2;
    private Aset.button btn_detail3;
    private Aset.button btn_detail4;
    private Aset.button btn_edit;
    private Aset.button btn_kembali;
    private Aset.button button1;
    private Aset.roundednew displey_1;
    private Aset.roundednew displey_2;
    private Aset.roundednew displey_3;
    private Aset.roundednew displey_4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private Aset.Jcomboboxcustom jc_produk;
    private Aset.Jcomboboxcustom jc_status;
    private javax.swing.JLabel judul;
    private javax.swing.JLabel judul1;
    private javax.swing.JLabel lb_pelanggan1;
    private javax.swing.JLabel lb_pelanggan2;
    private javax.swing.JLabel lb_pelanggan3;
    private javax.swing.JLabel lb_pelanggan4;
    private javax.swing.JLabel lb_status1;
    private javax.swing.JLabel lb_status2;
    private javax.swing.JLabel lb_status3;
    private javax.swing.JLabel lb_status4;
    private Aset.roundednew pn_detailPesanan;
    private Aset.roundednew pn_view;
    private Aset.roundednew roundednew4;
    private Aset.roundednew roundednew5;
    private Aset.RoundedTextField txt_alamat;
    private javax.swing.JLabel txt_id1;
    private javax.swing.JLabel txt_id2;
    private javax.swing.JLabel txt_id3;
    private javax.swing.JLabel txt_id4;
    private javax.swing.JLabel txt_judul;
    private Aset.RoundedTextField txt_jumlahPesanan;
    private Aset.RoundedTextField txt_nama;
    private Aset.RoundedTextField txt_namaRoti;
    private Aset.RoundedTextField txt_search;
    private Aset.RoundedTextField txt_tanggal;
    private Aset.RoundedTextField txt_tanggalSelesai;
    // End of variables declaration//GEN-END:variables
}
