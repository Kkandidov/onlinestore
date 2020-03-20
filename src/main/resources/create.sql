-- categories table
CREATE TABLE categories(
	id		INT			NOT NULL	AUTO_INCREMENT,
	name	VARCHAR(50)	NOT NULL,
	active	BOOLEAN,

	CONSTRAINT	pk_categories_id	PRIMARY KEY	(id),
	UNIQUE (name)
)ENGINE = InnoDB;

-- brands table
CREATE TABLE brands(
	id			INT				NOT NULL	AUTO_INCREMENT,
	name		VARCHAR(50)		NOT NULL,
	description	VARCHAR(255),
	active		BOOLEAN,

	CONSTRAINT	pk_brands_id	PRIMARY KEY	(id),
	UNIQUE (name)
)ENGINE = InnoDB;

-- roles table
CREATE TABLE roles(
	id		INT			NOT NULL	AUTO_INCREMENT,
	name	VARCHAR(50)	NOT NULL,
	active	BOOLEAN,

	CONSTRAINT	pk_roles_id	PRIMARY KEY	(id),
	UNIQUE (name)
)ENGINE = InnoDB;

-- users table (password - BCrypt Hash Generator)
CREATE TABLE users(
	id				INT				NOT NULL	AUTO_INCREMENT,
	first_name		VARCHAR(50)		NOT NULL,
	last_name		VARCHAR(50),
	email			VARCHAR(50)		NOT NULL,
	password		VARCHAR(100)	NOT NULL,
	contact_number	VARCHAR(30)		NOT NULL,
	enabled			BOOLEAN,
	role_id			INT 			NOT NULL,

	CONSTRAINT	pk_users_id			PRIMARY KEY	(id),
	CONSTRAINT	fk_users_role_id	FOREIGN KEY (role_id)	REFERENCES	roles	(id)	ON DELETE CASCADE,
	UNIQUE (email)
)ENGINE = InnoDB;

-- addresses table
CREATE TABLE addresses(
	id			INT				NOT NULL	AUTO_INCREMENT,
	user_id		INT 			NOT NULL,
	line_one	VARCHAR(100)	NOT NULL,
	line_two	VARCHAR(100),
	city		VARCHAR(50)		NOT NULL,
	state		VARCHAR(50)		NOT NULL,
	country		VARCHAR(50)		NOT NULL,
	postal_code	VARCHAR(50)		NOT NULL,
	billing		BOOLEAN,
	shipping	BOOLEAN,

	CONSTRAINT	pk_addresses_id			PRIMARY KEY	(id),
	CONSTRAINT	fk_addresses_user_id	FOREIGN KEY (user_id)	REFERENCES	users	(id)	ON DELETE CASCADE	
)ENGINE = InnoDB;

-- products table
CREATE TABLE products(
	id			INT				NOT NULL	AUTO_INCREMENT,
	name		VARCHAR(50)		NOT NULL,
	code		VARCHAR(50)		NOT NULL,
	brand_id	INT 			NOT NULL,
	unit_price	DECIMAL(10,2),
	quantity	INT,
	active		BOOLEAN,
	category_id	INT 			NOT NULL,

	CONSTRAINT	pk_products_id			PRIMARY KEY	(id),
	CONSTRAINT	fk_products_brand_id	FOREIGN KEY	(brand_id)		REFERENCES	brands		(id)	ON DELETE CASCADE,
	CONSTRAINT	fk_products_category_id	FOREIGN KEY	(category_id)	REFERENCES	categories	(id)	ON DELETE CASCADE
)ENGINE = InnoDB;

-- descriptions table
CREATE TABLE descriptions(
	id						INT				NOT NULL,
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

	CONSTRAINT	pk_descriptions_id	PRIMARY KEY	(id),
	CONSTRAINT	fk_descriptions_id	FOREIGN KEY (id)	REFERENCES	products	(id)	ON DELETE CASCADE
)ENGINE = InnoDB;

-- views table
CREATE TABLE views(
	id			INT			NOT NULL	AUTO_INCREMENT,
	code		VARCHAR(50)	NOT NULL,
	product_id	INT			NOT NULL,

	CONSTRAINT	pk_views_id			PRIMARY KEY	(id),
	CONSTRAINT	fk_views_product_id	FOREIGN KEY (product_id)	REFERENCES	products	(id)	ON DELETE CASCADE
)ENGINE = InnoDB;

-- carts table
CREATE TABLE carts(
	id			INT				NOT NULL,
	total		DECIMAL(10,2),
	cart_items	INT,

	CONSTRAINT	pk_carts_id	PRIMARY KEY	(id),
	CONSTRAINT	fk_carts_id	FOREIGN KEY (id)	REFERENCES	users	(id)	ON DELETE CASCADE
)ENGINE = InnoDB;

-- cart_items table
CREATE TABLE cart_items(
	id				INT				NOT NULL	AUTO_INCREMENT,
	cart_id			INT				NOT NULL,
	total			DECIMAL(10,2),
	product_id		INT				NOT NULL,
	product_count	INT,
	product_price	DECIMAL(10,2),
	available		BOOLEAN,

	CONSTRAINT	pk_cart_items_id			PRIMARY KEY	(id),
	CONSTRAINT	fk_cart_items_cart_id		FOREIGN KEY	(cart_id)		REFERENCES	carts		(id)	ON DELETE CASCADE,
	CONSTRAINT	fk_cart_items_product_id	FOREIGN KEY	(product_id)	REFERENCES	products	(id)	ON DELETE CASCADE
)ENGINE = InnoDB;
-- orders table
CREATE TABLE orders(
	id				INT				NOT NULL	AUTO_INCREMENT,
	user_id 		INT				NOT NULL,
	total			DECIMAL(10,2),
	count			INT,
	shipping_id 	INT				NOT NULL,
	billing_id 		INT				NOT NULL,
	date 			DATETIME		NOT NULL,

	CONSTRAINT	pk_orders_id			PRIMARY KEY	(id),
	CONSTRAINT	fk_orders_user_id		FOREIGN KEY	(user_id)		REFERENCES	users		(id)	ON DELETE CASCADE,
	CONSTRAINT 	fk_orders_shipping_id 	FOREIGN KEY (shipping_id) 	REFERENCES 	addresses 	(id)	ON DELETE CASCADE,
	CONSTRAINT 	fk_orders_billing_id	FOREIGN KEY (billing_id) 	REFERENCES 	addresses 	(id)	ON DELETE CASCADE
)ENGINE = InnoDB;

-- order_items table
CREATE TABLE order_items(
	id 				INT 			NOT NULL	AUTO_INCREMENT,
	order_id 		INT				NOT NULL,
	total			DECIMAL(10,2),
	product_id 		INT				NOT NULL,
	product_count 	INT,
	product_price	DECIMAL(10,2),

	CONSTRAINT	pk_order_items_id			PRIMARY KEY	(id),
	CONSTRAINT	fk_order_items_order_id		FOREIGN KEY (order_id)		REFERENCES orders	(id)	ON DELETE CASCADE,
	CONSTRAINT	fk_order_items_product_id	FOREIGN KEY (product_id)	REFERENCES products (id)	ON DELETE CASCADE
)ENGINE = InnoDB;
