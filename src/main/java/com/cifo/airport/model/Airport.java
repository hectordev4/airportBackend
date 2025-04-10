package com.cifo.airport.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.IdGeneratorType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "airports")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "airport_seq")
    @SequenceGenerator(
            name = "airport_seq",
            sequenceName = "airport_sequence",
            initialValue = 1,
            allocationSize = 1
    )
    private Long id;
    private String name;
    private String city;
    private String country;
    private String code;
    private String phoneNumber;
    private String email;

    @OneToMany(mappedBy = "departureAirport")
    @JsonIgnore
    private List<Flight> flightsDeparture;

    @OneToMany(mappedBy = "arrivalAirport")
    @JsonIgnore
    private List<Flight> flightsArrival;

    @Override
    public String toString() {
        return "Airport{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", code='" + code + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}