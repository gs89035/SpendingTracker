/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.swing.JOptionPane;

/**,,,,
 *
 * @author gaurav
 */
public class DbConnect extends javax.swing.JFrame{
  
    
    Connection connection=null;
    Statement statement=null;
    PreparedStatement preparedStatement=null;
    ResultSet resultSet=null;
    List c;
		
		//mysql url,user,pswd
    String ip;
    String port;
    String user;
    String pswd;
    
    String url;
    String dbName;
    String dbSchemaName;
    
    public DbConnect()
    {
        
    }
  
   public DbConnect(String ip,String port,String user,String pswd,String dbName,String dbSchemaName) 
  {
      this.ip=ip;
      this.port=port;
      this.user=user;
      this.pswd=pswd;
      this.dbName=dbName;
      this.dbSchemaName=dbSchemaName;
      //connectDb();
      
      
  }
    
//      public static void main(String args[])
//    {
//       //DbConnect dbc=new DbConnect("127.0.0.1","5432","gaurav","yoyo@007","postgres");
//      DbConnect dbc=new DbConnect("192.168.0.106","3306","root","qwertyuiop","mysql","loginApp");
//       
//      /* boolean x=dbc.connectDb();
//       if(x==true)
//       {
//           System.out.println("Db connected");
//           
//       }
//       else
//       {
//           System.out.println("Db not connected");
//       }*/
//    }
//    

  
  public Connection connectDb()
  {
      
      try{
                String mysqlUrl="jdbc:mysql://"+ip+":"+port+"/"+dbSchemaName+"";
                String oracleUrl="jdbc:oracle:thin:"+ip+":"+port+":"+dbSchemaName+"";
                String postgresUrl="jdbc:postgresql://"+ip+":"+port+"/"+dbSchemaName+"";
                 if(dbName.equals("mysql"))
                  {
                    this.url=mysqlUrl;
                  }
                 else if(dbName.equals("postgres"))
                 {
                     this.url=postgresUrl;
                 }
                 else if(dbName.equals("oracle"))
                 {
                     this.url=oracleUrl;
                     
                 }
                
                //JOptionPane.showMessageDialog(this,"Try Connection with ip:"+ip+"|port:"+port+"|user::"+user+"|password:"+pswd+"|url:"+url);
                connection=DriverManager.getConnection(url,user,pswd);
//                if(connection!=null)
//                {
//                    JOptionPane.showMessageDialog(this,"Connected with "+dbName+" Database");
//                }
//                
                
               return connection;
                
        }catch(Exception e)
        {
         
            JOptionPane.showMessageDialog(this,"Exception!!!!!!"+e.getMessage());
           // JOptionPane.showMessageDialog(this,"Configure databse first");
            
            return connection;
        }
      
  }
  
  
    
}
