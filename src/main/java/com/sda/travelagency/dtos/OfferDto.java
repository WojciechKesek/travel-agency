package com.sda.travelagency.dtos;

import com.sda.travelagency.entities.City;
import com.sda.travelagency.entities.Continent;
import com.sda.travelagency.entities.Country;
import com.sda.travelagency.entities.Hotel;

public class OfferDto {
    private String name;

    private String hotelName;
    private String cityName;
    private String countryName;
    private String continentName;

    public OfferDto(String name, String hotelName, String cityName, String countryName, String continentName) {
        this.name = name;
        this.hotelName = hotelName;
        this.cityName = cityName;
        this.countryName = countryName;
        this.continentName = continentName;
    }

    public OfferDto() {
    }

    public String getName() {
        return name;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    @Override
    public String toString() {
        return "OfferDto{" +
                "hotelName='" + hotelName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", countryName='" + countryName + '\'' +
                ", continentName='" + continentName + '\'' +
                '}';
    }
}
