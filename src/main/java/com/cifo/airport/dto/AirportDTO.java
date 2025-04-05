package com.cifo.airport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AirportDTO {
    private Long id;
    private String name;
    private String city;
    private String country;
    private String code;
    private String phoneNumber;
    private String email;
    private List<String> flightDepartureNumbers; // Added list of flight numbers for departures
    private List<String> flightArrivalNumbers; // Added list of flight numbers for arrivals

}

