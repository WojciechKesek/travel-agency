package com.sda.travelagency.service;

import com.sda.travelagency.dtos.AirportDto;
import com.sda.travelagency.entities.Airport;
import com.sda.travelagency.entities.City;
import com.sda.travelagency.exception.AirportNotFoundException;
import com.sda.travelagency.exception.CityNotFoundException;
import com.sda.travelagency.mapper.AirportMapper;
import com.sda.travelagency.repository.AirportRepository;
import com.sda.travelagency.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService {
    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;
    private final AirportMapper airportMapper;

    public AirportService(AirportRepository airportRepository, CityRepository cityRepository, AirportMapper airportMapper) {
        this.airportRepository = airportRepository;
        this.cityRepository = cityRepository;
        this.airportMapper = airportMapper;
    }
    /**
     * This method finds airports in the database.
     * Then, it uses the AirportMapper class to transform instances of the Airport objects into an AirportDto, which is added to List and passed on.
     * @return List of AirportDto
     **/
    public List<AirportDto> getAllAirports(){
        return airportRepository.findAll().stream().map(airportMapper::AirportToAirportDto).toList();
    }
    /**
     * This method gets a cityName as a param.
     * Then, it uses the CityRepository class to find City object in database or else throws CityNotFoundException.
     * Next, it finds Airpor objects associated with given cityName and uses the AirportMapper class to transform instances of the Airport objects into AirportDto,
     * which is added to List and passed on.
     * @param cityName
     * @return List of AirportDto
     * @throws CityNotFoundException "No such hotel exists"
     **/
    public List<AirportDto> getAirportsByCity(String cityName){
        City city = cityRepository.findByName(cityName)
                .orElseThrow(() -> new CityNotFoundException("No such city exists"));
        return airportRepository.findByCity(city).stream().map(airportMapper::AirportToAirportDto).toList();
    }
    /**
     * This method gets a AirportDto as a param.
     * which is saved in database by HotelRepository.
     * @param airportDto
     **/
    public void addAirport(AirportDto airportDto){
        airportRepository.save(airportMapper.AirportDtoToAirport(airportDto));
    }
    /**
     * This method gets a hotelName and hotelDto as a param.
     * Then, it uses the HotelRepository class to find Hotel object in database or else throws HotelNotFoundException,
     * If present it updates its name and save in database.
     * @param airportName
     * @param airportDto
     * @return void
     * @throws AirportNotFoundException "No such airport exists"
     **/
    public void updateAirport(String airportName, AirportDto airportDto){
        Airport airport = airportRepository.findByName(airportName)
                .orElseThrow(() -> new AirportNotFoundException("No such airport exists"));
        airport.setName(airportDto.getName());
        airport.setAddress(airportDto.getAddress());
        airportRepository.save(airport);
    }
    /**
     * This method gets a airportName as a param.
     * Then, it uses the AirportRepository class to find Hotel object in database or else throws AirportNotFoundException.
     * Next it is deleted from database.
     * @param airportName
     * @return AirportDto
     * @throws AirportNotFoundException "No such hotel exists"
     **/
    public AirportDto deleteAirport(String airportName) {
        Airport airport = airportRepository.findByName(airportName)
                .orElseThrow(() -> new AirportNotFoundException("No such airport exists"));
        airportRepository.delete(airport);
        return airportMapper.AirportToAirportDto(airport);
    }
}
