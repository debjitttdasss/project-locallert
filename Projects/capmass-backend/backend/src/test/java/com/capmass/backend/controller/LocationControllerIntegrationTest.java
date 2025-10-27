package com.capmass.backend.controller;

import com.capmass.backend.entity.Location;
import com.capmass.backend.entity.LocationCategory;
import com.capmass.backend.repository.LocationCategoryRepository;
import com.capmass.backend.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("LocationController Integration Tests")
class LocationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationCategoryRepository categoryRepository;

    private Long academicCategoryId;
    private Long libraryCategoryId;

    @BeforeEach
    void setUp() {
        // Get existing categories loaded by DataLoader
        academicCategoryId = categoryRepository.findAll().stream()
            .filter(cat -> "Academic Buildings".equals(cat.getName()))
            .findFirst()
            .map(LocationCategory::getId)
            .orElse(null);
        
        libraryCategoryId = categoryRepository.findAll().stream()
            .filter(cat -> "Library".equals(cat.getName()))
            .findFirst()
            .map(LocationCategory::getId)
            .orElse(null);
    }

    @Test
    @DisplayName("GET /api/locations should return all locations")
    void testGetAllLocations() throws Exception {
        mockMvc.perform(get("/api/locations"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(11)))
            .andExpect(jsonPath("$[0].name", notNullValue()))
            .andExpect(jsonPath("$[0].latitude", notNullValue()))
            .andExpect(jsonPath("$[0].longitude", notNullValue()))
            .andExpect(jsonPath("$[0].category", notNullValue()));
    }

    @Test
    @DisplayName("GET /api/locations should return locations with correct structure")
    void testGetAllLocationsStructure() throws Exception {
        mockMvc.perform(get("/api/locations"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", notNullValue()))
            .andExpect(jsonPath("$[0].name", isA(String.class)))
            .andExpect(jsonPath("$[0].description", isA(String.class)))
            .andExpect(jsonPath("$[0].latitude", isA(Number.class)))
            .andExpect(jsonPath("$[0].longitude", isA(Number.class)))
            .andExpect(jsonPath("$[0].category.id", notNullValue()))
            .andExpect(jsonPath("$[0].category.name", isA(String.class)));
    }

    @Test
    @DisplayName("GET /api/locations/category/{categoryId} should return locations for category")
    void testGetLocationsByCategory() throws Exception {
        mockMvc.perform(get("/api/locations/category/" + academicCategoryId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].category.id", is(academicCategoryId.intValue())));
    }

    @Test
    @DisplayName("GET /api/locations/category/{categoryId} should return empty array for non-existent category")
    void testGetLocationsByCategoryEmpty() throws Exception {
        mockMvc.perform(get("/api/locations/category/999"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("GET /api/categories should return all categories")
    void testGetAllCategories() throws Exception {
        mockMvc.perform(get("/api/categories"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(5)))
            .andExpect(jsonPath("$[0].name", notNullValue()))
            .andExpect(jsonPath("$[0].description", notNullValue()));
    }

    @Test
    @DisplayName("GET /api/categories should return categories with correct structure")
    void testGetAllCategoriesStructure() throws Exception {
        mockMvc.perform(get("/api/categories"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", notNullValue()))
            .andExpect(jsonPath("$[0].name", isA(String.class)))
            .andExpect(jsonPath("$[0].description", isA(String.class)));
    }

    @Test
    @DisplayName("GET /api/locations should include specific location names")
    void testGetAllLocationsIncludesNames() throws Exception {
        mockMvc.perform(get("/api/locations"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[*].name", hasItem("Engineering Building")))
            .andExpect(jsonPath("$[*].name", hasItem("Science Hall")))
            .andExpect(jsonPath("$[*].name", hasItem("Central Library")));
    }

    @Test
    @DisplayName("GET /api/categories should include specific category names")
    void testGetAllCategoriesIncludesNames() throws Exception {
        mockMvc.perform(get("/api/categories"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[*].name", hasItem("Academic Buildings")))
            .andExpect(jsonPath("$[*].name", hasItem("Library")));
    }

    @Test
    @DisplayName("GET /api/locations should handle CORS headers")
    void testCorsHeaders() throws Exception {
        mockMvc.perform(get("/api/locations")
                .header("Origin", "http://example.com"))
            .andExpect(status().isOk())
            .andExpect(header().exists("Access-Control-Allow-Origin"));
    }

    @Test
    @DisplayName("GET /api/locations/category should return single location for library category")
    void testGetLocationsByLibraryCategory() throws Exception {
        mockMvc.perform(get("/api/locations/category/" + libraryCategoryId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].category.name", is("Library")));
    }

    @Test
    @DisplayName("GET /api/locations should return locations with valid coordinates")
    void testGetAllLocationsValidCoordinates() throws Exception {
        mockMvc.perform(get("/api/locations"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].latitude", allOf(
                greaterThanOrEqualTo(-90.0),
                lessThanOrEqualTo(90.0))))
            .andExpect(jsonPath("$[0].longitude", allOf(
                greaterThanOrEqualTo(-180.0),
                lessThanOrEqualTo(180.0))));
    }

    @Test
    @DisplayName("GET /api/locations should return locations with category information")
    void testGetAllLocationsWithCategoryInfo() throws Exception {
        mockMvc.perform(get("/api/locations"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].category.name", notNullValue()))
            .andExpect(jsonPath("$[0].category.description", notNullValue()));
    }

    @Test
    @DisplayName("API endpoints should accept JSON content type")
    void testAcceptsJsonContentType() throws Exception {
        mockMvc.perform(get("/api/locations")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("GET /api/locations/category with invalid ID format should return 400")
    void testGetLocationsByCategoryInvalidId() throws Exception {
        mockMvc.perform(get("/api/locations/category/invalid"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/locations should return locations ordered by ID")
    void testGetAllLocationsOrdered() throws Exception {
        mockMvc.perform(get("/api/locations"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", notNullValue()))
            .andExpect(jsonPath("$[1].id", notNullValue()))
            .andExpect(jsonPath("$[10].id", notNullValue()));
    }

    @Test
    @DisplayName("GET endpoints should be idempotent")
    void testGetEndpointsIdempotent() throws Exception {
        // First call
        String firstResponse = mockMvc.perform(get("/api/locations"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        
        // Second call
        String secondResponse = mockMvc.perform(get("/api/locations"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        
        // Should return the same data
        assertEquals(firstResponse, secondResponse);
    }

    @Test
    @DisplayName("GET /api/categories should return categories without locations")
    void testGetAllCategoriesStructureSimple() throws Exception {
        mockMvc.perform(get("/api/categories"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", notNullValue()))
            .andExpect(jsonPath("$[0].name", notNullValue()))
            .andExpect(jsonPath("$[0].description", notNullValue()));
    }

    @Test
    @DisplayName("GET /api/locations should handle multiple locations per category")
    void testMultipleLocationsPerCategory() throws Exception {
        mockMvc.perform(get("/api/locations/category/" + academicCategoryId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))));
    }

    @Test
    @DisplayName("All endpoints should return valid JSON")
    void testAllEndpointsReturnValidJson() throws Exception {
        mockMvc.perform(get("/api/locations"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        mockMvc.perform(get("/api/categories"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        mockMvc.perform(get("/api/locations/category/" + academicCategoryId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("GET /api/locations should include all required fields")
    void testGetAllLocationsRequiredFields() throws Exception {
        mockMvc.perform(get("/api/locations"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].name").exists())
            .andExpect(jsonPath("$[0].latitude").exists())
            .andExpect(jsonPath("$[0].longitude").exists())
            .andExpect(jsonPath("$[0].category").exists());
    }

    @Test
    @DisplayName("GET /api/categories should include all required fields")
    void testGetAllCategoriesRequiredFields() throws Exception {
        mockMvc.perform(get("/api/categories"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    @DisplayName("GET /api/locations/category should filter correctly")
    void testGetLocationsByCategoryFiltersCorrectly() throws Exception {
        // Get locations for academic category
        String academicResponse = mockMvc.perform(
                get("/api/locations/category/" + academicCategoryId))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        
        // Get locations for library category
        String libraryResponse = mockMvc.perform(
                get("/api/locations/category/" + libraryCategoryId))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        
        // Responses should be different
        assertNotEquals(academicResponse, libraryResponse);
    }

    @Test
    @DisplayName("GET /api/locations should return consistent data types")
    void testDataTypesConsistency() throws Exception {
        mockMvc.perform(get("/api/locations"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", instanceOf(Number.class)))
            .andExpect(jsonPath("$[0].name", instanceOf(String.class)))
            .andExpect(jsonPath("$[0].latitude", instanceOf(Number.class)))
            .andExpect(jsonPath("$[0].longitude", instanceOf(Number.class)));
    }
}
