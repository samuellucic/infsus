-- Insert data into EstateType
INSERT INTO EstateType (name, description) VALUES
('House', 'A standalone residential building'),
('Apartment', 'A residential unit in a building'),
('Villa', 'A luxurious country house with extensive grounds'),
('Studio', 'A small apartment with a single room');

-- Insert data into Town
INSERT INTO Town (name, postCode, region, country) VALUES
('New York', '10001', 'New York', 'USA'),
('Los Angeles', '90001', 'California', 'USA'),
('Chicago', '60601', 'Illinois', 'USA'),
('Houston', '77001', 'Texas', 'USA');

-- Insert data into EstateOwner
INSERT INTO EstateOwner (name, surname, birthDate, address, email, townId) VALUES
('John', 'Doe', '1980-05-15', '123 Main St', 'johndoe@example.com', 1),
('Jane', 'Smith', '1975-03-22', '456 Elm St', 'janesmith@example.com', 2),
('Robert', 'Brown', '1990-11-11', '789 Oak St', 'robertbrown@example.com', 3),
('Emily', 'Davis', '1988-07-07', '101 Pine St', 'emilydavis@example.com', 4);

-- Insert data into Estate
INSERT INTO Estate (price, address, area, description, townId, ownerId, estateType) VALUES
(500000, '123 Main St', 150, 'A beautiful house in New York', 1, 1, 'House'),
(300000, '456 Elm St', 100, 'A modern apartment in Los Angeles', 2, 2, 'Apartment'),
(750000, '789 Oak St', 200, 'A luxurious villa in Chicago', 3, 3, 'Villa'),
(250000, '101 Pine St', 50, 'A cozy studio in Houston', 4, 4, 'Studio');