-- categories table
CREATE TABLE categories(
	category_id		INT			NOT NULL	AUTO_INCREMENT,
	category_name	VARCHAR(50)	NOT NULL,
	category_active	BOOLEAN,
		
	CONSTRAINT	pk_category_id	PRIMARY KEY	(category_id) 
)ENGINE = InnoDB;

-- brands table 
CREATE TABLE brands(
	brand_id			INT			NOT NULL	AUTO_INCREMENT,
	brand_name			VARCHAR(50)	NOT NULL,
	brand_description	VARCHAR(255),
	
	CONSTRAINT	pk_brand_id	PRIMARY KEY	(brand_id)
)ENGINE = InnoDB;

-- roles table
CREATE TABLE roles(
	role_id		INT			NOT NULL	AUTO_INCREMENT,
	role_name	VARCHAR(50)	NOT NULL,

	CONSTRAINT	pk_role_id	PRIMARY KEY	(role_id),
	UNIQUE (role_name)
)ENGINE = InnoDB;

-- users table (password - BCrypt Hash Generator)
CREATE TABLE users(
	user_id			INT				NOT NULL	AUTO_INCREMENT,
	user_first_name	VARCHAR(50)		NOT NULL,
	user_last_name	VARCHAR(50),	
	user_email		VARCHAR(50)		NOT NULL,
	user_password	VARCHAR(100)	NOT NULL,
	user_contact	VARCHAR(30)		NOT NULL,
	user_enabled	BOOLEAN,

	CONSTRAINT	pk_user_id	PRIMARY KEY	(user_id)
)ENGINE = InnoDB;

-- user_roles table (for mapping user and roles)
CREATE TABLE user_roles(
	role_id	INT	NOT NULL,
	user_id	INT	NOT NULL,

	CONSTRAINT	pk_user_roles			PRIMARY KEY	(role_id, user_id),
	CONSTRAINT	fk_user_roles_role_id	FOREIGN KEY	(role_id)	REFERENCES	roles	(role_id),
	CONSTRAINT	fk_user_roles_user_id	FOREIGN KEY	(user_id)	REFERENCES	users	(user_id)
)ENGINE = InnoDB;

-- addresses table 
CREATE TABLE addresses(
	address_id			INT				NOT NULL	AUTO_INCREMENT,
	user_address		INT,
	address_line_one	VARCHAR(100),
	address_line_two	VARCHAR(100),
	city				VARCHAR(50),
	state				VARCHAR(50),
	country				VARCHAR(50),
	postal_code			VARCHAR(50),
	is_billing			BOOLEAN,
	is_shipping			BOOLEAN,
	
	CONSTRAINT	pk_address_id				PRIMARY KEY	(address_id),
	CONSTRAINT	fk_addresses_user_address	FOREIGN KEY (user_address)	REFERENCES users	(user_id)
)ENGINE = InnoDB;

-- products table 
CREATE TABLE products(
	product_id			INT				NOT NULL	AUTO_INCREMENT,
	product_name		VARCHAR(50)		NOT NULL,
	product_code		VARCHAR(50)		NOT NULL,
	product_brand		INT,	
	product_unit_price	DECIMAL(10,2),
	product_quantity	INT,
	product_active		BOOLEAN,
	product_category	INT,
    
	CONSTRAINT	pk_product_id					PRIMARY KEY	(product_id),
	CONSTRAINT	fk_products_product_brand		FOREIGN KEY	(product_brand)		REFERENCES	brands		(brand_id),
	CONSTRAINT	fk_products_product_category	FOREIGN KEY	(product_category)	REFERENCES	categories	(category_id)
)ENGINE = InnoDB;

-- descriptions table 
CREATE TABLE descriptions(
	description_id			INT				NOT NULL,
	screen					VARCHAR(50),
	color					VARCHAR(50),
	processor				VARCHAR(50),
	front_camera			VARCHAR(50),
	rear_camera				VARCHAR(50),
	capacity				VARCHAR(50),
	battery					VARCHAR(50),
	display_technology		VARCHAR(50),
	graphics				VARCHAR(50),
	wireless_communication	VARCHAR(50),	
    
	CONSTRAINT	pk_description_id				PRIMARY KEY	(description_id),
	CONSTRAINT	fk_descriptions_description_id	FOREIGN KEY (description_id)	REFERENCES products	(product_id)
)ENGINE = InnoDB;

-- views table 
CREATE TABLE views(
	view_id		INT			NOT NULL	AUTO_INCREMENT,
	view_code	VARCHAR(50)	NOT NULL,
	product_id	INT,
	
	CONSTRAINT	pk_view_id			PRIMARY KEY	(view_id),
	CONSTRAINT	fk_views_product_id	FOREIGN KEY (product_id)	REFERENCES products	(product_id)
)ENGINE = InnoDB;

-- carts table
CREATE TABLE carts(
	cart_id		INT				NOT NULL,
	cart_total	DECIMAL(10,2),
	cart_items	INT,

	CONSTRAINT	pk_cart_id			PRIMARY KEY	(cart_id),	
	CONSTRAINT	fk_carts_cart_id	FOREIGN KEY (cart_id)	REFERENCES	users	(user_id)
)ENGINE = InnoDB;

-- cart_items table 
CREATE TABLE cart_items(
	cart_item_id		INT				NOT NULL	AUTO_INCREMENT,
	cart_id				INT,
	cart_item_total		DECIMAL(10,2),
	product_id			INT,
	product_count		INT,
	product_price		DECIMAL(10,2),
	is_available		BOOLEAN,

	CONSTRAINT	pk_cart_item_id				PRIMARY KEY	(cart_item_id),	
	CONSTRAINT	fk_cart_items_cart_id		FOREIGN KEY	(cart_id)		REFERENCES	carts		(cart_id),
	CONSTRAINT	fk_cart_items_product_id	FOREIGN KEY	(product_id)	REFERENCES	products	(product_id)
)ENGINE = InnoDB;

-- orders table 
CREATE TABLE orders(
	oder_id			INT				NOT NULL	AUTO_INCREMENT,
	user_id 		INT,
	order_total		DECIMAL(10,2),
	order_count		INT,
	shipping_id 	INT,
	billing_id 		INT,
	order_date 		DATETIME		NOT NULL,
	
	CONSTRAINT	pk_oder_id				PRIMARY KEY	(oder_id),
	CONSTRAINT	fk_orders_user_id		FOREIGN KEY	(user_id)		REFERENCES	users		(user_id),
	CONSTRAINT 	fk_orders_shipping_id 	FOREIGN KEY (shipping_id) 	REFERENCES 	addresses 	(address_id),
	CONSTRAINT 	fk_orders_billing_id	FOREIGN KEY (billing_id) 	REFERENCES 	addresses 	(address_id)
)ENGINE = InnoDB;

-- order_items table 
CREATE TABLE order_items(
	order_item_id 		INT 			NOT NULL	AUTO_INCREMENT,
	order_id 			INT,
	order_item_total	DECIMAL(10,2),
	product_id 			INT,
	product_count 		INT,
	product_price 		DECIMAL(10,2),
	
	CONSTRAINT	pk_order_item_id				PRIMARY KEY	(order_item_id),
	CONSTRAINT	fk_order_items_order_id			FOREIGN KEY (order_id)			REFERENCES orders	(oder_id),
	CONSTRAINT	fk_order_items_order_product_id	FOREIGN KEY (product_id)		REFERENCES products (product_id)
)ENGINE = InnoDB;
