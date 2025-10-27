package com.capmass.backend.controller;

import com.capmass.backend.entity.Location;
import com.capmass.backend.entity.LocationCategory;
import com.capmass.backend.repository.LocationCategoryRepository;
import com.capmass.backend.repository.LocationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LocationController {

    private final LocationRepository locationRepository;
    private final LocationCategoryRepository categoryRepository;

    public LocationController(LocationRepository locationRepository, 
                             LocationCategoryRepository categoryRepository) {
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/locations/category/{categoryId}")
    public ResponseEntity<List<Location>> getLocationsByCategory(@PathVariable Long categoryId) {
        List<Location> locations = locationRepository.findByCategoryId(categoryId);
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<LocationCategory>> getAllCategories() {
        List<LocationCategory> categories = categoryRepository.findAll();
        return ResponseEntity.ok(categories);
    }
}
