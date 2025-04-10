package com.cifo.airport.dto;

import lombok.Data;

@Data
public class FlightRequestDTO {
    private String flightNumber;
    private String departureTime;  // Format: "yyyy-MM-dd HH:mm:ss"
    private String arrivalTime;    // Format: "yyyy-MM-dd HH:mm:ss"
    private String status;         // "ON_TIME", "DELAYED", etc.
    private Long departureAirportId;
    private Long arrivalAirportId;
    private Long planeId;
}
