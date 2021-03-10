# Endterm
SE-2009 Kusherbaeva Diana and Dana Kopzhassarova
The programm starts from connecting to the database "Bookstore" using DBMS class.
Next we call main menu, which greets user and calls other methods depending on the user's choices.
Store works only with books, but other products could be used because we used an abstract class Product. 
To add other products, they should extend Product class.
Account class is for users and to store their data. 
Each account has cart with which we can perform operations like add to cart, delete from cart, clean cart and display cart. 
Cart uses an arraylist to store products.
Cart has its own interface to perform operations like add product from database and work with amount of products.
Account class has interface that helps with creating new accounts, loging into an existing account and working with wallet.
