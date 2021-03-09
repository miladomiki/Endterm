package com.company;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Menu implements AccountOperations, Operations, displayItems{

    public Menu(){}

    public void mainMenu(){
        Account userAccount = new Account();
        Scanner scanner = new Scanner(System.in);
        int x = 0;
        while(x<1 || x>31) {
            System.out.println("We are happy to welcome you in our book store!\n" +
                    "First, we aks you to log in ore register!\n" +
                    "You can choose one of the following options:\n" +
                    "1)Login.\n" +
                    "2)Register.\n" +
                    "3)Exit.");
            try {
                x = scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("Incorrect input.");
            } finally {
                scanner.nextLine();
            }
        }
        if(x == 1) {
            userAccount = loginAccount();
            if (userAccount == null) {
                System.out.println("Account wasn't found. Please try again.");
                mainMenu(); //Menu is restarted in this case
            } else
                System.out.println("You have successfully entered your account!");
        }
        else if(x == 2) {
            try {
                userAccount = createAccount();
                if (userAccount == null) {
                    mainMenu(); //Menu is restarted in case user couldn't register
                }
            } catch (Exception ex) {
                System.out.println(ex);
                mainMenu(); //Menu is restarted if we get an exception
            }
        }
        else if(x == 3)
            return;

        x = 0;
        while(x!=9){
            System.out.println("Please choose one of the following options:\n" +
                    "1)Display products.\n" +
                    "2)Add book to your cart.\n" +
                    "3)Delete an item from your cart.\n" +
                    "4)Print your cart.\n" +
                    "5)Buy products in your cart.\n" +
                    "6)Deposit money to the wallet.\n" +
                    "7)Display money in your wallet.\n" +
                    "8)Delete all items from your cart.\n" +
                    "9)Exit the application");
            try {
                x = scanner.nextInt();
            } catch (Exception ex) {
                System.out.println("Input is incorrect.");
            } finally {
                scanner.nextLine();
            }

            if(x==1) {
                displayBooks(); //Interface method that shows every book in the table
            }
            else if(x == 2) {
                if (userAccount.getUserCart().addProductToCart(addProduct())) //After getting the Book from DB it adds this book to the cart using Cart class' method
                    System.out.println("You have successfully added a book to your cart!");
                else
                    System.out.println("Book wasn't added.");
            }
            else if(x == 3){
                userAccount = deleteProduct(userAccount); //We recieve a new instance of user without the product
            }
            else if(x == 4) {
                userAccount.getUserCart().printCart();
            }
            else if(x == 5){
                userAccount = buyCart(userAccount); //We recieve a new instance of user after all of the items in the cart were bought
                //Cart is now empty and wallet has been deducted by the sum of cart
            }
            else if(x == 6){
                userAccount = addToWallet(userAccount); //Adds aditional money to the user's wallet
            }
            else if(x == 7){
                System.out.println("You have " + userAccount.getWallet() + " in your wallet.");
            }
            else if(x == 8) {
                userAccount = cleanCart(userAccount); //Returns a new userAccount with clean cart
            }
            else if(x == 9) {
                System.out.println("Thanks for visitting us!");
                return;
            }
        }
    }

    public Account createAccount() throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        String newEmail, newPassword, newName;
        Integer newWallet;
        System.out.println("To register, please enter your email: ");
        newEmail = scanner.nextLine();
        System.out.println("Next, you have to enter your password: ");
        newPassword = scanner.nextLine();
        System.out.println("Please enter your name: ");
        newName = scanner.nextLine();
        try{
            System.out.println("Please enter your balance for your wallet: ");
            newWallet = scanner.nextInt();
        } catch (Exception ex){
            throw new InputMismatchException("Something went wrong. Integer expected.");
        } finally {
            scanner.nextLine();
        }

        Account userAccount = new Account(newEmail, newPassword, newName, newWallet); //Creates new Account and stores it in the DB
        if(userAccount.accountRegistration(userAccount)) //If accountRegistration returns true - that means the operation was successfull
            return userAccount;
        else
            return null;
    }

    public Account loginAccount(){
        Scanner scanner = new Scanner(System.in);
        String yourEmail, yourPassword;
        System.out.println("Enter the email of your account: ");
        yourEmail = scanner.nextLine();
        System.out.println("Enter the password of your account: ");
        yourPassword = scanner.nextLine();

        try{
            Account userAccount = loginAccount(yourEmail, yourPassword);
            return userAccount;
        }catch (NoSuchElementException ex){
            System.out.println(ex);
            return null;
        }
    }

    public Product addProduct(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the book you wish to add: ");
        String itemName = scanner.nextLine();
        return addProductFromDB(itemName); //Calls the method in Operations interface that gets the Book from DBMS
        //And returns this book as an object Product
    }

    public Account deleteProduct(Account userAccount){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the book you wish to delete from your cart: ");
        String productName = scanner.nextLine();
        if(userAccount.getUserCart().deleteItemFromCart(productName)) //If the operation was a success - we get true
            System.out.println("Delete was successfull!");
        else
            System.out.println("Couldn't delete this item.");
        return userAccount; //Return new account without this book
    }


    public Account addToWallet(Account userAccount){
        Scanner scanner = new Scanner(System.in);
        Integer addMoney = 0;
        try{
            System.out.println("Please enter how much you wish to add to your wallet: ");
            addMoney = scanner.nextInt();
        } catch (Exception e){
            System.out.println("Please enter an integer number.");
            userAccount = addToWallet(userAccount); //If we get an exception, restart operation and later return the user
            return userAccount;
        } finally {
            scanner.nextLine();
        }
        userAccount.depositToWallet(addMoney);
        return userAccount; //Return the user with updated wallet
    }

    public Account buyCart(Account userAccount){
        if(userAccount.getUserCart().isEmpty() == false){ //Check that cart isn't empty
            if(userAccount.getWallet() >= userAccount.getUserCart().totalSum()) { //Then we have to check that the user has enough balance
                userAccount.setWallet(userAccount.getWallet() - userAccount.getUserCart().totalSum()); //Deduct the sum of order from the wallet and update the user in DBMS
                userAccount.getUserCart().clearCart(); //Afterwards, we just clean the cart
                System.out.println("You have successfully bought products from the cart.");
            }
            else{
                System.out.println("Wallet's balance is not enough.");
            }
        }
        else{
            System.out.println("User's cart is empty. Can't perform the buy operation");
        }
        return userAccount; //returns the account with updated wallet and clean cart
    }

    public Account cleanCart(Account userAccount){
        if(userAccount.getUserCart().isEmpty() == false){ //Check if the cart is already empty
            for(int i = 0; i < userAccount.getUserCart().getLength(); i++){
                addQuantity(userAccount.getUserCart().get(i).getName()); //Since we clean the cart, we first have to return all of the products to the database
            }
            userAccount.getUserCart().clearCart(); //After all of the items were returned, we just clean the cart of all the products
            System.out.println("Cart is cleaned.");
        }
        else{
            System.out.println("Cart is already empty.");
        }
        return userAccount; //Return the account with clean cart
    }
}
