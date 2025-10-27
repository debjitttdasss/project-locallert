package com.capmass.backend.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LocationCategory Entity Tests")
class LocationCategoryTest {

    private LocationCategory category;

    @BeforeEach
    void setUp() {
        category = new LocationCategory(1L, "Academic Buildings", "Buildings for classes and lectures");
    }

    @Test
    @DisplayName("Should create category with all fields")
    void testCategoryCreation() {
        assertNotNull(category);
        assertEquals(1L, category.getId());
        assertEquals("Academic Buildings", category.getName());
        assertEquals("Buildings for classes and lectures", category.getDescription());
    }

    @Test
    @DisplayName("Should create category with no-args constructor")
    void testNoArgsConstructor() {
        LocationCategory newCategory = new LocationCategory();
        assertNotNull(newCategory);
        assertNull(newCategory.getId());
        assertNull(newCategory.getName());
        assertNull(newCategory.getDescription());
    }

    @Test
    @DisplayName("Should set and get id")
    void testSetAndGetId() {
        category.setId(2L);
        assertEquals(2L, category.getId());
    }

    @Test
    @DisplayName("Should set and get name")
    void testSetAndGetName() {
        category.setName("Library");
        assertEquals("Library", category.getName());
    }

    @Test
    @DisplayName("Should set and get description")
    void testSetAndGetDescription() {
        category.setDescription("Study and research facilities");
        assertEquals("Study and research facilities", category.getDescription());
    }

    @Test
    @DisplayName("Should handle null description")
    void testNullDescription() {
        category.setDescription(null);
        assertNull(category.getDescription());
    }

    @Test
    @DisplayName("Should correctly implement equals and hashCode for same object")
    void testEqualsAndHashCodeSameObject() {
        assertEquals(category, category);
        assertEquals(category.hashCode(), category.hashCode());
    }

    @Test
    @DisplayName("Should correctly implement equals for identical categories")
    void testEqualsForIdenticalCategories() {
        LocationCategory sameCategory = new LocationCategory(1L, "Academic Buildings", 
                                                             "Buildings for classes and lectures");
        assertEquals(category, sameCategory);
        assertEquals(category.hashCode(), sameCategory.hashCode());
    }

    @Test
    @DisplayName("Should correctly implement equals for different categories")
    void testEqualsForDifferentCategories() {
        LocationCategory differentCategory = new LocationCategory(2L, "Library", 
                                                                  "Study facilities");
        assertNotEquals(category, differentCategory);
    }

    @Test
    @DisplayName("Should not equal null")
    void testNotEqualsNull() {
        assertNotEquals(category, null);
    }

    @Test
    @DisplayName("Should not equal different class")
    void testNotEqualsDifferentClass() {
        assertNotEquals(category, "Not a LocationCategory");
    }

    @Test
    @DisplayName("Should generate correct toString")
    void testToString() {
        String toString = category.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Academic Buildings"));
        assertTrue(toString.contains("Buildings for classes and lectures"));
    }

    @Test
    @DisplayName("Should handle very long description")
    void testVeryLongDescription() {
        String longDescription = "A".repeat(500);
        category.setDescription(longDescription);
        assertEquals(longDescription, category.getDescription());
        assertEquals(500, category.getDescription().length());
    }

    @Test
    @DisplayName("Should handle special characters in name")
    void testSpecialCharactersInName() {
        category.setName("Sports & Recreation");
        assertEquals("Sports & Recreation", category.getName());
    }

    @Test
    @DisplayName("Should handle special characters in description")
    void testSpecialCharactersInDescription() {
        category.setDescription("Includes gym, pool & fitness center");
        assertEquals("Includes gym, pool & fitness center", category.getDescription());
    }

    @Test
    @DisplayName("Should create category using all-args constructor")
    void testAllArgsConstructor() {
        LocationCategory newCategory = new LocationCategory(5L, "Dining", "Food facilities");
        assertEquals(5L, newCategory.getId());
        assertEquals("Dining", newCategory.getName());
        assertEquals("Food facilities", newCategory.getDescription());
    }

    @Test
    @DisplayName("Should handle empty string name")
    void testEmptyStringName() {
        category.setName("");
        assertEquals("", category.getName());
    }

    @Test
    @DisplayName("Should handle empty string description")
    void testEmptyStringDescription() {
        category.setDescription("");
        assertEquals("", category.getDescription());
    }

    @Test
    @DisplayName("Should handle Unicode characters in name")
    void testUnicodeCharactersInName() {
        category.setName("图书馆 Library");
        assertEquals("图书馆 Library", category.getName());
    }

    @Test
    @DisplayName("Should handle Unicode characters in description")
    void testUnicodeCharactersInDescription() {
        category.setDescription("学习设施 Study facilities");
        assertEquals("学习设施 Study facilities", category.getDescription());
    }

    @Test
    @DisplayName("Should handle multiple word name")
    void testMultipleWordName() {
        category.setName("Sports and Recreation Facilities");
        assertEquals("Sports and Recreation Facilities", category.getName());
    }

    @Test
    @DisplayName("Should handle multiple sentence description")
    void testMultipleSentenceDescription() {
        category.setDescription("Primary dining facility. Open 7am to 10pm. Accepts meal plans.");
        assertEquals("Primary dining facility. Open 7am to 10pm. Accepts meal plans.", 
                    category.getDescription());
    }
}
