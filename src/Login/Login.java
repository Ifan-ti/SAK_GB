/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Login;

import Kasir_Produk.DataManager;
import Koneksi.Koneksi;
import Menu.Kasir;
import Menu.Owner;
import java.awt.Color;
import java.awt.GradientPaint;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author achmad ifan
 */
public class Login extends javax.swing.JFrame {
    Connection conn = (Connection) Koneksi.koneksi();
    String id_user = "";
    public void Presensi(String Absen, String Id, String fm, String FWaktu, int jam){
        try{
            
                String Presensi = "INSERT INTO `presensi` (`Id_Presensi`,`Id_User`,  `Tanggal`, `Keterangan`, `Waktu_Masuk`, Nama) VALUES (NULL, ?, ?, ?, ?,?)";
                java.sql.PreparedStatement pst = conn.prepareStatement(Presensi);
                pst.setString(1,Id);
                pst.setString(2,fm);
                pst.setString(3,Absen);
                pst.setString(4,FWaktu);
                pst.setString(5,txt_user.getText());
                
               if (Absen.equals("Hadir")){
                JOptionPane.showMessageDialog(null, "Anda hadir tepat "+Absen);
               }else{
                JOptionPane.showMessageDialog(null, "Anda hadir "+Absen+", Lebih "+(jam-7)+" Jam");   
               }
                pst.executeUpdate();
        }catch(Exception e){
                   System.err.println("Terjadi kesalahan saat memasukkan data pelanggan: " + e.getMessage()); 
               }
    }
    
    
     public void Login (){
        DateTimeFormatter waktuFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter jamFormatter = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter tanggalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String fWaktu = LocalDateTime.now().format(waktuFormatter);
        String fJam = LocalDateTime.now().format(jamFormatter);
        String fm = LocalDateTime.now().format(tanggalFormatter);
        int iJam = Integer.parseInt(fJam);

        try {
            String sql = "SELECT Id_USer, role FROM user WHERE Username = ? AND Password = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txt_user.getText());
            pst.setString(2, new String(txt_pw.getPassword())); // Gunakan getPassword() untuk JPasswordField
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String idUser = rs.getString("Id_USer");
                String role = rs.getString("role");
                JOptionPane.showMessageDialog(null, "Berhasil login sebagai " + role);

                if (role.equals("owner")) {
                    Owner ow = new Owner();
                    ow.setVisible(true);
                    // this.dispose(); // Pastikan 'this' mengacu pada JFrame login
                } else if (role.equals("kasir")) {
                    

                        String presensiSql = "SELECT * FROM presensi WHERE Nama = ? AND Tanggal = ?";
                        PreparedStatement st = conn.prepareStatement(presensiSql);
                        st.setString(1, txt_user.getText());
                        st.setString(2, fm);
                        ResultSet presensiRs = st.executeQuery();

                    if (!presensiRs.next()) { // Jika belum ada presensi hari ini
                        int choice = JOptionPane.showOptionDialog(null, "Apakah Anda Ingin Melakukan Presensi?", "Presensi",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                        if (choice == JOptionPane.YES_OPTION) {
                            if (iJam < 7) {
                                Presensi("Hadir", idUser, fm, fWaktu, iJam);
                            } else if (iJam < 15) {
                                Presensi("Telambat", idUser, fm, fWaktu, iJam);
                            } else {
                                JOptionPane.showMessageDialog(null, "Presensi di luar jam yang ditentukan.");
                            }
                        } 
                        
                    }else{
                            JOptionPane.showMessageDialog(null, "Anda sudah melakukan presensi hari ini.");
                        }

                    Kasir kasirForm = new Kasir(txt_user.getText());
                    id_user =idUser;
                    kasirForm.setVisible(true);
                    this.dispose(); // Pastikan 'this' mengacu pada JFrame login
                }
                rs.close();
                pst.close();
                
            } else {
                JOptionPane.showMessageDialog(null, "Username atau password salah");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error login: " + e.getMessage());
        }
    }
    
   
     /**
     * Creates new form desktop
     */
    public Login() {
        initComponents();
        Eopen.setVisible(false);
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
        jLabel2 = new javax.swing.JLabel();
        button2 = new Aset.button();
        roundednew1 = new Aset.roundednew();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Eopen = new javax.swing.JLabel();
        Eclose = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_user = new Aset.RoundedTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_pw = new Aset.JPassword();
        button3 = new Aset.button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(238, 238, 238));
        setLocationByPlatform(true);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        roundednew2.setColorend(new java.awt.Color(146, 199, 207));
        roundednew2.setColorstar(new java.awt.Color(93, 224, 244));
        roundednew2.setRoundedkananatas(10000);
        roundednew2.setRoundedkananbawah(10000);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Group 101.png"))); // NOI18N

        button2.setForeground(new java.awt.Color(42, 85, 87));
        button2.setText("Login Dengan RFID");
        button2.setFont(new java.awt.Font("Poppins", 1, 20)); // NOI18N
        button2.setRadius(20);
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundednew2Layout = new javax.swing.GroupLayout(roundednew2);
        roundednew2.setLayout(roundednew2Layout);
        roundednew2Layout.setHorizontalGroup(
            roundednew2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundednew2Layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addGroup(roundednew2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundednew2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(213, Short.MAX_VALUE))
        );
        roundednew2Layout.setVerticalGroup(
            roundednew2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundednew2Layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(272, Short.MAX_VALUE))
        );

        getContentPane().add(roundednew2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 880));

        roundednew1.setColorend(new java.awt.Color(170, 215, 217));
        roundednew1.setColorstar(new java.awt.Color(170, 215, 217));
        roundednew1.setRoundedkananatas(51);
        roundednew1.setRoundedkananbawah(51);
        roundednew1.setRoundedkiriatas(51);
        roundednew1.setRoundedkiribawah(51);
        roundednew1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Password.png"))); // NOI18N
        roundednew1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 265, -1, 40));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Username.png"))); // NOI18N
        roundednew1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 165, -1, 40));

        Eopen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Eye.png"))); // NOI18N
        Eopen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EopenMouseClicked(evt);
            }
        });
        roundednew1.add(Eopen, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 265, -1, 40));

        Eclose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Eye off.png"))); // NOI18N
        Eclose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EcloseMouseClicked(evt);
            }
        });
        roundednew1.add(Eclose, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 265, -1, 40));

        jLabel1.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(42, 85, 87));
        jLabel1.setText("Lupa Password?");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        roundednew1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 390, -1, 39));

        jLabel3.setFont(new java.awt.Font("Poppins", 1, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(42, 85, 87));
        jLabel3.setText("LOGIN");
        roundednew1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 50, -1, 39));

        txt_user.setForeground(new java.awt.Color(42, 85, 87));
        txt_user.setText("Username");
        txt_user.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        txt_user.setMargin(new java.awt.Insets(5, 45, 2, 6));
        txt_user.setRoundedkananatas(20);
        txt_user.setRoundedkananbawah(20);
        txt_user.setRoundedkiriatas(20);
        txt_user.setRoundedkiribawah(20);
        txt_user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_userMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_userMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txt_userMousePressed(evt);
            }
        });
        txt_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_userActionPerformed(evt);
            }
        });
        roundednew1.add(txt_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 370, 50));

        jLabel4.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(42, 85, 87));
        jLabel4.setText("Password");
        roundednew1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, 39));

        jLabel5.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(42, 85, 87));
        jLabel5.setText("Username");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        roundednew1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, 39));

        txt_pw.setForeground(new java.awt.Color(42, 85, 87));
        txt_pw.setText("jPassword1");
        txt_pw.setMargin(new java.awt.Insets(5, 45, 2, 6));
        txt_pw.setRoundedkananatas(20);
        txt_pw.setRoundedkananbawah(20);
        txt_pw.setRoundedkiriatas(20);
        txt_pw.setRoundedkiribawah(20);
        roundednew1.add(txt_pw, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, 370, 50));

        button3.setBackground(new java.awt.Color(229, 225, 218));
        button3.setForeground(new java.awt.Color(42, 85, 87));
        button3.setText("KONFIRMASI");
        button3.setFont(new java.awt.Font("Poppins", 1, 20)); // NOI18N
        button3.setRadius(20);
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });
        roundednew1.add(button3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 370, 50));

        getContentPane().add(roundednew1, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 200, 449, 470));

        setBounds(0, 0, 1552, 889);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked

    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel5MouseClicked

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        this.dispose();
        RFID pp = new RFID();
        pp.setVisible(true);
    }//GEN-LAST:event_button2ActionPerformed

    private void txt_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_userActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_userActionPerformed

    private void txt_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_userMouseClicked
        txt_user.setText(null);
        
        
    }//GEN-LAST:event_txt_userMouseClicked

    private void txt_userMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_userMouseExited
        String i = txt_user.getText();
        if (i.equals("")){
            txt_user.setText("Username");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_userMouseExited

    private void txt_userMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_userMousePressed
        
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_userMousePressed

    private void EopenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EopenMouseClicked
        txt_pw.setEchoChar('\u2022');
        Eopen.setVisible(false);
        Eclose.setVisible(true);
    }//GEN-LAST:event_EopenMouseClicked

    private void EcloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EcloseMouseClicked
        txt_pw.setEchoChar((char)0);
        Eopen.setVisible(true);
        Eclose.setVisible(false);
    }//GEN-LAST:event_EcloseMouseClicked

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        
        Login();
       
    }//GEN-LAST:event_button3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Eclose;
    private javax.swing.JLabel Eopen;
    private Aset.button button2;
    private Aset.button button3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private Aset.roundednew roundednew1;
    private Aset.roundednew roundednew2;
    private Aset.JPassword txt_pw;
    private Aset.RoundedTextField txt_user;
    // End of variables declaration//GEN-END:variables
}
