package com.wartsila.demo.arttuplaygroundapi.model.entity;

/**
 * Created by Giulio De Biasio (GBI012)
 * on 16.6.2023
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * This will go the Entity class mapping the database table
 */
@Getter
@Setter
@Entity
@Table(name = "t_car")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank(message = "Brand is mandatory")
    private String brand;
    @NotNull
    private int year;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner")
    @JsonBackReference
    private OwnerEntity owner;
    @OneToOne(mappedBy = "car", cascade = {CascadeType.ALL})
    private GarageEntity garage;
}
