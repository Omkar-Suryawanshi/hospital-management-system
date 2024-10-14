/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospital.management.system;
import java.util.Scanner;
import java.sql.*;

/**
 *
 * @author Test
 */
public class Patient {
    private Connection connection;
    private Scanner scanner;
    
    public Patient(Connection connection, Scanner scanner)
    {
        this.connection = connection;
        this.scanner = scanner;
    }
    public void addPatient()
    {
       System.out.print("Enter Patient Name : ");
       String name = scanner.next();
       System.out.print("Enter Patient Age : ");
       int age = scanner.nextInt();
       System.out.print("Enter Patient Gender :");
       String gender = scanner.next();
       
       
       try{
           String query = "insert into patients (name,age,gender) values (?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,name);
            pstmt.setInt(2,age);
            pstmt.setString(3,gender);
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows >0)
            {
                System.out.println("Patient added successfully.");
            }
            else
            {
                System.out.println("Faild to add patient");
            }
            
           
           
       }
       catch(SQLException e){
           e.printStackTrace();
       }
    }
    public void viewPatient()
    {
        String query = "select * from patients";
        try
        {
            PreparedStatement pstmt  = connection.prepareStatement(query);
            ResultSet resultset = pstmt.executeQuery();
            System.out.println("Patient : ");
            System.out.println("+--------+--------------------------------+------------+-------------+");
            System.out.println("| ID     |            Name                |    Age     | Gender      |");
            System.out.println("+--------+--------------------------------+------------+-------------+");
            while(resultset.next())
            {
                int id = resultset.getInt("id");
                String name = resultset.getString("name");
                int age = resultset.getInt("age");
                String gender = resultset.getString("gender");
                System.out.printf("|%-8s|%-32s|%-12s|%-13s|\n",id,name,age,gender);
                
                System.out.println("+--------+--------------------------------+------------+-------------+");
                

            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public boolean getPatientByID(int id)
    {
        String query = "SELECT * from PATIENTS where id = ?";
        try
        {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,id);
            ResultSet resultset = pstmt.executeQuery();
            if(resultset.next())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return true;
        
    }
    
}
