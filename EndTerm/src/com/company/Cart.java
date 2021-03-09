package com.company;

import java.util.ArrayList;

public class Cart implements Operations {
    private ArrayList<Product> userCart;

    public Cart(){
        userCart = new ArrayList<Product>();
    }

    public int getLength(){
        return userCart.size();
    }

    public Product get(int i){
        return userCart.get(i);
    }

    public boolean addProductToCart(Product obj){
        if(obj!=null)
            return userCart.add(obj);
        else
            return false;
    }

    public boolean deleteItemFromCart(String name){
        /** Items are removed by their name, since user shouldn't know the id of items
         * And names are Unique*/
        for(int i = 0; i < userCart.size(); i++){
            if(userCart.get(i).getName().equals(name)) { //Using equals since == doesn't work properly (compares if objects are the same instances)
                userCart.remove(i);
                addQuantity(name); //Calls for the interface method to add quantity to SQL database
                return true; //Break from the method and return true if the item was found and deleted
            }
        }
        return false; //Returns false if the item wasn't deleted
    }

    public void printCart(){
        for(int i = 0; i < userCart.size(); i++)
            System.out.println("------------------------" +
                    "\nName: " + userCart.get(i).getName() +
                    "\nPrice: " + userCart.get(i).getPrice() +
                    "\nDescription: " + userCart.get(i).getDescription());
        System.out.println("------------------------\n" +
                "Total sum is: " + totalSum());

    }

    public Integer totalSum(){
        Integer sum = 0;
        for(int i = 0; i < userCart.size(); i++)
            sum += userCart.get(i).price;
        return sum;
    }

    public boolean isEmpty(){
        return userCart.isEmpty();
    }

    public void clearCart(){
        userCart.clear();
    }
}
