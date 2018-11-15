package com.company;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Scanner;

public class HomePage {
    private String username;
    private String password;
    private Connection connection;
    private String userId;
    Scanner reader = new Scanner(System.in);
    public HomePage(Connection _connection,String _userId)
    {
        this.connection=_connection;
        this.userId=_userId;
    }
    public void showHomeMenu()
    {
        show("Enter 1 to View/Edit Profile");
        show("Enter 2 to View Courses");
        show("Enter your option");
        if(getInt()==1)
        {
            viewProfile();
        }
        if(getInt()==2)
        {
            viewCourses();
        }
        else
        {
            showHomeMenu();
        }
    }
    public void viewProfile()
    {
        Statement statement;
        String query="EXEC sp_viewProfile "+userId+"";
        try {
            statement = connection.createStatement();
            ResultSet rs= statement.executeQuery(query);
            while(rs.next())
            {
                String firstname=rs.getString("FirstName");
                String lastname=rs.getString("LastName");
                String studentId=rs.getString("StudentId");
                show("Firstname : "+ firstname);
                show("Lastname : "+ lastname);
                show("StudentId : "+ studentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void viewCourses()
    {
        Statement statement;
        String query="EXEC sp_viewCourses "+userId+"";
        HashMap<String,String> courseMap=new HashMap<String, String>();
        try {
            statement = connection.createStatement();
            ResultSet rs= statement.executeQuery(query);
            while(rs.next())
            {
                courseMap.put(rs.getString(0),rs.getString(1));
                show( rs.getString(0)+" : "+ rs.getString(1));
            }
            show("Press 0 to return else enter course id to view the course details ");
            String courseId = getStr();
            if(courseId.equals("0"))
            {
                showHomeMenu();
            }
            else
            {
                Statement innerStatement;
                String innerQuery="EXEC sp_viewCourseDetails "+courseId+"";
                try
                {
                    innerStatement = connection.createStatement();
                    ResultSet irs= statement.executeQuery(query);
                    while(rs.next())
                    {
                        show( "CourseID : "+rs.getString(0));
                        show( "Course Name : "+rs.getString(1));
                        show( "Start Date : "+rs.getString(2));
                        show( "End Date : "+rs.getString(3));
                        show( "No_of_enrolled students : "+rs.getString(4));
                        show( "Max allowed students : "+rs.getString(5));
                        show( "Professor Name : "+rs.getString(6));
                        show( "Course Level : "+rs.getString(7));
                    }
                    show("Press any key to go back ");
                    String str = getStr();
                    showHomeMenu();
                }
                catch (Exception e)
                {e.printStackTrace();}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void show(String statement)
    {
        System.out.println(statement);
    }
    public String getStr()
    {
        // Reading from System.in
        String str = reader.nextLine(); // Scans the next token of the input as an int.
        return str;
    }
    public int getInt()
    { // Reading from System.in
        String inputString="";
        int input=-1;
        while (input==-1)
        {
            try
            {
                inputString = reader.nextLine();
                input=Integer.parseInt(inputString);
            }
            catch (Exception e)
            {
            }
        } // Scans the next token of the input as an int.
        return input;
    }
}
