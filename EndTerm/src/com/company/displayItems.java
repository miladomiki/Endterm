package com.company;

import java.sql.*;

public interface displayItems {
    public default void displayBooks(){
        String allSql = "SELECT * FROM Books ORDER BY name ASC";
        Statement statement;
        try {
            statement = DBMS.getConnection().createStatement();
            ResultSet resultAll = statement.executeQuery(allSql);

            while (resultAll.next()) {
                System.out.println("++++++++++++++++++++++++++" +
                        "\nBook's name: " + resultAll.getString("name") +
                        "\nPrice: " + resultAll.getInt("price") +
                        "\nDescription: " + resultAll.getString("description") +
                        "\nQuantity: " + resultAll.getInt("amount") +
                        "\nAuthor: " + resultAll.getString("creator"));
            }
            System.out.println("++++++++++++++++++++++++++");
        } catch (Exception e) {
            System.out.println("Couldn't display all books.");
            return;
        }
    }
}
