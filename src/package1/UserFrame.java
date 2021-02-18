/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

/**
 *
 * @author hemant
 */

import java.sql.Connection;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.Vector;
public class UserFrame extends javax.swing.JFrame {
    
    
    Connection connection;
    PreparedStatement preparedStatement;
    Statement statement;
    int uid;
    javax.swing.table.DefaultTableModel dtm;

    /**
     * Creates new form UserFrame
     */
    
    public UserFrame() {
        
        try
        {
            connection=DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12392471","sql12392471","59JSdZAp2H");
            
        }catch(Exception e)
        {
            
        }
        
        initComponents();
        refreshCategory();
        getEntries1();
         setname();
    }
    
     public UserFrame(Connection connection,int uid) {
        this.connection=connection;
        this.uid=uid;
        initComponents();
        refreshCategory();
        getEntries1();
         setname();
    
    }
    void setname()
    {
        try
        {
            String sql="select user_name from users where user_sno=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1, uid);
            ResultSet rs=preparedStatement.executeQuery();
            rs.next();
            jLabel11.setText(rs.getString(1));
            
        }catch(Exception e)
        {
             JOptionPane.showMessageDialog(this,e);
        }
        
    }
     void refreshCategory()
    {
        try
        {
            String sql="select category_name from category where user_sno=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1, uid);
            ResultSet rs=preparedStatement.executeQuery();
            while(rs.next())
            {
                jComboBox1.addItem(rs.getString(1));
                jComboBox2.addItem(rs.getString(1));
            }
            
        }catch(Exception e)
        {
             JOptionPane.showMessageDialog(this,e);
        }
       
    }
    
    private void getEntries1()
    {
        try{
                java.time.LocalDate upperdate=java.time.LocalDate.now();
                java.sql.Date sqlupperdate= java.sql.Date.valueOf(upperdate);
                java.time.LocalDate lowerdate=upperdate.minusDays(30);
                java.sql.Date sqllowerdate= java.sql.Date.valueOf(lowerdate);
                int amount=0;
                String sql1="select item_name,item_price,item_date,category_sno from items where user_sno=? and item_date<=? and item_date>=? order by item_date asc";
                
                preparedStatement=connection.prepareStatement(sql1);
                preparedStatement.setInt(1, uid);
                preparedStatement.setDate(2, sqlupperdate);
                preparedStatement.setDate(3, sqllowerdate);
                
                ResultSet rs=preparedStatement.executeQuery();
                
                
                
                dtm=(javax.swing.table.DefaultTableModel)jTable1.getModel();
                int rowCount=dtm.getRowCount();
                while(rowCount!=0)
                {
                    dtm.removeRow(0);
                    --rowCount;
                }
                 
                int count=0;
                while(rs.next())
                {
                    
                    String item_name=rs.getString(1);
                    int item_price=rs.getInt(2);
                    amount=amount+item_price;
                    java.sql.Date date=rs.getDate(3);
                    int category_sno=rs.getInt(4);
                    
                    
                    preparedStatement=connection.prepareStatement("select category_name from category where category_sno=? ");
                    preparedStatement.setInt(1, category_sno);
                    ResultSet rs1=preparedStatement.executeQuery();
                    if(rs1.next())
                    {
                        String category_sno2=rs1.getString(1);
                        //Store data using object array
                        //Object o[]={++count,category};
                        //dtm.addRow(o);
                    
                         //Store data using vector
                        Vector row=new Vector();
                        row.add(++count);
                        row.add(item_name);
                        row.add(item_price);
                        row.add(category_sno2);
                         row.add(date);
                    
                        dtm.addRow(row);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this,"invalid category!!!!!");   
                    }    
                }
                totalAmountField1.setText(Integer.toString(amount));
           }
           catch(Exception e)
           {
                  JOptionPane.showMessageDialog(this,"1"+e);
           }
    
    }
    
    private void getEntries2()
    {
        
        try
        {
            int amount=0;
        
            String category=(String)jComboBox2.getSelectedItem();
             
            java.util.Date date2=dateChooser2.getDate();
            dateChooser1.setDate(null);
            java.util.Date date3=dateChooser3.getDate();
            dateChooser1.setDate(null);
            java.sql.Date sqldate2=new java.sql.Date(date2.getTime());
            java.sql.Date sqldate3=new java.sql.Date(date3.getTime());
          
            if(category.equals("ALL"))
            {
                
                String sql="select item_name,item_price,item_date,category_sno from items where user_sno=? and item_date>=? and item_date<=? order by item_date asc" ;
                preparedStatement=connection.prepareStatement(sql);
            }
            else
            {
                preparedStatement=connection.prepareStatement("select category_sno from category where category_name=? and user_sno=?");
                preparedStatement.setString(1,category);
                 preparedStatement.setInt(2,uid);
                
                ResultSet rs=preparedStatement.executeQuery();
                rs.next();
                int category_sno=rs.getInt(1);
           
            
                String sql="select item_name,item_price,item_date,category_sno from items where user_sno=? and item_date>=? and item_date<=?  and category_sno=? order by item_date asc";
                preparedStatement=connection.prepareStatement(sql);
                preparedStatement.setInt(4,category_sno);
            }
            
        
            preparedStatement.setInt(1, uid);
            preparedStatement.setDate(2, sqldate2);
            preparedStatement.setDate(3, sqldate3);
            ResultSet rs1=preparedStatement.executeQuery();
           
           
                dtm=(javax.swing.table.DefaultTableModel)jTable2.getModel();
                int rowCount=dtm.getRowCount();
                while(rowCount!=0)
                {
                    dtm.removeRow(0);
                    --rowCount;
                }
                 
                int count=0;
                while(rs1.next())
                {
                    
                    String item_name=rs1.getString(1);
                    int item_price=rs1.getInt(2);
                    amount=amount+item_price;
                    java.sql.Date date=rs1.getDate(3);
                    int category_sno=rs1.getInt(4);
                    
                    
                    preparedStatement=connection.prepareStatement("select category_name from category where category_sno=? ");
                    preparedStatement.setInt(1, category_sno);
                    ResultSet rs2=preparedStatement.executeQuery();
                    if(rs2.next())
                    {
                        String category_name2=rs2.getString(1);
                        //Store data using object array
                        //Object o[]={++count,category};
                        //dtm.addRow(o);
                    
                         //Store data using vector
                        Vector row=new Vector();
                        row.add(++count);
                        row.add(item_name);
                        row.add(item_price);
                        row.add(category_name2);
                         row.add(date);
                    
                        dtm.addRow(row);
                         
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this,"invalid category!!!!!");   
                    }    
                }
               totalAmountField2.setText(Integer.toString(amount));
                
        }
        catch(Exception e)
        {
              JOptionPane.showMessageDialog(this,e);
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

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        logout = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        addItemButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        amountField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        dateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        totalAmountField1 = new javax.swing.JTextField();
        deleteButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        searchButton = new javax.swing.JButton();
        dateChooser2 = new com.toedter.calendar.JDateChooser();
        dateChooser3 = new com.toedter.calendar.JDateChooser();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        totalAmountField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        deleteButton2 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SPENDING TRACKER");

        jPanel1.setBackground(new java.awt.Color(136, 227, 71));
        jPanel1.setForeground(new java.awt.Color(95, 243, 60));

        logout.setBackground(new java.awt.Color(204, 34, 40));
        logout.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        logout.setForeground(new java.awt.Color(13, 13, 11));
        logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/3005766-32.png"))); // NOI18N
        logout.setText("Logout");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Ubuntu Condensed", 0, 24)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/2754575-64 (copy).png"))); // NOI18N

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/wallet.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(396, 396, 396)
                .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(logout)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.setBackground(new java.awt.Color(238, 61, 43));

        jPanel3.setBackground(new java.awt.Color(244, 206, 175));
        jPanel3.setForeground(new java.awt.Color(232, 189, 103));

        jPanel5.setBackground(new java.awt.Color(173, 162, 166));
        jPanel5.setForeground(new java.awt.Color(155, 137, 233));

        addItemButton.setBackground(new java.awt.Color(125, 182, 239));
        addItemButton.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        addItemButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/3669464-48.png"))); // NOI18N
        addItemButton.setText(" ADD ITEM");
        addItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addItemButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel1.setText("Name:");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel2.setText("Amount:");

        amountField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel3.setText("Date:");

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel4.setText("Category:");

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jButton3.setText("Add New Category");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Serial No.", "Item Name", "Amount", "Category", "Date"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel1)
                        .addGap(1, 1, 1)
                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(amountField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(addItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(165, 165, 165)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(amountField, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/3069197-32.png"))); // NOI18N
        jLabel5.setText("LAST 30 DAY TOTAL AMOUNT:");

        totalAmountField1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        totalAmountField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalAmountField1ActionPerformed(evt);
            }
        });

        deleteButton1.setBackground(new java.awt.Color(251, 35, 30));
        deleteButton1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        deleteButton1.setForeground(new java.awt.Color(27, 11, 11));
        deleteButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/3162615-32.png"))); // NOI18N
        deleteButton1.setText("DELETE");
        deleteButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(totalAmountField1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deleteButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalAmountField1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("ADD", jPanel3);

        jPanel4.setBackground(new java.awt.Color(216, 190, 139));
        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.setForeground(new java.awt.Color(254, 254, 254));

        jPanel6.setBackground(new java.awt.Color(194, 179, 220));
        jPanel6.setForeground(new java.awt.Color(163, 144, 217));

        searchButton.setBackground(new java.awt.Color(61, 148, 98));
        searchButton.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        searchButton.setForeground(new java.awt.Color(1, 0, 26));
        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/3162603-32.png"))); // NOI18N
        searchButton.setText("SEARCH");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL" }));

        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel7.setText("FROM:");

        jLabel8.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel8.setText("TO:");

        jLabel9.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel9.setText("CATEGORY:");

        jTable2.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Serial No.", "Item Name", "Amount", "Category", "Date"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(dateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
            .addComponent(jScrollPane2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(423, 423, 423))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel8))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(dateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9)))))
                .addGap(12, 12, 12)
                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
        );

        totalAmountField2.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        totalAmountField2.setText("0");
        totalAmountField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalAmountField2ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/3069197-32.png"))); // NOI18N
        jLabel6.setText("TOTAL AMOUNT:");

        deleteButton2.setBackground(new java.awt.Color(225, 32, 27));
        deleteButton2.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        deleteButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/3162615-32.png"))); // NOI18N
        deleteButton2.setText("DELETE");
        deleteButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 1160, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(totalAmountField2, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deleteButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalAmountField2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("SEARCH", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addItemButtonActionPerformed
        // TODO add your handling code here:
         try{
            String item_name=nameField.getText();
            nameField.setText("");
            String s1=amountField.getText();
            amountField.setText("");
            java.util.Date date=dateChooser1.getDate();
            dateChooser1.setDate(null);
            String category=(String)jComboBox1.getSelectedItem();
            if(item_name.equals("")||s1.equals("")||date==null||category.equals(""))
            {
                JOptionPane.showMessageDialog(this,"Invalid input!!!!!");
            }
            else
            {
                int item_price=Integer.parseInt(s1);
                
                java.sql.Date sqlDate= new java.sql.Date(date.getTime());
                preparedStatement=connection.prepareStatement("select category_sno from category where category_name=? and user_sno=?");
                preparedStatement.setString(1, category);
                preparedStatement.setInt(2, uid);
                ResultSet rs=preparedStatement.executeQuery();
                if(rs.next())
                {
                    int category_sno=rs.getInt(1);
                    
                    
                    String sql="insert into items(item_name,item_price,item_date,category_sno,user_sno) values(?,?,?,?,?)";
                    preparedStatement=connection.prepareStatement(sql);
                    preparedStatement.setString(1, item_name);
                    preparedStatement.setInt(2, item_price);
                    preparedStatement.setDate(3,sqlDate);
                    preparedStatement.setInt(4, category_sno);
                    preparedStatement.setInt(5, uid);
                    preparedStatement.execute();
                    getEntries1();
                    JOptionPane.showMessageDialog(this,"Item added successfully");
                }
                else
                {
                    JOptionPane.showMessageDialog(this,"Invalid Category!!!!!");
                }
               
            }
            
            
            
        }
         catch(NumberFormatException ne)
         {
             JOptionPane.showMessageDialog(this,"Invalid Amount!!!!!!");
         }
         catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,"mymy"+e);
        }
    }//GEN-LAST:event_addItemButtonActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
         new Category(connection,uid).setVisible(true);
         dispose();
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void amountFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
         if(dateChooser2.getDate()==null || dateChooser3.getDate()==null)
         {
             JOptionPane.showMessageDialog(this,"Please select the date");
         }
         else
         {
              getEntries2();
              dtm=(javax.swing.table.DefaultTableModel)jTable2.getModel();
              int rowCount2=dtm.getRowCount();
                if(rowCount2==0)
                {
                    JOptionPane.showMessageDialog(this,"No Item found"); 
                }
         }
                    
            
            
        
    }//GEN-LAST:event_searchButtonActionPerformed

    private void totalAmountField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalAmountField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalAmountField1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void totalAmountField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalAmountField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalAmountField2ActionPerformed

    private void deleteButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButton2ActionPerformed
        // TODO add your handling code here:
        String item="";
        try{
            
            int remove=jTable2.getSelectedRow();
           
            if(remove==-1)
            {
                JOptionPane.showMessageDialog(this,"Plese select one item");
                
            }
            else
            {
                
                item=(String)jTable2.getValueAt(remove,1);
                int price=(int)jTable2.getValueAt(remove,2);
                String category_name=(String)jTable2.getValueAt(remove,3);
                preparedStatement=connection.prepareStatement("select category_sno from category where category_name=? and user_sno=?");
                preparedStatement.setString(1, category_name);
                preparedStatement.setInt(2, uid);
                ResultSet rsc=preparedStatement.executeQuery();
                rsc.next();
                int category_sno=rsc.getInt(1);
                java.sql.Date date=(java.sql.Date)jTable2.getValueAt(remove,4);
               
                int rs=JOptionPane.showConfirmDialog(null, "Do you realy want to delete "+item+"?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                if(rs==JOptionPane.YES_OPTION)
                {
                    String sql="select item_sno from items where item_name=? and item_price=? and item_date=? and category_sno=? and user_sno=?" ;
                    preparedStatement=connection.prepareStatement(sql);
                    preparedStatement.setString(1, item);
                    preparedStatement.setInt(2, price);
                    preparedStatement.setDate(3, date);
                    preparedStatement.setInt(4, category_sno);
                    preparedStatement.setInt(5, uid);
                    ResultSet rsf=preparedStatement.executeQuery();
                    rsf.next();
                    int item_sno=rsf.getInt(1);
                    preparedStatement=connection.prepareStatement("delete from items where item_sno=?");
                    preparedStatement.setInt(1, item_sno);
                    preparedStatement.execute();
                    getEntries2();
                    JOptionPane.showMessageDialog(this,item+" is deleted");
                    
                }
                
               
               
            }
          
        
        }
        catch(Exception e)
          {
                    JOptionPane.showMessageDialog(this,"gaurav Exception!!!!!!"+e+"\n"+e.getMessage());
           }
        
        
        
    }//GEN-LAST:event_deleteButton2ActionPerformed

    private void deleteButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButton1ActionPerformed
        // TODO add your handling code here:
        String item="";
        try{
            
            int remove=jTable1.getSelectedRow();
           
            if(remove==-1)
            {
                JOptionPane.showMessageDialog(this,"Plese select one item");
                
            }
            else
            {
                
                item=(String)jTable1.getValueAt(remove,1);
                int price=(int)jTable1.getValueAt(remove,2);
                String category_name=(String)jTable1.getValueAt(remove,3);
                preparedStatement=connection.prepareStatement("select category_sno from category where category_name=? and user_sno=?");
                preparedStatement.setString(1, category_name);
                preparedStatement.setInt(2, uid);
                ResultSet rsc=preparedStatement.executeQuery();
                rsc.next();
                int category_sno=rsc.getInt(1);
                java.sql.Date date=(java.sql.Date)jTable1.getValueAt(remove,4);
               
                int rs=JOptionPane.showConfirmDialog(null, "Do you realy want to delete "+item+"?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                if(rs==JOptionPane.YES_OPTION)
                {
                    String sql="select item_sno from items where item_name=? and item_price=? and item_date=? and category_sno=? and user_sno=?" ;
                    preparedStatement=connection.prepareStatement(sql);
                    preparedStatement.setString(1, item);
                    preparedStatement.setInt(2, price);
                    preparedStatement.setDate(3, date);
                    preparedStatement.setInt(4, category_sno);
                    preparedStatement.setInt(5, uid);
                    ResultSet rsf=preparedStatement.executeQuery();
                    rsf.next();
                    int item_sno=rsf.getInt(1);
                    preparedStatement=connection.prepareStatement("delete from items where item_sno=?");
                    preparedStatement.setInt(1, item_sno);
                    preparedStatement.execute();
                    getEntries1();
                    JOptionPane.showMessageDialog(this,item+" is deleted");
                    
                }
                
               
               
            }
          
        
        }
        catch(Exception e)
          {
                    JOptionPane.showMessageDialog(this,"Exception!!!!!!"+e+"\n"+e.getMessage());
           }
        
        
        
    }//GEN-LAST:event_deleteButton1ActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // TODO add your handling code here:

        dispose();
        new LoginFrame(connection).setVisible(true);
    }//GEN-LAST:event_logoutActionPerformed

    /**
     * @param args the command line arguments
     */
//  0  public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(UserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(UserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(UserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(UserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new UserFrame().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addItemButton;
    private javax.swing.JTextField amountField;
    private com.toedter.calendar.JDateChooser dateChooser1;
    private com.toedter.calendar.JDateChooser dateChooser2;
    private com.toedter.calendar.JDateChooser dateChooser3;
    private javax.swing.JButton deleteButton1;
    private javax.swing.JButton deleteButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JButton logout;
    private javax.swing.JTextField nameField;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField totalAmountField1;
    private javax.swing.JTextField totalAmountField2;
    // End of variables declaration//GEN-END:variables
}
