package com.wartsila.demo.arttuplaygroundapi.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GarageFullDto extends GarageDto {
    private CarDto car;
}
