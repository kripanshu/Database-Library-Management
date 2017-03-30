/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagement_kxb162030;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;


/**
 *
 * @author kripanshubhargava
 */
public class Borrower_Management {
     static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost:3306/Library?zeroDateTimeBehavior=convertToNull&useSSL=false";
static String error_bor="";

   
   //  Database credentials
   static final String USER = "root";
   static final String PASS = "KRIPs$$110924";
   
   
   
    public static void borrower_add(String ssn,String first_name,String last_name,String email,String address,String city,String state,String phone)
    {
        
        
        
        
        
    String card_id="";
     Random rnd = new Random();
     int n;
        n = 100 + rnd.nextInt(900);
        card_id = Integer.toString(n);
    
        
        
     Connection conn = null;
   Statement stmt = null;
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);
System.out.println("Connected");
      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      
    // System.out.println(Loan_id + " , "+ Isbn + " , "+card_id +" , "+" NULL"+" , "+ due_datefinal + " , " + date_outfinal);
      
     
     String sql1="insert into borrower values('"+card_id +"','"+ ssn +"','"+ first_name +"','"+last_name+"' ,'"+ email +"', '"+ address+"', '"+ city+"', '"+ state+"', '"+ phone+"') ;";
      try  {
          
               //STEP 5: Extract data from result set
              
                  
                   int rs = stmt.executeUpdate(sql1);
                   System.out.println("data inserted");
                   error_bor="data inserted";
                  
               //STEP 6: Clean-up environment
           }catch(SQLException se){se.printStackTrace();
      error_bor="Cannot insert data";}
      stmt.close();
      conn.close();
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
      System.out.println("The book_loan insert query failed 1");
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
          System.out.println("The book_loan insert query failed 2");
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   
      System.out.print(error_bor);
   }
   
   
   
   
    }
    
    
    
    
}
