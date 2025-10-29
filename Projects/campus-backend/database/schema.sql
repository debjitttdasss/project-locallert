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
DROP TABLE IF EXISTS post_likes;
DROP TABLE IF EXISTS forum_comments;
DROP TABLE IF EXISTS forum_posts;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS lost_and_found_items;
DROP TABLE IF EXISTS gig_skills;
DROP TABLE IF EXISTS campus_gigs;


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

-- =================================================================
-- NEW SCHEMAS
-- =================================================================

-- Users table to store information about who is posting
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Forum posts (threads)
CREATE TABLE forum_posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Comments on forum posts
CREATE TABLE forum_comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    parent_comment_id BIGINT, -- For nested comments/replies
    content TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES forum_posts(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (parent_comment_id) REFERENCES forum_comments(id) ON DELETE CASCADE
);

-- To track likes on posts
CREATE TABLE post_likes (
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES forum_posts(id) ON DELETE CASCADE
);

-- Table for Lost and Found Items
CREATE TABLE lost_and_found_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    item_name VARCHAR(100) NOT NULL,
    description TEXT,
    image_url VARCHAR(255),
    status VARCHAR(10) NOT NULL CHECK (status IN ('Lost', 'Found', 'Claimed')),
    last_seen_location VARCHAR(255),
    reported_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Table for Campus Gigs
CREATE TABLE campus_gigs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    client VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    budget NUMERIC(10, 2),
    posted_date DATE NOT NULL,
    is_active BOOLEAN DEFAULT TRUE
);

-- Table for skills related to gigs
CREATE TABLE gig_skills (
    gig_id BIGINT NOT NULL,
    skill_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (gig_id, skill_name),
    FOREIGN KEY (gig_id) REFERENCES campus_gigs(id) ON DELETE CASCADE
);
