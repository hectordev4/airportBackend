package com.cifo.airport.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_seq")
    @SequenceGenerator(
            name = "flight_seq",
            sequenceName = "flight_sequence",
            initialValue = 1,
            allocationSize = 1
    )
    private Long id;
    private String flightNumber;
    private String departureTime;
    private String arrivalTime;

    @Enumerated(EnumType.STRING) // Stores ENUM as String in DB
    private FlightStatus status;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "departure_airport_id", referencedColumnName = "id")
    private Airport departureAirport;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "arrival_airport_id", referencedColumnName = "id")
    private Airport arrivalAirport;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "plane_id", referencedColumnName = "id")
    private Plane plane;



    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", flightNumber='" + flightNumber + '\'' +
                ", status='" + status + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                '}';
    }
}