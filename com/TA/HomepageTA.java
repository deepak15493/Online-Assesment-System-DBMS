package com.company.TA;

import com.company.Login;
import oracle.jdbc.OracleCallableStatement;
import sun.rmi.runtime.Log;

import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class HomepageTA {
    private String username;
    private String password;
    private Connection connection;
    private int input;
    private String inputString;
    private Login lastPage;
    private String userId;
    Scanner reader = new Scanner(System.in);
    public HomepageTA(Connection _connection, String _userId, Login _lastPage)
    {
        show("Welcome TA!");
        this.lastPage=_lastPage;
        this.connection=_connection;
        this.userId=_userId;
        showHomeMenu();
    }
    public void showHomeMenu()
    {
        show("Enter 1 to View/Edit Profile");
        show("Enter 2 to View Courses");
        show("Enter 3 to Enroll/Drop Student");
        show("Enter 4 to View Student REport");
        show("Enter your option");
        input=getInt();
        if(input==1)
        {
            viewProfile();
        }
        if(input==2)
        {
            viewAddCourses();
        }
        if(input==3)
        {
            enrollDropStudent();
        }
        if(input==4)
        {
            viewStudentReport();
        }
        else
        {
            showHomeMenu();
        }
    }

    public void viewStudentReport() {
        try {
            String studentID="";
            String studentFirstName="";
            String studentLastName="";
            show("Please enter studentID or student's full name to view the report : Ex. aadp2 or bruce banner");
            inputString = getStr();
            String[] elements = inputString.split(" ");
            if(elements.length==1)
            {
                studentID = elements[0];
            }

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////

            OracleCallableStatement statement;
            String sp="{call SP_VIEWPROFILE (?,?)}";
            statement = (OracleCallableStatement) connection.prepareCall(sp);
            statement.setString(1,studentID);
            statement.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);
            statement.execute();
            ResultSet rs = statement.getCursor(2);
            if(rs!=null) {
                while (rs.next())
                {
                    studentFirstName=rs.getString(1);
                    studentLastName=rs.getString(2);
                    show("UserID :  " + studentID);
                    show("FirstName : " + studentFirstName);
                    show("LastName : " + studentLastName);
                }
            }

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////

            OracleCallableStatement statement2;
            String sp2="{call SP_VIEWPROFSTUDENTCOURSES" + " (?,?,?)}";

            statement2 = (OracleCallableStatement) connection.prepareCall(sp2);
            statement2.setString(1,userId.toLowerCase());
            statement2.setString(2,studentID);
            statement2.registerOutParameter(3,oracle.jdbc.OracleTypes.CURSOR);
            statement2.execute();
            ResultSet rs2 = statement2.getCursor(3);
            HashMap<Integer,String> courses= new HashMap<Integer, String>();
            int i=1;
            String courseName="";
            String courseId="";
            if(rs2!=null) {
                while (rs2.next())
                {
                    courseId=rs2.getString(1);
                    courseName=rs2.getString(2);
                    courses.put(i,courseId);
                    show(i+" : " + courseName);
                    i++;
                }
            }
            show("Enter the course number you want to view for a student:");
            input=getInt();

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if(input!=0)
            {
                OracleCallableStatement statement3;
                String sp3="{call SP_VIEWSTUDENTCOURSESCORES" + " (?,?,?)}";

                statement3 = (OracleCallableStatement) connection.prepareCall(sp3);
                statement3.setString(1,studentID);
                statement3.setString(2,(String)courses.get(input));
                statement3.registerOutParameter(3,oracle.jdbc.OracleTypes.CURSOR);
                statement3.execute();
                ResultSet rs3 = statement3.getCursor(3);
                HashMap<String,String> homeworks= new HashMap<String, String>();
                String homework="";
                String score="";
                if(rs3!=null) {
                    while (rs3.next())
                    {
                        homework=rs3.getString(1);
                        score=rs3.getString(2);
                        show("Hemowork  "+homework+" : " + score);
                    }
                }


                show("Please any key to return:");
            }



            ////////////////////////////////////////////////////////////////////////////////////////////////////////////


            inputString=getStr();
            showHomeMenu();



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void enrollDropStudent()
    {
        String studentFirstName="";
        String studentLastName="";
        String studentID="";
        String courseID="";
        show("Press 1 to enroll a student");
        show("Press 2 to drop a student");
        input=  getInt();
        if(input==1)
        {
            // Enroll a student
            show("Please enter the details of the student to enroll: UserId CourseID , Ex: aadp1 bruce banner CSC540");
            inputString=getStr();
            String[] elements= inputString.split(" ");
            int i=0;

            studentID= elements[0];
            courseID= elements[1];
            try{
                OracleCallableStatement statement;
                String sp="{call SP_ENROLL_STUDENT(?,?,?,?)}";
                statement = (OracleCallableStatement) connection.prepareCall(sp);
                statement.setString(1,userId.toLowerCase());
                statement.setString(2,studentID);
                statement.setString(3,courseID);
                statement.registerOutParameter(4, Types.VARCHAR);
                statement.execute();
                String  feedback= statement.getString(4);
                show(feedback);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        if(input==2)
        {
            // Drop a student
            show("Please enter the details of the student to drop: UserId CourseID , Ex: aadp1 CSC540");
            inputString=getStr();
            String[] elements= inputString.split(" ");
            int i=0;
            while(elements[i].equals(""))
                i++;
            studentID= elements[i];
            i++;
            while(elements[i].equals(""))
                i++;
            courseID= elements[i];


            OracleCallableStatement statement;
            String sp="{call SP_DROP_STUDENT(?,?,?,?)}";
            try {
                statement = (OracleCallableStatement) connection.prepareCall(sp);
                statement.setString(1,userId.toLowerCase());
                statement.setString(2,studentID);
                statement.setString(3,courseID);
                statement.registerOutParameter(4,Types.VARCHAR);
                statement.execute();
                String feedback=  statement.getString(4);
                show(feedback);

            } catch (SQLException e) {
                e.printStackTrace();
            }
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
    public void viewAddCourses() {
        show("Press 1 to View exsiting courses, and 2 to add a new Course .... Press 0 to go back");
        input = getInt();
        if (input == 1) {

            OracleCallableStatement statement;
            String sp = "{call SP_VIEWTACOURSES (?,?)}";
            try {
                statement = (OracleCallableStatement) connection.prepareCall(sp);
                statement.setString(1, userId.toLowerCase());
                statement.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
                statement.execute();
                ResultSet rs = statement.getCursor(2);
                if (rs != null) {
                    while (rs.next()) {
                        show("CourseID : " + rs.getString(1));
                        show("Course Name : " + rs.getString(2));
                        show("Start Date : " + rs.getString(3));
                        show("End Date : " + rs.getString(4));
                        show("Professor Name : " + rs.getString(5));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (input == 2) {
            // Add  Course
            show("Please enter the details of the course to drop: ");
            show("Enter CourseID");
            String courseID = getStr();
            show("Enter CourseName");
            String courseName = getStr();
            show("Enter StartDate");
            String startDate = getStr();
            show("Enter EndDate");
            String endDate = getStr();


            OracleCallableStatement statement;
            String sp = "{call SP_ADDCOURSE(?,?,?,?,?)}";
            try {
                statement = (OracleCallableStatement) connection.prepareCall(sp);
                statement.setString(1, userId.toLowerCase());
                statement.setString(2, courseID);
                statement.setString(3, courseName);
                statement.setString(4, startDate);
                statement.setString(5, endDate);
                statement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        if (input == 0) {
            showHomeMenu();
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
    public final  void clearScreen()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }
}
