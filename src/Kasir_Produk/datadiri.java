/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Kasir_Produk;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import raven.glasspanepopup.GlassPanePopup;
import Aset.roundednew;
import Koneksi.Koneksi;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author achmad ifan
 */
public class datadiri extends roundednew {

    /**
     * Creates new form datadiri
     */
    public datadiri() {
        initComponents();
       
    }
     Connection conn = (Connection)Koneksi.koneksi();
     String id_pelanggan ;
    private void filterIdAuto( String id_database,String len, int nextid){
        
                    id_database = "PL"+len+nextid;
                    id_pelanggan = id_database;
                    
                   
    }
    private void idaout(String nama_tabel, String id){
        String sql = "SELECT MAX(CAST(SUBSTRING("+id+", 3) AS INT)) AS MaxNumericValue FROM "+nama_tabel ;
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            if(rs.next()){
                String id_database = rs.getString("MaxNumericValue");
                
//                String potong = id_database.substring(2);
                 
                int nextid = Integer.parseInt(id_database)+1;
                
                if(nextid<=9){
                filterIdAuto(id_database,"000", nextid);
                    
                }else if(nextid<=99){
                    filterIdAuto( id_database,"00", nextid);
                }else if(nextid<= 999){
                    filterIdAuto(id_database,"0", nextid);
                }else if(nextid<= 999){
                    filterIdAuto(id_database,"", nextid);
                }
                
                id = id_pelanggan;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }        
    
    private void simpanNilai() {
    DataManager.getInstance().setNilai(id_pelanggan);
    }
    
    private void tambahDataDiri(){
        String nama = txt_nama.getText();
        String nomerHp = txt_nomerhp.getText();
        String alamat = txt_alamt.getText();
        
        if (nama.isEmpty() || nomerHp.isEmpty() || alamat.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return; // Jika ada field kosong, hentikan proses
        }

        // Validasi tambahan, seperti memeriksa panjang nomor HP
        if (!nomerHp.matches("\\d+")) { // Memastikan nomor HP hanya terdiri dari angka
            JOptionPane.showMessageDialog(null, "Nomor HP harus berupa angka!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return; // Hentikan proses jika format salah
        }

        // Query SQL
        String sql = "INSERT INTO pelanggan(Id_Pelanggan, Nama_Pelanggan, Alamat, No_HP) VALUES(?, ?, ?, ?)";

        try {
            // Membuka koneksi
            PreparedStatement pst = conn.prepareStatement(sql);

            // Mengisi parameter SQL
            pst.setString(1, id_pelanggan);
            pst.setString(2, nama);
            pst.setString(3, alamat);
            pst.setString(4, nomerHp);


            // Eksekusi query
            int row = pst.executeUpdate();

            // Cek hasil eksekusi
            if (row > 0) {
                JOptionPane.showMessageDialog(null, "Data berhasil ditambah!");

                simpanNilai();
                GlassPanePopup.closePopupLast();
                
            } else {
                JOptionPane.showMessageDialog(null, "Data gagal ditambahkan, coba lagi!");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            // Menangani error jika ID sudah ada atau constraint violation
            JOptionPane.showMessageDialog(null, "Gagal menambahkan data: ID pelanggan sudah ada atau melanggar aturan!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            // Menangani error SQL lainnya
            JOptionPane.showMessageDialog(null, "Kesalahan database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            // Menangani error lainnya
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        judul = new javax.swing.JLabel();
        judul1 = new javax.swing.JLabel();
        judul2 = new javax.swing.JLabel();
        txt_alamt = new Aset.RoundedTextField();
        txt_nomerhp = new Aset.RoundedTextField();
        judul3 = new javax.swing.JLabel();
        txt_nama = new Aset.RoundedTextField();
        button1 = new Aset.button();
        judul4 = new javax.swing.JLabel();

        setColorend(new java.awt.Color(242, 242, 242));
        setColorstar(new java.awt.Color(242, 242, 242));
        setRoundedkananatas(30);
        setRoundedkananbawah(30);
        setRoundedkiriatas(30);
        setRoundedkiribawah(30);
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        roundednew2.setColorend(new java.awt.Color(242, 242, 242));
        roundednew2.setColorstar(new java.awt.Color(242, 242, 242));
        roundednew2.setRoundedkananatas(30);
        roundednew2.setRoundedkananbawah(30);
        roundednew2.setRoundedkiriatas(30);
        roundednew2.setRoundedkiribawah(30);
        roundednew2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        roundednew1.setColorend(new java.awt.Color(170, 215, 217));
        roundednew1.setColorstar(new java.awt.Color(170, 215, 217));
        roundednew1.setRoundedkananatas(15);
        roundednew1.setRoundedkananbawah(15);
        roundednew1.setRoundedkiriatas(15);
        roundednew1.setRoundedkiribawah(15);

        judul.setFont(new java.awt.Font("Poppins", 1, 30)); // NOI18N
        judul.setForeground(new java.awt.Color(90, 142, 149));
        judul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        judul.setText("Data Diri Pelanggan");

        judul1.setFont(new java.awt.Font("Poppins", 0, 25)); // NOI18N
        judul1.setForeground(new java.awt.Color(90, 142, 149));
        judul1.setText("Nama");

        judul2.setFont(new java.awt.Font("Poppins", 0, 25)); // NOI18N
        judul2.setForeground(new java.awt.Color(90, 142, 149));
        judul2.setText("Nomor Telefon");

        txt_alamt.setBackground(new java.awt.Color(251, 249, 241));
        txt_alamt.setForeground(new java.awt.Color(153, 153, 153));
        txt_alamt.setFont(new java.awt.Font("Poppins Medium", 0, 20)); // NOI18N
        txt_alamt.setRoundedkananatas(20);
        txt_alamt.setRoundedkananbawah(20);
        txt_alamt.setRoundedkiriatas(20);
        txt_alamt.setRoundedkiribawah(20);

        txt_nomerhp.setBackground(new java.awt.Color(251, 249, 241));
        txt_nomerhp.setForeground(new java.awt.Color(153, 153, 153));
        txt_nomerhp.setFont(new java.awt.Font("Poppins Medium", 0, 20)); // NOI18N
        txt_nomerhp.setRoundedkananatas(20);
        txt_nomerhp.setRoundedkananbawah(20);
        txt_nomerhp.setRoundedkiriatas(20);
        txt_nomerhp.setRoundedkiribawah(20);

        judul3.setFont(new java.awt.Font("Poppins", 0, 25)); // NOI18N
        judul3.setForeground(new java.awt.Color(90, 142, 149));
        judul3.setText("Alamat");

        txt_nama.setBackground(new java.awt.Color(251, 249, 241));
        txt_nama.setForeground(new java.awt.Color(153, 153, 153));
        txt_nama.setFont(new java.awt.Font("Poppins Medium", 0, 20)); // NOI18N
        txt_nama.setRoundedkananatas(20);
        txt_nama.setRoundedkananbawah(20);
        txt_nama.setRoundedkiriatas(20);
        txt_nama.setRoundedkiribawah(20);

        button1.setForeground(new java.awt.Color(90, 142, 149));
        button1.setText("KONFIRMASI");
        button1.setColor(new java.awt.Color(229, 225, 218));
        button1.setColorborder(new java.awt.Color(229, 225, 218));
        button1.setColorclic(new java.awt.Color(229, 225, 218));
        button1.setColorover(new java.awt.Color(229, 225, 218));
        button1.setFont(new java.awt.Font("Poppins", 1, 25)); // NOI18N
        button1.setRadius(20);
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        judul4.setFont(new java.awt.Font("Poppins SemiBold", 0, 30)); // NOI18N
        judul4.setForeground(new java.awt.Color(90, 142, 149));
        judul4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        judul4.setText("Kembali");
        judul4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                judul4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout roundednew1Layout = new javax.swing.GroupLayout(roundednew1);
        roundednew1.setLayout(roundednew1Layout);
        roundednew1Layout.setHorizontalGroup(
            roundednew1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundednew1Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(roundednew1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(judul4, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundednew1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(button1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_alamt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundednew1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(judul3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(roundednew1Layout.createSequentialGroup()
                                .addGroup(roundednew1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(judul1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(roundednew1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(judul2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_nomerhp, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(46, 46, 46))
            .addGroup(roundednew1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(judul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundednew1Layout.setVerticalGroup(
            roundednew1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundednew1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(judul, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(roundednew1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(judul1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(judul2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(roundednew1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nomerhp, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(judul3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(txt_alamt, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(judul4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        roundednew2.add(roundednew1, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 45, 665, 460));

        add(roundednew2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 755, 550));
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        idaout("pelanggan", "Id_Pelanggan");
        tambahDataDiri();
    }//GEN-LAST:event_button1ActionPerformed

    private void judul4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_judul4MouseClicked
       GlassPanePopup.closePopupLast();
    }//GEN-LAST:event_judul4MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Aset.button button1;
    private javax.swing.JLabel judul;
    private javax.swing.JLabel judul1;
    private javax.swing.JLabel judul2;
    private javax.swing.JLabel judul3;
    private javax.swing.JLabel judul4;
    private Aset.roundednew roundednew1;
    private Aset.roundednew roundednew2;
    private Aset.RoundedTextField txt_alamt;
    private Aset.RoundedTextField txt_nama;
    private Aset.RoundedTextField txt_nomerhp;
    // End of variables declaration//GEN-END:variables
}
