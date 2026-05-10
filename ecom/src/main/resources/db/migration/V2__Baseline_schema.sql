-- V2__Add_wallet_and_owners.sql

-- 1. Add wallet balance to existing users table
ALTER TABLE users ADD COLUMN walletbalance DECIMAL(19, 2);

-- 2. Create the completely new product_owners table
CREATE TABLE product_owners (
    username VARCHAR(255) PRIMARY KEY,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255),
    earnings DECIMAL(19, 2)
);

-- 3. Add the foreign key column to the products table
ALTER TABLE products ADD COLUMN owner_username VARCHAR(255);

-- 4. Establish the relationship
ALTER TABLE products
ADD CONSTRAINT fk_product_owner
FOREIGN KEY (owner_username) REFERENCES product_owners(username);

-- 5. Create the missing indexes
CREATE INDEX prod_name_index ON products(prod_name);
CREATE INDEX prod_description_index ON products(prod_description);