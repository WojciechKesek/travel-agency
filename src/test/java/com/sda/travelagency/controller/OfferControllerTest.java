package com.sda.travelagency.controller;


import com.sda.travelagency.dtos.OfferDto;
import com.sda.travelagency.entities.Hotel;
import com.sda.travelagency.entities.Offer;
import com.sda.travelagency.repository.MapperRepository;
import com.sda.travelagency.repository.OfferRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class OfferControllerTest {

    @Autowired
    private WebTestClient testClient;

    @Autowired
    private MapperRepository mapperRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Test
    void shouldAddOffer() {
        Hotel testHotel = mapperRepository.findAll().get(0);
        OfferDto offerDto = new OfferDto(
                "Test Offer",
                testHotel.getName(),
                testHotel.getCity().getName(),
                testHotel.getCity().getCountry().getName(),
                testHotel.getCity().getCountry().getContinent().getName()
        );
        testClient
                .post()
                .uri("/offers/addOffer")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(offerDto)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void shouldGetAllOffers() {
        testClient
                .get()
                .uri("/offers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(OfferDto.class);
    }

    @Test
    void shouldDeleteOffer() {
        Offer testOffer = offerRepository.findAll().get(0);
        testClient
                .delete()
                .uri("/offers/{offerName}", testOffer.getName())
                .exchange()
                .expectStatus().isAccepted();

    }

    @Test
    void shouldGetOfferByName() {
        Offer testOffer = offerRepository.findAll().get(1);
        OfferDto expectedOfferDto = new OfferDto(
                testOffer.getName(),
                testOffer.getHotel().getName(),
                testOffer.getHotel().getCity().getName(),
                testOffer.getHotel().getCity().getCountry().getName(),
                testOffer.getHotel().getCity().getCountry().getContinent().getName()
        );
        testClient
                .get()
                .uri("/offers/{name}", testOffer.getName())
                .exchange()
                .expectStatus().isOk()
                .expectBody(OfferDto.class)
                .isEqualTo(expectedOfferDto);
    }
    @Test
    void shouldUpdateOffer() {
        Offer testOffer = offerRepository.findAll().get(0);
        OfferDto updatedOfferDto = new OfferDto(
                "testOffer",
                testOffer.getHotel().getName(),
                testOffer.getHotel().getCity().getName(),
                testOffer.getHotel().getCity().getCountry().getName(),
                testOffer.getHotel().getCity().getCountry().getContinent().getName()
        );
        testClient
                .put()
                .uri("/offers/{offerName}", offerRepository.findAll().get(0).getName())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedOfferDto)
                .exchange()
                .expectStatus().isAccepted()
                .expectBody(String.class).isEqualTo("Offer updated");
    }

    @Test
    void shouldDeleteAllOffersForHotel() {
        String hotelToClear = mapperRepository.findAll().get(0).getName();
        testClient
                .delete()
                .uri("/offers/hotel/{hotelName}", hotelToClear)
                .exchange()
                .expectStatus().isAccepted()
                .expectBody(String.class).isEqualTo("Offers for " + hotelToClear +  " deleted");
    }
    @Test
    void shouldNotDeleteAllOffersForHotel() {
        String invalidHotelToClear = "invalidHotelName";
        ProblemDetail detail = testClient
                .delete()
                .uri("/offers/hotel/{hotelName}", invalidHotelToClear)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();
        Assertions.assertEquals("No such hotel exists", detail.getDetail());
    }

}