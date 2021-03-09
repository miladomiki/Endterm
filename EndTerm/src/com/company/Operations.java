package com.company;

import java.util.NoSuchElementException;
import java.sql.*;

public interface Operations {
    public default Product addProductFromDB(String name){
        String itemSql = "SELECT * FROM Books WHERE name=? AND amount>0"; //Find the book by it's name and check that amount is enough
        PreparedStatement statement;
        try {
            statement = DBMS.getConnection().prepareStatement(itemSql);
            statement.setString(1,name);
            ResultSet resultProduct = statement.executeQuery();

            while (resultProduct.next()) {
                /** Create a Book object with data from Database */
                Book newBook = new Book(resultProduct.getString("name"),
                        resultProduct.getInt("price"),
                        resultProduct.getString("description"),
                        resultProduct.getInt("amount"),
                        resultProduct.getString("creator"));
                if (newBook.getAmount() > 0 && deductQuantity(name) == true) //check if we have enough books and then deduct this book from DB
                    return newBook;
                else
                    return null;
            }
        } catch (Exception e) {
            throw new NoSuchElementException("Couldn't find the book.");
        }
        return null;
    }

    public default boolean deductQuantity(String name){
        String deductSql = "UPDATE Books SET amount = amount - 1 WHERE name=?"; //Since book is added to cart, we have to update the record in DBMS
        PreparedStatement statement;
        try {
            statement = DBMS.getConnection().prepareStatement(deductSql);
            statement.setString(1, name);

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated>0; //if it's true - then the update was successfull
        } catch (Exception ex){
            System.out.println("Couldn't deduct amount.");
            return false;
        }
    }

    public default boolean addQuantity(String name){
        String deductQuantity = "UPDATE Books SET amount = amount + 1 WHERE name=?"; //If the cart is cleaned or the product was deleted from the cart, we return it to DB
        PreparedStatement statement;
        try {
            statement = DBMS.getConnection().prepareStatement(deductQuantity);
            statement.setString(1, name);

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated>0; //if it's true - then the update was successfull
        } catch (Exception ex){
            System.out.println("Couldn't add amount.");
            return false;
        }
    }
}
