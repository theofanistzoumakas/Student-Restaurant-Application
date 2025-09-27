package com.example.student_restaurant_appplication_development.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Allergen {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String allergenName;


    public Allergen() {}

    public Allergen(String allergenName) {
        this.allergenName = allergenName;
    }

    public String getAllergenName() {
        return allergenName;
    }

    public Long getId() {
        return Id;
    }

}
