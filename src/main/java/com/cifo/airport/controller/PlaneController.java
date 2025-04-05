package com.cifo.airport.controller;

import com.cifo.airport.dto.PlaneDTO;
import com.cifo.airport.model.Plane;
import com.cifo.airport.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/planes")
public class PlaneController {

    @Autowired
    private PlaneService planeService;

    // Get all planes, returning PlaneDTO objects
    @GetMapping
    public List<PlaneDTO> findAll() {
        List<Plane> planes = planeService.findAll();

        // Map the Plane objects to PlaneDTO
        return planes.stream().map(plane -> {
            String flightNumber = null;
            if (plane.getFlights() != null && !plane.getFlights().isEmpty()) {
                flightNumber = plane.getFlights().get(0).getFlightNumber(); // Assuming the first flight has the flight number
            }

            return new PlaneDTO(
                    plane.getId(),
                    plane.getModel(),
                    plane.getManufacturer(),
                    plane.getRegistrationNumber(),
                    plane.getCapacity(),
                    plane.getYearOfManufacture(),
                    Collections.singletonList(flightNumber) // Pass the flight number
            );
        }).collect(Collectors.toList());
    }

    // Get a specific plane by ID, returning PlaneDTO
    @GetMapping("/{id}")
    public ResponseEntity<Plane> findById(@PathVariable Long id) {
        Optional<Plane> plane = planeService.findById(id);
        if (plane.isPresent()) {
            return ResponseEntity.ok(plane.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new plane, but we'll return the PlaneDTO in response
    @PostMapping
    public ResponseEntity<Plane> create(@RequestBody Plane plane) {
        Plane planeDTO = planeService.save(plane); // Saving the plane and returning the DTO
        return ResponseEntity.ok(planeDTO);
    }

    // Update a plane by ID, returning the updated PlaneDTO
    @PutMapping("/{id}")
    public ResponseEntity<Plane> update(@PathVariable Long id, @RequestBody Plane plane) {
        Optional<Plane> existingPlane = planeService.findById(id);
        if (existingPlane.isPresent()) {
            plane.setId(id); // Update the plane's ID
            Plane updatedPlane = planeService.save(plane); // Save and return updated DTO
            return ResponseEntity.ok(updatedPlane);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a plane by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Plane> plane = planeService.findById(id);
        if (plane.isPresent()) {
            planeService.delete(plane.get()); // Delete the plane
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
