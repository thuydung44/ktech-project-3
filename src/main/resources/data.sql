INSERT INTO category (name) VALUES ('food');
INSERT INTO category (name) VALUES ('electronics');
INSERT INTO category (name) VALUES ('fashion');
INSERT INTO category (name) VALUES ('beauty');
INSERT INTO category (name) VALUES ('home');


-- User Table
--INSERT INTO user_table (id, nickname, name, username, age, email, phone, password, passworkCheck, profileImgUrl, authorities, businessStatus) VALUES
--(1, 'JohnDoe', 'John Doe', 'johndoe', 30, 'john.doe@example.com', '123-456-7890', 'password123', 'NULL', 'NULL', 'ROLE_USER', 'NONE'),
--(2, 'JaneSmith', 'Jane Smith', 'janesmith', 28, 'jane.smith@example.com', '987-654-3210', 'password456', 'NULL', 'NULL', 'ROLE_BUSINESS', 'APPROVED'),
--(3, 'AliceJohnson', 'Alice Johnson', 'alicejohnson', 34, 'alice.johnson@example.com', '555-123-4567', 'password789', 'NULL', 'NULL', 'ROLE_USER', 'PENDING'),
--(4, 'BobBrown', 'Bob Brown', 'bobbrown', 40, 'bob.brown@example.com', '555-765-4321', 'password101', 'NULL', 'NULL', 'ROLE_USER', 'REJECTED'),
--(5, 'CharlieDavis', 'Charlie Davis', 'charliedavis', 29, 'charlie.davis@example.com', '555-999-0000', 'password202', 'NULL', 'NULL', 'ROLE_USER', 'PENDING');
--
---- Shop Table
--INSERT INTO shop_table (id, name, description, category, owner_id, ownerStatus) VALUES
--(1, 'Gourmet Bistro', 'A fine dining restaurant with an upscale menu', 'food', 1, 'REQUESTED'),
--(2, 'Tech Haven', 'Latest gadgets and electronics at great prices', 'electronics', 1, 'APPROVED'),
--(3, 'Fashion World', 'Trendy clothing and accessories', 'fashion', 3, 'APPROVED'),
--(4, 'Book Nook', 'A cozy bookstore with a wide selection', 'beauty', 4, 'REQUESTED'),
--(5, 'Home Essentials', 'Everything you need for your home', 'home', 5, 'PENDING');
--
---- Product Table
--INSERT INTO product (id, name, price, description, stock, shop_id, productImgUrl) VALUES
--(1, 'Deluxe Burger', 12.99, 'A gourmet burger with premium ingredients', 50, 1, '/images/burger.jpg'),
--(2, 'Smartphone X', 899.99, 'Latest model with high-end features', 30, 2, '/images/smartphone.jpg'),
--(3, 'Winter Coat', 120.00, 'Warm and stylish winter coat', 25, 3, '/images/wintercoat.jpg'),
--(4, 'Mystery Novel', 15.00, 'A thrilling mystery novel', 40, 4, '/images/mysterynovel.jpg'),
--(5, 'Coffee Maker', 55.00, 'High-quality coffee maker for home use', 15, 5, '/images/coffeemaker.jpg');
--
---- Order Table
--INSERT INTO orders_table (id, product_id, buyer_id, quantity, totalAmount, orderStatus, paid) VALUES
--(1, 1, 1, 2, 25.98, 'PENDING', FALSE),
--(2, 2, 2, 1, 899.99, 'PENDING', FALSE),
--(3, 3, 3, 1, 120.00, 'PENDING', FALSE),
--(4, 4, 4, 3, 45.00, 'PENDING', FALSE),
--(5, 5, 5, 1, 55.00, 'PENDING', FALSE);
