package com.capmass.backend.repository;

import com.capmass.backend.entity.LocationCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("LocationCategoryRepository Integration Tests")
class LocationCategoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LocationCategoryRepository categoryRepository;

    private LocationCategory academicCategory;
    private LocationCategory libraryCategory;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
        
        academicCategory = new LocationCategory(null, "Academic Buildings", 
            "Buildings for classes and lectures");
        libraryCategory = new LocationCategory(null, "Library", 
            "Study and research facilities");
    }

    @Test
    @DisplayName("Should save category successfully")
    void testSaveCategory() {
        LocationCategory saved = categoryRepository.save(academicCategory);
        
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("Academic Buildings", saved.getName());
        assertEquals("Buildings for classes and lectures", saved.getDescription());
    }

    @Test
    @DisplayName("Should find category by id")
    void testFindById() {
        LocationCategory saved = categoryRepository.save(academicCategory);
        
        Optional<LocationCategory> found = categoryRepository.findById(saved.getId());
        
        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
        assertEquals("Academic Buildings", found.get().getName());
    }

    @Test
    @DisplayName("Should return empty when finding non-existent category")
    void testFindByIdNotFound() {
        Optional<LocationCategory> found = categoryRepository.findById(999L);
        
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Should find all categories")
    void testFindAll() {
        categoryRepository.save(academicCategory);
        categoryRepository.save(libraryCategory);
        
        List<LocationCategory> allCategories = categoryRepository.findAll();
        
        assertEquals(2, allCategories.size());
    }

    @Test
    @DisplayName("Should update category")
    void testUpdateCategory() {
        LocationCategory saved = categoryRepository.save(academicCategory);
        
        saved.setName("Updated Academic");
        saved.setDescription("Updated description");
        
        LocationCategory updated = categoryRepository.save(saved);
        
        assertEquals("Updated Academic", updated.getName());
        assertEquals("Updated description", updated.getDescription());
    }

    @Test
    @DisplayName("Should delete category")
    void testDeleteCategory() {
        LocationCategory saved = categoryRepository.save(academicCategory);
        Long id = saved.getId();
        
        categoryRepository.delete(saved);
        
        Optional<LocationCategory> found = categoryRepository.findById(id);
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Should delete all categories")
    void testDeleteAll() {
        categoryRepository.save(academicCategory);
        categoryRepository.save(libraryCategory);
        
        categoryRepository.deleteAll();
        
        List<LocationCategory> allCategories = categoryRepository.findAll();
        assertTrue(allCategories.isEmpty());
    }

    @Test
    @DisplayName("Should count categories")
    void testCount() {
        categoryRepository.save(academicCategory);
        categoryRepository.save(libraryCategory);
        
        long count = categoryRepository.count();
        
        assertEquals(2, count);
    }

    @Test
    @DisplayName("Should check if category exists by id")
    void testExistsById() {
        LocationCategory saved = categoryRepository.save(academicCategory);
        
        boolean exists = categoryRepository.existsById(saved.getId());
        boolean notExists = categoryRepository.existsById(999L);
        
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    @DisplayName("Should handle category with null description")
    void testCategoryWithNullDescription() {
        LocationCategory categoryWithNullDesc = new LocationCategory(null, "Test Category", null);
        
        LocationCategory saved = categoryRepository.save(categoryWithNullDesc);
        
        assertNotNull(saved.getId());
        assertNull(saved.getDescription());
    }

    @Test
    @DisplayName("Should handle very long description")
    void testVeryLongDescription() {
        String longDescription = "A".repeat(500);
        academicCategory.setDescription(longDescription);
        
        LocationCategory saved = categoryRepository.save(academicCategory);
        
        assertEquals(longDescription, saved.getDescription());
        assertEquals(500, saved.getDescription().length());
    }

    @Test
    @DisplayName("Should save categories in batch")
    void testSaveAll() {
        List<LocationCategory> categories = List.of(academicCategory, libraryCategory);
        
        List<LocationCategory> savedCategories = categoryRepository.saveAll(categories);
        
        assertEquals(2, savedCategories.size());
        assertTrue(savedCategories.stream().allMatch(cat -> cat.getId() != null));
    }

    @Test
    @DisplayName("Should delete category by id")
    void testDeleteById() {
        LocationCategory saved = categoryRepository.save(academicCategory);
        Long id = saved.getId();
        
        categoryRepository.deleteById(id);
        
        assertFalse(categoryRepository.existsById(id));
    }

    @Test
    @DisplayName("Should handle special characters in name")
    void testSpecialCharactersInName() {
        academicCategory.setName("Sports & Recreation");
        
        LocationCategory saved = categoryRepository.save(academicCategory);
        
        assertEquals("Sports & Recreation", saved.getName());
    }

    @Test
    @DisplayName("Should retrieve categories in order")
    void testFindAllOrder() {
        LocationCategory cat1 = categoryRepository.save(academicCategory);
        LocationCategory cat2 = categoryRepository.save(libraryCategory);
        
        List<LocationCategory> allCategories = categoryRepository.findAll();
        
        assertEquals(2, allCategories.size());
        assertTrue(allCategories.stream().anyMatch(c -> c.getId().equals(cat1.getId())));
        assertTrue(allCategories.stream().anyMatch(c -> c.getId().equals(cat2.getId())));
    }

    @Test
    @DisplayName("Should update only changed fields")
    void testPartialUpdate() {
        LocationCategory saved = categoryRepository.save(academicCategory);
        String originalName = saved.getName();
        
        saved.setDescription("Updated description only");
        LocationCategory updated = categoryRepository.save(saved);
        
        assertEquals(originalName, updated.getName());
        assertEquals("Updated description only", updated.getDescription());
    }

    @Test
    @DisplayName("Should persist changes after flush")
    void testFlushAndClear() {
        LocationCategory saved = categoryRepository.save(academicCategory);
        entityManager.flush();
        entityManager.clear();
        
        LocationCategory found = categoryRepository.findById(saved.getId()).orElseThrow();
        
        assertEquals("Academic Buildings", found.getName());
    }

    @Test
    @DisplayName("Should handle empty string description")
    void testEmptyStringDescription() {
        academicCategory.setDescription("");
        
        LocationCategory saved = categoryRepository.save(academicCategory);
        
        assertEquals("", saved.getDescription());
    }

    @Test
    @DisplayName("Should handle Unicode characters")
    void testUnicodeCharacters() {
        academicCategory.setName("图书馆 Library");
        academicCategory.setDescription("学习设施 Study facilities");
        
        LocationCategory saved = categoryRepository.save(academicCategory);
        
        assertEquals("图书馆 Library", saved.getName());
        assertEquals("学习设施 Study facilities", saved.getDescription());
    }

    @Test
    @DisplayName("Should not save duplicate category names")
    void testUniqueCategoryName() {
        categoryRepository.save(academicCategory);
        
        LocationCategory duplicate = new LocationCategory(null, "Academic Buildings", 
            "Different description");
        
        assertThrows(DataIntegrityViolationException.class, () -> {
            categoryRepository.save(duplicate);
            entityManager.flush();
        });
    }

    @Test
    @DisplayName("Should handle multiple sentence description")
    void testMultipleSentenceDescription() {
        String multiSentence = "Primary category. Contains multiple buildings. Open all day.";
        academicCategory.setDescription(multiSentence);
        
        LocationCategory saved = categoryRepository.save(academicCategory);
        
        assertEquals(multiSentence, saved.getDescription());
    }

    @Test
    @DisplayName("Should find by id after multiple saves")
    void testFindByIdAfterMultipleSaves() {
        LocationCategory saved1 = categoryRepository.save(academicCategory);
        categoryRepository.save(libraryCategory);
        
        Optional<LocationCategory> found = categoryRepository.findById(saved1.getId());
        
        assertTrue(found.isPresent());
        assertEquals("Academic Buildings", found.get().getName());
    }

    @Test
    @DisplayName("Should maintain data integrity after update")
    void testDataIntegrityAfterUpdate() {
        LocationCategory saved = categoryRepository.save(academicCategory);
        Long originalId = saved.getId();
        
        saved.setName("Updated Name");
        LocationCategory updated = categoryRepository.save(saved);
        
        assertEquals(originalId, updated.getId());
        assertEquals("Updated Name", updated.getName());
    }
}
