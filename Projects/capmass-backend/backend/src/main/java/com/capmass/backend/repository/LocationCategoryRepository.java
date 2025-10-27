package com.capmass.backend.repository;

import com.capmass.backend.entity.LocationCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationCategoryRepository extends JpaRepository<LocationCategory, Long> {
}
