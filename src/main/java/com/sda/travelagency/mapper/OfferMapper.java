package com.sda.travelagency.mapper;

import com.sda.travelagency.dtos.OfferDto;
import com.sda.travelagency.entities.Hotel;
import com.sda.travelagency.entities.Offer;
import com.sda.travelagency.repository.MapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfferMapper {

    public Offer offerDtoToOffer(String name, Hotel hotel) {
        return new Offer(name, hotel);
    }

    //custom join pare tabel
    public static OfferDto offerToOfferDto(Offer offer){
        OfferDto offerDto = new OfferDto();
        offerDto.setName(offer.getName());
        offerDto.setHotelName(offer.getHotel().getName());
        offerDto.setCityName(offer.getHotel().getCity().getName());
        offerDto.setCountryName(offer.getHotel().getCity().getCountry().getName());
        offerDto.setContinentName(offer.getHotel().getCity().getCountry().getContinent().getName());
        return offerDto;
    };
}