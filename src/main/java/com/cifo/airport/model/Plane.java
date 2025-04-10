package com.cifo.airport.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "planes")
public class Plane {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plane_seq")
    @SequenceGenerator(name = "plane_seq", sequenceName = "plane_sequence", allocationSize = 1)
    private Long id;
    private String model;
    private String manufacturer;
    private String registrationNumber;
    private Integer capacity;
    private Integer yearOfManufacture;

    @OneToMany(mappedBy = "plane")
    @JsonIgnore
    private List<Flight> flights;

    @Override
    public String toString() {
        return "Plane{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", capacity=" + capacity +
                ", yearOfManufacture=" + yearOfManufacture +
                '}';
    }
}