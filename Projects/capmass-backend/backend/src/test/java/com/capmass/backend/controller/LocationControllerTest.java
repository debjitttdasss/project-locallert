package com.capmass.backend.controller;

import com.capmass.backend.entity.Location;
import com.capmass.backend.entity.LocationCategory;
import com.capmass.backend.repository.LocationCategoryRepository;
import com.capmass.backend.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("LocationController Unit Tests")
class LocationControllerTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private LocationCategoryRepository categoryRepository;

    @InjectMocks
    private LocationController locationController;

    private LocationCategory academicCategory;
    private LocationCategory libraryCategory;
    private Location engineeringBuilding;
    private Location scienceHall;
    private Location centralLibrary;

    @BeforeEach
    void setUp() {
        academicCategory = new LocationCategory(1L, "Academic Buildings", 
            "Buildings for classes and lectures");
        libraryCategory = new LocationCategory(2L, "Library", 
            "Study and research facilities");
        
        engineeringBuilding = new Location(1L, "Engineering Building", 
            "Main engineering building", academicCategory, 40.7128, -74.0060);
        scienceHall = new Location(2L, "Science Hall", 
            "Chemistry and Physics labs", academicCategory, 40.7130, -74.0058);
        centralLibrary = new Location(3L, "Central Library", 
            "Main campus library", libraryCategory, 40.7125, -74.0061);
    }

    @Test
    @DisplayName("Should get all locations successfully")
    void testGetAllLocations() {
        List<Location> locations = Arrays.asList(engineeringBuilding, scienceHall, centralLibrary);
        when(locationRepository.findAll()).thenReturn(locations);
        
        ResponseEntity<List<Location>> response = locationController.getAllLocations();
        
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
        verify(locationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no locations exist")
    void testGetAllLocationsEmpty() {
        when(locationRepository.findAll()).thenReturn(Collections.emptyList());
        
        ResponseEntity<List<Location>> response = locationController.getAllLocations();
        
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(locationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get locations by category id")
    void testGetLocationsByCategory() {
        List<Location> academicLocations = Arrays.asList(engineeringBuilding, scienceHall);
        when(locationRepository.findByCategoryId(1L)).thenReturn(academicLocations);
        
        ResponseEntity<List<Location>> response = locationController.getLocationsByCategory(1L);
        
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().stream()
            .allMatch(loc -> loc.getCategory().getId().equals(1L)));
        verify(locationRepository, times(1)).findByCategoryId(1L);
    }

    @Test
    @DisplayName("Should return empty list when category has no locations")
    void testGetLocationsByCategoryEmpty() {
        when(locationRepository.findByCategoryId(999L)).thenReturn(Collections.emptyList());
        
        ResponseEntity<List<Location>> response = locationController.getLocationsByCategory(999L);
        
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(locationRepository, times(1)).findByCategoryId(999L);
    }

    @Test
    @DisplayName("Should get all categories successfully")
    void testGetAllCategories() {
        List<LocationCategory> categories = Arrays.asList(academicCategory, libraryCategory);
        when(categoryRepository.findAll()).thenReturn(categories);
        
        ResponseEntity<List<LocationCategory>> response = locationController.getAllCategories();
        
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no categories exist")
    void testGetAllCategoriesEmpty() {
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());
        
        ResponseEntity<List<LocationCategory>> response = locationController.getAllCategories();
        
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should call repository only once for getAllLocations")
    void testGetAllLocationsRepositoryCallCount() {
        when(locationRepository.findAll()).thenReturn(Collections.emptyList());
        
        locationController.getAllLocations();
        
        verify(locationRepository, times(1)).findAll();
        verifyNoMoreInteractions(locationRepository);
    }

    @Test
    @DisplayName("Should call repository only once for getAllCategories")
    void testGetAllCategoriesRepositoryCallCount() {
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());
        
        locationController.getAllCategories();
        
        verify(categoryRepository, times(1)).findAll();
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    @DisplayName("Should call repository only once for getLocationsByCategory")
    void testGetLocationsByCategoryRepositoryCallCount() {
        when(locationRepository.findByCategoryId(1L)).thenReturn(Collections.emptyList());
        
        locationController.getLocationsByCategory(1L);
        
        verify(locationRepository, times(1)).findByCategoryId(1L);
        verifyNoMoreInteractions(locationRepository);
    }

    @Test
    @DisplayName("Should handle multiple locations with same category")
    void testMultipleLocationsWithSameCategory() {
        List<Location> locations = Arrays.asList(engineeringBuilding, scienceHall);
        when(locationRepository.findByCategoryId(1L)).thenReturn(locations);
        
        ResponseEntity<List<Location>> response = locationController.getLocationsByCategory(1L);
        
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().stream()
            .allMatch(loc -> loc.getCategory().getName().equals("Academic Buildings")));
    }

    @Test
    @DisplayName("Should return correct location details")
    void testLocationDetails() {
        List<Location> locations = Arrays.asList(engineeringBuilding);
        when(locationRepository.findAll()).thenReturn(locations);
        
        ResponseEntity<List<Location>> response = locationController.getAllLocations();
        Location location = response.getBody().get(0);
        
        assertEquals("Engineering Building", location.getName());
        assertEquals("Main engineering building", location.getDescription());
        assertEquals(40.7128, location.getLatitude());
        assertEquals(-74.0060, location.getLongitude());
        assertEquals("Academic Buildings", location.getCategory().getName());
    }

    @Test
    @DisplayName("Should return correct category details")
    void testCategoryDetails() {
        List<LocationCategory> categories = Arrays.asList(academicCategory);
        when(categoryRepository.findAll()).thenReturn(categories);
        
        ResponseEntity<List<LocationCategory>> response = locationController.getAllCategories();
        LocationCategory category = response.getBody().get(0);
        
        assertEquals("Academic Buildings", category.getName());
        assertEquals("Buildings for classes and lectures", category.getDescription());
    }

    @Test
    @DisplayName("Should handle different category IDs")
    void testDifferentCategoryIds() {
        when(locationRepository.findByCategoryId(1L))
            .thenReturn(Arrays.asList(engineeringBuilding, scienceHall));
        when(locationRepository.findByCategoryId(2L))
            .thenReturn(Arrays.asList(centralLibrary));
        
        ResponseEntity<List<Location>> response1 = locationController.getLocationsByCategory(1L);
        ResponseEntity<List<Location>> response2 = locationController.getLocationsByCategory(2L);
        
        assertEquals(2, response1.getBody().size());
        assertEquals(1, response2.getBody().size());
        verify(locationRepository, times(1)).findByCategoryId(1L);
        verify(locationRepository, times(1)).findByCategoryId(2L);
    }

    @Test
    @DisplayName("Should maintain location order from repository")
    void testLocationOrderMaintained() {
        List<Location> orderedLocations = Arrays.asList(
            engineeringBuilding, scienceHall, centralLibrary);
        when(locationRepository.findAll()).thenReturn(orderedLocations);
        
        ResponseEntity<List<Location>> response = locationController.getAllLocations();
        
        assertEquals("Engineering Building", response.getBody().get(0).getName());
        assertEquals("Science Hall", response.getBody().get(1).getName());
        assertEquals("Central Library", response.getBody().get(2).getName());
    }

    @Test
    @DisplayName("Should maintain category order from repository")
    void testCategoryOrderMaintained() {
        List<LocationCategory> orderedCategories = Arrays.asList(
            academicCategory, libraryCategory);
        when(categoryRepository.findAll()).thenReturn(orderedCategories);
        
        ResponseEntity<List<LocationCategory>> response = locationController.getAllCategories();
        
        assertEquals("Academic Buildings", response.getBody().get(0).getName());
        assertEquals("Library", response.getBody().get(1).getName());
    }

    @Test
    @DisplayName("Should return OK status for all endpoints")
    void testAllEndpointsReturnOkStatus() {
        when(locationRepository.findAll()).thenReturn(Collections.emptyList());
        when(locationRepository.findByCategoryId(anyLong())).thenReturn(Collections.emptyList());
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());
        
        ResponseEntity<List<Location>> locationsResponse = locationController.getAllLocations();
        ResponseEntity<List<Location>> locationsByCategoryResponse = 
            locationController.getLocationsByCategory(1L);
        ResponseEntity<List<LocationCategory>> categoriesResponse = 
            locationController.getAllCategories();
        
        assertEquals(200, locationsResponse.getStatusCodeValue());
        assertEquals(200, locationsByCategoryResponse.getStatusCodeValue());
        assertEquals(200, categoriesResponse.getStatusCodeValue());
    }

    @Test
    @DisplayName("Should handle single location result")
    void testSingleLocationResult() {
        List<Location> singleLocation = Arrays.asList(engineeringBuilding);
        when(locationRepository.findByCategoryId(1L)).thenReturn(singleLocation);
        
        ResponseEntity<List<Location>> response = locationController.getLocationsByCategory(1L);
        
        assertEquals(1, response.getBody().size());
        assertEquals("Engineering Building", response.getBody().get(0).getName());
    }

    @Test
    @DisplayName("Should handle single category result")
    void testSingleCategoryResult() {
        List<LocationCategory> singleCategory = Arrays.asList(academicCategory);
        when(categoryRepository.findAll()).thenReturn(singleCategory);
        
        ResponseEntity<List<LocationCategory>> response = locationController.getAllCategories();
        
        assertEquals(1, response.getBody().size());
        assertEquals("Academic Buildings", response.getBody().get(0).getName());
    }

    @Test
    @DisplayName("Should not modify repository data")
    void testNoRepositoryDataModification() {
        List<Location> locations = Arrays.asList(engineeringBuilding);
        when(locationRepository.findAll()).thenReturn(locations);
        
        locationController.getAllLocations();
        
        verify(locationRepository, never()).save(any());
        verify(locationRepository, never()).delete(any());
        verify(locationRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Should handle multiple consecutive calls")
    void testMultipleConsecutiveCalls() {
        when(locationRepository.findAll()).thenReturn(Collections.emptyList());
        
        locationController.getAllLocations();
        locationController.getAllLocations();
        locationController.getAllLocations();
        
        verify(locationRepository, times(3)).findAll();
    }
}
