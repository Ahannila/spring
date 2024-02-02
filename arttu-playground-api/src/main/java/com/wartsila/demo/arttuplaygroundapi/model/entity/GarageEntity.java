package com.wartsila.demo.arttuplaygroundapi.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Garage has to be in relation 1 to 1 with a car.
 */
@Getter
@Setter
@Entity
@Table(name = "t_garage")
public class GarageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank(message = "name is mandatory")
    private String name;
    @NotBlank(message = "Location is mandatory")
    private String location;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "car")
    private CarEntity car;
}
