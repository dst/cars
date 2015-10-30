package com.stefanski.cars.store;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.persistence.*;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import static javax.persistence.CascadeType.ALL;


/**
 * @author Dariusz Stefanski
 */
@Data
@Entity
class Car {

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

    @OneToMany(mappedBy = "car", cascade = ALL)
    private List<Attribute> attributes = new LinkedList<>();

    public Car() {
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

