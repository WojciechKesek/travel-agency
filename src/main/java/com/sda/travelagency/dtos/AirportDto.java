package com.sda.travelagency.dtos;

import com.sda.travelagency.annotation.JsonElement;
import com.sda.travelagency.annotation.JsonSerializable;
import jakarta.validation.constraints.NotBlank;
@JsonSerializable
public class AirportDto {
    @JsonElement
    @NotBlank(message = "Airport name is mandatory")
    private String name;
    @JsonElement
    @NotBlank(message = "Address is mandatory")
    private String address;

    @JsonElement
    @NotBlank(message = "City name is mandatory")
    private String cityName;

    public AirportDto() {
    }

    public AirportDto(String name, String address, String cityName) {
        this.name = name;
        this.address = address;
        this.cityName = cityName;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCityName() {
        return cityName;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
