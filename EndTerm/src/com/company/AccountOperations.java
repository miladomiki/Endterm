package com.company;

import java.sql.*;
import java.util.NoSuchElementException;

public interface AccountOperations {
    public default boolean accountRegistration(Account userAccount){
        String addSql = "INSERT INTO Accounts (email, password, name, wallet) VALUES (?, ?, ?, ?)";
        /** We register new accounts by inserting them into the DBMS */
        PreparedStatement statement;
        try {
            statement = DBMS.getConnection().prepareStatement(addSql);
            statement.setString(1, userAccount.getEmail());
            statement.setString(2, userAccount.getPassword());
            statement.setString(3, userAccount.getName());
            statement.setInt(4, userAccount.getWallet());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("New account was registered!");
            }
        } catch (Exception e) {
            System.out.println("Couldn't register a new account.");
            return false;
        }
        return true;
    }

    public default Account loginAccount(String email, String password) throws NoSuchElementException {
        String login = "SELECT * FROM Accounts WHERE email=? and password=?";
        PreparedStatement statement;
        try {
            statement = DBMS.getConnection().prepareStatement(login);
            statement.setString(1,email);
            statement.setString(2,password);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                /** We are creating new user with data we get from DBMS */
                Account userAccount = new Account(result.getString("email"),
                        result.getString("password"),
                        result.getString("name"),
                        result.getInt("wallet"));
                return userAccount;
            }
        } catch (Exception e) {
            throw new NoSuchElementException("Account wasn't found.");
        }
        return null;
    }

    public default boolean updateWallet(Account userAccount){
        String update = "UPDATE Accounts SET wallet=? WHERE email=?";
        PreparedStatement statement;
        try {
            statement = DBMS.getConnection().prepareStatement(update);
            statement.setLong(1,userAccount.getWallet());
            statement.setString(2,userAccount.getEmail());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Couldn't deposit money to the wallet.");
        }
        return false;
    }
}
