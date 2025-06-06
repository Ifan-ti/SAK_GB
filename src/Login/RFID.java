/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Login;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import Koneksi.Koneksi;
import Menu.*;
import java.sql.*;
/**
 *
 * @author achmad ifan
 */
public class RFID extends javax.swing.JFrame {

    Connection conn = (Connection) Koneksi.koneksi();
    public void Presensi(String Absen, String Id, String fm, String FWaktu, int jam){
        try{
            
                String Presensi = "INSERT INTO `presensi` (`Id_Presensi`,`Id_User`,  `Tanggal`, `Keterangan`, `Waktu`) VALUES (NULL, ?, ?, ?, ?)";
                java.sql.PreparedStatement pst = conn.prepareStatement(Presensi);
                pst.setString(1,Id);
                pst.setString(2,fm);
                pst.setString(3,Absen);
                pst.setString(4,FWaktu);
                
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
        DateTimeFormatter Waktu = DateTimeFormatter.ofPattern("HH:mm:ss ");
        DateTimeFormatter Jam = DateTimeFormatter.ofPattern("HH");
        String FWaktu = LocalDateTime.now().format(Waktu);
        String FJam = LocalDateTime.now().format(Jam);
        int IJam = Integer.parseInt(FJam);
        // deklarasi bulan, hari, dan tahun
        DateTimeFormatter Tanggal = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        

        // mengubah format ke string
        LocalDateTime now = LocalDateTime.now();
        String fm = now.format(Tanggal); // Mengubah LocalDateTime menjadi string dengan format yyyy-MM-dd
      
        
    
    try {
        String sql = "SELECT * FROM user WHERE RFID = ?";

        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, scan.getText());

        java.sql.ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            // ambil id user
            String Id = rs.getString("Id_USer");

                JOptionPane.showMessageDialog(null, "berhasil login");

            int choice = JOptionPane.showOptionDialog( null, "Apakah Anda Ingin Melakukan Presensi","Presensi",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, null,null);

            if (rs.getString("role").equals("owner")) {
                Owner ow = new Owner();
                ow.setVisible(true);
                this.dispose();
            }else if (rs.getString("role").equals("kasir")) {

                    if(IJam< 7 && choice == JOptionPane.YES_OPTION){

                        Presensi("Hadir", Id, fm, FWaktu, IJam);

                    }else if(IJam< 15 && choice == JOptionPane.YES_OPTION){

                        Presensi("Telambat", Id, fm, FWaktu, IJam);
                    }
                Kasir ow = new Kasir("");
                ow.setVisible(true);
                this.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(null, "username atau password salah");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
    
    public RFID() {
        initComponents();
        
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
        jLabel1 = new javax.swing.JLabel();
        scan = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(238, 238, 238));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        roundednew1.setBackground(new java.awt.Color(238, 238, 238));
        roundednew1.setColorend(new java.awt.Color(146, 199, 207));
        roundednew1.setColorstar(new java.awt.Color(93, 224, 244));
        roundednew1.setRoundedkananatas(889);
        roundednew1.setRoundedkananbawah(889);
        roundednew1.setRoundedkiriatas(889);
        roundednew1.setRoundedkiribawah(889);
        roundednew1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Big Logo.png"))); // NOI18N
        roundednew1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(372, 165, 410, 381));

        scan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scanActionPerformed(evt);
            }
        });
        roundednew1.add(scan, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 330, 334, 81));

        getContentPane().add(roundednew1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 1140, 889));

        setBounds(0, 0, 1552, 889);
    }// </editor-fold>//GEN-END:initComponents

    private void scanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scanActionPerformed
        Login();
    }//GEN-LAST:event_scanActionPerformed

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
            java.util.logging.Logger.getLogger(RFID.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RFID.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RFID.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RFID.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RFID().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private Aset.roundednew roundednew1;
    private javax.swing.JTextField scan;
    // End of variables declaration//GEN-END:variables
}
