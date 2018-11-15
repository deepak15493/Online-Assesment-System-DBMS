package com.company;

import com.company.Instructor.HomepageInstructor;
import com.company.Student.HomepageStudent;
import com.company.TA.HomepageTA;
import oracle.jdbc.OracleCallableStatement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

public class Login {
    private String username;
    private String password;
    private String role;
    int input=0;
    String inputString="";
    private Connection connection;
    Scanner reader = new Scanner(System.in);
    public Login(Connection _connection)
    {
        this.connection=_connection;
        showLoginPage();
        reader.close();
    }
    public void showLoginPage()
    {
        show("Enter 1 to login, 2 to signup:");
        input=getInt();
        if(input==1)
        {
            show("Please enter login credentials:");
            show("Username:");
            username=getStr();
            show("Passwords:");
            password=getPassword();
            show("Are you a Professor? Y or N:");
            inputString=getStr();

            if(inputString.equalsIgnoreCase("Y"))
            {
                role="P";
            }
            else
            {
                show("Are you a TA? Y or N:");
                inputString=getStr();
                if(inputString.equalsIgnoreCase("Y"))
                {
                    role="T";
                }
                else
                {
                    role="S";
                }
            }
            show("Enter 1 to login, 0 to retry:");
            int option = getInt();
            if(option==1)
            {
                OracleCallableStatement statement;
                String sp="{call sp_login (?,?,?,?)}";
                try {
                    statement = (OracleCallableStatement) connection.prepareCall(sp);
                    statement.setString(1,username);
                    statement.setString(2,password);
                    statement.setString(3,role);
                    statement.registerOutParameter(4,Types.INTEGER);
                    statement.execute();

                    int found=statement.getInt(4);

                    show(found+"");
                    if (found==1) {
                        if (role.equals("P")) {
                            clearScreen();
                            HomepageInstructor homepage = new HomepageInstructor(connection, username, this);
                        } else {
                            if (role.equals("T")) {
                                clearScreen();
                                HomepageTA homepage = new HomepageTA(connection, username, this);
                            } else {
                                clearScreen();
                                HomepageStudent homepage = new HomepageStudent(connection, username, this);
                            }
                        }
                    }
                    else
                    {
                        showLoginPage();
                    }

                /*else {
                    ResultSet rs = statement.getResultSet();
                    while (rs.next()) {
                        System.out.println("Name : " + rs.getString(3));
                    }
                }*/
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        else if(input==2)
        {
            String firstName="",lastName="";
            show("Please enter SignUp credentials:");
            show("Username:");
            username=getStr();
            show("First Name:");
            firstName=getStr();
            show("Last Name:");
            lastName=getStr();
            show("Passwords:");
            password=getPassword();
            show("Are you a Professor? Y or N:");
            inputString=getStr();

            if(inputString.equalsIgnoreCase("Y"))
            {
                role="P";
            }
            else
            {
                role="S";
            }
            show("Enter 1 to signUp, 0 to retry:");
            int option = getInt();
            if(option==1)
            {
                OracleCallableStatement statement;
                String sp="{call SP_SIGNUP (?,?,?,?,?,?)}";
                try {
                    statement = (OracleCallableStatement) connection.prepareCall(sp);
                    statement.setString(1,username);
                    statement.setString(2,firstName);
                    statement.setString(3,lastName);
                    statement.setString(4,role);
                    statement.setString(5,role);
                    statement.registerOutParameter(6,Types.INTEGER);
                    statement.execute();

                    int found=statement.getInt(6);
                    if(found==2)
                        show("Success");
                    else
                        show("User already exists");
                    showLoginPage();


                /*else {
                    ResultSet rs = statement.getResultSet();
                    while (rs.next()) {
                        System.out.println("Name : " + rs.getString(3));
                    }
                }*/
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        else
            showLoginPage();

    }
    public void show(String statement)
    {
        System.out.println(statement);
    }
    public String getStr()
    {
          // Reading from System.in
        String str="";
        while (str.equals(""))
            str = reader.nextLine(); // Scans the next token of the input as an int.
        return str;
    }

    public String getPassword() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            password = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return password;
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
    public void reopen()
    {
        this.showLoginPage();
    }
}
