package com.sda.travelagency.service;

import com.sda.travelagency.dtos.OfferDto;
import com.sda.travelagency.entities.Hotel;
import com.sda.travelagency.entities.Offer;
import com.sda.travelagency.exception.HotelNotFoundException;
import com.sda.travelagency.exception.OfferNotFoundException;
import com.sda.travelagency.mapper.OfferMapper;
import com.sda.travelagency.repository.MapperRepository;
import com.sda.travelagency.repository.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferMapper offerMapper;
    private final OfferRepository offerRepository;

    private final MapperRepository mapperRepository;


    public OfferService(OfferMapper offerMapper, OfferRepository offerRepository, MapperRepository mapperRepository) {
        this.offerMapper = offerMapper;
        this.offerRepository = offerRepository;
        this.mapperRepository = mapperRepository;
    }

    public List<OfferDto> getAllOffers() {
        return offerRepository.findAll().stream()
                .map(OfferMapper::offerToOfferDto)
                .collect(Collectors.toList());
    }

    public OfferDto getOffer(String name){
        return OfferMapper.offerToOfferDto(offerRepository.findByName(name).orElseThrow(() -> new OfferNotFoundException("No such offer exists")));
    }

    public void addOffer(OfferDto offerDto) {
        Offer offer = offerMapper.offerDtoToOffer(offerDto.getName(),mapperRepository.findByNameAndCityName(offerDto.getHotelName(),offerDto.getCityName()).orElseThrow());
        offerRepository.save(offer);
    }

    public void deleteOffer(String name){
        Offer offerToDelete = offerRepository.findByName(name).orElseThrow(() -> new OfferNotFoundException("No such offer exists"));
        offerRepository.delete(offerToDelete);
    }

    public void deleteAllOffersForHotel(String hotelName){
        Hotel hotelToClear = mapperRepository.findByName(hotelName).orElseThrow(() -> new HotelNotFoundException("No such hotel exists"));
        offerRepository.deleteAll(hotelToClear.getOffers());
    }

    public void updateOffer(String name, OfferDto offerDto){
        Offer offerToUpdate = offerRepository.findByName(name).orElseThrow(() -> new OfferNotFoundException("No such offer exists"));
        offerToUpdate.setName(offerDto.getName());
        offerRepository.save(offerToUpdate);
    }
}
