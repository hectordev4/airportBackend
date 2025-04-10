package com.cifo.airport.service;

import com.cifo.airport.dto.FlightRequestDTO;
import com.cifo.airport.model.Flight;
import com.cifo.airport.model.FlightStatus;
import com.cifo.airport.repository.AirportRepository;
import com.cifo.airport.repository.FlightRepository;
import com.cifo.airport.repository.PlaneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private AirportRepository airportRepository;
    @Autowired
    private PlaneRepository planeRepository;

    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    public Optional<Flight> findById(Long id) {
        return flightRepository.findById(id);
    }

    public Flight save(Flight flight) {
        return flightRepository.save(flight);
    }

    @Transactional
    public List<Flight> saveAllFlights(List<FlightRequestDTO> flightDTOs) {
        return flightDTOs.stream().map(dto -> {
            Flight flight = new Flight();
            flight.setFlightNumber(dto.getFlightNumber());
            flight.setDepartureTime(dto.getDepartureTime());
            flight.setArrivalTime(dto.getArrivalTime());
            flight.setStatus(FlightStatus.valueOf(dto.getStatus()));

            // Set relationships
            flight.setDepartureAirport(
                    airportRepository.findById(dto.getDepartureAirportId())
                            .orElseThrow(() -> new RuntimeException("Departure airport not found"))
            );
            flight.setArrivalAirport(
                    airportRepository.findById(dto.getArrivalAirportId())
                            .orElseThrow(() -> new RuntimeException("Arrival airport not found"))
            );
            flight.setPlane(
                    planeRepository.findById(dto.getPlaneId())
                            .orElseThrow(() -> new RuntimeException("Plane not found"))
            );

            return flightRepository.save(flight);
        }).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        flightRepository.deleteById(id);
    }

    public void delete(Flight flight) {
        flightRepository.delete(flight);
    }

    public void deleteAll() {
        flightRepository.deleteAll();
    }

    public boolean existsById(Long id) {
        return flightRepository.existsById(id);
    }

    public long count() {
        return flightRepository.count();
    }

    public List<Flight> findByDepartureAirport(Long departureAirportId) {
        return flightRepository.findByDepartureAirport_Id(departureAirportId);
    }

    public List<Flight> findByArrivalAirport(Long arrivalAirportId) {
        return flightRepository.findByArrivalAirport_Id(arrivalAirportId);
    }
}