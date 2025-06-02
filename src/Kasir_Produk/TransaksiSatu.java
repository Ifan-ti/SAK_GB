/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Kasir_Produk;

import Aset.roundednew;
import Aset.button;
import Koneksi.Koneksi;
import java.awt.CardLayout;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import Menu.Kasir;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;

 
 
public class TransaksiSatu extends javax.swing.JPanel {

    /**
     * Creates new form Transaksi
     */
    public TransaksiSatu() {
        initComponents();
        tampildata();
        rangebaru();

    }
    public void rangebaru() {
        double totalHariini = sekarang();
        double totalKemarin = kemarin();
        double persen;
//        ikon();
        if (totalKemarin !=0) {
        persen = ((totalHariini-totalKemarin) / totalKemarin) * 100;
    }
        else {
            persen = totalHariini == 0 ? 0:100;
        }
        double persenUntukTampilan = Math.abs(persen);
        range.setText(String.format("%.0f", persenUntukTampilan));
        
         

if (persen > 0) {
    down.setIcon(new ImageIcon("nak2.png")); // Ganti dengan path ikon naik
//    up.setText(String.valueOf(nilai));
} else if (persen < 0) {
    up.setIcon(new ImageIcon("nak1.png")); // Ganti dengan path ikon turun
//    down.setText(String.valueOf(nilai));
} else {
    up.setIcon(null); // atau ikon netral jika nilai = 0
    down.setIcon(null);
}
    }
    private int kemarin() {
        String sql = "SELECT COUNT(DISTINCT pesanan.Id_Pelanggan) " +
                     "FROM pesanan " +
                     "WHERE pesanan.Tanggal_Pesanan = ?";
        
        Connection conn = Koneksi.koneksi();
        int PelangganKemarin = 0;
        try ( 
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Ambil tanggal hari ini
            LocalDate kemarin = LocalDate.now().minusDays(1);

            // Set parameter tanggal ke PreparedStatement
            ps.setDate(1, Date.valueOf(kemarin));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                PelangganKemarin = rs.getInt(1);
//                System.out.println("Jumlah pelanggan unik hari ini: " + jumlahPelangganUnik);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return PelangganKemarin;
    }
    private int sekarang() {
        String sql = "SELECT COUNT(DISTINCT detail_pesanan.Id_Pelanggan) " +
                     "FROM pesanan " +
                     "WHERE pesanan.Tanggal_Pesanan = ?";
        Connection conn = Koneksi.koneksi();
        int PelangganSekarang = 0;
        try ( 
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Ambil tanggal hari ini
            LocalDate hariini = LocalDate.now();

            // Set parameter tanggal ke PreparedStatement
            ps.setDate(1, Date.valueOf(hariini));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                PelangganSekarang = rs.getInt(1);
//                System.out.println("Jumlah pelanggan unik hari ini: " + jumlahPelangganUnik);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return PelangganSekarang;
    }
    public void tampildata() {
        Connection conn;
PreparedStatement pst;
ResultSet rs;
if (tgl_awal.getDate() == null || tgl_akhir.getDate() == null) {
    JOptionPane.showMessageDialog(null, "Silakan pilih tanggal awal dan tanggal akhir terlebih dahulu!");
    return; // Hentikan eksekusi selanjutnya
}

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String tglAwalStr = sdf.format(tgl_awal.getDate());
String tglAkhirStr = sdf.format(tgl_akhir.getDate());

String sql = "SELECT \n" +
"    pesanan.Tanggal_pesanan, \n" +
"    pelanggan.Nama_Pelanggan, \n" +
"    produk.Nama_Produk, \n" +
"    detail_pesanan.Jumlah_Pesanan, \n" +
"    pesanan.Total_Harga, \n" +
"    pesanan.Status\n" +
"FROM \n" +
"    detail_pesanan\n" +
"JOIN \n" +
"    produk ON detail_pesanan.Id_Produk = produk.Id_Produk\n" +
"JOIN \n" +
"    pesanan ON detail_pesanan.Id_Pesanan = pesanan.Id_Pesanan\n" +
"JOIN \n" +
"    pelanggan ON pesanan.Id_Pelanggan = pelanggan.Id_Pelanggan\n" +
"WHERE \n" +
"    pesanan.Tanggal_pesanan  BETWEEN ? AND ? ORDER BY pesanan.Tanggal_pesanan ASC";
try {
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kasir_v2", "root", "");
    pst = conn.prepareStatement(sql);
    pst.setString(1, tglAwalStr);
    pst.setString(2, tglAkhirStr);
    rs = pst.executeQuery();

    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Tanggal_Pesanan");
    model.addColumn("Nama_Pelanggan");
    model.addColumn("Nama_Produk");
    model.addColumn("Jumlah_Pesanan");
    model.addColumn("Total_Harga");
    model.addColumn("Status");
    
    while (rs.next()) {
        model.addRow(new Object[] {
            rs.getString("Tanggal_Pesanan"),
            rs.getString("Nama_Pelanggan"),
            rs.getString("Nama_Produk"),
            rs.getString("Jumlah_Pesanan"),
            rs.getString("Total_Harga"),
            rs.getString("Status")
        });
    }

    table1.setModel(model);

} catch (SQLException e) {
    e.printStackTrace();
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

        roundednew2 = new Aset.roundednew();
        roundednew1 = new Aset.roundednew();
        konfir = new Aset.button();
        judul14 = new javax.swing.JLabel();
        judul1 = new javax.swing.JLabel();
        judul16 = new javax.swing.JLabel();
        judul13 = new javax.swing.JLabel();
        tgl_awal = new com.toedter.calendar.JDateChooser();
        tgl_akhir = new com.toedter.calendar.JDateChooser();
        roundednew3 = new Aset.roundednew();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new Aset.JTableRounded();
        roundednew6 = new Aset.roundednew();
        jLabel1 = new javax.swing.JLabel();
        range = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        up = new javax.swing.JLabel();
        down = new javax.swing.JLabel();

        roundednew2.setColorend(new java.awt.Color(242, 242, 242));
        roundednew2.setColorstar(new java.awt.Color(242, 242, 242));
        roundednew2.setRoundedkananatas(50);
        roundednew2.setRoundedkananbawah(50);
        roundednew2.setRoundedkiriatas(50);
        roundednew2.setRoundedkiribawah(50);

        roundednew1.setBackground(new java.awt.Color(251, 249, 241));
        roundednew1.setForeground(new java.awt.Color(251, 249, 241));
        roundednew1.setColorend(new java.awt.Color(170, 215, 217));
        roundednew1.setColorstar(new java.awt.Color(170, 215, 217));
        roundednew1.setPreferredSize(new java.awt.Dimension(520, 340));
        roundednew1.setRoundedkananatas(50);
        roundednew1.setRoundedkananbawah(50);
        roundednew1.setRoundedkiriatas(50);
        roundednew1.setRoundedkiribawah(50);

        konfir.setBackground(new java.awt.Color(229, 225, 218));
        konfir.setForeground(new java.awt.Color(90, 142, 149));
        konfir.setText("KONFIRMASI");
        konfir.setColor(new java.awt.Color(229, 225, 218));
        konfir.setColorborder(new java.awt.Color(229, 225, 218));
        konfir.setColorclic(new java.awt.Color(229, 225, 218));
        konfir.setColorover(new java.awt.Color(229, 225, 218));
        konfir.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        konfir.setRadius(15);
        konfir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                konfirActionPerformed(evt);
            }
        });

        judul14.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        judul14.setForeground(new java.awt.Color(255, 255, 255));
        judul14.setText("Tanggal Awal");

        judul1.setFont(new java.awt.Font("Poppins", 1, 30)); // NOI18N
        judul1.setForeground(new java.awt.Color(255, 255, 255));
        judul1.setText("Filter");

        judul16.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        judul16.setForeground(new java.awt.Color(255, 255, 255));
        judul16.setText("Tanggal Akhir");

        judul13.setFont(new java.awt.Font("Poppins", 1, 20)); // NOI18N
        judul13.setForeground(new java.awt.Color(255, 255, 255));
        judul13.setText("Mencari Sesuai Nama Pelanggan   ?");
        judul13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                judul13MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout roundednew1Layout = new javax.swing.GroupLayout(roundednew1);
        roundednew1.setLayout(roundednew1Layout);
        roundednew1Layout.setHorizontalGroup(
            roundednew1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundednew1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(roundednew1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundednew1Layout.createSequentialGroup()
                        .addComponent(konfir, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(roundednew1Layout.createSequentialGroup()
                        .addGroup(roundednew1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(judul14, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tgl_awal, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addGroup(roundednew1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(judul16, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tgl_akhir, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11))))
            .addGroup(roundednew1Layout.createSequentialGroup()
                .addGap(207, 207, 207)
                .addComponent(judul1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundednew1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(judul13)
                .addGap(103, 103, 103))
        );
        roundednew1Layout.setVerticalGroup(
            roundednew1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundednew1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(judul1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(roundednew1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(judul14)
                    .addComponent(judul16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundednew1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundednew1Layout.createSequentialGroup()
                        .addComponent(tgl_akhir, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(konfir, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(judul13))
                    .addComponent(tgl_awal, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        roundednew3.setBackground(new java.awt.Color(251, 249, 241));
        roundednew3.setForeground(new java.awt.Color(251, 249, 241));
        roundednew3.setColorend(new java.awt.Color(170, 215, 217));
        roundednew3.setColorstar(new java.awt.Color(170, 215, 217));
        roundednew3.setPreferredSize(new java.awt.Dimension(520, 340));
        roundednew3.setRoundedkananatas(50);
        roundednew3.setRoundedkananbawah(50);
        roundednew3.setRoundedkiriatas(50);
        roundednew3.setRoundedkiribawah(50);

        table1.setBackground(new java.awt.Color(204, 204, 204));
        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Tanggal", "Nama Pelanggan", "Produk", "Jumlah", "Harga", "Status"
            }
        ));
        table1.setRoundedkananatas(50);
        table1.setRoundedkananbawah(50);
        table1.setRoundedkiriatas(50);
        table1.setRoundedkiribawah(50);
        jScrollPane1.setViewportView(table1);

        javax.swing.GroupLayout roundednew3Layout = new javax.swing.GroupLayout(roundednew3);
        roundednew3.setLayout(roundednew3Layout);
        roundednew3Layout.setHorizontalGroup(
            roundednew3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundednew3Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 672, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );
        roundednew3Layout.setVerticalGroup(
            roundednew3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundednew3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        roundednew6.setBackground(new java.awt.Color(251, 249, 241));
        roundednew6.setForeground(new java.awt.Color(251, 249, 241));
        roundednew6.setColorend(new java.awt.Color(170, 215, 217));
        roundednew6.setColorstar(new java.awt.Color(170, 215, 217));
        roundednew6.setPreferredSize(new java.awt.Dimension(520, 340));
        roundednew6.setRoundedkananatas(50);
        roundednew6.setRoundedkananbawah(50);
        roundednew6.setRoundedkiriatas(50);
        roundednew6.setRoundedkiribawah(50);
        roundednew6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Poppins ExtraBold", 0, 100)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(60, 63, 65));
        jLabel1.setText("%");
        roundednew6.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, -1, -1));

        range.setFont(new java.awt.Font("Poppins ExtraBold", 0, 100)); // NOI18N
        range.setForeground(new java.awt.Color(60, 63, 65));
        range.setText("0");
        roundednew6.add(range, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 60, -1, -1));

        jLabel3.setFont(new java.awt.Font("Poppins Black", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(60, 63, 65));
        jLabel3.setText("Dari Transaksi Kemarin");
        roundednew6.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 228, -1, -1));

        up.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/nak2.png"))); // NOI18N
        up.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                upMouseClicked(evt);
            }
        });
        roundednew6.add(up, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 228, -1, -1));

        down.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/nak1.png"))); // NOI18N
        roundednew6.add(down, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 228, -1, -1));

        javax.swing.GroupLayout roundednew2Layout = new javax.swing.GroupLayout(roundednew2);
        roundednew2.setLayout(roundednew2Layout);
        roundednew2Layout.setHorizontalGroup(
            roundednew2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundednew2Layout.createSequentialGroup()
                .addGroup(roundednew2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundednew2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(roundednew1, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(roundednew6, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundednew2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(roundednew3, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundednew2Layout.setVerticalGroup(
            roundednew2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundednew2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundednew2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundednew1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roundednew6, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundednew3, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(roundednew2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 75, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundednew2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void konfirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_konfirActionPerformed
   
       tampildata();
        // TODO add your handling code here:
    }//GEN-LAST:event_konfirActionPerformed

    private void upMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_upMouseClicked
       
    }//GEN-LAST:event_upMouseClicked

    private void judul13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_judul13MouseClicked
         this.setLayout(new BorderLayout());
        this.removeAll();
        this.add(new TransaksiDua());
        this.revalidate();
        this.repaint();

//        TransaksiDua td = new TransaksiDua();
//        TransaksiSatu ts = new TransaksiSatu();
//        ts.setVisible(false);
//        td.setVisible(true);
    }//GEN-LAST:event_judul13MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel down;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel judul1;
    private javax.swing.JLabel judul13;
    private javax.swing.JLabel judul14;
    private javax.swing.JLabel judul16;
    private Aset.button konfir;
    private javax.swing.JLabel range;
    private Aset.roundednew roundednew1;
    private Aset.roundednew roundednew2;
    private Aset.roundednew roundednew3;
    private Aset.roundednew roundednew6;
    private Aset.JTableRounded table1;
    private com.toedter.calendar.JDateChooser tgl_akhir;
    private com.toedter.calendar.JDateChooser tgl_awal;
    private javax.swing.JLabel up;
    // End of variables declaration//GEN-END:variables
}
