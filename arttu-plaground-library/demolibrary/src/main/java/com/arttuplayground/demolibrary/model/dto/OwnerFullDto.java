package com.arttuplayground.demolibrary.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OwnerFullDto extends OwnerDto {
    private List<CarDto> cars;
}
