package com.cifo.airport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlightResponseDTO {
    private Long id;
    private String flightNumber;
    private String departureTime;
    private String arrivalTime;
    private String status;
    private String departureAirportCode;  // ← Human-friendly codes
    private String arrivalAirportCode;    // ← Human-friendly codes
    private String planeRegistrationNumber;
}
