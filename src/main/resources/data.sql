DELETE FROM offer;
ALTER TABLE offer AUTO_INCREMENT = 1;

DELETE FROM hotel;
ALTER TABLE hotel AUTO_INCREMENT = 1;

DELETE FROM city;
ALTER TABLE city AUTO_INCREMENT = 1;

DELETE FROM country;
ALTER TABLE country AUTO_INCREMENT = 1;

DELETE FROM continent;
ALTER TABLE continent AUTO_INCREMENT = 1;

INSERT INTO continent (name)
VALUES
    ('Europe'),
    ('North America'),
    ('South America'),
    ('Asia'),
    ('Africa'),
    ('Australia');

INSERT INTO country (name, continent_id)
VALUES
    ('Argentina', 1),
    ('Bolivia', 2),
    ('Brazil', 3),
    ('Chile', 4),
    ('Colombia', 1),
    ('Ecuador', 1),
    ('Guyana', 1),
    ('Paraguay', 1),
    ('Peru', 1),
    ('Suriname', 1),
    ('Uruguay', 1),
    ('Venezuela', 1);

INSERT INTO city (name, country_id)
VALUES
    ('Kraków', 1),
    ('Wrocław', 2),
    ('Warszawa', 3);

INSERT INTO hotel (name, city_id)
VALUES
    ('Hilton', 1),
    ('Cubus', 2),
    ('Marriot', 3);

INSERT INTO offer (name, hotel_id)
VALUES
    ('Kraków_offer',1),
    ('Wrocław_offer',2),
    ('Warszawa_offer',3);