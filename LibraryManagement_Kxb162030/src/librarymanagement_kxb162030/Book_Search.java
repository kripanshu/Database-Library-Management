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


import java.util.*;

/**
 *
 * @author kripanshubhargava
 */
public class Book_Search {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/Library?zeroDateTimeBehavior=convertToNull&useSSL=false";
    static String error="";
static String Isbn_value="";
    public static ArrayList<String> bookList= new ArrayList<String>();
    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   //  Database credentials
   static final String USER = "root";
   static final String PASS = "KRIPs$$110924";

    static void search(String input) {
       
    String search=input;
       String output = null;
       
        Connection conn = null;
   Statement stmt = null;
   if(! input.equals(" ")){
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
     // System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);
//System.out.println("Connected");
      //STEP 4: Execute a query
      System.out.println("Creating statement...for the main parts");
      stmt = conn.createStatement();
      String sql="Select * from Book where Isbn10 Like '%"+search+"%' OR Author like '%" +search+"%' OR Title like '%" + search + "%' ;"  ;
      
      String set="";
      try (ResultSet rs = stmt.executeQuery(sql)) {
               //STEP 5: Extract data from result set
               while (rs.next()) 
               {
                   //Retrieve by column name
                    String Isbn = rs.getString("Isbn10");
                   String Title = rs.getString("title");
                   String Author = rs.getString("author");
                   Isbn_value=Isbn;
                 
                   /*//Display values
                   System.out.print(" Isbn: " + Isbn);
                   System.out.print(" , title: " + Title);
                   System.out.println(", author: " + Author);
                   */
                   if(availability(Isbn_value))
                   {
                  set=  " ISBN : " + Isbn +" ,  TITLE : " + Title + ",  AUTHOR  : " + Author + " , Book Available" ;
                   }else 
                   {
                   set=  " ISBN : " + Isbn +" ,  TITLE : " + Title + ",  AUTHOR  : " + Author + " , Book Not Available" ;
                   }
                  bookList.add(set);
               
               }
      }catch(SQLException se)
      {
          se.printStackTrace();
      }
          /*      
               String sql1="Select * from Book_loans where Isbn10= '"+Isbn_value+"' ;";
               try (ResultSet rs1 = stmt.executeQuery(sql1)) {
               //STEP 5: Extract data from result set
               while (rs1.next())
               {
                   Date date = new Date();
        //System.out.println(sdf.format(date));
                   Date dt= rs1.getDate("Date_in");
                   
                   if(dt.after(date))
                   {
                   System.out.print("book is available");
                   
                   }else if(dt.before(date))
                   {
                   System.out.print("book is not availbale");
                   }
               }

               //STEP 6: Clean-up environment
           }
*/
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
   }else
   {
   System.out.println("empty String");
   }
   System.out.println("Goodbye!");
    }
    
    public static boolean availability(String Isbn)
    {
        Boolean output = true;
        Connection conn = null;
   Statement stmt = null;
   Calendar cal = Calendar.getInstance();
  // HashMap hm = new HashMap();
   
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
   // String isbn=Isbn_value;
     
      String sql1="Select * from Book_loans where Isbn10= '"+Isbn+"' ;";
               try (ResultSet rs1 = stmt.executeQuery(sql1)) {
                   
                           boolean val = rs1.next(); //next() returns false if there are no-rows retrieved 
        if(val==false){
            System.out.println("set is empty");
            output=true;//prints this message if your resultset is empty
         }
        else{    
       
               //STEP 5: Extract data from result set
               while (val)
               {
                   Date  dt=null ,date_out=null,due_date=null;
  Date today= new Date();
  //date_out=date;
 cal.setTime(today);
 Date date=cal.getTime();
 
 
      //   System.out.println("...... checking .....");
              //  System.out.println(Isbn_value);   
               //    Date date = new Date();

// date = (Date) sdf.parse(sdf.format(date));
     //   System.out.println("this is format :"+todayWithZeroTime);
        
       // System.out.println("this is format :"+date1);
       
        try{            
        Date dt1= rs1.getDate("Date_in");
        cal.setTime(dt1);
        dt=cal.getTime();
        
        System.out.println(dt + " and this is  " + date);
         
                   if(dt.after(date))
                   {
                   output=false;
                   System.out.println("The book is not available ");
                   }
                   else 
                   {
                   output=true;
                   System.out.println("The book is available ");
                   }
        }catch(NullPointerException e) 
        {
        dt=sdf.parse("2017-02-19");
        output=true;
       
        System.out.println(dt + " and " + date);
        System.out.println("The book is not available by exception");
        }
        
              
               val= rs1.next();    
                   //System.out.println(dt + " and " + date);
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
      //output=true;
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
    
    
    
}
