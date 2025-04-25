/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Owner_Produk;
import Aset.roundednew;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;
import Koneksi.Koneksi;
import java.awt.BorderLayout;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author achmad ifan
 */
public class Menu extends roundednew {
    Connection conn = (Connection) Koneksi.koneksi();
    int i = 0;
    
    
    /**
     * Creates new form NewJPanel
     */
    public Menu() {
        initComponents();
        setRoundedkananatas(94);
        setRoundedkananbawah(94);
        setRoundedkiriatas(94);
        setRoundedkiribawah(94);
        
        
        jTable1.getTableHeader().setFont(new Font ( "Poppins" , Font.PLAIN, 12) );
        jTable1.getTableHeader().setOpaque(false);
        jTable1.getTableHeader().setBackground(Color.WHITE);
        jTable1.getTableHeader().setForeground(Color.BLACK);
        
        loadData();
        
        
        
        
        
        
        
    }
    public void Hapus_Bahan(int rowIndex){
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            String Id = model.getValueAt(rowIndex, 0).toString(); 
            
        setLayout(new BorderLayout());
        removeAll();
        add(new Menu());
        revalidate();
        repaint();
        
    }
    
    public void koreksi (){
        if (i == 2){
            jLabel1.setVisible(false);
            jLabel3.setVisible(false);
            txt_harga.setVisible(false);
            txt_name.setVisible(false);
        }else{
            jLabel1.setVisible(true);
            jLabel3.setVisible(true);
            txt_harga.setVisible(true);
            txt_name.setVisible(true);
        }
    }
    
    public void HapusProduk(int rowIndex) {
        try {
            
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            
            String Id = model.getValueAt(rowIndex, 0).toString(); 
            String query = "DELETE FROM Produk WHERE Id_produk = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(query);
             pst.setString(1, Id);
             pst.executeUpdate();
             
            loadData(); 
            
            
            
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting data: " + e.getMessage());
        }
       
     }
    
    
     public void loadData(){ //fungsi load data
         
      try { //kondisi 
        String query = "SELECT * FROM Produk ";
        PreparedStatement pst = conn.prepareStatement(query);
        
        ResultSet rs = pst.executeQuery();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Id_Produk","Nama_produk", "Harga"}, 0);
        jTable1.setModel(model);
        
        DecimalFormat df = new DecimalFormat("#"); //format angka tanpa koma
        
        while (rs.next()) { //memasukkan data ke row table
            Object[] row = {
                rs.getString("Id_Produk"), 
                rs.getString("Nama_produk"), 
                rs.getString("Harga")
                
            };
            model.addRow(row);
            
         }
        
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
    }
    }
     
     
     public void UpdateProduk (int rowIndex) {
        try {
            
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            
            
            
            
            
            String Nama_Produk = model.getValueAt(rowIndex, 0).toString(); 
            String query = "UPDATE Produk SET Nama_produk = ?, Harga = ? WHERE Id_Produk = ?";
            PreparedStatement pst = conn.prepareStatement(query);
             pst.setString(1, txt_name.getText());
             pst.setString(2, txt_harga.getText());
             pst.setString(3, Nama_Produk);
             
             int row = pst.executeUpdate();
             
             if (row>0){
                 JOptionPane.showMessageDialog(null, "Produk Berhasil Di Update", "Update", 1);
             }else{
                 JOptionPane.showMessageDialog(null, "Produk Gagal Di Update", "Update", 0);

             }
             
            loadData(); // Update table after delete
            
            
            
            
         
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting data: " + e.getMessage());
        }
       
     }
    
    public void tambahProduk(){
        try {
       String sql = "INSERT INTO Produk (Id_Produk , Nama_produk, Harga ) VALUES (NULL,?,?) "; 
            PreparedStatement pst = conn.prepareStatement(sql);
            
            pst.setString(1, txt_name.getText());
            pst.setString(2, txt_harga.getText());
            
            int row = pst.executeUpdate();
            
            if(row >0){
                JOptionPane.showMessageDialog(null, "Produk Berhasil Di Tambahkan", "Tambah Produk", 1);
                loadData();
            }else{
                JOptionPane.showMessageDialog(null, "Produk Gagal Di Tambahkan","Tambah Produk", 0);
                loadData();
                
            }
            
            
        } catch (Exception e) {
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

        roundednew3 = new Aset.roundednew();
        jLabel1 = new javax.swing.JLabel();
        judul = new javax.swing.JLabel();
        txt_name = new Aset.RoundedTextField();
        txt_harga = new Aset.RoundedTextField();
        jLabel3 = new javax.swing.JLabel();
        button1 = new Aset.button();
        roundednew4 = new Aset.roundednew();
        button2 = new Aset.button();
        Hapus = new Aset.button();
        Update = new Aset.button();
        roundednew5 = new Aset.roundednew();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setColorend(new java.awt.Color(242, 242, 242));
        setColorstar(new java.awt.Color(242, 242, 242));
        setRoundedkananatas(94);
        setRoundedkananbawah(94);
        setRoundedkiriatas(94);
        setRoundedkiribawah(94);
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        roundednew3.setColorend(new java.awt.Color(170, 215, 217));
        roundednew3.setColorstar(new java.awt.Color(170, 215, 217));
        roundednew3.setRoundedkananatas(92);
        roundednew3.setRoundedkananbawah(92);
        roundednew3.setRoundedkiriatas(92);
        roundednew3.setRoundedkiribawah(92);
        roundednew3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(90, 142, 149));
        jLabel1.setText("Nama Produk");
        roundednew3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, 31));

        judul.setFont(new java.awt.Font("Poppins", 1, 30)); // NOI18N
        judul.setForeground(new java.awt.Color(90, 142, 149));
        judul.setText("Tambah Produk");
        roundednew3.add(judul, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, -1, 31));

        txt_name.setBackground(new java.awt.Color(242, 242, 242));
        txt_name.setBorder(null);
        txt_name.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_name.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        txt_name.setRoundedkananatas(15);
        txt_name.setRoundedkananbawah(15);
        txt_name.setRoundedkiriatas(15);
        txt_name.setRoundedkiribawah(15);
        roundednew3.add(txt_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 490, 50));

        txt_harga.setBackground(new java.awt.Color(242, 242, 242));
        txt_harga.setBorder(null);
        txt_harga.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_harga.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        txt_harga.setRoundedkananatas(15);
        txt_harga.setRoundedkananbawah(15);
        txt_harga.setRoundedkiriatas(15);
        txt_harga.setRoundedkiribawah(15);
        roundednew3.add(txt_harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 490, 50));

        jLabel3.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(90, 142, 149));
        jLabel3.setText("Harga Produk");
        roundednew3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, 31));

        button1.setBackground(new java.awt.Color(229, 225, 218));
        button1.setText("KONFIMASI");
        button1.setFont(new java.awt.Font("Poppins", 1, 25)); // NOI18N
        button1.setRadius(20);
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        roundednew3.add(button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 490, 50));

        add(roundednew3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 550, 380));

        roundednew4.setColorend(new java.awt.Color(170, 215, 217));
        roundednew4.setColorstar(new java.awt.Color(170, 215, 217));
        roundednew4.setRoundedkananatas(92);
        roundednew4.setRoundedkananbawah(92);
        roundednew4.setRoundedkiriatas(92);
        roundednew4.setRoundedkiribawah(92);
        roundednew4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        button2.setText("button2");
        button2.setRadius(30);
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        roundednew4.add(button2, new org.netbeans.lib.awtextra.AbsoluteConstraints(371, 39, 150, 225));

        Hapus.setRadius(30);
        Hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HapusActionPerformed(evt);
            }
        });
        roundednew4.add(Hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(203, 39, 150, 225));

        Update.setFont(new java.awt.Font("Poppins", 1, 20)); // NOI18N
        Update.setRadius(30);
        Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateActionPerformed(evt);
            }
        });
        roundednew4.add(Update, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 39, 150, 225));

        add(roundednew4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, 550, 300));

        roundednew5.setColorend(new java.awt.Color(170, 215, 217));
        roundednew5.setColorstar(new java.awt.Color(170, 215, 217));
        roundednew5.setRoundedkananatas(92);
        roundednew5.setRoundedkananbawah(92);
        roundednew5.setRoundedkiriatas(92);
        roundednew5.setRoundedkiribawah(92);

        jTable1.setBackground(new java.awt.Color(242, 242, 242));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama Produk", "Harga"
            }
        ));
        jTable1.setFocusable(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(200);
        }

        javax.swing.GroupLayout roundednew5Layout = new javax.swing.GroupLayout(roundednew5);
        roundednew5.setLayout(roundednew5Layout);
        roundednew5Layout.setHorizontalGroup(
            roundednew5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundednew5Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        roundednew5Layout.setVerticalGroup(
            roundednew5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundednew5Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 632, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        add(roundednew5, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 40, 560, 700));
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
       switch(i){
           case 0 :
                tambahProduk();
                break;
           case 1 :
                int selectedRow = jTable1.getSelectedRow();
                    if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus.");
                    return;
                    }else{
                UpdateProduk(selectedRow);
                    }
            break;
           case 2 :
                    selectedRow = jTable1.getSelectedRow();
                    if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus.");
                    return;
                    }else{
                    HapusProduk(selectedRow);
                    }
            break;
           case 3:
               selectedRow = jTable1.getSelectedRow();
                    if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus.");
                    return;
                    }else{
                        
                HapusProduk(selectedRow);
                    }
       } 
        
    }//GEN-LAST:event_button1ActionPerformed

    private void HapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HapusActionPerformed
        int b;
        if (i == 2){
            i = 0;
        }else{
            i = 2;
        }
         
       
        switch(i){
           case 0 :
            judul.setText("Tambah Produk");
           koreksi();
            break;
           case 1 :
            judul.setText("Edit Produk");
           koreksi();
            break;
           case 2 :
            judul.setText("Hapus Produk");
           koreksi();
            break;
               
       }

    }//GEN-LAST:event_HapusActionPerformed

    private void UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateActionPerformed
        int b;
        if (i == 1){
            i = 0;
        }else{
            i = 1;
        }
         
       
        switch(i){
           case 0 :
            judul.setText("Tambah Produk");
            koreksi();
            break;
           case 1 :
            judul.setText("Edit Produk");
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("Gambar\\Edit_Produk.png"));
            Update.setIcon(icon);
            Update.setText("Edit \n"+"Produk");
            koreksi();
            break;
           case 2 :
            judul.setText("Hapus Produk");
            koreksi();
           
            break;
               
       }
        // TODO add your handling code here:
    }//GEN-LAST:event_UpdateActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Aset.button Hapus;
    private Aset.button Update;
    private Aset.button button1;
    private Aset.button button2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel judul;
    private Aset.roundednew roundednew3;
    private Aset.roundednew roundednew4;
    private Aset.roundednew roundednew5;
    private Aset.RoundedTextField txt_harga;
    private Aset.RoundedTextField txt_name;
    // End of variables declaration//GEN-END:variables
}
