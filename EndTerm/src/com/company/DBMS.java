package com.company;

import java.sql.*;

public class DBMS {
    private static Connection connection = null;

    public static boolean ConnectToDBMS() {
        String postgresUrl = "jdbc:postgresql://localhost:5432/Bookstore";
        /** Username and password are stored here */
        String userName = "postgres";
        String password = "postgres";
        try {
            connection = DriverManager.getConnection(postgresUrl,userName,password);
        }
        catch (Exception e) {
            System.out.println("Couldn't connect to the Bookstore Database.");
        }
        return connection!=null; //Returns true if we connected to DBMS
    }

    public static void CloseTheDBMS() {
        try {
            connection.close();
        }
        catch (Exception e) {
            System.out.println("An error occured when trying to close the connection");
        }
    }

    public static Connection getConnection(){
        return connection;
    }
}
