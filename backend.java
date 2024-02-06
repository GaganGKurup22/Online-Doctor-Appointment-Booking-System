package Frontend;

import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
public class backend
{
    String name; 
    String age ;
    String phone;
    Date selectedDate;
    Connection connection=null;
    PreparedStatement preparedStatement;
    String doctor;
    String url="jdbc:mysql://localhost:3306/doctorbooking";
    String usernmae="root";
    String password="1234";
    String withoutDoubts;
    String doctorFormatted;
    String query;
    String formattedDate;

    backend(String name,String age,String phone,Date selecetdDate,String doctor)
    {
        this.name=name;
        this.age=age;
        this.phone=phone;
        this.selectedDate=selecetdDate;
        this.doctor=doctor;
    }
    public void editdata() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        withoutDoubts = doctor.replaceAll(" ", "");
        doctorFormatted = withoutDoubts.replaceAll(".", "");
        formattedDate = sdf.format(selectedDate);
    }
    public void setquery()
    {
        editdata();
        if(doctor.equals("Dr. Smith"))
        {
            query=  "INSERT INTO doctorbooking.drsmith (Name, Age, PhoneNumber, Date) VALUES (?, ?, ?, ?)";
        }
        else if(doctor.equals("Dr. Johnson"))
        {
            query=  "INSERT INTO doctorbooking.drjohnson (Name, Age, PhoneNumber, Date) VALUES (?, ?, ?, ?)";
        }
        else if(doctor.equals("Dr. Williams"))
        {
            query=  "INSERT INTO doctorbooking.drwilliams (Name, Age, PhoneNumber, Date) VALUES (?, ?, ?, ?)";
        }
        else if(doctor.equals("Dr. Jones"))
        {
            query=  "INSERT INTO doctorbooking.drjones (Name, Age, PhoneNumber, Date) VALUES (?, ?, ?, ?)";
        }
        else if(doctor.equals("Dr. Brown"))
        {
            query=  "INSERT INTO doctorbooking.drbrown (Name, Age, PhoneNumber, Date) VALUES (?, ?, ?, ?)";
        }
        else if(doctor.equals("Dr. Davis"))
        {
            query=  "INSERT INTO doctorbooking.drdavis (Name, Age, PhoneNumber, Date) VALUES (?, ?, ?, ?)";
        }
        else if(doctor.equals("Dr. Miller"))
        {
            query=  "INSERT INTO doctorbooking.drmiller (Name, Age, PhoneNumber, Date) VALUES (?, ?, ?, ?)";
        }

    }
   public void adddata() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, usernmae, password);
            setquery();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, Integer.parseInt(age));
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, formattedDate);
            int check = preparedStatement.executeUpdate();
            System.out.println(check + " Rows affected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
