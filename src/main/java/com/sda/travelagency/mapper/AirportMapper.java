package com.sda.travelagency.mapper;


import com.sda.travelagency.dtos.AirportDto;
import com.sda.travelagency.entities.Airport;
import com.sda.travelagency.exception.CityNotFoundException;
import com.sda.travelagency.repository.CityRepository;
import org.springframework.stereotype.Component;

@Component
public class AirportMapper {
    private final CityRepository cityRepository;

    public AirportMapper(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }
    /**
     * This method takes as a param AirportDto object.
     * It is using empty constructor to initialize Airport object and sets it required fields with data from AirportDto object
     * To set City object it uses cityName from AirportDto to find it in CityRepository
     * @param airportDto
     * @return Airport
     * @throws CityNotFoundException "No such city exists"
     **/
    public Airport AirportDtoToAirport(AirportDto airportDto){
        Airport mappedAirport = new Airport();
        mappedAirport.setName(airportDto.getName());
        mappedAirport.setAddress(airportDto.getAddress());
        mappedAirport.setCity(cityRepository.findByName(airportDto.getCityName())
                .orElseThrow(() -> new CityNotFoundException("No such city exists")));
        return mappedAirport;
    }
    /**
     * This method takes as a param Airport object.
     * It is using empty constructor to initialize AirportDto object and sets it required fields with data from Airport object
     * @param airport
     * @return AirportDto
     **/
    public AirportDto AirportToAirportDto(Airport airport){
        AirportDto mappedAirport = new AirportDto();
        mappedAirport.setName(airport.getName());
        mappedAirport.setAddress(airport.getAddress());
        mappedAirport.setCityName(airport.getCity().getName());
        return mappedAirport;
    }
}
