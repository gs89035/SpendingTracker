/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;
import java.sql.*;
import java.util.Vector;
import javax.swing.JOptionPane;
/**
 *
 * @author gaurav
 */
public class Category extends javax.swing.JFrame {

    /**
     * Creates new form C
     */
    Connection connection;
    PreparedStatement preparedStatement; 
    int uid;
    
    //temp connection 
    
    
    
    
    public Category() 
    {
        initComponents();
//        try
//        {
//             connection=DriverManager.getConnection("jdbc:mysql://196.168.0.106:3306/loginApp","remote","Remote@007");
//             getEntries();
//        }catch(Exception e)
//        {
//           
//        }
//        
     
    }
    public Category(Connection connection) {
        this.connection=connection;
     
        initComponents();
        getEntries();
    }
    public Category(Connection connection,int uid) {
        this.connection=connection;
        this.uid=uid;
        initComponents();
        getEntries();
    }
    
    
    private void getEntries()
    {
        try{
                String sql="select category_name from category where user_sno=?";
                preparedStatement=connection.prepareStatement(sql);
                preparedStatement.setInt(1, uid);
                ResultSet rs=preparedStatement.executeQuery();
                
                
                 javax.swing.table.DefaultTableModel dtm;
                 dtm=(javax.swing.table.DefaultTableModel)categoryTable.getModel();
                 int rowCount=dtm.getRowCount();
                 while(rowCount!=0)
                 {
                     dtm.removeRow(0);
                     --rowCount;
                 }
                 
                int count=0;
                while(rs.next())
                {
                    
                    String category=rs.getString(1);
                   
                    
                    //Store data using object array
                    //Object o[]={++count,category};
                    //dtm.addRow(o);
                    
                    //Store data using vector
                    Vector row=new Vector();
                    row.add(++count);
                    row.add(category);
                    dtm.addRow(row);
                    
                }
        }
        catch(Exception e)
        {
                  JOptionPane.showMessageDialog(this,e);
        }
    
    }
    private void addCategory()
    {
        String newCategory=categoryInputField.getText();
        
        if(newCategory.equals(""))
        {
            JOptionPane.showMessageDialog(this,"Invalid Input");
        }
        else{
            try{

                String sql2="select * from category where category_name=? and user_sno=?";
                preparedStatement=connection.prepareStatement(sql2);
                preparedStatement.setString(1, categoryInputField.getText());
                preparedStatement.setInt(2, uid);
                ResultSet rs=preparedStatement.executeQuery();
                if(rs.next())
                {
                    int uidTemp=rs.getInt(3);
                    if(uid==uidTemp)
                    {
                        JOptionPane.showMessageDialog(this,"This Category already exist");
                    }
                    else
                    {
                        String sql1="insert into category(category_name,user_sno) values(?,?)";
                        preparedStatement=connection.prepareStatement(sql1);
                        preparedStatement.setString(1, categoryInputField.getText());
                        preparedStatement.setInt(2, uid);
                        preparedStatement.execute();
                        getEntries();
                        JOptionPane.showMessageDialog(this,"Category added successfully");
                    }

                }
                else
                {
                    String sql1="insert into category(category_name,user_sno) values(?,?)";
                    preparedStatement=connection.prepareStatement(sql1);
                    preparedStatement.setString(1, categoryInputField.getText());
                    preparedStatement.setInt(2, uid);
                    preparedStatement.execute();
                    getEntries();
                    JOptionPane.showMessageDialog(this,"Category added successfully");
                    categoryInputField.setText("");
                }
                

            }catch(SQLIntegrityConstraintViolationException sqe)
            {
                JOptionPane.showMessageDialog(this,"this category is already exist ");

            }
            catch(Exception e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,"Exception!!!!!!"+e.getMessage());
            }
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        categoryInputField = new javax.swing.JTextField();
        addButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        back = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        categoryTable = new javax.swing.JTable();
        deleteButton = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SPENDING TRACKER");

        jPanel2.setBackground(new java.awt.Color(240, 166, 25));

        categoryInputField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryInputFieldActionPerformed(evt);
            }
        });

        addButton.setBackground(new java.awt.Color(168, 203, 238));
        addButton.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        addButton.setText("ADD");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Yrsa SemiBold", 1, 36)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/103433-64.png"))); // NOI18N
        jLabel1.setText("ADD CATEGORY");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel2.setText("New Category:");

        back.setBackground(new java.awt.Color(235, 39, 73));
        back.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/216437-32.png"))); // NOI18N
        back.setText("Back");
        back.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(categoryInputField, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(categoryInputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        categoryTable.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        categoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Serial No,", "Category"
            }
        ));
        jScrollPane1.setViewportView(categoryTable);

        deleteButton.setBackground(new java.awt.Color(230, 34, 11));
        deleteButton.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        deleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/3162615-32.png"))); // NOI18N
        deleteButton.setText("DELETE");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        addCategory();
    }//GEN-LAST:event_addButtonActionPerformed

    private void categoryInputFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryInputFieldActionPerformed
        // TODO add your handling code here:
        addCategory();
    }//GEN-LAST:event_categoryInputFieldActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
        String category="";
        try{
            
            int remove=categoryTable.getSelectedRow();
           
            if(remove==-1)
            {
                JOptionPane.showMessageDialog(this,"Plese select one category");
                
            }
            else
            {
                
                category=(String)categoryTable.getValueAt(remove,1);
               
                int rs=JOptionPane.showConfirmDialog(null, "Do you realy want to delete "+category+"?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                if(rs==JOptionPane.YES_OPTION)
                {
                    String sql="delete from category where category_name=? and user_sno=?";
                    preparedStatement=connection.prepareStatement(sql);
                    preparedStatement.setString(1, category);
                    preparedStatement.setInt(2, uid);
                    preparedStatement.execute();
                    getEntries();
                    JOptionPane.showMessageDialog(this,category+" is deleted");
                    
                }
                
               
               
            }
          
        
        }catch(java.sql.SQLIntegrityConstraintViolationException ex)
        {
            JOptionPane.showMessageDialog(this,category+" category is used by some items delete those items first to delete this catagory");
        }
        catch(Exception e)
          {
                    JOptionPane.showMessageDialog(this,"Exception!!!!!!"+e+"\n"+e.getMessage());
            }
        
        
        
        
        
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        // TODO add your handling code here:
           new UserFrame(connection,uid).setVisible(true);
           dispose();
    }//GEN-LAST:event_backActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
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
//            java.util.logging.Logger.getLogger(Category.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Category.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Category.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Category.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Category().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton back;
    private javax.swing.JTextField categoryInputField;
    private javax.swing.JTable categoryTable;
    private javax.swing.JButton deleteButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
