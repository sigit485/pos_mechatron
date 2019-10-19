/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel;

import View.cekdata;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.placeholder.PlaceHolder;
import java.awt.Component;
import java.awt.HeadlessException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author User
 */

 

public class barang extends javax.swing.JPanel {
    private Statement stat;
    private Connection con;
    private ResultSet res;
    private String t;
    private Component rootPane;
    private PreparedStatement pst;
    int baris;
    String[] dataPopup = new String[7];
    PlaceHolder holder;
    DefaultTableModel tabmod;
    cekdata cek = new cekdata();
    

    public barang(){
        initComponents();
        holder();
        koneksi();
        cmbValue();
        tabel();
    }
    
     private void koneksi(){
    try {
    Class.forName("com.mysql.jdbc.Driver");
    con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/penjualanarduino", "root", "");
    stat=(Statement) con.createStatement();
    } catch (ClassNotFoundException | SQLException e) {
    JOptionPane.showMessageDialog(null, e);
    }
    }
     
     private void holder(){
         holder = new PlaceHolder(berat, "Gram");
         holder = new PlaceHolder(txtcari, "Masukkan Nama Barang");
     }
     
       private void kosongkan()
       { 
       kd_barang.setText(""); 
       nama_barang.setText("");
       stok.setText("");
       berat.setText("");
       deskripsi.setText("");
       cmb_supplier.setSelectedItem("");
       harga.setText("");
       harga_beli.setText("");
        }
       
       private void tabel(){ 
    DefaultTableModel t= new DefaultTableModel();
     t.addColumn("kd_barang"); 
     t.addColumn("nama_barang"); 
     t.addColumn("stok");
     t.addColumn("berat"); 
     t.addColumn("deskripsi"); 
     t.addColumn("supplier"); 
     t.addColumn("harga"); 
     t.addColumn("harga_beli"); 
     tabel_barang.setModel(t); try{ res=stat.executeQuery("select * from barang order by kd_barang"); 
     while (res.next()) { 
     t.addRow(new Object[]{ res.getString("kd_barang"),
     res.getString("nama_barang"), 
     res.getString("stok"), 
     res.getString("berat"), 
     res.getString("deskripsi"), 
     res.getString("supplier"), 
     res.getString("harga"),
     res.getString("harga_beli")
     }); 
     }
     }catch (Exception e) { 
     JOptionPane.showMessageDialog(rootPane, e); 
     } 
    }
       
    public void setDataPopup(String[] datapopup){
        this.dataPopup = datapopup;

    }
    public String[] getDataPopup(){
        return dataPopup;
    } 
    
    private void cmbValue(){
        try{
            String sql = "select * from supplier";
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();
            
            while(res.next()){
                String name = res.getString("nama_supplier");
                cmb_supplier.addItem(name);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
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

        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        kd_barang = new javax.swing.JTextField();
        nama_barang = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        stok = new javax.swing.JTextField();
        berat = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        deskripsi = new javax.swing.JTextArea();
        cmb_supplier = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        harga = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        harga_beli = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_barang = new javax.swing.JTable();
        btedit = new javax.swing.JButton();
        bthapus = new javax.swing.JToggleButton();
        btsimpan = new javax.swing.JToggleButton();
        btncari = new javax.swing.JButton();
        txtcari = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(230, 230, 230));
        setOpaque(false);
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Bell Gothic Std Black", 0, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("PRODUCT");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, -1, -1));

        jLabel1.setText("Kode Barang");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));
        add(kd_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 275, -1));
        add(nama_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 275, -1));

        jLabel2.setText("Nama Barang");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        jLabel3.setText("Stok");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));
        add(stok, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 275, -1));
        add(berat, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 275, -1));

        jLabel4.setText("Berat");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));

        jLabel6.setText("Deskripsi");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, -1));

        deskripsi.setColumns(20);
        deskripsi.setRows(5);
        jScrollPane1.setViewportView(deskripsi);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, 275, -1));

        add(cmb_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 420, 280, -1));

        jLabel8.setText("Supplier");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, -1, -1));

        jLabel9.setText("Harga Jual");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 470, -1, -1));
        add(harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 470, 275, -1));

        jLabel10.setText("Harga Beli");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, -1, -1));
        add(harga_beli, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 510, 275, -1));

        tabel_barang.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tabel_barang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Kode barang", "Nama Barang", "Stok", "Berat", "Title 5", "Title 6", "Title 7", "null"
            }
        ));
        tabel_barang.setGridColor(new java.awt.Color(153, 153, 153));
        tabel_barang.setSelectionBackground(new java.awt.Color(0, 153, 51));
        tabel_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_barangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabel_barang);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 150, 559, 210));

        btedit.setText("UPDATE");
        btedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bteditActionPerformed(evt);
            }
        });
        add(btedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 370, -1, 30));

        bthapus.setText("DELETE");
        bthapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bthapusActionPerformed(evt);
            }
        });
        add(bthapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 370, -1, 30));

        btsimpan.setText("SAVE");
        btsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsimpanActionPerformed(evt);
            }
        });
        add(btsimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 370, -1, 30));

        btncari.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btncari.setText("CARI");
        btncari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncariActionPerformed(evt);
            }
        });
        add(btncari, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 110, 60, 30));
        add(txtcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 110, 170, 30));

        jButton1.setText("REFRESH");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 370, -1, 30));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/panel.png"))); // NOI18N
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void bteditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bteditActionPerformed
         try {
            stat.executeUpdate("update barang set "
                + "kd_barang='"+kd_barang.getText()+"',"
                + "nama_barang='"+nama_barang.getText()+"',"
                + "stok='"+stok.getText()+"',"
                + "berat='"+berat.getText()+"',"
                + "deskripsi='"+deskripsi.getText()+"',"
                + "supplier='"+cmb_supplier.getSelectedItem()+"',"
                + "harga='"+harga.getText()+"',"
                + "harga_beli='"+harga_beli.getText()+"'"
                + " where " + "kd_barang='"+kd_barang.getText()+"'" );
            kosongkan();
            JOptionPane.showMessageDialog(rootPane, "Data berhasil Di update");
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }finally{
         tabel();
        }     
    }//GEN-LAST:event_bteditActionPerformed

    private void bthapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bthapusActionPerformed
        try { 
        stat.executeUpdate("delete from barang where " 
        + "kd_barang='"+kd_barang.getText()
        +"'" ); 
        kosongkan();
        JOptionPane.showMessageDialog(null, "Berhasil");
        } catch (SQLException | HeadlessException e) { 
        JOptionPane.showMessageDialog(null, "pesan salah : "+e);
         } finally{
         tabel();
          }
    }//GEN-LAST:event_bthapusActionPerformed

    private void btsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsimpanActionPerformed
   try {
 stat.executeUpdate("insert into barang values (" 
+ "'" + kd_barang.getText()+"',"
+ "'" + nama_barang.getText()+"',"
+ "'" + stok.getText()+"',"
+ "'" + berat.getText()+"'," 
+ "'" + deskripsi.getText()+ "',"
+ "'" + cmb_supplier.getSelectedItem()+"',"
+ "'" + harga.getText()+ "'," 
+ "'" + harga_beli.getText()+ "')");
kosongkan(); 
JOptionPane.showMessageDialog(null, "Berhasil Menyimpan Data"); 
} catch (SQLException | HeadlessException e) { 
JOptionPane.showMessageDialog(null, "Perintah Salah : "+e);
}finally{
         tabel();       
 } 
    }//GEN-LAST:event_btsimpanActionPerformed

    private void tabel_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_barangMouseClicked
        baris = tabel_barang.getSelectedRow();
        kd_barang.setText(tabel_barang.getValueAt(baris,0).toString());
        nama_barang.setText(tabel_barang.getValueAt(baris,1).toString());
        stok.setText(tabel_barang.getValueAt(baris,2).toString());
        berat.setText(tabel_barang.getValueAt(baris,3).toString());
        deskripsi.setText(tabel_barang.getValueAt(baris,4).toString());
        cmb_supplier.setSelectedItem(tabel_barang.getValueAt(baris,5).toString());
        harga.setText(tabel_barang.getValueAt(baris,6).toString());
        harga_beli.setText(tabel_barang.getValueAt(baris,7).toString());
    }//GEN-LAST:event_tabel_barangMouseClicked

    private void btncariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncariActionPerformed
    
    DefaultTableModel tabelTampil1 = new DefaultTableModel();    
    tabelTampil1.addColumn("kd_barang");
    tabelTampil1.addColumn("nama_barang ");
    tabelTampil1.addColumn("stok");
    tabelTampil1.addColumn("berat");
    tabelTampil1.addColumn("deskripsi");
    tabelTampil1.addColumn("supplier");
    tabelTampil1.addColumn("harga");
    tabelTampil1.addColumn("harga_beli");
        try{
            koneksi();
            String sql = "Select * from barang where kd_barang like '%" + txtcari.getText() + "%'" +
            "or nama_barang like '%" + txtcari.getText() + "%'";
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();
            while (res.next()) {
            tabelTampil1.addRow(new Object[]{
            res.getString(1),
            res.getString(2),
            res.getString(3),
            res.getString(4),
            res.getString(5),
            res.getString(6),
            res.getString(7),
            res.getString(8)
            });
            }
            tabel_barang.setModel(tabelTampil1);

                }catch (Exception e){
                    
                }
    }//GEN-LAST:event_btncariActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       tabel();
       kosongkan();
    }//GEN-LAST:event_jButton1ActionPerformed

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField berat;
    private javax.swing.JButton btedit;
    private javax.swing.JToggleButton bthapus;
    private javax.swing.JButton btncari;
    private javax.swing.JToggleButton btsimpan;
    private javax.swing.JComboBox cmb_supplier;
    private javax.swing.JTextArea deskripsi;
    private javax.swing.JTextField harga;
    private javax.swing.JTextField harga_beli;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField kd_barang;
    private javax.swing.JTextField nama_barang;
    private javax.swing.JTextField stok;
    private javax.swing.JTable tabel_barang;
    private javax.swing.JTextField txtcari;
    // End of variables declaration//GEN-END:variables
}
   

