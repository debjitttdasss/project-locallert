-- =================================================================
-- Database Schema for Campus Finder Application
-- =================================================================
-- This file defines the structure of the database tables.
-- It is used for documentation and can be used to set up a new database.
-- The application uses H2, but this schema is standard SQL.
-- =================================================================

-- Drop tables if they exist to ensure a clean setup
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS articles;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS location_categories;


-- Table for storing location categories (e.g., 'Engineering', 'Library')
CREATE TABLE location_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    icon VARCHAR(255)
);


-- Table for storing specific locations on campus
CREATE TABLE locations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES location_categories(id)
);


-- Table for storing campus events
CREATE TABLE events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    date VARCHAR(255),
    description VARCHAR(2000),
    image_name VARCHAR(255)
);


-- Table for storing campus articles or news
CREATE TABLE articles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    date VARCHAR(255),
    content VARCHAR(5000),
    image_name VARCHAR(255)
);