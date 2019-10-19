/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import PopUp.pop_up_barang;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author FB
 */
public class FrmJual extends javax.swing.JFrame {
private DefaultTableModel model;
 private com.mysql.jdbc.Statement stat;
    private com.mysql.jdbc.Connection con;
    private ResultSet res;
    private String t;
    private PreparedStatement p;
    String[] dataPopup = new String[7];



    /**
     * Creates new form FrmJual
     */
    public FrmJual() {
        initComponents();
        setLocationRelativeTo(null);
        koneksi();
        combo_pelanggan();
        auto_key();
//        cari_nama();
        lihat_tabel();
        txtNofa.disable();
        
        //variabel pembantu di tutup
        TxtIdPelanggan.hide();
        TxtStock.hide();
        TxtDateTime.hide();
        
        model =new DefaultTableModel();
        TblDetail.setModel(model);
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Harga");
        model.addColumn("Qty");
        model.addColumn("Sub Total");
        model.addColumn("Jual Time");
        
        //START fungsi tidak menampilkan column ID barang(0) dan jual time (5)
        TblDetail.getColumnModel().getColumn(5).setMinWidth(0);
        TblDetail.getColumnModel().getColumn(5).setMaxWidth(0);
        TblDetail.getColumnModel().getColumn(5).setWidth(0);
        
        TblDetail.getColumnModel().getColumn(0).setMinWidth(0);
        TblDetail.getColumnModel().getColumn(0).setMaxWidth(0);
        TblDetail.getColumnModel().getColumn(0).setWidth(0);
        
        loadData();
        Date date = new Date();
        JdateJual.setDate(date);
    }
    
    private void koneksi(){
    try {
    Class.forName("com.mysql.jdbc.Driver");
    con = (com.mysql.jdbc.Connection) (Connection) DriverManager.getConnection("jdbc:mysql://localhost/penjualanarduino", "root", "");
    stat=(com.mysql.jdbc.Statement) (Statement) con.createStatement();
    } catch (ClassNotFoundException | SQLException e) {
    JOptionPane.showMessageDialog(null, e);
    }
    }
    
    
    

   public void Batal(){
    int x, y, z;
    x = Integer.parseInt(TxtStock.getText());
    y = Integer.parseInt(TxtQty.getText());
    z = x+y;
    
    String Barang_ID=this.TxtKode.getText();
     try{
//       Connection c= ClassKoneksi.getkoneksi();  
       String sql ="UPDATE barang set stok=? WHERE kd_barang=?";  
       p = con.prepareStatement(sql);
           p.setInt(1,z);
           p.setString(2,Barang_ID);//yang kode atau id letakkan di nomor terakhir  
           p.executeUpdate();  
           p.close();  
     }catch(SQLException e){  
       System.out.println("Terjadi Kesalahan");  
     }finally{   
       //JOptionPane.showMessageDialog(this,"Stock barang telah di update Diubah");  
     }
     
     
     
     //Proses mengahapus data dari tabel detail
     try {
//        Connection c= ClassKoneksi.getkoneksi();
        String sql="DELETE From t_jual WHERE jual_nofa='"+this.txtNofa.getText()+"' AND  jual_tgl ='"+this.TxtDateTime.getText()+"'";
        p = con.prepareStatement(sql);
        p.executeUpdate();
        p.close();
    }catch(SQLException e){
        System.out.println("Terjadi Kesalahan");
    }finally{
        loadData();
        JOptionPane.showMessageDialog(this,"Sukses Hapus Data...");
    }  
   }
   
    
   public void Cari_Kode(){
   int i=TblDetail.getSelectedRow();  
   if(i==-1)  
   { return; }  
   String ID=(String)model.getValueAt(i, 0); 
   TxtKode.setText(ID);
   }
    
    
   public void ShowData(){
   try {
//        Connection c=ClassKoneksi.getkoneksi();
        String sql="Select * from t_jual, barang WHERE t_jual.jual_barangid = barang.kd_barang AND t_jual.jual_barangid='"+this.TxtKode.getText()+"'"; 
//        Statement stat = ClassKoneksi.getkoneksi().createStatement();
        res = stat.executeQuery(sql);
        while(res.next()){
        this.TxtQty.setText(res.getString("jual_qty"));
        this.TxtNama.setText(res.getString("nama_barang"));
        this.TxtHJual.setText(res.getString("jual_harga"));
        this.TxtSubTotal.setText(res.getString("jual_subtotal"));
        this.TxtDateTime.setText(res.getString("jual_tgl"));
        }
   //     res.close(); stat.close();
   }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
 }

   
   public void ShowSisa(){
   try {
//        Connection c=ClassKoneksi.getkoneksi();
        String sql="Select * from  barang WHERE kd_barang ='"+this.TxtKode.getText()+"'"; 
//        Statement st = ClassKoneksi.getkoneksi().createStatement();
        res = stat.executeQuery(sql);
        while(res.next()){
        this.TxtStock.setText(res.getString("stok"));      
        }
   //     res.close(); stat.close();
   }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
 } 

  
    
public void UpdateStock(){
    int x, y, z;
    x = Integer.parseInt(TxtStock.getText());
    y = Integer.parseInt(TxtQty.getText());
    z = x-y;
    
    String Barang_ID=this.TxtKode.getText();
     try{
//       Connection c= ClassKoneksi.getkoneksi();  
       String sql ="UPDATE barang set stok=? WHERE kd_barang=?";  
        p= con.prepareStatement(sql);  
           p.setInt(1,z);
           p.setString(2,Barang_ID);//yang kode atau id letakkan di nomor terakhir  
           p.executeUpdate();  
           p.close();  
     }catch(SQLException e){  
       System.out.println("Terjadi Kesalahan");  
     }finally{   
       //JOptionPane.showMessageDialog(this,"Stock barang telah di update Diubah");  
     }
}
    
    
   public final void loadData(){
   model.getDataVector().removeAllElements();
   model.fireTableDataChanged();
   try{  
//     Connection c= ClassKoneksi.getkoneksi();
        stat= (com.mysql.jdbc.Statement) con.createStatement();
     String sql="Select * from t_jual, barang WHERE t_jual.jual_barangid = barang.kd_barang AND t_jual.jual_nofa='"+this.txtNofa.getText()+"'"; 
     res =stat.executeQuery(sql);
     while(res.next()){
       Object[]o=new Object[6];
       o[0]=res.getString("jual_barangid");
       o[1]=res.getString("nama_barang");
       o[2]=res.getString("jual_harga");
       o[3]=res.getString("jual_qty");
       o[4]=res.getString("jual_subtotal");
       o[5]=res.getString("stok");
       model.addRow(o);
     }  
//     res.close();  
//     stat.close();  
     //ShowData();  
   }catch(SQLException e){  
     System.out.println("Terjadi Kesalahan");  
   }



   //menjumlahkan isi colom ke 4 dalam tabel
   int total = 0;
   for (int i =0; i< TblDetail.getRowCount(); i++){
       int amount = Integer.parseInt((String)TblDetail.getValueAt(i, 4));
       total += amount;
   }
   TxtTotal.setText(""+total);
 }  
    
    public void AutoSum() {     
        int a, b, c;
        a = Integer.parseInt(TxtHJual.getText());
        b = Integer.parseInt(TxtQty.getText());
        c = a*b;
        TxtSubTotal.setText(""+c);
    }
    
    
        public void HitungKembali() {     
        int d, e, f;
        d = Integer.parseInt(TxtTotal.getText());
        e = Integer.parseInt(TxtCash.getText());
        f = e-d;
        TxtKembali.setText(""+f);
    }
        
        
        
        
    public void auto_key(){  
   try {  
   java.util.Date tgl = new java.util.Date();  
   java.text.SimpleDateFormat kal = new java.text.SimpleDateFormat("yyMMdd");  
   java.text.SimpleDateFormat tanggal = new java.text.SimpleDateFormat("yyyyMMdd");  
//     Connection c=ClassKoneksi.getkoneksi();  
     String sql = "select max(jual_nofa) from t_jual WHERE jual_tgl ="+tanggal.format(tgl);   
      p = con.prepareStatement(sql);
      res = p.executeQuery();
     while(res.next()){  
     Long a =res.getLong(1); //mengambil nilai tertinggi  
       if(a == 0){  
         this.txtNofa.setText(kal.format(tgl)+"0000"+(a+1));  
       }else{  
         this.txtNofa.setText(""+(a+1));  
       }  
   }  
//   res.close(); 
//   stat.close();
   }  
   catch (Exception e) {  
       JOptionPane.showMessageDialog(null, e);  
   }  
 }  
   
    


   
    public void Selesai(){
   try{  
//ini coba
stat.executeUpdate("update t_jual set "
                + "jual_total='"+TxtTotal.getText()+"',"
                + "jual_cash='"+TxtCash.getText()+"',"
                + "jual_kembali='"+TxtKembali.getText()+"'"
                + " where " + "jual_nofa='"+txtNofa.getText()+"'" );
//            bersih();
            JOptionPane.showMessageDialog(rootPane, "Data berhasil disimpan");
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }finally{
        }
  auto_key();
  loadData();
 }  
    

   public void TambahDetail(){
   Date HariSekarang = new Date( );
   SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
      
   String jual_nofa =this.txtNofa.getText(); 
   String jual_pelanggan=this.cmbPelanggan.getSelectedItem().toString();
   String jual_total=this.TxtTotal.getText();
   String jual_cash =this.TxtCash.getText();
   String jual_kembali =this.TxtKembali.getText();
   String jual_barangid =this.TxtKode.getText();  
   String jual_harga=this.TxtHJual.getText();  
   String jual_qty=this.TxtQty.getText();
   String jual_subtotal =this.TxtSubTotal.getText();
   String jual_namabarang =this.TxtNama.getText();
   String jual_tgl = ft.format(HariSekarang);
   
   
   try{  
//     Connection c=ClassKoneksi.getkoneksi();  
     String sql="Insert into t_jual (jual_nofa,jual_tgl,jual_pelanggan,jual_total,jual_cash,jual_kembali,jual_barangid,jual_harga,jual_qty,jual_subtotal,jual_nama_barang) values (?,?,?,?,?,?,?,?,?,?,?)";  
     p=con.prepareStatement(sql);  
     p.setString(1,jual_nofa);
     p.setString(2,jual_tgl);
     p.setString(3,jual_pelanggan);
     p.setString(4,jual_total);
     p.setString(5,jual_cash);
     p.setString(6,jual_kembali);
     p.setString(7,jual_barangid);
     p.setString(8,jual_harga);
     p.setString(9,jual_qty);
     p.setString(10,jual_subtotal);
     p.setString(11,jual_namabarang);
     p.executeUpdate();
     p.close();
   }catch(SQLException e){ 
   System.out.println(e);  
   }finally{  
   //loadData();
       //JOptionPane.showMessageDialog(this,"Data Telah Tersimpan");  
  }
 }
    
    


    public void cari_id(){
        try {
        String sql = "select * from barang where " + "kd_barang='" +TxtKode.getText()+"'"; 
        p = con.prepareStatement(sql);
        res = p.executeQuery();
        
        while(res.next()){
        this.TxtNama.setText(res.getString("nama_barang"));
        this.TxtHJual.setText(res.getString("harga"));
        this.TxtStock.setText(res.getString("stok"));
        }
        //res.close(); stat.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
}

    public void cari_nama()
    {
        try {
//        Connection c=ClassKoneksi.getkoneksi();
        String sql_t = "select kd_pelanggan from pelanggan where nama_pelanggan='"+cmbPelanggan.getSelectedItem()+"'"; 
//        Statement st = ClassKoneksi.getkoneksi().createStatement();
          p = con.prepareStatement(sql_t);
          res = p.executeQuery();                              // yang anda ingin kan
        
        while(res.next()){
            this.TxtIdPelanggan.setText(res.getString("nama_pelanggan"));
//        String name = res.getString("nama_pelanggan");
//                cmbPelanggan.addItem(name);
        }
        //res.close(); stat.close();
         
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }     
    }
    
    
    public  void bersihkan(){
        TxtKode.setText("");
        cmbPelanggan.setSelectedItem("");
        TxtNama.setText("");
        TxtHJual.setText("");
        TxtQty.setText("");
        TxtCash.setText("");
        TxtSubTotal.setText("");
        TxtKembali.setText("");
       
    }
    
    
    
    
    
    public void combo_pelanggan()
    {
        try {
//        Connection c=ClassKoneksi.getkoneksi();
         stat = (com.mysql.jdbc.Statement) con.createStatement();
        String sql_tc = "select nama_pelanggan from pelanggan order by kd_pelanggan asc";
        res = stat.executeQuery(sql_tc);

        while(res.next()){
            String nama = res.getString("nama_pelanggan");
            cmbPelanggan.addItem(nama);
        }
        //res.close(); stat.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void lihat_tabel(){
     DefaultTableModel t= new DefaultTableModel();
     t.addColumn("jual_nofa"); 
     t.addColumn("jual_tgl"); 
     t.addColumn("jual_pelanggan"); 
     t.addColumn("jual_total");
     t.addColumn("jual_cash"); 
     t.addColumn("jual_kembali"); 
     t.addColumn("jual_barangid"); 
     t.addColumn("jual_harga");
     t.addColumn("jual_qty");
     t.addColumn("jual_subtotal");
     t.addColumn("jual_nama_barang");
     tbl_jual.setModel(t); try{ res=stat.executeQuery("select * from t_jual order by jual_tgl desc"); 
     while (res.next()) { 
     t.addRow(new Object[]{ res.getString("jual_nofa"),
     res.getString("jual_tgl"), 
     res.getString("jual_pelanggan"), 
     res.getString("jual_total"), 
     res.getString("jual_cash"), 
     res.getString("jual_kembali"), 
     res.getString("jual_barangid"), 
     res.getString("jual_harga"),
     res.getString("jual_qty"),
     res.getString("jual_subtotal"),
     res.getString("jual_nama_barang")
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNofa = new javax.swing.JTextField();
        JdateJual = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        BtnSimpan = new javax.swing.JButton();
        BtnBatal = new javax.swing.JButton();
        BtnAdd = new javax.swing.JButton();
        cmbPelanggan = new javax.swing.JComboBox<String>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        TxtKode = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        TxtNama = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        TxtHJual = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        TxtQty = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        TxtSubTotal = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblDetail = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        TxtTotal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        TxtCash = new javax.swing.JTextField();
        TxtKembali = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        TxtIdPelanggan = new javax.swing.JTextField();
        TxtStock = new javax.swing.JTextField();
        TxtDateTime = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btn_cetak = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        ctk_faktur = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_jual = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PENJUALAN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(281, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(275, 275, 275))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 50));

        jLabel2.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel2.setText("Nomor Faktur");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        txtNofa.setName(""); // NOI18N
        txtNofa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNofaActionPerformed(evt);
            }
        });
        txtNofa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNofaKeyPressed(evt);
            }
        });
        getContentPane().add(txtNofa, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 150, -1));

        JdateJual.setDateFormatString("dd MMM, yyyy");
        JdateJual.setMaxSelectableDate(new java.util.Date(253370743298000L));
        JdateJual.setMinSelectableDate(new java.util.Date(-62135791102000L));
        getContentPane().add(JdateJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 148, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel3.setText("Tanggal");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        BtnSimpan.setText("Simpan");
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        getContentPane().add(BtnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 380, -1, -1));

        BtnBatal.setText("Batal");
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        getContentPane().add(BtnBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 140, 60, -1));

        BtnAdd.setText("ADD");
        BtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAddActionPerformed(evt);
            }
        });
        getContentPane().add(BtnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 170, 60, -1));

        cmbPelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbPelangganMouseClicked(evt);
            }
        });
        cmbPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPelangganActionPerformed(evt);
            }
        });
        getContentPane().add(cmbPelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 150, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel4.setText("Pelanggan");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel5.setText("Kode Barang");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, -1, -1));

        TxtKode.setName(""); // NOI18N
        TxtKode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtKodeActionPerformed(evt);
            }
        });
        TxtKode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtKodeKeyPressed(evt);
            }
        });
        getContentPane().add(TxtKode, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 120, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel7.setText("Nama");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, -1, -1));

        TxtNama.setName(""); // NOI18N
        TxtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNamaActionPerformed(evt);
            }
        });
        TxtNama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtNamaKeyPressed(evt);
            }
        });
        getContentPane().add(TxtNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 170, 120, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel8.setText("Harga Jual");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 150, -1, -1));

        TxtHJual.setName(""); // NOI18N
        TxtHJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtHJualActionPerformed(evt);
            }
        });
        TxtHJual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtHJualKeyPressed(evt);
            }
        });
        getContentPane().add(TxtHJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 170, 120, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel9.setText("Qty");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 150, -1, -1));

        TxtQty.setText("1");
        TxtQty.setName(""); // NOI18N
        TxtQty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TxtQtyMouseClicked(evt);
            }
        });
        TxtQty.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                TxtQtyPropertyChange(evt);
            }
        });
        TxtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtQtyKeyPressed(evt);
            }
        });
        getContentPane().add(TxtQty, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 170, 50, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel10.setText("Sub Total");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 150, -1, -1));

        TxtSubTotal.setName(""); // NOI18N
        TxtSubTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtSubTotalActionPerformed(evt);
            }
        });
        TxtSubTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtSubTotalKeyPressed(evt);
            }
        });
        getContentPane().add(TxtSubTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 170, 110, -1));

        TblDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Barang", "Nama  Barang", "Harga", "Qty", "Sub Total", "jual_time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblDetail.getTableHeader().setReorderingAllowed(false);
        TblDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblDetailMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TblDetail);
        if (TblDetail.getColumnModel().getColumnCount() > 0) {
            TblDetail.getColumnModel().getColumn(0).setMinWidth(0);
            TblDetail.getColumnModel().getColumn(0).setPreferredWidth(0);
            TblDetail.getColumnModel().getColumn(0).setMaxWidth(0);
            TblDetail.getColumnModel().getColumn(1).setResizable(false);
            TblDetail.getColumnModel().getColumn(2).setResizable(false);
            TblDetail.getColumnModel().getColumn(3).setResizable(false);
            TblDetail.getColumnModel().getColumn(4).setResizable(false);
            TblDetail.getColumnModel().getColumn(5).setMinWidth(0);
            TblDetail.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 630, 130));

        jLabel6.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel6.setText("Total Pembelian (Rp)");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 50, -1, -1));

        TxtTotal.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        TxtTotal.setName(""); // NOI18N
        TxtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtTotalActionPerformed(evt);
            }
        });
        TxtTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtTotalKeyPressed(evt);
            }
        });
        getContentPane().add(TxtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, 430, 70));

        jLabel11.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel11.setText("Cash");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, -1, -1));

        TxtCash.setName(""); // NOI18N
        TxtCash.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtCashKeyPressed(evt);
            }
        });
        getContentPane().add(TxtCash, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 350, 150, -1));

        TxtKembali.setName(""); // NOI18N
        getContentPane().add(TxtKembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 380, 150, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel13.setText("Kembali");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, -1, -1));

        TxtIdPelanggan.setName(""); // NOI18N
        TxtIdPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtIdPelangganActionPerformed(evt);
            }
        });
        TxtIdPelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtIdPelangganKeyPressed(evt);
            }
        });
        getContentPane().add(TxtIdPelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 380, 206, -1));
        getContentPane().add(TxtStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 350, 206, -1));
        getContentPane().add(TxtDateTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 410, 206, -1));

        jButton1.setText("Keluar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 380, -1, -1));

        jButton2.setText("Cari");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, -1, -1));

        btn_cetak.setText("Cetak");
        btn_cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cetakActionPerformed(evt);
            }
        });
        getContentPane().add(btn_cetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 430, -1, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI Light", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Cetak No.Faktur");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 100, 20));
        getContentPane().add(ctk_faktur, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 430, 140, -1));

        tbl_jual.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_jual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_jualMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_jual);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 760, 140));

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 780, 210));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNofaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNofaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNofaActionPerformed

    private void txtNofaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNofaKeyPressed

    }//GEN-LAST:event_txtNofaKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        // TODO add your handling code here:
        Selesai();
        bersihkan();
        lihat_tabel();
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        Batal();
        bersihkan();
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAddActionPerformed
       TambahDetail();
       UpdateStock();
       loadData();
       bersihkan();
       
    }//GEN-LAST:event_BtnAddActionPerformed

    private void cmbPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPelangganActionPerformed
        // TODO add your handling code here:
//        cari_nama();
    }//GEN-LAST:event_cmbPelangganActionPerformed

    private void TxtKodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtKodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtKodeActionPerformed

    private void TxtKodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtKodeKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {     
            cari_id();
        }
    }//GEN-LAST:event_TxtKodeKeyPressed

    private void TxtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNamaActionPerformed

    private void TxtNamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtNamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNamaKeyPressed

    private void TxtHJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtHJualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtHJualActionPerformed

    private void TxtHJualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtHJualKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtHJualKeyPressed

    private void TxtQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtQtyKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {  
        AutoSum();
        }
    }//GEN-LAST:event_TxtQtyKeyPressed

    private void TxtSubTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtSubTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtSubTotalActionPerformed

    private void TxtSubTotalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtSubTotalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtSubTotalKeyPressed

    private void TblDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblDetailMouseClicked
        this.Cari_Kode();
        this.ShowData();
        this.ShowSisa();
    }//GEN-LAST:event_TblDetailMouseClicked

    private void TxtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtTotalActionPerformed

    private void TxtTotalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtTotalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtTotalKeyPressed

    private void TxtQtyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtQtyMouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_TxtQtyMouseClicked

    private void TxtQtyPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_TxtQtyPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtQtyPropertyChange

    private void TxtCashKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtCashKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {  
        HitungKembali();
        }
    }//GEN-LAST:event_TxtCashKeyPressed

    private void TxtIdPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtIdPelangganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtIdPelangganActionPerformed

    private void TxtIdPelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtIdPelangganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtIdPelangganKeyPressed

    private void cmbPelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbPelangganMouseClicked
        // TODO add your handling code here:
        
//        cari_nama();
        
    }//GEN-LAST:event_cmbPelangganMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
     java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                pop_up_barang dialog = new pop_up_barang(new javax.swing.JFrame(), true);
                dialog.setVisible(true);

                TxtKode.setText(dialog.getData()[0]);
                TxtNama.setText(dialog.getData()[1]);
                TxtHJual.setText(dialog.getData()[3]);
                
           

            }
        });
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cetakActionPerformed
try{
            String namaFile = "src/Report/struk.jasper";
            Connection conn =DriverManager.getConnection("jdbc:mysql://localhost/penjualanarduino", "root", "");
            stat = (com.mysql.jdbc.Statement) con.createStatement();
            HashMap parameter = new HashMap();
            
            parameter.put("kode",ctk_faktur.getText());
            File report_file = new File(namaFile);
            net.sf.jasperreports.engine.JasperReport jasperreport = (net.sf.jasperreports.engine.JasperReport) JRLoader.loadObject(report_file.getPath());
            JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport, parameter, conn);
            JasperViewer.viewReport(jasperprint, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        }catch (Exception e){
        JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }//GEN-LAST:event_btn_cetakActionPerformed

    private void tbl_jualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_jualMouseClicked
        int baris;
        baris = tbl_jual.getSelectedRow();
        ctk_faktur.setText(tbl_jual.getValueAt(baris,0).toString());
    }//GEN-LAST:event_tbl_jualMouseClicked

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
            java.util.logging.Logger.getLogger(FrmJual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmJual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmJual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmJual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmJual().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAdd;
    private javax.swing.JButton BtnBatal;
    private javax.swing.JButton BtnSimpan;
    private com.toedter.calendar.JDateChooser JdateJual;
    private javax.swing.JTable TblDetail;
    private javax.swing.JTextField TxtCash;
    private javax.swing.JTextField TxtDateTime;
    private javax.swing.JTextField TxtHJual;
    private javax.swing.JTextField TxtIdPelanggan;
    private javax.swing.JTextField TxtKembali;
    private javax.swing.JTextField TxtKode;
    private javax.swing.JTextField TxtNama;
    private javax.swing.JTextField TxtQty;
    private javax.swing.JTextField TxtStock;
    private javax.swing.JTextField TxtSubTotal;
    private javax.swing.JTextField TxtTotal;
    private javax.swing.JButton btn_cetak;
    private javax.swing.JComboBox<String> cmbPelanggan;
    private javax.swing.JTextField ctk_faktur;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbl_jual;
    private javax.swing.JTextField txtNofa;
    // End of variables declaration//GEN-END:variables
}
