package com.company;

public class Main {
    public static void main(String[] args) {
        DBMS.ConnectToDBMS(); //We connect to the database

        Menu menu = new Menu();
        menu.mainMenu(); //Start mainMenu

        DBMS.CloseTheDBMS(); //Close the connection to the database
    }
}
