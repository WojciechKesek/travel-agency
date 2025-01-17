package com.sda.travelagency.repository;

import com.sda.travelagency.entities.Airport;
import com.sda.travelagency.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AirportRepository extends JpaRepository<Airport, Integer> {
    List<Airport> findByCity(City city);
    Optional<Airport> findByName(String name);
}
