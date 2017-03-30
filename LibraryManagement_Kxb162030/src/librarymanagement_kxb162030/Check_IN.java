/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagement_kxb162030;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;




/**
 *
 * @author kripanshubhargava
 */
public class Check_IN {
    
    
    
private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
static final String DB_URL = "jdbc:mysql://localhost:3306/Library?zeroDateTimeBehavior=convertToNull&useSSL=false";
static String error="";
public static ArrayList<String> book_loan_list=new ArrayList<String>();
  //  Database credentials
static final String USER = "root";
static final String PASS = "KRIPs$$110924";
    
    public static void search_results(String check)
    {
        
    
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
      String sql="Select * from Book_loans a INNER JOIN Borrower b ON a.card_id = b.card_id where a.Isbn10 Like '%"+check+"%' OR a.Card_id like '%" +check+"%' OR a.loan_id like '%" + check + "%' OR b.first_name like '%" + check + "%'OR b.Last_name like '%" + check + "%' ;"  ;
     
      try (ResultSet rs = stmt.executeQuery(sql)) {
               //STEP 5: Extract data from result set
               while (rs.next()) {
                   String set="";
                   String loan_id,card_id,Isbn,first_name,date_out,due_date;
                   
                   loan_id=rs.getString("Loan_id");
                   Isbn=rs.getString("Isbn10");
                   card_id=rs.getString("Card_id");
                  first_name=rs.getString("first_name");
                   date_out=rs.getString("date_out");
                   due_date=rs.getString("due_date");
                   
                   set= loan_id+"\t"+Isbn+ "\t"+card_id+"\t"+first_name+"\t"+date_out+"\t"+due_date+"\t";
                   System.out.println(set);
                   book_loan_list.add(set);
                   
                   //print the list in the list view
               }
           }
      stmt.close();
      conn.close();
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
        
    
    }
    
      public static void book_checking(String search_query,String date_in) throws ParseException
      {
          String loan_id="";
      String [] data=new String[6];
      data=search_query.split("\t");
     Date date = sdf.parse(date_in);
    String date_infinal=sdf.format(date);
      
      loan_id=data[0];
    //  java.sql.Date sqlDate = java.sql.Date.valueOf(sdf.date_i=);
      
   // String date_infinal = sdf.format(date);
    
    System.out.println(loan_id+" and "+date_infinal);
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
    //  System.out.println("Creating statement...");
      stmt = conn.createStatement();
      
      
      
           String sql=" Update Book_loans SET date_in=' "+date_infinal+"' where Loan_id= '"+loan_id+"' ;"  ;
     
           {
               try{
                int rs = stmt.executeUpdate(sql);
                System.out.println("data updated with loan_id = "+ loan_id);
                error="data updated with loan_id = "+ loan_id;
               }catch(SQLException se)
               {
               System.out.println(se);
               error="update failed";
               }
           }
      stmt.close();
      conn.close();
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }
          
          
      }
    
}
