package com.customeer.customeer.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a customer in the system.
 */
@Entity
@Getter  // Lombok annotation to generate getters
@Setter  // Lombok annotation to generate setters
public class Customer {

    /** Unique identifier for the customer */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** Full name of the customer */
    private String name;

    /** Email address of the customer */
    private String email;

    /** Additional notes or comments related to the customer */
    private String notes;

    public Customer(String name, String email, String notes) {
        this.name = name;
        this.email = email;
        this.notes = notes;
    }

    public Customer() {
        return;
    }
}
