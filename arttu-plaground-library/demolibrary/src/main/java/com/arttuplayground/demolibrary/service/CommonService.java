package com.arttuplayground.demolibrary.service;

import com.arttuplayground.demolibrary.model.dto.CommonDto;
import com.arttuplayground.demolibrary.repository.CommonRepository;
import com.github.dozermapper.core.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Giulio De Biasio (GBI012)
 * on 30.8.2023
 */
@Service
@Slf4j
@ConditionalOnProperty(name = "common.services.enable.sample-endpoint", havingValue = "true", matchIfMissing = true)
public class CommonService {
    @Autowired
    Mapper mapper;
    private final CommonRepository commonRepository;

    // Here is where the magic happens: the library user repository is actually used!
    public CommonService(CommonRepository commonRepository) {
        this.commonRepository = commonRepository;
    }

    public List<CommonDto> getAllEntities() {
        var res = commonRepository.findAll().stream()
                .map(current -> mapper.map(current, CommonDto.class))
                .toList();
        return res;
    }
}
