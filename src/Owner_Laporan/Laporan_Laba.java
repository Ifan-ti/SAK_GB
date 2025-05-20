/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Owner_Laporan;

import Aset.roundednew;
/**
 *
 * @author Lenovo
 */
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
import javax.swing.table.DefaultTableModel;
public class Laporan_Laba extends roundednew {

    /**
     * Creates new form Laporan_Laba
     */
    public Laporan_Laba() {
        initComponents();
    }
    
    private void loadData(){
        try {
            
        } catch (Exception e) {
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
        button1 = new Aset.button();
        jcomboboxcustom1 = new Aset.Jcomboboxcustom();
        jcomboboxcustom2 = new Aset.Jcomboboxcustom();
        jLabel1 = new javax.swing.JLabel();
        roundednew3 = new Aset.roundednew();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        button2 = new Aset.button();
        button3 = new Aset.button();
        button4 = new Aset.button();

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

        roundednew1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 860, 270));

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
        judul.setText("Filter Laba");
        roundednew2.add(judul, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, -1, 31));

        jLabel3.setFont(new java.awt.Font("Poppins", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(90, 142, 149));
        jLabel3.setText("Tanggal awal");
        roundednew2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, 31));

        jLabel8.setFont(new java.awt.Font("Poppins", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(90, 142, 149));
        jLabel8.setText("Tanggal akhir");
        roundednew2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, -1, 31));

        button1.setBackground(new java.awt.Color(229, 225, 218));
        button1.setForeground(new java.awt.Color(90, 142, 149));
        button1.setText("KONFIMASI");
        button1.setColor(new java.awt.Color(229, 225, 218));
        button1.setColorborder(new java.awt.Color(229, 225, 218));
        button1.setColorclic(new java.awt.Color(229, 225, 218));
        button1.setColorover(new java.awt.Color(229, 225, 218));
        button1.setFont(new java.awt.Font("Poppins", 1, 20)); // NOI18N
        button1.setRadius(15);
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        roundednew2.add(button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 360, 50));
        roundednew2.add(jcomboboxcustom1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, 170, 40));
        roundednew2.add(jcomboboxcustom2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 170, 40));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(90, 142, 149));
        jLabel1.setText("Lihat total dari Laporan?");
        roundednew2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, -1, -1));

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
        roundednew3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, -1, 31));

        jLabel5.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(90, 142, 149));
        jLabel5.setText("Laporan Pengeluaran");
        roundednew3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, -1, 31));

        jLabel4.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(90, 142, 149));
        jLabel4.setText("Print");
        roundednew3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, -1, 31));

        jLabel6.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(90, 142, 149));
        jLabel6.setText("Laporan Stok");
        roundednew3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, -1, 31));

        button2.setRadius(25);
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        roundednew3.add(button2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 140, 220));

        button3.setRadius(25);
        roundednew3.add(button3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 220, 90));

        button4.setRadius(25);
        roundednew3.add(button4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 220, 90));

        add(roundednew3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 470, 350));
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
  
    }//GEN-LAST:event_button1ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Aset.button button1;
    private Aset.button button2;
    private Aset.button button3;
    private Aset.button button4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private Aset.Jcomboboxcustom jcomboboxcustom1;
    private Aset.Jcomboboxcustom jcomboboxcustom2;
    private javax.swing.JLabel judul;
    private Aset.roundednew roundednew1;
    private Aset.roundednew roundednew2;
    private Aset.roundednew roundednew3;
    // End of variables declaration//GEN-END:variables
}
