-- =================================================================
-- Sample Data for Campus Finder Application
-- =================================================================
-- This file populates the tables with sample data for demonstration.
-- =================================================================

-- Populate location_categories
INSERT INTO location_categories (name, icon) VALUES
('Academic Building', 'ic_academic'),
('Library', 'ic_library'),
('Computer Lab', 'ic_computer_lab'),
('Sports Facility', 'ic_sports'),
('Cafeteria', 'ic_cafeteria'),
('Administrative Office', 'ic_admin');

-- Populate locations
-- Note: The category_id corresponds to the order above (1-6)
INSERT INTO locations (name, description, latitude, longitude, category_id) VALUES
('Engineering Block A', 'Main building for the Department of Computer Science and Engineering.', 22.5726, 88.3639, 1),
('Central Library', 'A quiet place for study and research, with a vast collection of books and digital resources.', 22.5730, 88.3645, 2),
('Main Computer Center', '24/7 computer lab with high-speed internet and printing facilities.', 22.5728, 88.3635, 3),
('Campus Gymnasium', 'State-of-the-art fitness center with a swimming pool and basketball court.', 22.5740, 88.3650, 4),
('University Cafeteria', 'Offers a variety of healthy and affordable meals and snacks.', 22.5735, 88.3630, 5),
('Admissions Office', 'Handles all student admissions, records, and registration processes.', 22.5720, 88.3640, 6),
('Science Block C', 'Houses the physics and chemistry departments and laboratories.', 22.5718, 88.3655, 1);

-- Populate events
INSERT INTO events (title, date, description, image_name) VALUES
('Tech-Fest 2025', '2025-03-15', 'The annual technology festival featuring coding competitions, robotics, and guest lectures from industry experts.', 'event_tech_fest'),
('Annual Sports Meet', '2025-04-22', 'A week-long event with track and field, indoor games, and team sports. Come and cheer for your department!', 'event_sports_meet'),
('Convocation Ceremony', '2025-05-20', 'Graduation ceremony for the class of 2025. The event will be held at the university auditorium.', 'event_convocation'),
('Freshers Welcome Party', '2025-08-10', 'A warm welcome for all new students joining the university. Music, food, and fun activities await!', 'event_freshers');

-- Populate articles
INSERT INTO articles (title, author, date, content, image_name) VALUES
('New Library Wing Inaugurated', 'Campus News Desk', '2025-09-01', 'The new west wing of the Central Library was inaugurated today by the Vice-Chancellor. It features modern study spaces and a digital media lab.', 'article_library_wing'),
('A Guide to Campus Eateries', 'Student Life Committee', '2025-09-05', 'Feeling hungry? This guide covers all the best spots to eat on campus, from the main cafeteria to the small food stalls near the sports complex.', 'article_campus_eateries'),
('Health and Wellness on Campus', 'Dr. Anjali Sharma', '2025-09-10', 'Maintaining a healthy lifestyle is crucial for academic success. This article provides tips on nutrition, exercise, and mental well-being, and details the support services available to all students.', 'article_health_wellness');
