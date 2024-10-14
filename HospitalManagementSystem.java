package hospital.management.system;
import java.sql.*;
import java.util.*;
public class HospitalManagementSystem {

    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String usename = "root";
    private static final String password = "password";
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url,usename,password);

            Patient patient = new Patient(connection, sc);
            Doctors doctors = new Doctors(connection);

            while (true) {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("+------------------------------+");
                System.out.println();
                System.err.println("1.Add Patient");
                System.err.println("2.View Patient");
                System.err.println("3.View Doctors");
                System.out.println("4.Book Appointment");
                System.out.println("5.Exit");
                System.out.println();

                System.out.print("Enter your choice = ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                    //Add Patient
                    patient.addPatient();
                    System.out.println();
                        
                    break;
                    case 2:
                    //View Patient
                    patient.viewPatient();
                    System.out.println();
                            
                    break;
                    case 3:
                    //View Doctor
                    doctors.viewDoctors();
                    System.out.println();
                        
                    break;
                    case 4:
                    //Book Appointment
                    bookAppointment(patient, doctors, connection, sc);
                    System.out.println();
                        
                    break;
                    case 5:
                    //Exit
                        
                    break;    
                
                
                    default:
                    System.out.println("Enter a valid choice");
                    break;
                }
            }
        } catch (SQLException e) {
         e.printStackTrace();
        }

    }
    
    public static void bookAppointment(Patient patient,Doctors doctors,Connection connection, Scanner sc)
    {
        System.out.print("Enter patient ID : ");
        int patientId = sc.nextInt();
        
        System.out.print("Enter Doctor ID : ");
        int doctorId = sc.nextInt();

        System.out.print("Enter appointment date(YYYY-MM-DD) : ");
        String appointmentDate = sc.next();

        if (patient.getPatientByID(patientId) && doctors.getDoctorByID(doctorId)) {
            if(chechDoctorAvailability(doctorId,appointmentDate,connection))
            {

                String appointmentQuery = "insert into appointments(patient_id,doctor_id,appointment_date)values(?,?,?)";

                try {
                    PreparedStatement  preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1,patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);

                    int affectedRows = preparedStatement.executeUpdate();
                    if(affectedRows>0)
                    {
                         System.out.println("Appointment Booked");
                    }
                    else{
                        System.out.println("Faild to book appointment");
                    }
                    
                } catch (SQLException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("Doctor not available on this date!!");
            }
            
        }
        else
        {
            System.out.println("Either doctor or patient doesn't exists!!");
        }


    }

    public static boolean chechDoctorAvailability(int doctorId,String appointmentDate, Connection connection)
   {
    String query = "select count(*) from appointments where doctor_id = ? AND appointment_date = ?";

    try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, doctorId);
        preparedStatement.setString(2, appointmentDate);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            if (count==0) {
                return true;
                
            }
            else{
            return false;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        
    }


    return false;
   } 
}
