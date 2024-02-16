package com.youcode.interplanetary.HealthCareService.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="name")
    private String name;

}
