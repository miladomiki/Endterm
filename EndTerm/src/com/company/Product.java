package com.company;

public abstract class Product {
    /** Abstract class Product is used in case we want to add new products aside from books */
    protected String name;
    protected Integer price;
    protected String description;
    protected int amount;

    public Product(){
        this.name = null;
        this.price = 0;
        this.description = null;
        this.amount = 0;
    }

    public Product(String name, int price, String description, int amount){
        this.name = name;
        this.price = price;
        this.description = description;
        this.amount = amount;
    }

    public abstract void setName(String name);

    public abstract String getName();

    public abstract void setPrice(int price);

    public abstract Integer getPrice();

    public abstract void setDescription(String description);

    public abstract String getDescription();

    public abstract void setAmount(int amount);

    public abstract int getAmount();
}
