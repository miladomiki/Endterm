CREATE TABLE Books(
	booksId SERIAL PRIMARY KEY,
	name VARCHAR(100) UNIQUE,
	price INT,
	description TEXT,
	amount INT,
	creator VARCHAR(100)
);