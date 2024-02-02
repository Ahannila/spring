package com.arttuplayground.demolibrary.model.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Created by Giulio De Biasio (GBI012)
 * on 30.8.2023
 */

@Getter
@Setter
@Entity
@Table(name = "t_common")
public class CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "generic_value")
    private String genericValue;
}
