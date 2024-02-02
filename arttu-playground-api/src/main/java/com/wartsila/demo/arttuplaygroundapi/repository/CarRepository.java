package com.wartsila.demo.arttuplaygroundapi.repository;

/**
 * Created by Giulio De Biasio (GBI012)
 * on 16.6.2023
 */

/**
 * This is doing nothing, just a placeholder for when you'll get connected to the DB and you'll need a repository.
 */

import com.wartsila.demo.arttuplaygroundapi.model.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {


}
