DROP TABLE IF EXISTS user_offers;
DROP TABLE IF EXISTS offer;
DROP TABLE IF EXISTS hotel;
DROP TABLE IF EXISTS airport;
DROP TABLE IF EXISTS city;
DROP TABLE IF EXISTS country;
DROP TABLE IF EXISTS continent;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS authorities;


CREATE TABLE IF NOT EXISTS continent (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS country (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    continent_id INT,
    CONSTRAINT FK_continent_id FOREIGN KEY (continent_id) REFERENCES continent(id)
);

CREATE TABLE IF NOT EXISTS city (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    country_id INT,
    CONSTRAINT FK_country_id FOREIGN KEY (country_id) REFERENCES country(id)
);

CREATE TABLE IF NOT EXISTS airport (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255),
    city_id INT,
    CONSTRAINT FK_airport_city_id FOREIGN KEY (city_id) REFERENCES city(id)
);

CREATE TABLE IF NOT EXISTS hotel (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255),
    city_id INT,
    rating FLOAT(1),
    CONSTRAINT FK_hotel_city_id FOREIGN KEY (city_id) REFERENCES city(id)
);

CREATE TABLE IF NOT EXISTS offer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    hotel_id INT,
    price DECIMAL(10,2),
    quantity INT,
    CONSTRAINT FK_hotel_id FOREIGN KEY (hotel_id) REFERENCES hotel(id)
);

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled INT NOT NULL,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS authorities (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
    );


CREATE TABLE IF NOT EXISTS user_offers (
    user_id INT,
    offer_id INT,
    CONSTRAINT FK_offer_id FOREIGN KEY (offer_id) REFERENCES offer(id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(id),
    PRIMARY KEY (user_id, offer_id)
);

