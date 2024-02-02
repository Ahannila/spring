package com.arttuplayground.demolibrary.model.dto;

/**
 * Created by Giulio De Biasio (GBI012)
 * on 16.6.2023
 */

import lombok.Getter;
import lombok.Setter;

/**
 * This will be the object returned (Data Transfer Object)
 */

@Getter
@Setter
public class CarFullDto extends CarDto {
    private OwnerDto owner;
    private GarageDto garage;
}
