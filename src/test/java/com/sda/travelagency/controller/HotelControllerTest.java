package com.sda.travelagency.controller;

import com.sda.travelagency.dtos.HotelDto;
import com.sda.travelagency.entities.Hotel;
import com.sda.travelagency.entities.Offer;
import com.sda.travelagency.mapper.HotelMapper;
import com.sda.travelagency.repository.CityRepository;
import com.sda.travelagency.repository.HotelRepository;
import com.sda.travelagency.repository.OfferRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class HotelControllerTest {
    @Autowired
    private WebTestClient testClient;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private HotelMapper hotelMapper;
    private final Float RATING = 10.0f;
    private final String ADDRESS = "testAddress";
    private final String ADMIN = "testAdmin";
    private final String USER = "testUser";
    private final String PASSWORD = "password";


    @Test
    void shouldGetAllHotels() {
        testClient
                .get()
                .uri("/hotels")
                .accept(MediaType.APPLICATION_JSON)
                .headers(headersConsumer -> headersConsumer.setBasicAuth(USER, PASSWORD))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(HotelDto.class);
    }

    @Test
    void shouldGetTopHotels(){
        List<HotelDto> expectedHotelsList = hotelRepository.findAll(Sort.by(Sort.Direction.DESC, "rating")).stream()
                .map(hotelMapper::hotelToHotelDto).toList();
        testClient
                .get()
                .uri("/hotels/topHotels")
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(HotelDto.class).isEqualTo(expectedHotelsList);

    }

    @Test
    void shouldGetHotelsByCityName(){
        String cityName = cityRepository.findAll().get(0).getName();
        testClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/hotels/filterByCity")
                        .queryParam("cityName", cityName)
                        .build())
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(HotelDto.class);
    }
    @Test
    void shouldGetHotelByName (){
        Hotel testHotel = hotelRepository.findAll().get(0);
        HotelDto testHotelDto = new HotelDto(testHotel.getName(), testHotel.getAddress(), testHotel.getRating(), testHotel.getCity().getName());
        testClient
                .get()
                .uri("/hotels/{name}",testHotel.getName())
                .accept(MediaType.APPLICATION_JSON)
                .headers(headersConsumer -> headersConsumer.setBasicAuth(USER, PASSWORD))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(HotelDto.class)
                .isEqualTo(testHotelDto);
    }

    @Test
    void shouldNotGetHotelByIncorrectName (){
        ProblemDetail detail = testClient
                .get()
                .uri("/hotels/incorrectHotelName")
                .accept(MediaType.APPLICATION_JSON)
                .headers(headersConsumer -> headersConsumer.setBasicAuth(USER, PASSWORD))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();
        Assertions.assertEquals("No such hotel exists", detail.getDetail());
    }

    @Test
    void shouldUpdateHotel(){
        testClient
                .put()
                .uri("/hotels/{name}", hotelRepository.findAll().get(0).getName())
                .bodyValue(new HotelDto("testHotel", ADDRESS, RATING, cityRepository.findAll().get(0).getName()))
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isOk();
    }
    @Test
    void shouldNotUpdateHotelWithIncorrectExistingHotelName(){
        ProblemDetail detail = testClient
                .put()
                .uri("/hotels/{name}","incorrectCityName")
                .bodyValue(new HotelDto("testHotel", "testAddress", RATING, cityRepository.findAll().get(0).getName()))
                .accept(MediaType.APPLICATION_JSON)
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();
        Assertions.assertEquals("No such hotel exists",detail.getDetail());
    }
    @Test
    void shouldAddHotel(){
        testClient
                .post()
                .uri("/hotels/addHotel")
                .bodyValue(new HotelDto("testHotel", ADDRESS, RATING, cityRepository.findAll().get(0).getName()))
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isOk();
    }
    @Test
    void shouldNotAddHotelWithNotExistingCityName (){
        ProblemDetail detail = testClient
                .post()
                .uri("/hotels/addHotel")
                .bodyValue(new HotelDto("testHotel", ADDRESS, RATING, "incorrectCity"))
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();
        Assertions.assertEquals("No such city exists", detail.getDetail());
    }


    @Test
    void shouldNotAddHotelWithBlankName (){
        ProblemDetail detail = testClient
                .post()
                .uri("/hotels/addHotel")
                .bodyValue(new HotelDto("", ADDRESS, RATING, cityRepository.findAll().get(0).getName()))
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();
        Assertions.assertEquals("Validation error: name: Hotel name is mandatory, ", detail.getDetail());
    }
    @Test
    void shouldNotAddHotelWithNullName (){
        ProblemDetail detail = testClient
                .post()
                .uri("/hotels/addHotel")
                .bodyValue(new HotelDto(null, ADDRESS, RATING, cityRepository.findAll().get(0).getName()))
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();
        Assertions.assertEquals("Validation error: name: Hotel name is mandatory, ", detail.getDetail());
    }
    @Test
    void shouldNotAddHotelWithCityBlankName (){
        ProblemDetail detail = testClient
                .post()
                .uri("/hotels/addHotel")
                .bodyValue(new HotelDto("testHotel", ADDRESS, RATING, " "))
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();
        Assertions.assertEquals("Validation error: cityName: City name is mandatory, ", detail.getDetail());
    }
    @Test
    void shouldNotAddHotelWithNullCityName (){
        ProblemDetail detail = testClient
                .post()
                .uri("/hotels/addHotel")
                .bodyValue(new HotelDto("testHotel", ADDRESS, RATING, null))
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();
        Assertions.assertEquals("Validation error: cityName: City name is mandatory, ", detail.getDetail());
    }

    @Test
    void shouldNotDeleteHotelWithOffers(){
        ProblemDetail detail = testClient
                .delete()
                .uri("/hotels/{name}", hotelRepository.findAll().get(0).getName())
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();
        Assertions.assertEquals("Hotel is associated with offers and cannot be deleted", detail.getDetail());
    }

    @Test
    void shouldDeleteHotel(){
        Offer offer = offerRepository.findAll().get(0);
        Hotel hotelToDelete = new Hotel("hotelToDelete", ADDRESS, offer.getHotel().getRating(), offer.getHotel().getCity());
        hotelRepository.save(hotelToDelete);
        testClient
                .delete()
                .uri("/hotels/{name}",hotelToDelete.getName())
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isOk();
        Assertions.assertFalse(hotelRepository.findAll().contains(hotelToDelete));
    }


}