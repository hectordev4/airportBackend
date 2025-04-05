package com.cifo.airport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlightDTO {
    private Long id;
    private String flightNumber;
    private String departureTime;
    private String arrivalTime;
    private String status;
    private String departureAirportCode;
    private String arrivalAirportCode;
    private String planeRegistrationNumber;

}
