package com.capmass.backend.config;

import com.capmass.backend.entity.Location;
import com.capmass.backend.entity.LocationCategory;
import com.capmass.backend.repository.LocationCategoryRepository;
import com.capmass.backend.repository.LocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("DataLoader Integration Tests")
class DataLoaderIntegrationTest {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationCategoryRepository categoryRepository;

    @Test
    @DisplayName("Should load all categories into database")
    void testCategoriesLoadedInDatabase() {
        List<LocationCategory> categories = categoryRepository.findAll();
        
        assertEquals(5, categories.size());
    }

    @Test
    @DisplayName("Should load all locations into database")
    void testLocationsLoadedInDatabase() {
        List<Location> locations = locationRepository.findAll();
        
        assertEquals(11, locations.size());
    }

    @Test
    @DisplayName("Should load Academic Buildings category")
    void testAcademicCategoryLoaded() {
        List<LocationCategory> categories = categoryRepository.findAll();
        
        assertTrue(categories.stream()
            .anyMatch(cat -> "Academic Buildings".equals(cat.getName())));
    }

    @Test
    @DisplayName("Should load Dining category")
    void testDiningCategoryLoaded() {
        List<LocationCategory> categories = categoryRepository.findAll();
        
        assertTrue(categories.stream()
            .anyMatch(cat -> "Dining".equals(cat.getName())));
    }

    @Test
    @DisplayName("Should load Library category")
    void testLibraryCategoryLoaded() {
        List<LocationCategory> categories = categoryRepository.findAll();
        
        assertTrue(categories.stream()
            .anyMatch(cat -> "Library".equals(cat.getName())));
    }

    @Test
    @DisplayName("Should load Sports & Recreation category")
    void testSportsCategoryLoaded() {
        List<LocationCategory> categories = categoryRepository.findAll();
        
        assertTrue(categories.stream()
            .anyMatch(cat -> "Sports & Recreation".equals(cat.getName())));
    }

    @Test
    @DisplayName("Should load Residence Halls category")
    void testResidenceCategoryLoaded() {
        List<LocationCategory> categories = categoryRepository.findAll();
        
        assertTrue(categories.stream()
            .anyMatch(cat -> "Residence Halls".equals(cat.getName())));
    }

    @Test
    @DisplayName("Should load Engineering Building")
    void testEngineeringBuildingLoaded() {
        List<Location> locations = locationRepository.findAll();
        
        assertTrue(locations.stream()
            .anyMatch(loc -> "Engineering Building".equals(loc.getName())));
    }

    @Test
    @DisplayName("Should load Science Hall")
    void testScienceHallLoaded() {
        List<Location> locations = locationRepository.findAll();
        
        assertTrue(locations.stream()
            .anyMatch(loc -> "Science Hall".equals(loc.getName())));
    }

    @Test
    @DisplayName("Should load Business School")
    void testBusinessSchoolLoaded() {
        List<Location> locations = locationRepository.findAll();
        
        assertTrue(locations.stream()
            .anyMatch(loc -> "Business School".equals(loc.getName())));
    }

    @Test
    @DisplayName("Should load Main Cafeteria")
    void testMainCafeteriaLoaded() {
        List<Location> locations = locationRepository.findAll();
        
        assertTrue(locations.stream()
            .anyMatch(loc -> "Main Cafeteria".equals(loc.getName())));
    }

    @Test
    @DisplayName("Should load Student Union Cafe")
    void testStudentUnionCafeLoaded() {
        List<Location> locations = locationRepository.findAll();
        
        assertTrue(locations.stream()
            .anyMatch(loc -> "Student Union Cafe".equals(loc.getName())));
    }

    @Test
    @DisplayName("Should load Central Library")
    void testCentralLibraryLoaded() {
        List<Location> locations = locationRepository.findAll();
        
        assertTrue(locations.stream()
            .anyMatch(loc -> "Central Library".equals(loc.getName())));
    }

    @Test
    @DisplayName("Should load Science Library")
    void testScienceLibraryLoaded() {
        List<Location> locations = locationRepository.findAll();
        
        assertTrue(locations.stream()
            .anyMatch(loc -> "Science Library".equals(loc.getName())));
    }

    @Test
    @DisplayName("Should load Campus Recreation Center")
    void testRecreationCenterLoaded() {
        List<Location> locations = locationRepository.findAll();
        
        assertTrue(locations.stream()
            .anyMatch(loc -> "Campus Recreation Center".equals(loc.getName())));
    }

    @Test
    @DisplayName("Should load Stadium")
    void testStadiumLoaded() {
        List<Location> locations = locationRepository.findAll();
        
        assertTrue(locations.stream()
            .anyMatch(loc -> "Stadium".equals(loc.getName())));
    }

    @Test
    @DisplayName("Should load North Hall")
    void testNorthHallLoaded() {
        List<Location> locations = locationRepository.findAll();
        
        assertTrue(locations.stream()
            .anyMatch(loc -> "North Hall".equals(loc.getName())));
    }

    @Test
    @DisplayName("Should load South Tower")
    void testSouthTowerLoaded() {
        List<Location> locations = locationRepository.findAll();
        
        assertTrue(locations.stream()
            .anyMatch(loc -> "South Tower".equals(loc.getName())));
    }

    @Test
    @DisplayName("Should have 3 academic locations")
    void testAcademicLocationsCount() {
        LocationCategory academic = categoryRepository.findAll().stream()
            .filter(cat -> "Academic Buildings".equals(cat.getName()))
            .findFirst()
            .orElseThrow();
        
        List<Location> academicLocations = locationRepository.findByCategoryId(academic.getId());
        assertEquals(3, academicLocations.size());
    }

    @Test
    @DisplayName("Should have 2 dining locations")
    void testDiningLocationsCount() {
        LocationCategory dining = categoryRepository.findAll().stream()
            .filter(cat -> "Dining".equals(cat.getName()))
            .findFirst()
            .orElseThrow();
        
        List<Location> diningLocations = locationRepository.findByCategoryId(dining.getId());
        assertEquals(2, diningLocations.size());
    }

    @Test
    @DisplayName("Should have 2 library locations")
    void testLibraryLocationsCount() {
        LocationCategory library = categoryRepository.findAll().stream()
            .filter(cat -> "Library".equals(cat.getName()))
            .findFirst()
            .orElseThrow();
        
        List<Location> libraryLocations = locationRepository.findByCategoryId(library.getId());
        assertEquals(2, libraryLocations.size());
    }

    @Test
    @DisplayName("Should have 2 sports locations")
    void testSportsLocationsCount() {
        LocationCategory sports = categoryRepository.findAll().stream()
            .filter(cat -> "Sports & Recreation".equals(cat.getName()))
            .findFirst()
            .orElseThrow();
        
        List<Location> sportsLocations = locationRepository.findByCategoryId(sports.getId());
        assertEquals(2, sportsLocations.size());
    }

    @Test
    @DisplayName("Should have 2 residence locations")
    void testResidenceLocationsCount() {
        LocationCategory residence = categoryRepository.findAll().stream()
            .filter(cat -> "Residence Halls".equals(cat.getName()))
            .findFirst()
            .orElseThrow();
        
        List<Location> residenceLocations = locationRepository.findByCategoryId(residence.getId());
        assertEquals(2, residenceLocations.size());
    }

    @Test
    @DisplayName("All locations should have valid coordinates")
    void testAllLocationsHaveValidCoordinates() {
        List<Location> locations = locationRepository.findAll();
        
        assertTrue(locations.stream()
            .allMatch(loc -> loc.getLatitude() != null && 
                           loc.getLongitude() != null &&
                           loc.getLatitude() >= -90.0 && 
                           loc.getLatitude() <= 90.0 &&
                           loc.getLongitude() >= -180.0 && 
                           loc.getLongitude() <= 180.0));
    }

    @Test
    @DisplayName("All locations should have names")
    void testAllLocationsHaveNames() {
        List<Location> locations = locationRepository.findAll();
        
        assertTrue(locations.stream()
            .allMatch(loc -> loc.getName() != null && !loc.getName().isEmpty()));
    }

    @Test
    @DisplayName("All locations should have descriptions")
    void testAllLocationsHaveDescriptions() {
        List<Location> locations = locationRepository.findAll();
        
        assertTrue(locations.stream()
            .allMatch(loc -> loc.getDescription() != null && !loc.getDescription().isEmpty()));
    }

    @Test
    @DisplayName("All categories should have names")
    void testAllCategoriesHaveNames() {
        List<LocationCategory> categories = categoryRepository.findAll();
        
        assertTrue(categories.stream()
            .allMatch(cat -> cat.getName() != null && !cat.getName().isEmpty()));
    }

    @Test
    @DisplayName("All categories should have descriptions")
    void testAllCategoriesHaveDescriptions() {
        List<LocationCategory> categories = categoryRepository.findAll();
        
        assertTrue(categories.stream()
            .allMatch(cat -> cat.getDescription() != null && !cat.getDescription().isEmpty()));
    }

    @Test
    @DisplayName("All locations should be associated with a category")
    void testAllLocationsHaveCategories() {
        List<Location> locations = locationRepository.findAll();
        
        assertTrue(locations.stream()
            .allMatch(loc -> loc.getCategory() != null));
    }

    @Test
    @DisplayName("Engineering Building should have correct details")
    void testEngineeringBuildingDetails() {
        Location engineeringBuilding = locationRepository.findAll().stream()
            .filter(loc -> "Engineering Building".equals(loc.getName()))
            .findFirst()
            .orElseThrow();
        
        assertEquals("Main building for engineering departments", 
                    engineeringBuilding.getDescription());
        assertEquals(40.7128, engineeringBuilding.getLatitude());
        assertEquals(-74.0060, engineeringBuilding.getLongitude());
        assertEquals("Academic Buildings", engineeringBuilding.getCategory().getName());
    }

    @Test
    @DisplayName("Central Library should have correct details")
    void testCentralLibraryDetails() {
        Location centralLibrary = locationRepository.findAll().stream()
            .filter(loc -> "Central Library".equals(loc.getName()))
            .findFirst()
            .orElseThrow();
        
        assertEquals("Main campus library with extensive collection", 
                    centralLibrary.getDescription());
        assertEquals(40.7125, centralLibrary.getLatitude());
        assertEquals(-74.0061, centralLibrary.getLongitude());
        assertEquals("Library", centralLibrary.getCategory().getName());
    }

    @Test
    @DisplayName("Academic Buildings category should have correct description")
    void testAcademicCategoryDescription() {
        LocationCategory academic = categoryRepository.findAll().stream()
            .filter(cat -> "Academic Buildings".equals(cat.getName()))
            .findFirst()
            .orElseThrow();
        
        assertEquals("Buildings for classes and lectures", academic.getDescription());
    }

    @Test
    @DisplayName("All locations should have unique IDs")
    void testAllLocationsHaveUniqueIds() {
        List<Location> locations = locationRepository.findAll();
        long uniqueIdCount = locations.stream()
            .map(Location::getId)
            .distinct()
            .count();
        
        assertEquals(locations.size(), uniqueIdCount);
    }

    @Test
    @DisplayName("All categories should have unique IDs")
    void testAllCategoriesHaveUniqueIds() {
        List<LocationCategory> categories = categoryRepository.findAll();
        long uniqueIdCount = categories.stream()
            .map(LocationCategory::getId)
            .distinct()
            .count();
        
        assertEquals(categories.size(), uniqueIdCount);
    }
}
