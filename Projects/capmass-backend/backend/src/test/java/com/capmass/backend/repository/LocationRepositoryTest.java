package com.capmass.backend.repository;

import com.capmass.backend.entity.Location;
import com.capmass.backend.entity.LocationCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("LocationRepository Integration Tests")
class LocationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationCategoryRepository categoryRepository;

    private LocationCategory academicCategory;
    private LocationCategory libraryCategory;
    private Location engineeringBuilding;
    private Location scienceHall;
    private Location centralLibrary;

    @BeforeEach
    void setUp() {
        // Clear existing data
        locationRepository.deleteAll();
        categoryRepository.deleteAll();
        
        // Create categories
        academicCategory = new LocationCategory(null, "Academic Buildings", "Educational facilities");
        libraryCategory = new LocationCategory(null, "Library", "Study facilities");
        
        academicCategory = categoryRepository.save(academicCategory);
        libraryCategory = categoryRepository.save(libraryCategory);
        
        // Create locations
        engineeringBuilding = new Location(null, "Engineering Building", 
            "Main engineering building", academicCategory, 40.7128, -74.0060);
        scienceHall = new Location(null, "Science Hall", 
            "Chemistry and Physics labs", academicCategory, 40.7130, -74.0058);
        centralLibrary = new Location(null, "Central Library", 
            "Main campus library", libraryCategory, 40.7125, -74.0061);
    }

    @Test
    @DisplayName("Should save location successfully")
    void testSaveLocation() {
        Location saved = locationRepository.save(engineeringBuilding);
        
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("Engineering Building", saved.getName());
        assertEquals("Main engineering building", saved.getDescription());
        assertEquals(academicCategory.getId(), saved.getCategory().getId());
        assertEquals(40.7128, saved.getLatitude());
        assertEquals(-74.0060, saved.getLongitude());
    }

    @Test
    @DisplayName("Should find location by id")
    void testFindById() {
        Location saved = locationRepository.save(engineeringBuilding);
        
        Optional<Location> found = locationRepository.findById(saved.getId());
        
        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
        assertEquals("Engineering Building", found.get().getName());
    }

    @Test
    @DisplayName("Should return empty when finding non-existent location")
    void testFindByIdNotFound() {
        Optional<Location> found = locationRepository.findById(999L);
        
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Should find all locations")
    void testFindAll() {
        locationRepository.save(engineeringBuilding);
        locationRepository.save(scienceHall);
        locationRepository.save(centralLibrary);
        
        List<Location> allLocations = locationRepository.findAll();
        
        assertEquals(3, allLocations.size());
    }

    @Test
    @DisplayName("Should find locations by category id")
    void testFindByCategoryId() {
        locationRepository.save(engineeringBuilding);
        locationRepository.save(scienceHall);
        locationRepository.save(centralLibrary);
        
        List<Location> academicLocations = locationRepository.findByCategoryId(academicCategory.getId());
        
        assertEquals(2, academicLocations.size());
        assertTrue(academicLocations.stream()
            .allMatch(loc -> loc.getCategory().getId().equals(academicCategory.getId())));
    }

    @Test
    @DisplayName("Should return empty list when no locations for category")
    void testFindByCategoryIdEmpty() {
        LocationCategory emptyCategory = new LocationCategory(null, "Empty", "No locations");
        emptyCategory = categoryRepository.save(emptyCategory);
        
        List<Location> locations = locationRepository.findByCategoryId(emptyCategory.getId());
        
        assertTrue(locations.isEmpty());
    }

    @Test
    @DisplayName("Should update location")
    void testUpdateLocation() {
        Location saved = locationRepository.save(engineeringBuilding);
        
        saved.setName("Updated Engineering Building");
        saved.setDescription("Updated description");
        saved.setLatitude(41.0);
        saved.setLongitude(-75.0);
        
        Location updated = locationRepository.save(saved);
        
        assertEquals("Updated Engineering Building", updated.getName());
        assertEquals("Updated description", updated.getDescription());
        assertEquals(41.0, updated.getLatitude());
        assertEquals(-75.0, updated.getLongitude());
    }

    @Test
    @DisplayName("Should delete location")
    void testDeleteLocation() {
        Location saved = locationRepository.save(engineeringBuilding);
        Long id = saved.getId();
        
        locationRepository.delete(saved);
        
        Optional<Location> found = locationRepository.findById(id);
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Should delete all locations")
    void testDeleteAll() {
        locationRepository.save(engineeringBuilding);
        locationRepository.save(scienceHall);
        locationRepository.save(centralLibrary);
        
        locationRepository.deleteAll();
        
        List<Location> allLocations = locationRepository.findAll();
        assertTrue(allLocations.isEmpty());
    }

    @Test
    @DisplayName("Should count locations")
    void testCount() {
        locationRepository.save(engineeringBuilding);
        locationRepository.save(scienceHall);
        
        long count = locationRepository.count();
        
        assertEquals(2, count);
    }

    @Test
    @DisplayName("Should check if location exists by id")
    void testExistsById() {
        Location saved = locationRepository.save(engineeringBuilding);
        
        boolean exists = locationRepository.existsById(saved.getId());
        boolean notExists = locationRepository.existsById(999L);
        
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    @DisplayName("Should maintain relationship with category")
    void testCategoryRelationship() {
        Location saved = locationRepository.save(engineeringBuilding);
        
        entityManager.flush();
        entityManager.clear();
        
        Location found = locationRepository.findById(saved.getId()).orElseThrow();
        
        assertNotNull(found.getCategory());
        assertEquals(academicCategory.getId(), found.getCategory().getId());
        assertEquals("Academic Buildings", found.getCategory().getName());
    }

    @Test
    @DisplayName("Should save multiple locations with same category")
    void testMultipleLocationsWithSameCategory() {
        locationRepository.save(engineeringBuilding);
        locationRepository.save(scienceHall);
        
        List<Location> academicLocations = locationRepository.findByCategoryId(academicCategory.getId());
        
        assertEquals(2, academicLocations.size());
        assertTrue(academicLocations.stream()
            .allMatch(loc -> loc.getCategory().getName().equals("Academic Buildings")));
    }

    @Test
    @DisplayName("Should handle location with null description")
    void testLocationWithNullDescription() {
        Location locationWithNullDesc = new Location(null, "Test Building", 
            null, academicCategory, 40.0, -74.0);
        
        Location saved = locationRepository.save(locationWithNullDesc);
        
        assertNotNull(saved.getId());
        assertNull(saved.getDescription());
    }

    @Test
    @DisplayName("Should handle very long description")
    void testVeryLongDescription() {
        String longDescription = "A".repeat(1000);
        engineeringBuilding.setDescription(longDescription);
        
        Location saved = locationRepository.save(engineeringBuilding);
        
        assertEquals(longDescription, saved.getDescription());
        assertEquals(1000, saved.getDescription().length());
    }

    @Test
    @DisplayName("Should handle extreme coordinate values")
    void testExtremeCoordinates() {
        engineeringBuilding.setLatitude(90.0);
        engineeringBuilding.setLongitude(-180.0);
        
        Location saved = locationRepository.save(engineeringBuilding);
        
        assertEquals(90.0, saved.getLatitude());
        assertEquals(-180.0, saved.getLongitude());
    }

    @Test
    @DisplayName("Should handle decimal precision for coordinates")
    void testCoordinateDecimalPrecision() {
        engineeringBuilding.setLatitude(40.712345678901234);
        engineeringBuilding.setLongitude(-74.006012345678901);
        
        Location saved = locationRepository.save(engineeringBuilding);
        
        assertEquals(40.712345678901234, saved.getLatitude(), 0.0000000000001);
        assertEquals(-74.006012345678901, saved.getLongitude(), 0.0000000000001);
    }

    @Test
    @DisplayName("Should save locations in batch")
    void testSaveAll() {
        List<Location> locations = List.of(engineeringBuilding, scienceHall, centralLibrary);
        
        List<Location> savedLocations = locationRepository.saveAll(locations);
        
        assertEquals(3, savedLocations.size());
        assertTrue(savedLocations.stream().allMatch(loc -> loc.getId() != null));
    }

    @Test
    @DisplayName("Should delete location by id")
    void testDeleteById() {
        Location saved = locationRepository.save(engineeringBuilding);
        Long id = saved.getId();
        
        locationRepository.deleteById(id);
        
        assertFalse(locationRepository.existsById(id));
    }

    @Test
    @DisplayName("Should handle special characters in name")
    void testSpecialCharactersInName() {
        engineeringBuilding.setName("Building #1 - North & South");
        
        Location saved = locationRepository.save(engineeringBuilding);
        
        assertEquals("Building #1 - North & South", saved.getName());
    }

    @Test
    @DisplayName("Should retrieve locations in order")
    void testFindAllOrder() {
        Location loc1 = locationRepository.save(engineeringBuilding);
        Location loc2 = locationRepository.save(scienceHall);
        Location loc3 = locationRepository.save(centralLibrary);
        
        List<Location> allLocations = locationRepository.findAll();
        
        assertEquals(3, allLocations.size());
        // Verify all saved locations are present
        assertTrue(allLocations.stream().anyMatch(l -> l.getId().equals(loc1.getId())));
        assertTrue(allLocations.stream().anyMatch(l -> l.getId().equals(loc2.getId())));
        assertTrue(allLocations.stream().anyMatch(l -> l.getId().equals(loc3.getId())));
    }

    @Test
    @DisplayName("Should update only changed fields")
    void testPartialUpdate() {
        Location saved = locationRepository.save(engineeringBuilding);
        String originalName = saved.getName();
        
        saved.setDescription("Updated description only");
        Location updated = locationRepository.save(saved);
        
        assertEquals(originalName, updated.getName());
        assertEquals("Updated description only", updated.getDescription());
    }
}
