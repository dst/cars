package com.stefanski.cars.store;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;


/**
 * @author Dariusz Stefanski
 */
@Data
@Entity
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String make;

    @NotEmpty
    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int engineDisplacement;

    @OneToMany(fetch = EAGER, cascade = ALL)
    private Set<Attribute> attributes = new HashSet<>();

    public Car(String make, String model, Integer year, Integer engineDisplacement,
               Set<Attribute> attributes) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.engineDisplacement = engineDisplacement;
        this.attributes = attributes;
    }

    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    public Map<String, String> getAttributesMap() {
        Map<String, String> map = new HashMap<>();
        attributes.forEach(attr ->
            map.put(attr.getName(), attr.getValue())
        );
        return map;
    }
}

