/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagement_kxb162030;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import java.util.Date;
import java.util.Random;


/**
 *
 * @author kripanshubhargava
 */
public class Book_loans {
    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost:3306/Library?zeroDateTimeBehavior=convertToNull&useSSL=false";
static String error_message="";
  //  Database credentials
   static final String USER = "root";
   static final String PASS = "KRIPs$$110924";
   
   public static boolean check_loan(String card_id)
   {
   String check=card_id;
   int count=0;
   boolean output = true;
   
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
      String sql="Select Count(*) from Book_loans where card_id= '"+ check +"' ;"  ;
     
      try (ResultSet rs = stmt.executeQuery(sql)) {
               //STEP 5: Extract data from result set
               while (rs.next()) {
                   //Retrieve by column name
                   count=Integer.parseInt(rs.getString("Count(*)"));
               if(count>=3)
               {
               output=false;
               System.out.print("you can not loan out book");
               error_message+="You have reached maximum limit of 3 . ";
               }  
               else
               { output=true;
               System.out.println("you can loan out book");
               }
               
               }
               //STEP 6: Clean-up environment
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
   
   return output;
   }
    
   
   public static boolean check_availablity(String Isbn)
   
 {
     
     
     
     
        boolean availability = Book_Search.availability(Isbn);
    /* boolean output=true;
 String check=Isbn;
       
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
      String sql="Select due_date,date_in from Book_loans where Isbn10= '"+ check +"' ;"  ;
     
      try (ResultSet rs = stmt.executeQuery(sql)) {
               //STEP 5: Extract data from result set
               while (rs.next()) {
               
                   //Retrieve by column name
                 try{
                   Date due=rs.getDate("Due_date");
                 Date in=rs.getDate("Date_in");
                 
                  Date date = new Date();
                 
                  if(due.after(date)&& in.after(due))
                   {
                   System.out.print("book is not available");
                   error_message+="book is not available . ";
                   output=false;
                   }else 
                       
                   {
                       output=true;
                   System.out.println("Book is available");
                   }
                  
                 }catch(NullPointerException e)
                 {
                 System.out.println("date might be null");
                 error_message+="date might be null . ";
                 }
               }
               //STEP 6: Clean-up environment
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
 */  
 return availability;

 }
   public static boolean book_loan(String card_id,String Isbn) 
   {
       Calendar cal = Calendar.getInstance();
      boolean output=true;
       
  String Loan_id;
  Date  date_in=null ,date_out=null,due_date=null;
  Date today= new Date();
  date_out=today;
 cal.setTime(today);
 
 cal.add(Calendar.DATE, 14);
 due_date=cal.getTime();

 
        String date_outfinal = sdf.format(date_out);
        // sdf.format(date_in);
        String due_datefinal = sdf.format(due_date);
  System.out.println(date_outfinal);
 System.out.println(due_datefinal);
  //random key generated
  Random rnd = new Random();
 int n;
        n = 100000 + rnd.nextInt(900000);
        Loan_id = Integer.toString(n);
   
           
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
      System.out.println("Loan ID : "+ Loan_id);
    // System.out.println(Loan_id + " , "+ Isbn + " , "+card_id +" , "+" NULL"+" , "+ due_datefinal + " , " + date_outfinal);
      
    // String sql="Select * from Book,Borrower where book.Isbn10 = '"+Isbn+"' and borrower.card_id = '"+card_id+"';"  ;
     String sql1="insert into book_loans values('"+Loan_id +"','"+ Isbn +"','"+ card_id +"',NULL, '"+due_datefinal +"', '"+ date_outfinal+"');";
      try  {
          
               //STEP 5: Extract data from result set
              
                   //String stored_isbn=rs.getString("Isbn10");
                  // String stored_cardid=rs.getString("card_id");
                   //
                   //System.out.println(stored_isbn + " ......" + stored_cardid );
                   boolean cond1=check_loan(card_id);
                   boolean cond2=check_availablity(Isbn);
                   
                   if(cond1 && cond2 )
                   {
                       int rs = stmt.executeUpdate(sql1);
                        System.out.println("condition true");
                   //int rs1=stmt.executeUpdate(sql);
                   System.out.println("Query executed");
                   //System.out.print(rs1.getStatement());
                   error_message+="data inserted . ";
                       
                       output=true;
                   
                   //Retrieve by column name
               
               }else 
                   {
                   System.out.println("Cannot insert data");
                   error_message+="data inserted . ";
                   output=false;
                   }
               //STEP 6: Clean-up environment
           }catch(SQLException se){se.printStackTrace();
      System.out.println("The book_loan insert query failed 1");}
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
   
      
   }
        return output;
   
   
   
   }
   
      
   
}
