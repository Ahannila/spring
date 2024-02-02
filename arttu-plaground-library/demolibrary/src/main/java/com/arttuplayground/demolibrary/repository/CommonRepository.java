package com.arttuplayground.demolibrary.repository;

import com.arttuplayground.demolibrary.model.entity.CommonEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Giulio De Biasio (GBI012)
 * on 30.5.2023
 */

@Repository
@ConditionalOnProperty(name = "common.services.enable.sample-endpoint", havingValue = "true", matchIfMissing = true)
public interface CommonRepository extends JpaRepository<CommonEntity, Long> {
}
