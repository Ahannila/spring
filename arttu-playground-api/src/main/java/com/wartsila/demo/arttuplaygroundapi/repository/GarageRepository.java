package com.wartsila.demo.arttuplaygroundapi.repository;

import com.wartsila.demo.arttuplaygroundapi.model.entity.GarageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarageRepository extends JpaRepository<GarageEntity, Long> {
}
