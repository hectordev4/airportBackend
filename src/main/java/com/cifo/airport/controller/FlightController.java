package com.cifo.airport.controller;

import com.cifo.airport.model.Flight;
import com.cifo.airport.dto.FlightRequestDTO;
import com.cifo.airport.dto.FlightResponseDTO;
import com.cifo.airport.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public List<FlightResponseDTO> findAll() {
        return flightService.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponseDTO> findById(@PathVariable Long id) {
        Optional<Flight> flight = flightService.findById(id);
        return flight.map(f -> ResponseEntity.ok(convertToResponseDTO(f)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FlightResponseDTO> create(@RequestBody Flight flight) {
        Flight savedFlight = flightService.save(flight);
        return ResponseEntity.ok(convertToResponseDTO(savedFlight));
    }

    @PostMapping("/batch")
    public ResponseEntity<?> createFlightsBatch(@RequestBody List<FlightRequestDTO> requestDTOs) {
        try {
            List<Flight> savedFlights = flightService.saveAllFlights(requestDTOs);
            List<FlightResponseDTO> response = savedFlights.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage(),
                            "timestamp", LocalDateTime.now())
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponseDTO> update(@PathVariable Long id, @RequestBody Flight flight) {
        if (!flightService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        flight.setId(id);
        Flight updatedFlight = flightService.save(flight);
        return ResponseEntity.ok(convertToResponseDTO(updatedFlight));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!flightService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        flightService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/departure/{airportId}")
    public List<FlightResponseDTO> findByDepartureAirport(@PathVariable Long airportId) {
        return flightService.findByDepartureAirport(airportId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/arrival/{airportId}")
    public List<FlightResponseDTO> findByArrivalAirport(@PathVariable Long airportId) {
        return flightService.findByArrivalAirport(airportId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private FlightResponseDTO convertToResponseDTO(Flight flight) {
        return new FlightResponseDTO(
                flight.getId(),
                flight.getFlightNumber(),
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getStatus().toString(),
                flight.getDepartureAirport().getCode(),
                flight.getArrivalAirport().getCode(),
                flight.getPlane().getRegistrationNumber()
        );
    }
}