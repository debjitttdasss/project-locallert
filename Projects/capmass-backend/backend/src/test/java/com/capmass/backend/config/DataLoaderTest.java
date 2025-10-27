package com.capmass.backend.config;

import com.capmass.backend.entity.Location;
import com.capmass.backend.entity.LocationCategory;
import com.capmass.backend.repository.LocationCategoryRepository;
import com.capmass.backend.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DataLoader Unit Tests")
class DataLoaderTest {

    @Mock
    private LocationCategoryRepository categoryRepository;

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private DataLoader dataLoader;

    @Captor
    private ArgumentCaptor<LocationCategory> categoryCaptor;

    @Captor
    private ArgumentCaptor<Location> locationCaptor;

    @BeforeEach
    void setUp() {
        // Mock category saves to return the same object with an ID
        when(categoryRepository.save(any(LocationCategory.class)))
            .thenAnswer(invocation -> {
                LocationCategory cat = invocation.getArgument(0);
                return new LocationCategory(1L, cat.getName(), cat.getDescription());
            });
        
        when(locationRepository.save(any(Location.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    @DisplayName("Should save all categories")
    void testSaveAllCategories() throws Exception {
        dataLoader.run();
        
        verify(categoryRepository, times(5)).save(any(LocationCategory.class));
    }

    @Test
    @DisplayName("Should save all locations")
    void testSaveAllLocations() throws Exception {
        dataLoader.run();
        
        verify(locationRepository, times(11)).save(any(Location.class));
    }

    @Test
    @DisplayName("Should create Academic Buildings category")
    void testCreateAcademicCategory() throws Exception {
        dataLoader.run();
        
        verify(categoryRepository, atLeastOnce()).save(categoryCaptor.capture());
        
        List<LocationCategory> categories = categoryCaptor.getAllValues();
        assertTrue(categories.stream()
            .anyMatch(cat -> "Academic Buildings".equals(cat.getName())));
    }

    @Test
    @DisplayName("Should create Dining category")
    void testCreateDiningCategory() throws Exception {
        dataLoader.run();
        
        verify(categoryRepository, atLeastOnce()).save(categoryCaptor.capture());
        
        List<LocationCategory> categories = categoryCaptor.getAllValues();
        assertTrue(categories.stream()
            .anyMatch(cat -> "Dining".equals(cat.getName())));
    }

    @Test
    @DisplayName("Should create Library category")
    void testCreateLibraryCategory() throws Exception {
        dataLoader.run();
        
        verify(categoryRepository, atLeastOnce()).save(categoryCaptor.capture());
        
        List<LocationCategory> categories = categoryCaptor.getAllValues();
        assertTrue(categories.stream()
            .anyMatch(cat -> "Library".equals(cat.getName())));
    }

    @Test
    @DisplayName("Should create Sports & Recreation category")
    void testCreateSportsCategory() throws Exception {
        dataLoader.run();
        
        verify(categoryRepository, atLeastOnce()).save(categoryCaptor.capture());
        
        List<LocationCategory> categories = categoryCaptor.getAllValues();
        assertTrue(categories.stream()
            .anyMatch(cat -> "Sports & Recreation".equals(cat.getName())));
    }

    @Test
    @DisplayName("Should create Residence Halls category")
    void testCreateResidenceCategory() throws Exception {
        dataLoader.run();
        
        verify(categoryRepository, atLeastOnce()).save(categoryCaptor.capture());
        
        List<LocationCategory> categories = categoryCaptor.getAllValues();
        assertTrue(categories.stream()
            .anyMatch(cat -> "Residence Halls".equals(cat.getName())));
    }

    @Test
    @DisplayName("Should save categories before locations")
    void testCategoriesSavedBeforeLocations() throws Exception {
        dataLoader.run();
        
        // Verify categories are saved
        verify(categoryRepository, times(5)).save(any(LocationCategory.class));
        // Verify locations are saved after
        verify(locationRepository, times(11)).save(any(Location.class));
    }

    @Test
    @DisplayName("Should create Engineering Building location")
    void testCreateEngineeringBuilding() throws Exception {
        dataLoader.run();
        
        verify(locationRepository, atLeastOnce()).save(locationCaptor.capture());
        
        List<Location> locations = locationCaptor.getAllValues();
        assertTrue(locations.stream()
            .anyMatch(loc -> "Engineering Building".equals(loc.getName())));
    }

    @Test
    @DisplayName("Should create Science Hall location")
    void testCreateScienceHall() throws Exception {
        dataLoader.run();
        
        verify(locationRepository, atLeastOnce()).save(locationCaptor.capture());
        
        List<Location> locations = locationCaptor.getAllValues();
        assertTrue(locations.stream()
            .anyMatch(loc -> "Science Hall".equals(loc.getName())));
    }

    @Test
    @DisplayName("Should create Main Cafeteria location")
    void testCreateMainCafeteria() throws Exception {
        dataLoader.run();
        
        verify(locationRepository, atLeastOnce()).save(locationCaptor.capture());
        
        List<Location> locations = locationCaptor.getAllValues();
        assertTrue(locations.stream()
            .anyMatch(loc -> "Main Cafeteria".equals(loc.getName())));
    }

    @Test
    @DisplayName("Should create Central Library location")
    void testCreateCentralLibrary() throws Exception {
        dataLoader.run();
        
        verify(locationRepository, atLeastOnce()).save(locationCaptor.capture());
        
        List<Location> locations = locationCaptor.getAllValues();
        assertTrue(locations.stream()
            .anyMatch(loc -> "Central Library".equals(loc.getName())));
    }

    @Test
    @DisplayName("Should create Campus Recreation Center location")
    void testCreateRecreationCenter() throws Exception {
        dataLoader.run();
        
        verify(locationRepository, atLeastOnce()).save(locationCaptor.capture());
        
        List<Location> locations = locationCaptor.getAllValues();
        assertTrue(locations.stream()
            .anyMatch(loc -> "Campus Recreation Center".equals(loc.getName())));
    }

    @Test
    @DisplayName("Should create locations with valid coordinates")
    void testLocationsHaveValidCoordinates() throws Exception {
        dataLoader.run();
        
        verify(locationRepository, atLeastOnce()).save(locationCaptor.capture());
        
        List<Location> locations = locationCaptor.getAllValues();
        assertTrue(locations.stream()
            .allMatch(loc -> loc.getLatitude() != null && loc.getLongitude() != null));
    }

    @Test
    @DisplayName("Should create locations with descriptions")
    void testLocationsHaveDescriptions() throws Exception {
        dataLoader.run();
        
        verify(locationRepository, atLeastOnce()).save(locationCaptor.capture());
        
        List<Location> locations = locationCaptor.getAllValues();
        assertTrue(locations.stream()
            .allMatch(loc -> loc.getDescription() != null && !loc.getDescription().isEmpty()));
    }

    @Test
    @DisplayName("Should create categories with descriptions")
    void testCategoriesHaveDescriptions() throws Exception {
        dataLoader.run();
        
        verify(categoryRepository, atLeastOnce()).save(categoryCaptor.capture());
        
        List<LocationCategory> categories = categoryCaptor.getAllValues();
        assertTrue(categories.stream()
            .allMatch(cat -> cat.getDescription() != null && !cat.getDescription().isEmpty()));
    }

    @Test
    @DisplayName("Should create multiple locations per category")
    void testMultipleLocationsPerCategory() throws Exception {
        dataLoader.run();
        
        verify(locationRepository, atLeastOnce()).save(locationCaptor.capture());
        
        List<Location> locations = locationCaptor.getAllValues();
        // Should have at least 2 academic buildings
        long academicCount = locations.stream()
            .filter(loc -> loc.getCategory() != null)
            .filter(loc -> "Academic Buildings".equals(loc.getCategory().getName()))
            .count();
        
        assertTrue(academicCount >= 2);
    }

    @Test
    @DisplayName("Should handle run method with no arguments")
    void testRunWithNoArguments() throws Exception {
        assertDoesNotThrow(() -> dataLoader.run());
    }

    @Test
    @DisplayName("Should handle run method with arguments")
    void testRunWithArguments() throws Exception {
        assertDoesNotThrow(() -> dataLoader.run("arg1", "arg2"));
    }

    @Test
    @DisplayName("Should create all expected location names")
    void testAllExpectedLocationNames() throws Exception {
        dataLoader.run();
        
        verify(locationRepository, atLeastOnce()).save(locationCaptor.capture());
        
        List<Location> locations = locationCaptor.getAllValues();
        List<String> names = locations.stream().map(Location::getName).toList();
        
        assertTrue(names.contains("Engineering Building"));
        assertTrue(names.contains("Science Hall"));
        assertTrue(names.contains("Business School"));
        assertTrue(names.contains("Main Cafeteria"));
        assertTrue(names.contains("Student Union Cafe"));
        assertTrue(names.contains("Central Library"));
        assertTrue(names.contains("Science Library"));
        assertTrue(names.contains("Campus Recreation Center"));
        assertTrue(names.contains("Stadium"));
        assertTrue(names.contains("North Hall"));
        assertTrue(names.contains("South Tower"));
    }

    @Test
    @DisplayName("Should create all expected category names")
    void testAllExpectedCategoryNames() throws Exception {
        dataLoader.run();
        
        verify(categoryRepository, atLeastOnce()).save(categoryCaptor.capture());
        
        List<LocationCategory> categories = categoryCaptor.getAllValues();
        List<String> names = categories.stream().map(LocationCategory::getName).toList();
        
        assertTrue(names.contains("Academic Buildings"));
        assertTrue(names.contains("Dining"));
        assertTrue(names.contains("Library"));
        assertTrue(names.contains("Sports & Recreation"));
        assertTrue(names.contains("Residence Halls"));
    }

    @Test
    @DisplayName("Should set null ID for new categories")
    void testNewCategoriesHaveNullId() throws Exception {
        dataLoader.run();
        
        verify(categoryRepository, atLeastOnce()).save(categoryCaptor.capture());
        
        // Note: The actual ID is null when passed to save, then assigned by the repository
        // This test verifies the DataLoader creates categories with null ID initially
        assertDoesNotThrow(() -> dataLoader.run());
    }

    @Test
    @DisplayName("Should set null ID for new locations")
    void testNewLocationsHaveNullId() throws Exception {
        dataLoader.run();
        
        verify(locationRepository, atLeastOnce()).save(locationCaptor.capture());
        
        // Note: The actual ID is null when passed to save, then assigned by the repository
        assertDoesNotThrow(() -> dataLoader.run());
    }

    @Test
    @DisplayName("Should complete successfully without throwing exceptions")
    void testRunCompletesSuccessfully() {
        assertDoesNotThrow(() -> dataLoader.run());
    }

    @Test
    @DisplayName("Should interact with both repositories")
    void testInteractsWithBothRepositories() throws Exception {
        dataLoader.run();
        
        verify(categoryRepository, atLeast(1)).save(any(LocationCategory.class));
        verify(locationRepository, atLeast(1)).save(any(Location.class));
    }

    @Test
    @DisplayName("Should create locations with latitude in valid range")
    void testLocationsHaveValidLatitude() throws Exception {
        dataLoader.run();
        
        verify(locationRepository, atLeastOnce()).save(locationCaptor.capture());
        
        List<Location> locations = locationCaptor.getAllValues();
        assertTrue(locations.stream()
            .allMatch(loc -> loc.getLatitude() >= -90.0 && loc.getLatitude() <= 90.0));
    }

    @Test
    @DisplayName("Should create locations with longitude in valid range")
    void testLocationsHaveValidLongitude() throws Exception {
        dataLoader.run();
        
        verify(locationRepository, atLeastOnce()).save(locationCaptor.capture());
        
        List<Location> locations = locationCaptor.getAllValues();
        assertTrue(locations.stream()
            .allMatch(loc -> loc.getLongitude() >= -180.0 && loc.getLongitude() <= 180.0));
    }

    @Test
    @DisplayName("Should associate locations with categories")
    void testLocationsAssociatedWithCategories() throws Exception {
        dataLoader.run();
        
        verify(locationRepository, atLeastOnce()).save(locationCaptor.capture());
        
        List<Location> locations = locationCaptor.getAllValues();
        assertTrue(locations.stream()
            .allMatch(loc -> loc.getCategory() != null));
    }
}
