package com.stefanski.cars.store;

import javax.persistence.*;

import lombok.Data;

/**
 * @author Dariusz Stefanski
 */
@Entity
@Data
class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String value;

    @ManyToOne
    private Car car;

    public Attribute() {
    }

    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
