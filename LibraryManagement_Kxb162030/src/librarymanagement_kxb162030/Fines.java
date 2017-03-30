
package librarymanagement_kxb162030;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;



/**
 *
 * @author kripanshubhargava
 */
public class Fines {
     private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost:3306/Library?zeroDateTimeBehavior=convertToNull&useSSL=false";
public static String error="",due_date,Loan_id1,amount_status;
static Date due_datefinal,date_today,date_in;
  public static ArrayList<Float> fine =new ArrayList<Float>();
  public static ArrayList<Float> estimated_fine =new ArrayList<Float>();
   public static ArrayList<String> fine_data =new ArrayList<String>();
   //  Database credentials
   static final String USER = "root";
   static final String PASS = "KRIPs$$110924";
   static float fine1,estimated_fine1;
   
  static HashMap hm=new HashMap();
    public static float fine_amount;
    private static float local_amount;
   
   
   
   public static void calculate_fines ()
   {
     Connection conn = null;
   Statement stmt = null;
   
    Calendar cal = Calendar.getInstance();
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
      
      
      
           String sql=" Select * from Book_loans;"  ;
     
            try (ResultSet rs = stmt.executeQuery(sql)) {
               //STEP 5: Extract data from result set
               while (rs.next()) 
               {
                   //String date_in = "";
                  String Isbn=rs.getString("Isbn10");
                  String Loan_id=rs.getString("Loan_id");
                  String card_id=rs.getString("card_id");
                  String date_out=rs.getString("date_out");
                   due_date= rs.getString("due_date");
                  
                 
                  try
                  {
                      String date_infake = rs.getString("date_in");
                       fine1=0;estimated_fine1=0;
                       //Date  dt=null ,date_out=null,due_date=null;
  Date today= new Date();
  //date_out=date;
 cal.setTime(today);
  date_today=cal.getTime();
 Date date_in1=sdf.parse(date_infake);
  Date date_due1=sdf.parse(due_date);                  
 cal.setTime(date_in1);
  date_in = cal.getTime();
cal.setTime(date_due1);
 due_datefinal= cal.getTime();
if(date_in.after(date_today))
{
long days_case2=daysBetween(date_today,due_datefinal);
System.out.println(days_case2 + " are teh number of days for case 2");
estimated_fine1=(float) (days_case2*0.25);
System.out.println(estimated_fine1 + " is the fine for case 2 ");

hm.put(Loan_id, estimated_fine1);



estimated_fine.add(estimated_fine1);
}
if(date_in.before(due_datefinal))
{
long days_case1=daysBetween(date_in,due_datefinal);
System.out.println(days_case1 + " are teh number of days for case 1");
fine1=(float) (days_case1*0.25);
System.out.println(fine1 + " is the fine for case 1 ");


hm.put(Loan_id, fine1);

fine.add(fine1);
}

 
                      
                      
                      
                      
                      
                  }catch(NullPointerException e) 
        {
                             Date today= new Date();
  //date_out=date;
                         cal.setTime(today);
                  date_today=cal.getTime();
           
                     Date date_due1=sdf.parse(due_date);
                      cal.setTime(date_due1);
                        due_datefinal= cal.getTime();
             
             long days_case2=daysBetween(date_today,due_datefinal);
            System.out.println(days_case2 + " are teh number of days for case of exception");
            estimated_fine1=(float) (days_case2*0.25);
            System.out.println(estimated_fine1 + " is the fine for case 2 ");
            hm.put(Loan_id, estimated_fine1);

            estimated_fine.add(estimated_fine1);
        }
                  
                  
                   if(search_fine(Loan_id)==false)
                   {
                       System.out.println("the Loan_id "+Loan_id+" has amount : "+ (float)hm.get(Loan_id));
                  update_fine(Loan_id,(float)hm.get(Loan_id));
                  update_paid(Loan_id,false);
                      
                   }
                 
                  
                 else
                      if(search_fine(Loan_id)==true)
                      {
                       
                      
                      }
                      
                      
                  
                  
                  //pay button
                 
                  
                  //update button
                  
                  
                  
                  
                  
                 
                 // 
                 // System.out.println(date_in + " oh yeah" + due_date + " due date");
                 
  
                
               }
      }catch(SQLException se){se.printStackTrace();}
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
   
   public static void payment_method(String Loan_id,float amount)
   {
   
    Connection conn = null;
   Statement stmt = null;
   
    Calendar cal = Calendar.getInstance();
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
     // System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);
//System.out.println("Connected");
      //STEP 4: Execute a query
    //  System.out.println("Creating statement...");
      stmt = conn.createStatement();
      
      
      
           String sql=" select * from book_loans a INNER JOIN fines b ON a.Loan_id=b.Loan_id where b.Loan_id='"+Loan_id+"';"  ;
     
            try (ResultSet rs = stmt.executeQuery(sql)) {
               //STEP 5: Extract data from result set
               while (rs.next()) 
               {
                   //String date_in = "";
                  String Isbn=rs.getString("Isbn10");
                  String Loan_id1=rs.getString("Loan_id");
                  String card_id=rs.getString("card_id");
                  String date_out=rs.getString("date_out");
                   due_date= rs.getString("due_date");
                 String date_in=rs.getString("date_in");
                 float amt=rs.getFloat("fine_amt");
                         
                 if(!date_in.isEmpty())
                 {
                     if(amount == amt || amount>=amt){
                 update_fine(Loan_id,0);
                 update_paid(Loan_id,true);
                 
                 System.out.print("The amount has been paid");
                 amount_status="The amount has been paid";
                         }else if(amount != amt || amount <=amt)
                         {
                         amount_status="The full amount has not  been paid";
                         }
                 }
                 
                   
                
               }
      }catch(SQLException se){se.printStackTrace();}
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
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   public static void insert_fines(String Loan_id,float fine_amt)
   {
    Connection conn = null;
   Statement stmt = null;
   
    Calendar cal = Calendar.getInstance();
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
      
       String sql=" insert into fines values('"+ Loan_id+"' , '"+fine_amt+"' , '0' );"  ;
     
            try {
                int rs=stmt.executeUpdate(sql);
               //STEP 5: Extract data from result set
             error="data inserted";
             
      }catch(SQLException se){se.printStackTrace();}
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
   
   public static void update_fine(String Loan_id,float fine_amt)
   {
   
   Connection conn = null;
   Statement stmt = null;
   
   System.out.println(Loan_id + "and " + fine_amt);
   //Calendar cal = Calendar.getInstance();
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
     // System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);
System.out.println("update_fine ...Connected");
      //STEP 4: Execute a query
    //  System.out.println("Creating statement...");
      stmt = conn.createStatement();
      // String paid = null;
      //Paid=false;
       String sql=" Update fines SET fine_amt='"+fine_amt+"' where Loan_id='"+Loan_id+"' ;"  ;
     
            try {
                int rs=stmt.executeUpdate(sql);
               //STEP 5: Extract data from result set
             error="data updated";
      }catch(SQLException se){se.printStackTrace();}
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
      }//end finally t
   }
   }
   
   
   public static void update_paid(String Loan_id,Boolean Paid)
   {Connection conn = null;
   Statement stmt = null;
   int paid=1;
    //Calendar cal = Calendar.getInstance();
    if(Paid == true)
    {
    paid=1;
    }else if(Paid== false)
    {
    paid=0;
    }
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
    //  System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);
System.out.println("Update paid ..Connected");
      //STEP 4: Execute a query
    //  System.out.println("Creating statement...");
      stmt = conn.createStatement();
      // String paid = null;
      //Paid=false;
       String sql=" Update fines SET Paid= '"+paid+"' where Loan_id='"+Loan_id+"' ;"  ;
     
            try {
                int rs=stmt.executeUpdate(sql);
               //STEP 5: Extract data from result set
             error="data updated";
      }catch(SQLException se){se.printStackTrace();}
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
   
  
   
  public static boolean search_fine(String Loan_id)
  {
      boolean output= false;
  Connection conn = null;
   Statement stmt = null;
   
    Calendar cal = Calendar.getInstance();
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
      // String paid = null;
      //Paid=false;
       String sql=" Select * from fines where Loan_id= '"+Loan_id+"';"  ;
     
            try(ResultSet rs=stmt.executeQuery(sql)) {
                boolean  val=(rs.next());
                if (val==false)
                {
                insert_fines(Loan_id,(float)hm.get(Loan_id));
                }
                else if (val==true)
                
                {while(val)
                {
                local_amount=rs.getFloat("fine_amt");
                error="found";
                boolean paid=rs.getBoolean("Paid");
                output=paid;
                
                val=rs.next();
                }
                }
               //STEP 5: Extract data from result set
             
      }catch(SQLException se){se.printStackTrace();}
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
         return output;
  
  } 
   
   
   public static void display_fines()
   {
   
    Connection conn = null;
   Statement stmt = null;
   
   // Calendar cal = Calendar.getInstance();
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
      // String paid = null;
      //Paid=false;
       String sql=" Select b.card_id,SUM(a.fine_amt) as Amount from fines a INNER JOIN book_loans b ON a.Loan_id=b.Loan_id  GROUP BY b.card_id HAVING SUM(a.fine_amt)"  ;
      String set="";
            try(ResultSet rs=stmt.executeQuery(sql)) {
                while(rs.next())
                {
                String amount=rs.getString("amount");
               // String Loan_id=rs.getString("loan_id");
                String card_id1=rs.getString("card_id");
                set= " Card ID : "+ card_id1+ " Amount;" + amount;
               // System.out.print(set);
                fine_data.add(set);
                }
                
              System.out.print(fine_data.get(2));
               
                
               //STEP 5: Extract data from result set
             
      }catch(SQLException se){se.printStackTrace();}
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
   
   
   public static void delete_fines(String Loan_id)
   {
   float amount = fine_amount;
   
   search_fine(Loan_id);
   float difference;
         difference = (fine_amount-local_amount);
         System.out.println("the difference is "+difference );
         error= ""+difference+ "  ";
         if(difference==0)
         
         
         {
          Connection conn = null;
   Statement stmt = null;
   
   // Calendar cal = Calendar.getInstance();
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
     // System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);
//System.out.println("Connected");
      //STEP 4: Execute a query
    //  System.out.println("Creating statement...");
      stmt = conn.createStatement();
      // String paid = null;
      //Paid=false;
          String sql=" delete from fines where Loan_id= '"+Loan_id+"';"  ;
     
            try(ResultSet rs=stmt.executeQuery(sql)) {
                while(rs.next())
                {
                System.out.print("deleted");
                error+="deleted";
                }
                
               //STEP 5: Extract data from result set
             
      }catch(SQLException se){se.printStackTrace();}
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
         
         }else 
         
         {
         update_fine(Loan_id,difference);
         }
       
   }
   
   
   
   public static int daysBetween(Date d1, Date d2){
             return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
     }
}
