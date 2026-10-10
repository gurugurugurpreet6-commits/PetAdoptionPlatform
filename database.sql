CREATE DATABASE IF NOT EXISTS pet_adoption_db;
USE pet_adoption_db;

CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    status VARCHAR(20) DEFAULT 'Active'
);

CREATE TABLE IF NOT EXISTS pets (
    pet_id INT PRIMARY KEY AUTO_INCREMENT,
    pet_name VARCHAR(100) NOT NULL,
    type VARCHAR(50),
    breed VARCHAR(100),
    age INT,
    gender VARCHAR(20),
    description TEXT,
    image VARCHAR(255),
    status VARCHAR(30) DEFAULT 'Pending',
    shelter_id INT,
    FOREIGN KEY (shelter_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS adoption_requests (
    request_id INT PRIMARY KEY AUTO_INCREMENT,
    pet_id INT,
    adopter_id INT,
    request_date DATE,
    status VARCHAR(30) DEFAULT 'Pending',
    reason TEXT,
    FOREIGN KEY (pet_id) REFERENCES pets(pet_id),
    FOREIGN KEY (adopter_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS system_settings (
    setting_id INT PRIMARY KEY AUTO_INCREMENT,
    setting_name VARCHAR(100) UNIQUE NOT NULL,
    setting_value VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS favorites (
    favorite_id INT PRIMARY KEY AUTO_INCREMENT,
    adopter_id INT,
    pet_id INT,
    FOREIGN KEY (adopter_id) REFERENCES users(user_id),
    FOREIGN KEY (pet_id) REFERENCES pets(pet_id),
    UNIQUE(adopter_id, pet_id)
);

CREATE TABLE IF NOT EXISTS messages (
    message_id INT PRIMARY KEY AUTO_INCREMENT,
    sender_id INT,
    receiver_id INT,
    message TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES users(user_id),
    FOREIGN KEY (receiver_id) REFERENCES users(user_id)
);

INSERT INTO system_settings (setting_name, setting_value)
VALUES ('Platform Name', 'Pet Adoption Platform'),
       ('Registration Status', 'Open'),
       ('Maintenance Mode', 'Off')
ON DUPLICATE KEY UPDATE setting_value = VALUES(setting_value);

-- Demo accounts. Change these passwords before public deployment.
INSERT INTO users (name, email, password, role, status)
VALUES ('Admin', 'admin@gmail.com', 'admin123', 'Admin', 'Active')
ON DUPLICATE KEY UPDATE name=VALUES(name), role=VALUES(role), status=VALUES(status);

INSERT INTO users (name, email, password, role, status)
VALUES ('Happy Paws Shelter', 'shelter@gmail.com', '12345', 'Shelter', 'Active')
ON DUPLICATE KEY UPDATE name=VALUES(name), role=VALUES(role), status=VALUES(status);

INSERT INTO users (name, email, password, role, status)
VALUES ('Gurpreet Singh', 'gurpreet@gmail.com', '12345', 'Adopter', 'Active')
ON DUPLICATE KEY UPDATE name=VALUES(name), role=VALUES(role), status=VALUES(status);
