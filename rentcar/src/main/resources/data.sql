
DELETE FROM inventory;
DELETE FROM cartype;
DELETE FROM user;
DELETE FROM partner;

ALTER TABLE inventory AUTO_INCREMENT = 1;
ALTER TABLE user AUTO_INCREMENT = 1;
ALTER TABLE partner AUTO_INCREMENT = 1;

INSERT INTO cartype VALUES (1, "Standard");
INSERT INTO cartype VALUES (2, "SUV");
INSERT INTO cartype VALUES (3, "Truck");
INSERT INTO cartype VALUES (4, "Van");
INSERT INTO cartype VALUES (5, "Luxury");

INSERT INTO inventory (carTypeID, description, maxPassengers, location, image, dailyPrice, weeklyPrice) VALUES
(1, "Volkswagen Jetta, Toyota Corolla or similar. Features: Cruise Control, AM/FM Stereo Radio, Automatic, 
Air Conditioning, and Bluetooth.", 5, "Boston", "standard.png", 51.25, 305.50),
(1, "Volkswagen Jetta, Toyota Corolla or similar. Features: Cruise Control, AM/FM Stereo Radio, Automatic, 
Air Conditioning, and Bluetooth.", 5, "Boston", "standard.png", 51.25, 305.50),
(1, "Volkswagen Jetta, Toyota Corolla or similar. Features: Cruise Control, AM/FM Stereo Radio, Automatic, 
Air Conditioning, and Bluetooth.", 5, "Miami", "standard.png", 51.25, 305.50),
(1, "Volkswagen Jetta, Toyota Corolla or similar. Features: Cruise Control, AM/FM Stereo Radio, Automatic, 
Air Conditioning, and Bluetooth.", 5, "Miami", "standard.png", 51.25, 305.50),
(2, "Nissan Rogue or similar. Features: Cruise Control, 
AM/FM Stereo Radio, Automatic, Air Conditioning, and Bluetooth.", 5, "Boston", "suv.png", 63.60, 350.25),
(2, "Nissan Rogue or similar. Features: Cruise Control, 
AM/FM Stereo Radio, Automatic, Air Conditioning, and Bluetooth.", 5, "Boston", "suv.png", 63.60, 350.25),
(2, "Nissan Rogue or similar. Features: Cruise Control, 
AM/FM Stereo Radio, Automatic, Air Conditioning, and Bluetooth.", 5, "Miami", "suv.png", 63.60, 350.25),
(2, "Nissan Rogue or similar. Features: Cruise Control, 
AM/FM Stereo Radio, Automatic, Air Conditioning, and Bluetooth.", 5, "Miami", "suv.png", 63.60, 350.25),
(3, "Ford F150, Chevy Colorado or similar. Features: Cruise Control, AM/FM Stereo Radio, 
Automatic, Air Conditioning, and Bluetooth.", 4, "Boston", "truck.png", 85.50, 480.25),
(3, "Ford F150, Chevy Colorado or similar. Features: Cruise Control, AM/FM Stereo Radio, 
Automatic, Air Conditioning, and Bluetooth.", 4, "Miami", "truck.png", 85.50, 480.25),
(4, "Chrysler Pacifica or similar. Features: Cruise Control, 
AM/FM Stereo Radio, Automatic, Air Conditioning, and Bluetooth.", 7, "Boston", "van.png", 105.25, 600.25),
(4, "Chrysler Pacifica or similar. Features: Cruise Control, 
AM/FM Stereo Radio, Automatic, Air Conditioning, and Bluetooth.", 7, "Miami", "van.png", 105.25, 600.25),
(5, "BMW 7 Series , Mercedes Benz S Class , Porsche Panamera or similar. Features: Cruise Control, Leather interior, 
AM/FM Stereo Radio, Automatic, Air Conditioning, and Bluetooth.", 5, "Boston", "luxury.png", 130.00, 700.25),
(5, "BMW 7 Series , Mercedes Benz S Class , Porsche Panamera or similar. Features: Cruise Control, Leather interior, 
AM/FM Stereo Radio, Automatic, Air Conditioning, and Bluetooth.", 5, "Miami", "luxury.png", 130.00, 700.25);

INSERT INTO user (emailAddress, name, password, creditNumber, address) VALUES
("bob@gmail.com", "Bob", "bob_pwd", "123456", "bob home address"),
("sue@gmail.com", "Sue", "sue_pwd", "234567", "sue home address");

INSERT INTO partner(name, discountRate) VALUES
("Cheap Trip", 0.7),
("Happy Traveller", 0.8);
