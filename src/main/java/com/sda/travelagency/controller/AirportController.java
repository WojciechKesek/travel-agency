package com.sda.travelagency.controller;

import com.sda.travelagency.dtos.AirportDto;
import com.sda.travelagency.service.AirportService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airports")
public class AirportController {
    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public List<AirportDto> getAllAirports() {
        return airportService.getAllAirports();
    }

    @GetMapping ("/filterByCity")
    List<AirportDto> getAirportsByCityName(@RequestParam String cityName) throws RuntimeException {
        return airportService.getAirportsByCity(cityName);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{airportName}")
    ResponseEntity<AirportDto> deleteAirport(@PathVariable String airportName) throws RuntimeException {
        AirportDto airportDto = airportService.deleteAirport(airportName);
        return new ResponseEntity<>(airportDto, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{airportName}")
    ResponseEntity<AirportDto> updateHotel(@PathVariable String airportName,@Valid @RequestBody AirportDto airportDto) throws RuntimeException{
        airportService.updateAirport(airportName, airportDto);
        return new ResponseEntity<>(airportDto, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/addAirport")
    ResponseEntity<AirportDto> addAirport(@Valid @RequestBody AirportDto airportDto) {
        airportService.addAirport(airportDto);
        return new ResponseEntity<>(airportDto, HttpStatus.OK);
    }
}
