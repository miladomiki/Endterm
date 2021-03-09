package com.company;

public class Book extends Product {
    private String author;

    public Book(){
        super();
        this.author = null;
    }

    public Book(String name, int price, String description,int quantity, String author){
        super(name,price,description,quantity);
        this.author = author;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public Integer getPrice() {
        return this.price;
    }

    @Override
    public void setDescription(String description){
        this.description = description;
    }

    @Override
    public String getDescription(){
        return this.description;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public int getAmount(){
        return this.amount;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getAuthor(){
        return this.author;
    }
}
