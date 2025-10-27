package com.capmass.backend.config;

import com.capmass.backend.entity.Location;
import com.capmass.backend.entity.LocationCategory;
import com.capmass.backend.repository.LocationCategoryRepository;
import com.capmass.backend.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private final LocationCategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    public DataLoader(LocationCategoryRepository categoryRepository, 
                     LocationRepository locationRepository) {
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create categories
        LocationCategory academic = new LocationCategory(null, "Academic Buildings", "Buildings for classes and lectures");
        LocationCategory dining = new LocationCategory(null, "Dining", "Food and dining facilities");
        LocationCategory library = new LocationCategory(null, "Library", "Study and research facilities");
        LocationCategory sports = new LocationCategory(null, "Sports & Recreation", "Athletic and recreation facilities");
        LocationCategory residence = new LocationCategory(null, "Residence Halls", "Student housing and dormitories");

        categoryRepository.save(academic);
        categoryRepository.save(dining);
        categoryRepository.save(library);
        categoryRepository.save(sports);
        categoryRepository.save(residence);

        // Create locations for Academic Buildings
        locationRepository.save(new Location(null, "Engineering Building", 
            "Main building for engineering departments", academic, 40.7128, -74.0060));
        locationRepository.save(new Location(null, "Science Hall", 
            "Chemistry, Physics, and Biology labs", academic, 40.7130, -74.0058));
        locationRepository.save(new Location(null, "Business School", 
            "School of Business and Management", academic, 40.7126, -74.0062));

        // Create locations for Dining
        locationRepository.save(new Location(null, "Main Cafeteria", 
            "Large dining hall with multiple food stations", dining, 40.7132, -74.0056));
        locationRepository.save(new Location(null, "Student Union Cafe", 
            "Coffee shop and light meals", dining, 40.7129, -74.0059));

        // Create locations for Library
        locationRepository.save(new Location(null, "Central Library", 
            "Main campus library with extensive collection", library, 40.7125, -74.0061));
        locationRepository.save(new Location(null, "Science Library", 
            "Specialized science and engineering resources", library, 40.7131, -74.0057));

        // Create locations for Sports & Recreation
        locationRepository.save(new Location(null, "Campus Recreation Center", 
            "Gym, pool, and fitness facilities", sports, 40.7134, -74.0055));
        locationRepository.save(new Location(null, "Stadium", 
            "Football and track stadium", sports, 40.7135, -74.0054));

        // Create locations for Residence Halls
        locationRepository.save(new Location(null, "North Hall", 
            "Freshman dormitory", residence, 40.7127, -74.0063));
        locationRepository.save(new Location(null, "South Tower", 
            "Upper-class student housing", residence, 40.7124, -74.0064));

        logger.info("Sample data loaded successfully!");
    }
}
