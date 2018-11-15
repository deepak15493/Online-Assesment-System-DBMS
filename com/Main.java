package com.company;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Main {

    static Connection connection;
    public static void main(String[] args) {
	// write your code here

        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@orca.csc.ncsu.edu:1521:ORCL01","dpatil","200205114");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Login login = new Login(connection);
    }
}
