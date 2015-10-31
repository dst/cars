package com.stefanski.cars.store;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dariusz Stefanski
 */
@Data
@Entity
@NoArgsConstructor
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String value;

    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
