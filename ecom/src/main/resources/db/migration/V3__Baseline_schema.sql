-- V3__Add_product_price.sql

-- Add the column with a default value of 0.00 so existing rows don't crash
ALTER TABLE products
ADD COLUMN prod_price DECIMAL(19, 2) NOT NULL DEFAULT 0.00;