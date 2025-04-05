package com.cifo.airport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor

public class PlaneDTO {

    private Long id;
    private String model;
    private String manufacturer;
    private String registrationNumber;
    private Integer capacity;
    private Integer yearOfManufacture;
    private List<String> flightNumber; // Added flight number field

}
