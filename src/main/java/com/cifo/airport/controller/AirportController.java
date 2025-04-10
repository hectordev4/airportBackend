package com.cifo.airport.controller;

import com.cifo.airport.dto.AirportDTO;
import com.cifo.airport.model.Airport;
import com.cifo.airport.model.Flight;
import com.cifo.airport.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    @Autowired
    private AirportService airportService;

    @GetMapping
    public List<AirportDTO> findAll() {
        List<Airport> airports = airportService.findAll();

        return airports.stream().map(airport -> {
            List<String> departureFlightNumbers = airport.getFlightsDeparture().stream()
                    .map(Flight::getFlightNumber)
                    .collect(Collectors.toList());
            List<String> arrivalFlightNumbers = airport.getFlightsArrival().stream()
                    .map(Flight::getFlightNumber)
                    .collect(Collectors.toList());

            return new AirportDTO(
                    airport.getId(),
                    airport.getName(),
                    airport.getCity(),
                    airport.getCountry(),
                    airport.getCode(),
                    airport.getPhoneNumber(),
                    airport.getEmail(),
                    departureFlightNumbers,
                    arrivalFlightNumbers
            );
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airport> findById(@PathVariable Long id) {
        Optional<Airport> airport = airportService.findById(id);
        return airport.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Airport> create(@RequestBody Airport airport) {
        return ResponseEntity.ok(airportService.save(airport));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Airport>> createAirports(@RequestBody List<Airport> airports) {
        return ResponseEntity.ok(airportService.saveAllAirports(airports));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Airport> update(@PathVariable Long id, @RequestBody Airport airport) {
        return airportService.findById(id)
                .map(existing -> {
                    airport.setId(id);
                    return ResponseEntity.ok(airportService.save(airport));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return airportService.findById(id)
                .map(airport -> {
                    airportService.delete(airport);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}