package com.arttuplayground.demolibrary.config;

import com.arttuplayground.demolibrary.model.dto.CommonDto;
import com.arttuplayground.demolibrary.model.entity.CommonEntity;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Giulio De Biasio (GBI012)
 * on 29.3.2023
 */

@Configuration
public class DozerConfigCommon {
    @Bean(name = "commonServicesDozerConfig")
    public Mapper beanMapper() {
        return DozerBeanMapperBuilder.create().withMappingBuilders(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(
                    type(CommonEntity.class),
                    type(CommonDto.class)
                );
            }
        }).build();
    }
}
