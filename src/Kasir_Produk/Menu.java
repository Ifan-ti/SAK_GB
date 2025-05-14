/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Kasir_Produk;

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

/**
 *
 * @author achmad ifan
 */
public class Menu extends roundednew {

    

    /**
     * Creates new form Menu
     */
    public Menu(String Name) {
        initComponents();
        setOpaque(false);
        txt_harga.setEditable(false);
        givkiBakery();
        loadnama();
        loadharga();
        Username = Name;
        
    }
    Connection conn = (Connection) Koneksi.koneksi();
    int Harga = 0;
    int total = 0;
    int total_diskon = 0;
    String id_pelanggan ="";
    String id_pesanan="";
    String id_pembayaran="";
    String Username="";
    int nomer = 0 ;
    ArrayList <String> hargaperproduk = new ArrayList() ;
    ArrayList <String> idProduk = new ArrayList() ;
    ArrayList <String> jumlahPesanan = new ArrayList();
    ArrayList <String> idPesanan = new ArrayList();
    
    
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

    
    
    private void ambilIdPelanggan() {
    String nilai = DataManager.getInstance().getNilai();
    id_pelanggan = nilai;
    }

    
    private void filterIdAuto( String Nama_Table, String id_database,String len, int nextid){
            if(Nama_Table.equals("pesanan")){
                    id_database = "PS"+len+nextid;
                    id_pesanan = id_database;
            }else if(Nama_Table.equals("pembayaran")){
                    id_database = "BY"+len+nextid;
                    id_pembayaran = id_database;
                
            }

                    
                   
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
                filterIdAuto(nama_tabel, id_database,"000", nextid);
                    
                }else if(nextid<=99){
                    filterIdAuto(nama_tabel, id_database,"00", nextid);
                }else if(nextid<= 999){
                    filterIdAuto(nama_tabel, id_database,"0", nextid);
                }else if(nextid<= 999){
                    filterIdAuto(nama_tabel,id_database,"", nextid);
                }   
            }
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }  
    public void givkiBakery() {
          
          new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                    java.util.Date date = new java.util.Date();
                    SimpleDateFormat tf = new SimpleDateFormat("h:mm:ss aa");
                    SimpleDateFormat df = new SimpleDateFormat("EEEE, yyyy-MM-dd");
                    String time = tf.format(date);        
          
                    txtarea_view.setText("*********************************************Givki Bakery**************************************************\n"
                    + "Waktu (wib): "+time.split(" ")[0]+" " + time.split(" ") [1] +"\t\t\t                   "+ " Tanggal: " + tanggal+ "\n"
                        +"************************************************************************************************************\n"
                        +"Nama Roti:\t\t\t            "+"Jumlah"+"\t            "+"Diskon"+"\t            " + "Total(Rp)\n");
            }
            }
          }).start();     
          }
    private boolean qtynol(int qty) {
        if (qty==0) {
            JOptionPane.showMessageDialog(null, "tolong pilih pesanan");
            return false;
        }else{
            
        return true;
        }
    }
    private void viewnota(){
        int qty = Integer.parseInt(txt_jumlah.getText());
        if (qtynol(qty) ) {
                nomer++;
           
           
             int harga_produk= Integer.parseInt(txt_harga.getText()) ;
            if(hari.equals(bulan)){
                int diskon = (int) (harga_produk*0.1);
                Harga= qty * (harga_produk-diskon) ;
                hargaperproduk.add(String.valueOf(Harga));
                total +=Harga;
                total_diskon += diskon*qty;
                String dis = Integer.toString(total_diskon);
                txt_totalharga.setText(dis);
                
            txtarea_view1.setText(txtarea_view1.getText()+nomer+". "+jc_produk.getSelectedItem()+ "\t\t\t            "+qty+"\t            "+diskon+"\t            " + Harga +"\n");

            }else{
                Harga = qty*harga_produk;
                hargaperproduk.add(String.valueOf(Harga));
                total += Harga;
                txtarea_view1.setText(txtarea_view1.getText()+nomer+". "+jc_produk.getSelectedItem()+ "\t\t\t            "+qty+"\t            "+"-"+"\t            " + Harga +"\n");

            }
               
            txt_viewharga.setText(String.valueOf(total));
            txt_diskon.setText(String.valueOf(total_diskon));
            txt_totalharga.setText(String.valueOf(total-total_diskon));
            jumlahPesanan.add(txt_jumlah.getText());
            
            
            
//            p1=qty;
//            js1.setValue(0);
//            totall1 = Integer.toString(totall);
//            
//            String pp = String.format("%,.2f", Double.parseDouble(totall1));
//            
//            txttotalhrg.setText(pp);
        }else {
//            bnc1.setSelected(false);
            
            
        } 
    }
    
    // Memuat data dari database yang akan di input pada j
    private void loadnama(){
        try {
            
            
            String sql = "SELECT Nama_produk FROM produk"; // Ganti dengan query Anda
            PreparedStatement st = conn.prepareStatement(sql);

            // 3. Menjalankan query dan mendapatkan hasilnya
            ResultSet rs = st.executeQuery();

            // 4. Mengiterasi hasil query dan menambahkannya ke JComboBox
            while (rs.next()) {
                String namaKolom = rs.getString("Nama_produk"); // Ganti nama_kolom
                jc_produk.addItem(namaKolom);
            }
            System.out.println("Data berhasil ditambahkan ke JComboBox.");

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal mengambil data dari database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }
    private void loadharga(){
        try {
            
            
            String sql = "SELECT Harga FROM produk WHERE Nama_produk = ?"; // Ganti dengan query Anda
            PreparedStatement st = conn.prepareStatement(sql);
            
            st.setString(1, (String) jc_produk.getSelectedItem());
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                String namaKolom = rs.getString("Harga"); // Ganti nama_kolom
                txt_harga.setText(namaKolom);
            }
            System.out.println("Data berhasil ditambahkan ke JComboBox.");

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal mengambil data dari database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }
    
    private void getIdProduk(){
        String sql = "SELECT Id_Produk FROM produk WHERE Nama_produk = ?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, (String) jc_produk.getSelectedItem());
            
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                idProduk.add(rs.getString("Id_Produk"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    
    
    private void tambahPesanan(){
       
        ambilIdPelanggan();
        for (int i = 0; i < jumlahPesanan.toArray().length; i++) {
            
        idaout("pesanan", "Id_Pesanan");
        String pesanan = "INSERT INTO pesanan (Id_Pesanan, Id_Produk, Jumlah_Pesanan, Tanggal_Pesanan, Tanggal_Selesai, Total_Harga, Status, Kasir) VALUE (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement st =conn.prepareStatement(pesanan);
            
            st.setString(1, id_pesanan);
            st.setString(2, idProduk.get(i));
            st.setString(3, jumlahPesanan.get(i));
            st.setString(4, tanggal);
            st.setString(5, tanggalSelesai);
            st.setString(6, hargaperproduk.get(i));
            st.setString(7, "proses");
            st.setString(8, Username);
            
            int row = st.executeUpdate();
            if(row>0){
                JOptionPane.showMessageDialog(null, "Pesanan berhasil");
                idPesanan.add(id_pesanan);
                
            }else{
                JOptionPane.showMessageDialog(null, "Pesanan gagal");
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "menyimpan pesanan error "+ex);
        }
        }
   }
    
    private void tambahPembayaran(){
        int txtHargaTotal = Integer.valueOf(txt_totalharga.getText());
        int txtUang = Integer.valueOf(txt_uang.getText());
        
        String sql = "INSERT INTO pembayaran (Id_Pembayaran,tanggal, Uang_Masuk, Diskon) VAlUE (?,?,?,?)";
        idaout("pembayaran", "Id_Pembayaran");
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            
            st.setString(1, id_pembayaran);
            st.setString(2, tanggal);
            st.setInt(3, txtHargaTotal-(txtHargaTotal-txtUang) );
            st.setString(4, txt_diskon.getText());
            
            int row = st.executeUpdate();
            if(row >0){
                JOptionPane.showMessageDialog(null, "Pembayaran berhasil");
            }else{
                JOptionPane.showMessageDialog(null, "Pembayaran Gagal");
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "menyimpan pembayaran error "+ex);

        }
    }
    private void tambahDetailPesanan(){
        String sql = "INSERT INTO detail_pesanan (Id_Pesanan,Id_Pelanggan, Id_Pembayaran) VAlUE (?,?,?)";
        ambilIdPelanggan();
        
        
        for (int i = 0; i < idPesanan.toArray().length; i++) {

            try {
                    PreparedStatement st = conn.prepareStatement(sql);

                    st.setString(1, idPesanan.get(i));
                    st.setString(2, id_pelanggan);
                    st.setString(3, id_pembayaran);

                    int row = st.executeUpdate();
                if(row >0){
                    JOptionPane.showMessageDialog(null, "berhasil");
                }else{
                    JOptionPane.showMessageDialog(null, "Gagal");
                
                }
            
            }catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "menyimpan detail error "+ex);
            }
        }
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundednew3 = new Aset.roundednew();
        judul = new javax.swing.JLabel();
        txt_jumlah = new Aset.RoundedTextField();
        jLabel3 = new javax.swing.JLabel();
        addpesanan = new Aset.button();
        jLabel2 = new javax.swing.JLabel();
        txt_harga = new Aset.RoundedTextField();
        jLabel8 = new javax.swing.JLabel();
        jc_produk = new Aset.Jcomboboxcustom();
        roundednew4 = new Aset.roundednew();
        button2 = new Aset.button();
        nota = new Aset.button();
        konfirmasi = new Aset.button();
        Update5 = new Aset.button();
        reset = new Aset.button();
        Update7 = new Aset.button();
        Update2 = new Aset.button();
        Update8 = new Aset.button();
        roundednew5 = new Aset.roundednew();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtarea_view = new javax.swing.JTextArea();
        txt_viewharga = new Aset.RoundedTextField();
        txt_diskon = new Aset.RoundedTextField();
        txt_uang = new Aset.RoundedTextField();
        txt_totalharga = new Aset.RoundedTextField();
        txt_kembalian = new Aset.RoundedTextField();
        txtarea_view1 = new javax.swing.JTextArea();

        setBackground(new java.awt.Color(242, 242, 242));
        setColorend(new java.awt.Color(242, 242, 242));
        setColorstar(new java.awt.Color(242, 242, 242));
        setRoundedkananatas(94);
        setRoundedkananbawah(94);
        setRoundedkiriatas(94);
        setRoundedkiribawah(94);
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        roundednew3.setColorend(new java.awt.Color(170, 215, 217));
        roundednew3.setColorstar(new java.awt.Color(170, 215, 217));
        roundednew3.setRoundedkananatas(50);
        roundednew3.setRoundedkananbawah(50);
        roundednew3.setRoundedkiriatas(50);
        roundednew3.setRoundedkiribawah(50);
        roundednew3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        judul.setFont(new java.awt.Font("Poppins", 1, 30)); // NOI18N
        judul.setForeground(new java.awt.Color(90, 142, 149));
        judul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        judul.setText("Menu");
        roundednew3.add(judul, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 490, 31));

        txt_jumlah.setBackground(new java.awt.Color(251, 249, 241));
        txt_jumlah.setBorder(null);
        txt_jumlah.setForeground(new java.awt.Color(153, 153, 153));
        txt_jumlah.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_jumlah.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        txt_jumlah.setRoundedkananatas(15);
        txt_jumlah.setRoundedkananbawah(15);
        txt_jumlah.setRoundedkiriatas(15);
        txt_jumlah.setRoundedkiribawah(15);
        roundednew3.add(txt_jumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 150, 50));

        jLabel3.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(90, 142, 149));
        jLabel3.setText("Jumlah");
        roundednew3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, 31));

        addpesanan.setBackground(new java.awt.Color(229, 225, 218));
        addpesanan.setForeground(new java.awt.Color(90, 142, 149));
        addpesanan.setText("KONFIMASI");
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
        roundednew3.add(addpesanan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 490, 50));

        jLabel2.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(90, 142, 149));
        jLabel2.setText("Nama Produk");
        roundednew3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, 31));

        txt_harga.setBackground(new java.awt.Color(251, 249, 241));
        txt_harga.setBorder(null);
        txt_harga.setForeground(new java.awt.Color(153, 153, 153));
        txt_harga.setCaretColor(new java.awt.Color(242, 242, 242));
        txt_harga.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        txt_harga.setRoundedkananatas(15);
        txt_harga.setRoundedkananbawah(15);
        txt_harga.setRoundedkiriatas(15);
        txt_harga.setRoundedkiribawah(15);
        roundednew3.add(txt_harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 310, 50));

        jLabel8.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(90, 142, 149));
        jLabel8.setText("Harga Produk");
        roundednew3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, -1, 31));

        jc_produk.setBackground(new java.awt.Color(251, 249, 241));
        jc_produk.setForeground(new java.awt.Color(153, 153, 153));
        jc_produk.setToolTipText("");
        jc_produk.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jc_produk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jc_produkActionPerformed(evt);
            }
        });
        roundednew3.add(jc_produk, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 490, 50));

        add(roundednew3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 550, 380));

        roundednew4.setColorend(new java.awt.Color(170, 215, 217));
        roundednew4.setColorstar(new java.awt.Color(170, 215, 217));
        roundednew4.setRoundedkananatas(50);
        roundednew4.setRoundedkananbawah(50);
        roundednew4.setRoundedkiriatas(50);
        roundednew4.setRoundedkiribawah(50);
        roundednew4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        button2.setForeground(new java.awt.Color(251, 249, 241));
        button2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Data_diri.png"))); // NOI18N
        button2.setText("Data Diri");
        button2.setColor(new java.awt.Color(170, 215, 217));
        button2.setColorborder(new java.awt.Color(170, 215, 217));
        button2.setColorclic(new java.awt.Color(170, 215, 217));
        button2.setColorover(new java.awt.Color(170, 215, 217));
        button2.setFont(new java.awt.Font("Poppins", 1, 20)); // NOI18N
        button2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        button2.setIconTextGap(10);
        button2.setRadius(30);
        button2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button2MouseExited(evt);
            }
        });
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        roundednew4.add(button2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 200, 80));

        nota.setForeground(new java.awt.Color(251, 249, 241));
        nota.setText("Nota");
        nota.setColor(new java.awt.Color(251, 249, 241));
        nota.setColorborder(new java.awt.Color(170, 215, 217));
        nota.setColorclic(new java.awt.Color(251, 249, 241));
        nota.setColorover(new java.awt.Color(251, 249, 241));
        nota.setFont(new java.awt.Font("Poppins", 1, 20)); // NOI18N
        nota.setRadius(30);
        nota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notaActionPerformed(evt);
            }
        });
        roundednew4.add(nota, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 200, 80));

        konfirmasi.setForeground(new java.awt.Color(251, 249, 241));
        konfirmasi.setText("Konfirmasi");
        konfirmasi.setColor(new java.awt.Color(251, 249, 241));
        konfirmasi.setColorborder(new java.awt.Color(170, 215, 217));
        konfirmasi.setColorclic(new java.awt.Color(251, 249, 241));
        konfirmasi.setColorover(new java.awt.Color(251, 249, 241));
        konfirmasi.setFont(new java.awt.Font("Poppins", 1, 20)); // NOI18N
        konfirmasi.setRadius(30);
        konfirmasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                konfirmasiActionPerformed(evt);
            }
        });
        roundednew4.add(konfirmasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(305, 40, 200, 80));

        Update5.setColor(new java.awt.Color(251, 249, 241));
        Update5.setColorborder(new java.awt.Color(251, 249, 241));
        Update5.setColorclic(new java.awt.Color(251, 249, 241));
        Update5.setColorover(new java.awt.Color(251, 249, 241));
        Update5.setFont(new java.awt.Font("Poppins", 1, 20)); // NOI18N
        Update5.setRadius(30);
        Update5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Update5ActionPerformed(evt);
            }
        });
        roundednew4.add(Update5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 240, 120));

        reset.setForeground(new java.awt.Color(251, 249, 241));
        reset.setText("Reset");
        reset.setColor(new java.awt.Color(251, 249, 241));
        reset.setColorborder(new java.awt.Color(170, 215, 217));
        reset.setColorclic(new java.awt.Color(251, 249, 241));
        reset.setColorover(new java.awt.Color(251, 249, 241));
        reset.setFont(new java.awt.Font("Poppins", 1, 20)); // NOI18N
        reset.setRadius(30);
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        roundednew4.add(reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(305, 180, 200, 80));

        Update7.setColor(new java.awt.Color(251, 249, 241));
        Update7.setColorborder(new java.awt.Color(251, 249, 241));
        Update7.setColorclic(new java.awt.Color(251, 249, 241));
        Update7.setColorover(new java.awt.Color(251, 249, 241));
        Update7.setFont(new java.awt.Font("Poppins", 1, 20)); // NOI18N
        Update7.setRadius(30);
        Update7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Update7ActionPerformed(evt);
            }
        });
        roundednew4.add(Update7, new org.netbeans.lib.awtextra.AbsoluteConstraints(285, 20, 240, 120));

        Update2.setColor(new java.awt.Color(251, 249, 241));
        Update2.setColorborder(new java.awt.Color(251, 249, 241));
        Update2.setColorclic(new java.awt.Color(251, 249, 241));
        Update2.setColorover(new java.awt.Color(251, 249, 241));
        Update2.setFont(new java.awt.Font("Poppins", 1, 20)); // NOI18N
        Update2.setRadius(30);
        Update2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Update2ActionPerformed(evt);
            }
        });
        roundednew4.add(Update2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 240, 120));

        Update8.setColor(new java.awt.Color(251, 249, 241));
        Update8.setColorborder(new java.awt.Color(251, 249, 241));
        Update8.setColorclic(new java.awt.Color(251, 249, 241));
        Update8.setColorover(new java.awt.Color(251, 249, 241));
        Update8.setFont(new java.awt.Font("Poppins", 1, 20)); // NOI18N
        Update8.setRadius(30);
        Update8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Update8ActionPerformed(evt);
            }
        });
        roundednew4.add(Update8, new org.netbeans.lib.awtextra.AbsoluteConstraints(285, 160, 240, 120));

        add(roundednew4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, 550, 300));

        roundednew5.setColorend(new java.awt.Color(170, 215, 217));
        roundednew5.setColorstar(new java.awt.Color(170, 215, 217));
        roundednew5.setRoundedkananatas(50);
        roundednew5.setRoundedkananbawah(50);
        roundednew5.setRoundedkiriatas(50);
        roundednew5.setRoundedkiribawah(50);
        roundednew5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Poppins", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(90, 142, 149));
        jLabel1.setText("Diskon");
        roundednew5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 460, -1, 31));

        jLabel4.setFont(new java.awt.Font("Poppins", 0, 30)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(90, 142, 149));
        jLabel4.setText("Harga");
        roundednew5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, -1, 31));

        jLabel5.setFont(new java.awt.Font("Poppins", 0, 30)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(90, 142, 149));
        jLabel5.setText("Total Harga");
        roundednew5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 520, -1, 31));

        jLabel6.setFont(new java.awt.Font("Poppins", 0, 30)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(90, 142, 149));
        jLabel6.setText("Uang bayar");
        roundednew5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 580, -1, 31));

        jLabel7.setFont(new java.awt.Font("Poppins", 0, 30)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(90, 142, 149));
        jLabel7.setText("Kembalian");
        roundednew5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 640, -1, 31));

        txtarea_view.setEditable(false);
        txtarea_view.setBackground(new java.awt.Color(251, 249, 241));
        txtarea_view.setColumns(20);
        txtarea_view.setForeground(new java.awt.Color(153, 153, 153));
        txtarea_view.setRows(5);
        txtarea_view.setAutoscrolls(false);
        roundednew5.add(txtarea_view, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 520, 80));

        txt_viewharga.setBackground(new java.awt.Color(251, 249, 241));
        txt_viewharga.setBorder(null);
        txt_viewharga.setForeground(new java.awt.Color(153, 153, 153));
        txt_viewharga.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_viewharga.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        txt_viewharga.setInheritsPopupMenu(true);
        txt_viewharga.setRoundedkananatas(15);
        txt_viewharga.setRoundedkananbawah(15);
        txt_viewharga.setRoundedkiriatas(15);
        txt_viewharga.setRoundedkiribawah(15);
        roundednew5.add(txt_viewharga, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 395, 200, 40));

        txt_diskon.setBackground(new java.awt.Color(251, 249, 241));
        txt_diskon.setBorder(null);
        txt_diskon.setForeground(new java.awt.Color(153, 153, 153));
        txt_diskon.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_diskon.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        txt_diskon.setInheritsPopupMenu(true);
        txt_diskon.setRoundedkananatas(15);
        txt_diskon.setRoundedkananbawah(15);
        txt_diskon.setRoundedkiriatas(15);
        txt_diskon.setRoundedkiribawah(15);
        roundednew5.add(txt_diskon, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 455, 200, 40));

        txt_uang.setBackground(new java.awt.Color(251, 249, 241));
        txt_uang.setBorder(null);
        txt_uang.setForeground(new java.awt.Color(153, 153, 153));
        txt_uang.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_uang.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        txt_uang.setInheritsPopupMenu(true);
        txt_uang.setRoundedkananatas(15);
        txt_uang.setRoundedkananbawah(15);
        txt_uang.setRoundedkiriatas(15);
        txt_uang.setRoundedkiribawah(15);
        roundednew5.add(txt_uang, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 575, 200, 40));

        txt_totalharga.setBackground(new java.awt.Color(251, 249, 241));
        txt_totalharga.setBorder(null);
        txt_totalharga.setForeground(new java.awt.Color(153, 153, 153));
        txt_totalharga.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_totalharga.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        txt_totalharga.setInheritsPopupMenu(true);
        txt_totalharga.setRoundedkananatas(15);
        txt_totalharga.setRoundedkananbawah(15);
        txt_totalharga.setRoundedkiriatas(15);
        txt_totalharga.setRoundedkiribawah(15);
        roundednew5.add(txt_totalharga, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 515, 200, 40));

        txt_kembalian.setBackground(new java.awt.Color(251, 249, 241));
        txt_kembalian.setBorder(null);
        txt_kembalian.setForeground(new java.awt.Color(153, 153, 153));
        txt_kembalian.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_kembalian.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        txt_kembalian.setInheritsPopupMenu(true);
        txt_kembalian.setRoundedkananatas(15);
        txt_kembalian.setRoundedkananbawah(15);
        txt_kembalian.setRoundedkiriatas(15);
        txt_kembalian.setRoundedkiribawah(15);
        roundednew5.add(txt_kembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 635, 200, 40));

        txtarea_view1.setEditable(false);
        txtarea_view1.setBackground(new java.awt.Color(251, 249, 241));
        txtarea_view1.setColumns(20);
        txtarea_view1.setForeground(new java.awt.Color(153, 153, 153));
        txtarea_view1.setRows(5);
        txtarea_view1.setAutoscrolls(false);
        roundednew5.add(txtarea_view1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 520, 280));

        add(roundednew5, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 40, 560, 700));
    }// </editor-fold>//GEN-END:initComponents

    private void addpesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addpesananActionPerformed
        viewnota();
        getIdProduk();
        
    }//GEN-LAST:event_addpesananActionPerformed
    String id;
    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
       GlassPanePopup.showPopup(new datadiri());
       
       
    }//GEN-LAST:event_button2ActionPerformed

    private void konfirmasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_konfirmasiActionPerformed
        ambilIdPelanggan();
        if(txt_uang.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Masukan Uang Pembayran");
        }else{
          int Harga = Integer.parseInt(txt_totalharga.getText()   );
            int uang = Integer.parseInt(txt_uang.getText()   );
            if(Harga>uang){
              JOptionPane.showMessageDialog(null, "Maaf uang pembayaran kurang, silahkan di tambahkan, karna toko ini tidak menerima bon!");
            }else{ 
                if(!id_pelanggan.equals("")){
                txt_kembalian.setText(String.valueOf(uang-Harga));

                tambahPesanan();
                tambahPembayaran();
                tambahDetailPesanan();
                }else{
                JOptionPane.showMessageDialog(null, "Data Diri Pelanggan Belum Di Isi, Silahkan Di isi");
                }
            }
        }
    }//GEN-LAST:event_konfirmasiActionPerformed

    private void Update5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Update5ActionPerformed
        
    }//GEN-LAST:event_Update5ActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        txtarea_view1.setText(null);
        nomer = 0;
        total = 0;
        total_diskon = 0;
        txt_totalharga.setText(null);
        txt_viewharga.setText(null);
        txt_diskon.setText(null);
        txt_uang.setText(null);
        txt_kembalian.setText(null);
    }//GEN-LAST:event_resetActionPerformed

    private void button2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button2MouseEntered
        Color p = new Color(229,225,218);
        button2.setColorborder(p);
    }//GEN-LAST:event_button2MouseEntered

    private void button2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button2MouseClicked
       Color p = new Color(229,225,218);
        button2.setColorborder(p);
        // TODO add your handling code here:
    }//GEN-LAST:event_button2MouseClicked

    private void button2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button2MouseExited
        Color p = new Color(170,215,217);
        button2.setColorborder(p);
    }//GEN-LAST:event_button2MouseExited

    private void Update7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Update7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Update7ActionPerformed

    private void Update2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Update2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Update2ActionPerformed

    private void Update8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Update8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Update8ActionPerformed

    private void jc_produkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jc_produkActionPerformed
    loadharga();
    }//GEN-LAST:event_jc_produkActionPerformed

    private void notaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notaActionPerformed
     
        GlassPanePopup.showPopup(new Nota());

//        idaout("pesanan", "Id_Pesanan");
//        
//        ambilIdPelanggan();
//        JOptionPane.showMessageDialog(null, id_pesanan);
////        ambilIdUser();
//        
//        JOptionPane.showMessageDialog(null, id_pelanggan);
    }//GEN-LAST:event_notaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Aset.button Update2;
    private Aset.button Update5;
    private Aset.button Update7;
    private Aset.button Update8;
    private Aset.button addpesanan;
    private Aset.button button2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private Aset.Jcomboboxcustom jc_produk;
    private javax.swing.JLabel judul;
    private Aset.button konfirmasi;
    private Aset.button nota;
    private Aset.button reset;
    private Aset.roundednew roundednew3;
    private Aset.roundednew roundednew4;
    private Aset.roundednew roundednew5;
    private Aset.RoundedTextField txt_diskon;
    private Aset.RoundedTextField txt_harga;
    private Aset.RoundedTextField txt_jumlah;
    private Aset.RoundedTextField txt_kembalian;
    private Aset.RoundedTextField txt_totalharga;
    private Aset.RoundedTextField txt_uang;
    private Aset.RoundedTextField txt_viewharga;
    private javax.swing.JTextArea txtarea_view;
    private javax.swing.JTextArea txtarea_view1;
    // End of variables declaration//GEN-END:variables
}
