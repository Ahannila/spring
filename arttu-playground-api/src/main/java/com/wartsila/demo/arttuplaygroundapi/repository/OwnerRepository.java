package com.wartsila.demo.arttuplaygroundapi.repository;

import com.wartsila.demo.arttuplaygroundapi.model.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<OwnerEntity, Long> {


}
