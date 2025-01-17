package com.sda.travelagency.service;

import com.sda.travelagency.dtos.OfferAdditionDto;
import com.sda.travelagency.dtos.OfferDto;
import com.sda.travelagency.entities.Offer;
import com.sda.travelagency.entities.User;
import com.sda.travelagency.exception.*;
import com.sda.travelagency.mapper.OfferMapper;
import com.sda.travelagency.repository.HotelRepository;
import com.sda.travelagency.repository.OfferRepository;
import com.sda.travelagency.repository.UserRepository;
import com.sda.travelagency.util.Username;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferMapper offerMapper;
    private final OfferRepository offerRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    public OfferService(OfferMapper offerMapper, OfferRepository offerRepository, HotelRepository hotelRepository, UserRepository userRepository) {
        this.offerMapper = offerMapper;
        this.offerRepository = offerRepository;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
    }

    /**
     * This method finds an offers in the database.
     * Then, it uses the OfferMapper class to transform instances of the Offer objects into an OfferDto, which is added to List and passed on.
     * @return List of OfferDto
     **/
    public List<OfferDto> getAllOffers() {
        return offerRepository.findAll().stream()
                .map(offerMapper::offerToOfferDto)
                .collect(Collectors.toList());
    }

    /**
     * This method finds an Offer object in the OfferRepository by its or else throws OfferNotFoundException.
     * Then, it uses the OfferMapper class to transform an instance of the Offer objects into an OfferDto, which is passed on.
     * @param offerName
     * @return OfferDto
     * @throws OfferNotFoundException "No such offer exists"
     **/
    public OfferDto getOffer(String offerName){
        return offerMapper.offerToOfferDto(offerRepository.findByName(offerName).orElseThrow(() -> new OfferNotFoundException("No such offer exists")));
    }

    /**
     * This method gets an offerDto as a param.
     * Then, it uses the OfferMapper class to transform an instance of the OfferDto object into an Offer,
     * which is saved in database by OfferRepository.
     * @param offerDto
     * @return void
     **/
    public void addOffer(OfferAdditionDto offerDto) {
        Offer offer = offerMapper.offerDtoToOffer(offerDto);
        offerRepository.save(offer);
    }

    /**
     * This method gets an offerName as a param.
     * Then, it uses the OfferRepository class to find Offer object in OfferRepository or else throws OfferNotFoundException,
     * If present it is deleted from database.
     * @param offerName
     * @return OfferDto
     * @throws OfferNotFoundException "No such offer exists"
     **/
    public OfferDto deleteOffer(String offerName){
        Offer offerToDelete = offerRepository.findByName(offerName)
                .orElseThrow(() -> new OfferNotFoundException("No such offer exists"));
        offerRepository.delete(offerToDelete);
        return offerMapper.offerToOfferDto(offerToDelete);
    }
    /**
     * This method gets an offerName and offerDto as a param.
     * Then, it uses the OfferRepository class to find Offer object in database or else throws OfferNotFoundException,
     * If present it updates its name and save in database.
     * @param offerName
     * @param offerDto
     * @return void
     * @throws OfferNotFoundException "No such offer exists"
     **/
    public void updateOffer(String offerName, OfferAdditionDto offerDto){
        Offer offerToUpdate = offerRepository.findByName(offerName)
                .orElseThrow(() -> new OfferNotFoundException("No such offer exists"));
        offerToUpdate.setName(offerDto.getName());
        offerToUpdate.setHotel(hotelRepository.findByNameAndCityName(offerDto.getHotelName(), offerDto.getCityName())
                .orElseThrow(() -> new HotelNotFoundException("No such hotel exists")));
        offerToUpdate.setPrice(offerDto.getPrice());
        offerRepository.save(offerToUpdate);
    }
    /**
     * This method gets an offerName as a param.
     * Then, it uses the OfferRepository class to find Offer object in database or else throws OfferNotFoundException,
     * If present it adds user to offer list of users and offer to user list of offers and saves it in database.
     * @param offerName
     * @return OfferDto
     * @throws OfferNotFoundException "No such offer exists"
     * @throws AnonymousAuthorizationException "No user logged in"
     * @throws OfferNotAvailableException "Offer is already sold out"
     **/
    @Transactional
    public OfferDto reserveOffer(String offerName) {
        Offer offerByName = offerRepository.findByName(offerName)
                .orElseThrow(() -> new OfferNotFoundException("No such offer exists"));
        if(offerByName.getUsers().size() >= offerByName.getQuantity()) {
            throw new OfferNotAvailableException("Offer is already sold out");
        }
        User currentUser = userRepository.findByUsername(Username.getActive())
                .orElseThrow(() -> new AnonymousAuthorizationException("No user logged in"));
        if(offerByName.getUsers().contains(currentUser)){
            throw new OfferAlreadyReservedException("User already reserved this offer");
        }
        offerByName.getUsers().add(currentUser);
        currentUser.getOffers().add(offerByName);
        userRepository.save(currentUser);
        offerRepository.save(offerByName);
        return offerMapper.offerToOfferDto(offerByName);
    }

    /**
     * This method gets a range of prices as a param.
     * Then, it uses the OfferRepository class to find Offer objects in database within price range.
     * Next, it uses the OfferMapper class to transform instances of the Offer objects into an OfferDto,
     * which is added to List and passed on.
     * @param minPrice
     * @param maxPrice
     * @return List of OfferDto
     **/
    public List<OfferDto> getOfferByPriceGreaterThanAndPriceLessThanOrderByPriceDesc(BigDecimal minPrice, BigDecimal maxPrice){
        return offerRepository.findByPriceGreaterThanAndPriceLessThanOrderByPriceDesc(minPrice, maxPrice)
                .stream()
                .map(offerMapper::offerToOfferDto)
                .toList();
    }

    /**
     * This method gets an hotelName as a param.
     * Then, it uses the HotelRepository class to find Hotel object in database or else throws HotelNotFoundException.
     * Next, it finds Offer objects associated with given hotelName and uses the OfferMapper class to transform instances of the Offer objects into an OfferDto,
     * which is added to List and passed on.
     * @param hotelName
     * @return List of OfferDto
     * @throws HotelNotFoundException "No such hotel exists"
     **/
    public List<OfferDto> getOffersByHotelName(String hotelName){
        if(hotelRepository.findByName(hotelName).isEmpty()){
            throw new HotelNotFoundException("No such hotel exists");
        }
        return offerRepository.findOffersByHotelName(hotelName)
                .stream()
                .map(offerMapper::offerToOfferDto)
                .toList();
    }
    /**
     * This method gets an offers which was reserved by logged user.
     * It uses the offerRepository class to find Offer objects which was reserved by user in database.
     * Next, it uses the OfferMapper class to transform instances of the Offer objects into an OfferDto,
     * which is added to List and passed on.
     * @return List of OfferDto
     **/
    public List<OfferDto> getOffersForLoggedUser(){
        return offerRepository.findOffersByUsers_username(Username.getActive())
                .stream()
                .map(offerMapper::offerToOfferDto)
                .toList();
    }

    /**
     * This method gets an offerName as a param.
     * Then, it uses the OfferRepository class to find Offer object in database or else throws OfferNotFoundException,
     * Next, it gets currently logged user.
     * If present it removes user from offer list of users and removes offer from user list of offers and saves it in database.
     * @param offerName
     * @return OfferDto
     * @throws OfferNotFoundException "No such offer exists"
     * @throws AnonymousAuthorizationException "No user logged in"
     * @throws OfferWasntReservedByCurrentUserException "Offer wasn't reserved"
     **/
    @Transactional
    public OfferDto releaseOffer(String offerName){
        Offer offerToRelease = offerRepository.findByName(offerName)
                .orElseThrow(() -> new OfferNotFoundException("No such offer exists"));
        User currentUser = userRepository.findByUsername(Username.getActive())
                .orElseThrow(() -> new AnonymousAuthorizationException("No user logged in"));
        if(!currentUser.getOffers().contains(offerToRelease)){
            throw new OfferWasntReservedByCurrentUserException("Offer wasn't reserved");
        }
        offerToRelease.getUsers().remove(currentUser);
        currentUser.getOffers().remove(offerToRelease);
        userRepository.save(currentUser);
        offerRepository.save(offerToRelease);
        return offerMapper.offerToOfferDto(offerToRelease);
    }
}
