/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Kasir_Produk;
import Aset.roundednew;
import Aset.button;
import Koneksi.Koneksi;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author ThinkPad
 */
public class Checkout extends roundednew {
       
       
    /**
     * Creates new form status
     */
    public Checkout() {
        initComponents();
        Loaddata();
        tampilPesan();
        uangMasuk();
        status();
    }
    public void Loaddata(){
        try {
            String id = id_plg.getText();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kasir_v2","root", "");
            PreparedStatement stmt = conn.prepareStatement("SELECT Nama_Pelanggan, Alamat FROM pelanggan WHERE id_Pelanggan = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nm_plg.setText(rs.getString("Nama_Pelanggan"));
                alamat.setText(rs.getString("alamat"));
            } else {
                nm_plg.setText("");
                alamat.setText("");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan koneksi database.");
        }
    }
    public void tampilPesan(){
      try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kasir_v2", "root", "");
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT \n" +
            "    ps.Jumlah_Pesanan, \n" +
            "    pr.Nama_Produk, \n" +
            "    pr.Harga, \n" +
            "    ps.Total_Harga, \n" +
            "    py.Uang_Masuk \n" +
            "FROM \n" +
            "    detail_pesanan AS dp \n" +
            "JOIN \n" +
            "    pesanan AS ps ON dp.Id_Pesanan = ps.Id_Pesanan \n" +
            "JOIN \n" +
            "    pelanggan AS p ON dp.Id_Pelanggan = p.ID_Pelanggan \n" +
            "JOIN \n" +
            "    produk AS pr ON pr.Id_Produk = ps.Id_Produk \n" +
            "JOIN \n" +
            "    pembayaran AS py ON dp.Id_Pembayaran = py.Id_Pembayaran \n" +
            "WHERE p.ID_Pelanggan = ? \n" +
            "LIMIT 3"
       
        );
        String id = id_plg.getText();
        stmt.setString(1, id);        

        ResultSet rs = stmt.executeQuery();

        int index = 1;
        while (rs.next()) {
            int jumlah = rs.getInt("Jumlah_Pesanan");
            String namaProduk = rs.getString("Nama_Produk");
            double total = rs.getDouble("Total_Harga");
            String labelTombol = jumlah + " " + namaProduk;
            if (index == 1) {
                pesanan1.setText(labelTombol);
                hrg1.setText(String.valueOf(total));
//                 uang_msk.setText(rs.getString("Uang_Masuk"));
            } else if (index == 2) {
                pesanan2.setText(labelTombol);
                hrg2.setText(String.valueOf(total));
            } else if (index == 3) {
                pesanan3.setText(labelTombol);
                hrg3.setText(String.valueOf(total));
            }
            
            index++;
        }

        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Terjadi kesalahan koneksi database.");
    }
    }
     public void uangMasuk() {
    try {
        String nm = nm_plg.getText(); // Ambil nama pelanggan dari input
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kasir_v2", "root", "");
        
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT SUM(py.Uang_Masuk) AS Total_Uang_Masuk " +
            "FROM detail_pesanan dp " +
            "JOIN pelanggan p ON dp.Id_Pelanggan = p.ID_Pelanggan " +
            "JOIN pembayaran py ON dp.Id_Pembayaran = py.Id_Pembayaran " +
            "WHERE p.Nama_Pelanggan = ?"
        );
        stmt.setString(1, nm);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String totalUang = rs.getString("Total_Uang_Masuk");
            uang_msk.setText(totalUang != null ? totalUang : "");
        } else {
            uang_msk.setText("0");
        }

        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Terjadi kesalahan koneksi database.");
    }
}
     public void status() {
            try {
        String nm = nm_plg.getText();// Ambil Nama Pelanggan
        
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kasir_v2", "root", "");

        // Query untuk ambil data pesanan berdasarkan nama pelanggan
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT psn.Id_Pesanan, psn.Status " +
            "FROM kasir_v2.pelanggan AS pl " +
            "INNER JOIN kasir_v2.detail_pesanan AS dpsn ON pl.Id_Pelanggan = dpsn.Id_Pelanggan " +
            "INNER JOIN kasir_v2.pesanan AS psn ON dpsn.Id_Pesanan = psn.Id_Pesanan " +
            "WHERE pl.Nama_Pelanggan = ? " +
            "ORDER BY pl.Id_Pelanggan DESC LIMIT 1"
        );
        
        stmt.setString(1, nm);
        

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String idPesanan = rs.getString("Id_Pesanan");
            String status = rs.getString("Status");
            if("selesai".equalsIgnoreCase(status)) {
                JOptionPane.showMessageDialog(null, "Status sudah diselesaikan!");
            }
            else {

            // UPDATE status jadi "selesai"
            PreparedStatement updateStmt = conn.prepareStatement(
                "UPDATE pesanan SET Status = 'selesai' WHERE Id_Pesanan = ?"
            );
            updateStmt.setString(1, idPesanan);

            int rowsUpdated = updateStmt.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Pesanan " +nm + " telah diselesaikan.");
            } else {
                JOptionPane.showMessageDialog(null, "Gagal memperbarui status pesanan.");
            }

            updateStmt.close();
        } 
        }
        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Terjadi kesalahan koneksi database.");
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundednew1 = new Aset.roundednew();
        button2 = new Aset.button();
        id_plg = new Aset.RoundedTextField();
        judul14 = new javax.swing.JLabel();
        roundednew2 = new Aset.roundednew();
        judul = new javax.swing.JLabel();
        judul1 = new javax.swing.JLabel();
        judul2 = new javax.swing.JLabel();
        judul3 = new javax.swing.JLabel();
        alamat = new Aset.RoundedTextField();
        roundednew3 = new Aset.roundednew();
        tgl_psn = new Aset.RoundedTextField();
        nm_plg = new Aset.RoundedTextField();
        roundednew4 = new Aset.roundednew();
        button3 = new Aset.button();
        judul8 = new javax.swing.JLabel();
        judul9 = new javax.swing.JLabel();
        judul10 = new javax.swing.JLabel();
        judul11 = new javax.swing.JLabel();
        pesanan3 = new Aset.RoundedTextField();
        pesanan1 = new Aset.RoundedTextField();
        pesanan2 = new Aset.RoundedTextField();
        judul12 = new javax.swing.JLabel();
        hrg3 = new Aset.RoundedTextField();
        hrg1 = new Aset.RoundedTextField();
        hrg2 = new Aset.RoundedTextField();
        judul13 = new javax.swing.JLabel();
        uang_msk = new Aset.RoundedTextField();

        setColorend(new java.awt.Color(242, 242, 242));
        setColorstar(new java.awt.Color(242, 242, 242));
        setRoundedkananatas(50);
        setRoundedkananbawah(50);
        setRoundedkiriatas(50);
        setRoundedkiribawah(50);

        roundednew1.setBackground(new java.awt.Color(251, 249, 241));
        roundednew1.setForeground(new java.awt.Color(251, 249, 241));
        roundednew1.setColorend(new java.awt.Color(170, 215, 217));
        roundednew1.setColorstar(new java.awt.Color(170, 215, 217));
        roundednew1.setPreferredSize(new java.awt.Dimension(520, 340));
        roundednew1.setRoundedkananatas(50);
        roundednew1.setRoundedkananbawah(50);
        roundednew1.setRoundedkiriatas(50);
        roundednew1.setRoundedkiribawah(50);

        button2.setBackground(new java.awt.Color(229, 225, 218));
        button2.setForeground(new java.awt.Color(90, 142, 149));
        button2.setText("SCAN");
        button2.setColor(new java.awt.Color(229, 225, 218));
        button2.setColorborder(new java.awt.Color(229, 225, 218));
        button2.setColorclic(new java.awt.Color(229, 225, 218));
        button2.setColorover(new java.awt.Color(229, 225, 218));
        button2.setRadius(15);
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        id_plg.setBackground(new java.awt.Color(251, 249, 241));
        id_plg.setBorder(null);
        id_plg.setForeground(new java.awt.Color(153, 153, 153));
        id_plg.setCaretColor(new java.awt.Color(255, 255, 255));
        id_plg.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        id_plg.setRoundedkananatas(15);
        id_plg.setRoundedkananbawah(15);
        id_plg.setRoundedkiriatas(15);
        id_plg.setRoundedkiribawah(15);
        id_plg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_plgActionPerformed(evt);
            }
        });

        judul14.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        judul14.setForeground(new java.awt.Color(255, 255, 255));
        judul14.setText("Scan Id Pelanggan");

        javax.swing.GroupLayout roundednew1Layout = new javax.swing.GroupLayout(roundednew1);
        roundednew1.setLayout(roundednew1Layout);
        roundednew1Layout.setHorizontalGroup(
            roundednew1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundednew1Layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addGroup(roundednew1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundednew1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(judul14, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(id_plg, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(145, Short.MAX_VALUE))
        );
        roundednew1Layout.setVerticalGroup(
            roundednew1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundednew1Layout.createSequentialGroup()
                .addContainerGap(165, Short.MAX_VALUE)
                .addComponent(judul14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(id_plg, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        roundednew2.setBackground(new java.awt.Color(251, 249, 241));
        roundednew2.setForeground(new java.awt.Color(251, 249, 241));
        roundednew2.setColorend(new java.awt.Color(170, 215, 217));
        roundednew2.setColorstar(new java.awt.Color(170, 215, 217));
        roundednew2.setPreferredSize(new java.awt.Dimension(520, 340));
        roundednew2.setRoundedkananatas(50);
        roundednew2.setRoundedkananbawah(50);
        roundednew2.setRoundedkiriatas(50);
        roundednew2.setRoundedkiribawah(50);

        judul.setBackground(new java.awt.Color(0, 0, 0));
        judul.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        judul.setForeground(new java.awt.Color(255, 255, 255));
        judul.setText("Alamat Pelanggan");

        judul1.setFont(new java.awt.Font("Poppins", 1, 30)); // NOI18N
        judul1.setForeground(new java.awt.Color(255, 255, 255));
        judul1.setText("Data Diri Pelanggan");

        judul2.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        judul2.setForeground(new java.awt.Color(255, 255, 255));
        judul2.setText("Tanggal Ambil");

        judul3.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        judul3.setForeground(new java.awt.Color(255, 255, 255));
        judul3.setText("Nama Pelanggan");

        alamat.setBackground(new java.awt.Color(251, 249, 241));
        alamat.setBorder(null);
        alamat.setForeground(new java.awt.Color(153, 153, 153));
        alamat.setCaretColor(new java.awt.Color(255, 255, 255));
        alamat.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        alamat.setRoundedkananatas(15);
        alamat.setRoundedkananbawah(15);
        alamat.setRoundedkiriatas(15);
        alamat.setRoundedkiribawah(15);
        alamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alamatActionPerformed(evt);
            }
        });

        roundednew3.setBackground(new java.awt.Color(251, 249, 241));
        roundednew3.setForeground(new java.awt.Color(251, 249, 241));
        roundednew3.setColorend(new java.awt.Color(170, 215, 217));
        roundednew3.setColorstar(new java.awt.Color(170, 215, 217));
        roundednew3.setPreferredSize(new java.awt.Dimension(520, 340));
        roundednew3.setRoundedkananatas(50);
        roundednew3.setRoundedkananbawah(50);
        roundednew3.setRoundedkiriatas(50);
        roundednew3.setRoundedkiribawah(50);

        tgl_psn.setBackground(new java.awt.Color(251, 249, 241));
        tgl_psn.setBorder(null);
        tgl_psn.setForeground(new java.awt.Color(153, 153, 153));
        tgl_psn.setCaretColor(new java.awt.Color(255, 255, 255));
        tgl_psn.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        tgl_psn.setRoundedkananatas(15);
        tgl_psn.setRoundedkananbawah(15);
        tgl_psn.setRoundedkiriatas(15);
        tgl_psn.setRoundedkiribawah(15);
        tgl_psn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tgl_psnActionPerformed(evt);
            }
        });

        nm_plg.setBackground(new java.awt.Color(251, 249, 241));
        nm_plg.setBorder(null);
        nm_plg.setForeground(new java.awt.Color(153, 153, 153));
        nm_plg.setCaretColor(new java.awt.Color(255, 255, 255));
        nm_plg.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        nm_plg.setRoundedkananatas(15);
        nm_plg.setRoundedkananbawah(15);
        nm_plg.setRoundedkiriatas(15);
        nm_plg.setRoundedkiribawah(15);
        nm_plg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nm_plgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundednew3Layout = new javax.swing.GroupLayout(roundednew3);
        roundednew3.setLayout(roundednew3Layout);
        roundednew3Layout.setHorizontalGroup(
            roundednew3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundednew3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(nm_plg, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(tgl_psn, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        roundednew3Layout.setVerticalGroup(
            roundednew3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundednew3Layout.createSequentialGroup()
                .addContainerGap(156, Short.MAX_VALUE)
                .addGroup(roundednew3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tgl_psn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nm_plg, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(155, 155, 155))
        );

        javax.swing.GroupLayout roundednew2Layout = new javax.swing.GroupLayout(roundednew2);
        roundednew2.setLayout(roundednew2Layout);
        roundednew2Layout.setHorizontalGroup(
            roundednew2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundednew2Layout.createSequentialGroup()
                .addGroup(roundednew2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundednew2Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(judul1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundednew2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(roundednew2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(judul, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundednew2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(judul3, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(judul2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
            .addGroup(roundednew2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(roundednew2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(roundednew3, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        roundednew2Layout.setVerticalGroup(
            roundednew2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundednew2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(judul1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(roundednew2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(judul3)
                    .addComponent(judul2))
                .addGap(69, 69, 69)
                .addComponent(judul)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
            .addGroup(roundednew2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(roundednew2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(roundednew3, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        roundednew4.setBackground(new java.awt.Color(251, 249, 241));
        roundednew4.setForeground(new java.awt.Color(251, 249, 241));
        roundednew4.setColorend(new java.awt.Color(170, 215, 217));
        roundednew4.setColorstar(new java.awt.Color(170, 215, 217));
        roundednew4.setPreferredSize(new java.awt.Dimension(520, 340));
        roundednew4.setRoundedkananatas(50);
        roundednew4.setRoundedkananbawah(50);
        roundednew4.setRoundedkiriatas(50);
        roundednew4.setRoundedkiribawah(50);

        button3.setBackground(new java.awt.Color(229, 225, 218));
        button3.setForeground(new java.awt.Color(90, 142, 149));
        button3.setText("AMBIL PESANAN");
        button3.setColor(new java.awt.Color(229, 225, 218));
        button3.setColorborder(new java.awt.Color(229, 225, 218));
        button3.setColorclic(new java.awt.Color(229, 225, 218));
        button3.setColorover(new java.awt.Color(229, 225, 218));
        button3.setRadius(15);
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });

        judul8.setFont(new java.awt.Font("Poppins", 1, 30)); // NOI18N
        judul8.setForeground(new java.awt.Color(255, 255, 255));
        judul8.setText("DAFTAR PESANAN");

        judul9.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        judul9.setForeground(new java.awt.Color(255, 255, 255));
        judul9.setText("Pesanan Kedua");

        judul10.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        judul10.setForeground(new java.awt.Color(255, 255, 255));
        judul10.setText("Pesanan Pertama");

        judul11.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        judul11.setForeground(new java.awt.Color(255, 255, 255));
        judul11.setText("Pesanan Ketiga");

        pesanan3.setBackground(new java.awt.Color(251, 249, 241));
        pesanan3.setBorder(null);
        pesanan3.setForeground(new java.awt.Color(153, 153, 153));
        pesanan3.setCaretColor(new java.awt.Color(255, 255, 255));
        pesanan3.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        pesanan3.setRoundedkananatas(15);
        pesanan3.setRoundedkananbawah(15);
        pesanan3.setRoundedkiriatas(15);
        pesanan3.setRoundedkiribawah(15);
        pesanan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesanan3ActionPerformed(evt);
            }
        });

        pesanan1.setBackground(new java.awt.Color(251, 249, 241));
        pesanan1.setBorder(null);
        pesanan1.setForeground(new java.awt.Color(153, 153, 153));
        pesanan1.setCaretColor(new java.awt.Color(255, 255, 255));
        pesanan1.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        pesanan1.setRoundedkananatas(15);
        pesanan1.setRoundedkananbawah(15);
        pesanan1.setRoundedkiriatas(15);
        pesanan1.setRoundedkiribawah(15);
        pesanan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesanan1ActionPerformed(evt);
            }
        });

        pesanan2.setBackground(new java.awt.Color(251, 249, 241));
        pesanan2.setBorder(null);
        pesanan2.setForeground(new java.awt.Color(153, 153, 153));
        pesanan2.setCaretColor(new java.awt.Color(255, 255, 255));
        pesanan2.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        pesanan2.setRoundedkananatas(15);
        pesanan2.setRoundedkananbawah(15);
        pesanan2.setRoundedkiriatas(15);
        pesanan2.setRoundedkiribawah(15);
        pesanan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesanan2ActionPerformed(evt);
            }
        });

        judul12.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        judul12.setForeground(new java.awt.Color(255, 255, 255));
        judul12.setText("Harga");

        hrg3.setBackground(new java.awt.Color(251, 249, 241));
        hrg3.setBorder(null);
        hrg3.setForeground(new java.awt.Color(153, 153, 153));
        hrg3.setCaretColor(new java.awt.Color(255, 255, 255));
        hrg3.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        hrg3.setRoundedkananatas(15);
        hrg3.setRoundedkananbawah(15);
        hrg3.setRoundedkiriatas(15);
        hrg3.setRoundedkiribawah(15);
        hrg3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hrg3ActionPerformed(evt);
            }
        });

        hrg1.setBackground(new java.awt.Color(251, 249, 241));
        hrg1.setBorder(null);
        hrg1.setForeground(new java.awt.Color(153, 153, 153));
        hrg1.setCaretColor(new java.awt.Color(255, 255, 255));
        hrg1.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        hrg1.setRoundedkananatas(15);
        hrg1.setRoundedkananbawah(15);
        hrg1.setRoundedkiriatas(15);
        hrg1.setRoundedkiribawah(15);
        hrg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hrg1ActionPerformed(evt);
            }
        });

        hrg2.setBackground(new java.awt.Color(251, 249, 241));
        hrg2.setBorder(null);
        hrg2.setForeground(new java.awt.Color(153, 153, 153));
        hrg2.setCaretColor(new java.awt.Color(255, 255, 255));
        hrg2.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        hrg2.setRoundedkananatas(15);
        hrg2.setRoundedkananbawah(15);
        hrg2.setRoundedkiriatas(15);
        hrg2.setRoundedkiribawah(15);
        hrg2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hrg2ActionPerformed(evt);
            }
        });

        judul13.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        judul13.setForeground(new java.awt.Color(255, 255, 255));
        judul13.setText("Uang Masuk");

        uang_msk.setBackground(new java.awt.Color(251, 249, 241));
        uang_msk.setBorder(null);
        uang_msk.setForeground(new java.awt.Color(153, 153, 153));
        uang_msk.setCaretColor(new java.awt.Color(255, 255, 255));
        uang_msk.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        uang_msk.setRoundedkananatas(15);
        uang_msk.setRoundedkananbawah(15);
        uang_msk.setRoundedkiriatas(15);
        uang_msk.setRoundedkiribawah(15);
        uang_msk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uang_mskActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundednew4Layout = new javax.swing.GroupLayout(roundednew4);
        roundednew4.setLayout(roundednew4Layout);
        roundednew4Layout.setHorizontalGroup(
            roundednew4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundednew4Layout.createSequentialGroup()
                .addGroup(roundednew4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundednew4Layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addGroup(roundednew4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(judul8, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(roundednew4Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(roundednew4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(roundednew4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(judul9, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(judul10, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(judul11, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(pesanan2, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                                .addComponent(pesanan1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pesanan3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(judul13))
                        .addGap(28, 28, 28)
                        .addGroup(roundednew4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hrg2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hrg3, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hrg1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(judul12, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(uang_msk, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        roundednew4Layout.setVerticalGroup(
            roundednew4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundednew4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(judul8)
                .addGap(69, 69, 69)
                .addGroup(roundednew4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(judul10)
                    .addComponent(judul12))
                .addGap(18, 18, 18)
                .addGroup(roundednew4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pesanan1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hrg1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addComponent(judul9)
                .addGap(24, 24, 24)
                .addGroup(roundednew4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pesanan2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hrg2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(judul11)
                .addGap(18, 18, 18)
                .addGroup(roundednew4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pesanan3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hrg3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(roundednew4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(uang_msk, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(judul13))
                .addGap(81, 81, 81)
                .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(roundednew2, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roundednew1, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundednew4, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundednew4, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(roundednew1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(roundednew2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void alamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_alamatActionPerformed

    private void pesanan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesanan3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pesanan3ActionPerformed

    private void pesanan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesanan1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pesanan1ActionPerformed

    private void pesanan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesanan2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pesanan2ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
        tampilPesan();
        Loaddata();
        uangMasuk();
         LocalDate ini = LocalDate.now();
       DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
       tgl_psn.setText(ini.format(format));
    }//GEN-LAST:event_button2ActionPerformed

    private void hrg3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hrg3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hrg3ActionPerformed

    private void hrg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hrg1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hrg1ActionPerformed

    private void hrg2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hrg2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hrg2ActionPerformed

    private void uang_mskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uang_mskActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_uang_mskActionPerformed

    private void nm_plgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nm_plgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nm_plgActionPerformed

    private void tgl_psnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tgl_psnActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_tgl_psnActionPerformed

    private void id_plgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_plgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_id_plgActionPerformed

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        // TODO add your handling code here:
        status();
    }//GEN-LAST:event_button3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Aset.RoundedTextField alamat;
    private Aset.button button2;
    private Aset.button button3;
    private Aset.RoundedTextField hrg1;
    private Aset.RoundedTextField hrg2;
    private Aset.RoundedTextField hrg3;
    private Aset.RoundedTextField id_plg;
    private javax.swing.JLabel judul;
    private javax.swing.JLabel judul1;
    private javax.swing.JLabel judul10;
    private javax.swing.JLabel judul11;
    private javax.swing.JLabel judul12;
    private javax.swing.JLabel judul13;
    private javax.swing.JLabel judul14;
    private javax.swing.JLabel judul2;
    private javax.swing.JLabel judul3;
    private javax.swing.JLabel judul8;
    private javax.swing.JLabel judul9;
    private Aset.RoundedTextField nm_plg;
    private Aset.RoundedTextField pesanan1;
    private Aset.RoundedTextField pesanan2;
    private Aset.RoundedTextField pesanan3;
    private Aset.roundednew roundednew1;
    private Aset.roundednew roundednew2;
    private Aset.roundednew roundednew3;
    private Aset.roundednew roundednew4;
    private Aset.RoundedTextField tgl_psn;
    private Aset.RoundedTextField uang_msk;
    // End of variables declaration//GEN-END:variables
}
