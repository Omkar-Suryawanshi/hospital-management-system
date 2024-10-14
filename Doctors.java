package hospital.management.system;
import java.sql.*;

public class Doctors {
    private Connection connection;
    
    public  Doctors(Connection connection)
    {
        this.connection = connection;
    }
    public void viewDoctors()
    {
        String query = "select * from doctors";
        try
        {
            PreparedStatement pstmt  = connection.prepareStatement(query);
            ResultSet resultset = pstmt.executeQuery();
            System.out.println("Doctors :");
            System.out.println("+--------+--------------------------------+--------------------------+");
            System.out.println("| ID     |            Name                |    Specialization        |");
            System.out.println("+--------+--------------------------------+--------------------------+");
            while(resultset.next())
            {
                int id = resultset.getInt("id");
                String name = resultset.getString("name");
                String  specialization = resultset.getString("specialization");
                System.out.printf("|%-8s|%-32s|%-26s|\n",id,name,specialization);
                
                System.out.println("+--------+--------------------------------+--------------------------+");
                

            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public boolean getDoctorByID(int id)
    {
        String query = "SELECT * from doctors where id = ?";
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
        
        return false;
        
    }
    
}
