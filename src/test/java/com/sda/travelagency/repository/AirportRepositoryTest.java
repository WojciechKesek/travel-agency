package com.sda.travelagency.repository;

import com.sda.travelagency.entities.Airport;
import com.sda.travelagency.entities.City;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class AirportRepositoryTest {
    @Autowired
    AirportRepository airportRepository;
    @Test
    public void shouldFindByName() {
        Airport airport = new Airport("testRepositoryAirport", "testAddress", airportRepository.findAll().get(0).getCity());
        airportRepository.save(airport);

        Optional<Airport> result = airportRepository.findByName("testRepositoryAirport");

        Assertions.assertThat(result).isNotEmpty();
        airportRepository.delete(airport);
    }

    @Test
    public void shouldNotFindByName() {

        Optional<Airport> result = airportRepository.findByName("testRepositoryHotel");

        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void shouldFindByCityName() {
        City testCity = airportRepository.findAll().get(0).getCity();

        Airport airport = new Airport("testRepositoryAirport", "testAddress", testCity);
        airportRepository.save(airport);

        List<Airport> result = airportRepository.findByCity(testCity);

        Assertions.assertThat(result.contains(airport));
        airportRepository.delete(airport);
    }
    @Test
    public void shouldNotFindByCityName() {
        City testCity = airportRepository.findAll().get(0).getCity();

        Airport airport = new Airport("testRepositoryAirport", "testAddress", testCity);

        List<Airport> result = airportRepository.findByCity(testCity);

        Assertions.assertThat(!result.contains(airport));
    }

}