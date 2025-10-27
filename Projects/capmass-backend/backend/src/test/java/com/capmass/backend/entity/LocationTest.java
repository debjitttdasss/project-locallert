package com.capmass.backend.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Location Entity Tests")
class LocationTest {

    private Location location;
    private LocationCategory category;

    @BeforeEach
    void setUp() {
        category = new LocationCategory(1L, "Academic Buildings", "Buildings for classes");
        location = new Location(1L, "Engineering Building", "Main engineering building", 
                               category, 40.7128, -74.0060);
    }

    @Test
    @DisplayName("Should create location with all fields")
    void testLocationCreation() {
        assertNotNull(location);
        assertEquals(1L, location.getId());
        assertEquals("Engineering Building", location.getName());
        assertEquals("Main engineering building", location.getDescription());
        assertEquals(category, location.getCategory());
        assertEquals(40.7128, location.getLatitude());
        assertEquals(-74.0060, location.getLongitude());
    }

    @Test
    @DisplayName("Should create location with no-args constructor")
    void testNoArgsConstructor() {
        Location newLocation = new Location();
        assertNotNull(newLocation);
        assertNull(newLocation.getId());
        assertNull(newLocation.getName());
        assertNull(newLocation.getDescription());
        assertNull(newLocation.getCategory());
        assertNull(newLocation.getLatitude());
        assertNull(newLocation.getLongitude());
    }

    @Test
    @DisplayName("Should set and get id")
    void testSetAndGetId() {
        location.setId(2L);
        assertEquals(2L, location.getId());
    }

    @Test
    @DisplayName("Should set and get name")
    void testSetAndGetName() {
        location.setName("Science Building");
        assertEquals("Science Building", location.getName());
    }

    @Test
    @DisplayName("Should set and get description")
    void testSetAndGetDescription() {
        location.setDescription("New description");
        assertEquals("New description", location.getDescription());
    }

    @Test
    @DisplayName("Should set and get category")
    void testSetAndGetCategory() {
        LocationCategory newCategory = new LocationCategory(2L, "Library", "Study facilities");
        location.setCategory(newCategory);
        assertEquals(newCategory, location.getCategory());
    }

    @Test
    @DisplayName("Should set and get latitude")
    void testSetAndGetLatitude() {
        location.setLatitude(41.0);
        assertEquals(41.0, location.getLatitude());
    }

    @Test
    @DisplayName("Should set and get longitude")
    void testSetAndGetLongitude() {
        location.setLongitude(-75.0);
        assertEquals(-75.0, location.getLongitude());
    }

    @Test
    @DisplayName("Should handle null description")
    void testNullDescription() {
        location.setDescription(null);
        assertNull(location.getDescription());
    }

    @Test
    @DisplayName("Should correctly implement equals and hashCode for same object")
    void testEqualsAndHashCodeSameObject() {
        assertEquals(location, location);
        assertEquals(location.hashCode(), location.hashCode());
    }

    @Test
    @DisplayName("Should correctly implement equals for identical locations")
    void testEqualsForIdenticalLocations() {
        Location sameLocation = new Location(1L, "Engineering Building", "Main engineering building", 
                                            category, 40.7128, -74.0060);
        assertEquals(location, sameLocation);
        assertEquals(location.hashCode(), sameLocation.hashCode());
    }

    @Test
    @DisplayName("Should correctly implement equals for different locations")
    void testEqualsForDifferentLocations() {
        Location differentLocation = new Location(2L, "Science Building", "Science labs", 
                                                  category, 40.7130, -74.0058);
        assertNotEquals(location, differentLocation);
    }

    @Test
    @DisplayName("Should not equal null")
    void testNotEqualsNull() {
        assertNotEquals(location, null);
    }

    @Test
    @DisplayName("Should not equal different class")
    void testNotEqualsDifferentClass() {
        assertNotEquals(location, "Not a Location");
    }

    @Test
    @DisplayName("Should generate correct toString")
    void testToString() {
        String toString = location.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Engineering Building"), 
            "toString should contain location name");
        assertTrue(toString.contains("40.7") || toString.contains("40,7"), 
            "toString should contain latitude");
        assertTrue(toString.contains("-74.0") || toString.contains("-74,0"), 
            "toString should contain longitude");
    }

    @Test
    @DisplayName("Should handle extreme latitude values")
    void testExtremeLatitudeValues() {
        location.setLatitude(90.0);
        assertEquals(90.0, location.getLatitude());
        
        location.setLatitude(-90.0);
        assertEquals(-90.0, location.getLatitude());
    }

    @Test
    @DisplayName("Should handle extreme longitude values")
    void testExtremeLongitudeValues() {
        location.setLongitude(180.0);
        assertEquals(180.0, location.getLongitude());
        
        location.setLongitude(-180.0);
        assertEquals(-180.0, location.getLongitude());
    }

    @Test
    @DisplayName("Should handle very long description")
    void testVeryLongDescription() {
        String longDescription = "A".repeat(1000);
        location.setDescription(longDescription);
        assertEquals(longDescription, location.getDescription());
        assertEquals(1000, location.getDescription().length());
    }

    @Test
    @DisplayName("Should handle special characters in name")
    void testSpecialCharactersInName() {
        location.setName("Building #1 - North & South");
        assertEquals("Building #1 - North & South", location.getName());
    }

    @Test
    @DisplayName("Should handle special characters in description")
    void testSpecialCharactersInDescription() {
        location.setDescription("Located at 123 Main St., near the library & cafeteria");
        assertEquals("Located at 123 Main St., near the library & cafeteria", location.getDescription());
    }

    @Test
    @DisplayName("Should maintain category association")
    void testCategoryAssociation() {
        assertNotNull(location.getCategory());
        assertEquals("Academic Buildings", location.getCategory().getName());
        assertEquals(1L, location.getCategory().getId());
    }

    @Test
    @DisplayName("Should allow changing category")
    void testChangingCategory() {
        LocationCategory dining = new LocationCategory(3L, "Dining", "Food facilities");
        location.setCategory(dining);
        assertEquals(dining, location.getCategory());
        assertEquals("Dining", location.getCategory().getName());
    }

    @Test
    @DisplayName("Should handle decimal precision for coordinates")
    void testCoordinateDecimalPrecision() {
        location.setLatitude(40.712345678901234);
        location.setLongitude(-74.006012345678901);
        assertEquals(40.712345678901234, location.getLatitude(), 0.0000000000001);
        assertEquals(-74.006012345678901, location.getLongitude(), 0.0000000000001);
    }

    @Test
    @DisplayName("Should create location using all-args constructor")
    void testAllArgsConstructor() {
        Location newLocation = new Location(5L, "Library", "Central library", 
                                           category, 42.0, -73.5);
        assertEquals(5L, newLocation.getId());
        assertEquals("Library", newLocation.getName());
        assertEquals("Central library", newLocation.getDescription());
        assertEquals(category, newLocation.getCategory());
        assertEquals(42.0, newLocation.getLatitude());
        assertEquals(-73.5, newLocation.getLongitude());
    }
}
