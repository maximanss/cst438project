--
-- Table structure 
--

DROP TABLE IF EXISTS hybernate_squence;
DROP TABLE IF EXISTS inventory;
DROP TABLE IF EXISTS cartype;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS partner;
DROP TABLE IF EXISTS reservation;



CREATE TABLE cartype (
	carTypeID INT NOT NULL,
	carTypeName VARCHAR(40) NOT NULL DEFAULT '',
	CONSTRAINT cartype_pk PRIMARY KEY (carTypeID)
);

CREATE TABLE inventory (
	carID BIGINT NOT NULL AUTO_INCREMENT,
	carTypeID INT NOT NULL,
	description VARCHAR(300) NOT NULL DEFAULT '',
	maxPassengers INT NOT NULL DEFAULT '2',
	location VARCHAR(50) NOT NULL DEFAULT '',
	image VARCHAR(20),
	dailyPrice DECIMAL(10,2) NOT NULL DEFAULT '0.00',
	weeklyPrice DECIMAL(10,2) NOT NULL DEFAULT '0.00',
	
	CONSTRAINT inventory_pk PRIMARY KEY (carID),
	CONSTRAINT inventory_fk_type FOREIGN KEY (carTypeID) REFERENCES cartype (carTypeID)
);

CREATE TABLE user (
	userID BIGINT NOT NULL AUTO_INCREMENT,
	emailAddress VARCHAR(50) NOT NULL,
	name VARCHAR(50) NOT NULL,
	password VARCHAR(30) NOT NULL,
	creditNumber INT NOT NULL,
	address VARCHAR(80) NOT NULL,
	CONSTRAINT user_pk PRIMARY KEY (userID)
);

CREATE TABLE partner (
	companyID BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	discountRate DECIMAL(10,2) NOT NULL DEFAULT '1.00',
	CONSTRAINT partner_pk PRIMARY KEY (companyID)
);

CREATE TABLE reservation (
	reserveID BIGINT NOT NULL AUTO_INCREMENT,
	carID BIGINT NOT NULL,
	startDate DATE NOT NULL,
	endDate DATE NOT NULL,
	userID BIGINT NOT NULL DEFAULT '0',
	companyID BIGINT NOT NULL DEFAULT '0',
	totalPrice DECIMAL(10,2) NOT NULL DEFAULT '0.00',
	location VARCHAR(50) NOT NULL DEFAULT '',
	CONSTRAINT reservation_pk PRIMARY KEY (reserveID)
	
);

