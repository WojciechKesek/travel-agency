package com.sda.travelagency.controller;

import com.sda.travelagency.dtos.AirportDto;
import com.sda.travelagency.entities.Airport;
import com.sda.travelagency.repository.AirportRepository;
import com.sda.travelagency.repository.CityRepository;
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
class AirportControllerTest {
    @Autowired
    private WebTestClient testClient;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private AirportRepository airportRepository;
    private final String ADDRESS = "testAddress";
    private final String ADMIN = "testAdmin";
    private final String USER = "testUser";
    private final String PASSWORD = "password";


    @Test
    void shouldGetAllHotels() {
        testClient
                .get()
                .uri("/airports")
                .accept(MediaType.APPLICATION_JSON)
                .headers(headersConsumer -> headersConsumer.setBasicAuth(USER, PASSWORD))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(AirportDto.class);
    }

    @Test
    void shouldGetAirportsByCityName(){
        String cityName = cityRepository.findAll().get(0).getName();
        testClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/airports/filterByCity")
                        .queryParam("cityName", cityName)
                        .build())
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(AirportDto.class);
    }

    @Test
    void shouldUpdateAirport(){
        testClient
                .put()
                .uri("/airports/{name}", airportRepository.findAll().get(0).getName())
                .bodyValue(new AirportDto("testAirport", ADDRESS, cityRepository.findAll().get(0).getName()))
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isOk();
    }
    @Test
    void shouldNotUpdateAirportWithIncorrectExistingAirportName(){
        testClient
                .put()
                .uri("/airports/{name}","incorrectAirportName")
                .bodyValue(new AirportDto("testAirport", ADDRESS, cityRepository.findAll().get(0).getName()))
                .accept(MediaType.APPLICATION_JSON)
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();
    }
    @Test
    void shouldAddAirport(){
        testClient
                .post()
                .uri("/airports/addAirport")
                .bodyValue(new AirportDto("testAirport", ADDRESS, cityRepository.findAll().get(0).getName()))
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isOk();
    }
    @Test
    void shouldNotAddAirportWithNotExistingCityName (){
        testClient
                .post()
                .uri("/airports/addAirport")
                .bodyValue(new AirportDto("testAirport", ADDRESS, "incorrectCity"))
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();
    }


    @Test
    void shouldNotAddAirportWithBlankName (){
        ProblemDetail detail = testClient
                .post()
                .uri("/airports/addAirport")
                .bodyValue(new AirportDto("", ADDRESS, cityRepository.findAll().get(0).getName()))
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();
        Assertions.assertEquals("Validation error: name: Airport name is mandatory, ", detail.getDetail());
    }
    @Test
    void shouldNotAddAirportWithNullName (){
        ProblemDetail detail = testClient
                .post()
                .uri("/airports/addAirport")
                .bodyValue(new AirportDto(null, ADDRESS, cityRepository.findAll().get(0).getName()))
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();
        Assertions.assertEquals("Validation error: name: Airport name is mandatory, ", detail.getDetail());
    }
    @Test
    void shouldNotAddAirportWithCityBlankName (){
        ProblemDetail detail = testClient
                .post()
                .uri("/airports/addAirport")
                .bodyValue(new AirportDto("testAirport", ADDRESS, " "))
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();
        Assertions.assertEquals("Validation error: cityName: City name is mandatory, ", detail.getDetail());
    }
    @Test
    void shouldNotAddAirportWithNullCityName (){
        ProblemDetail detail = testClient
                .post()
                .uri("/airports/addAirport")
                .bodyValue(new AirportDto("testAirport", ADDRESS, null))
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();
        Assertions.assertEquals("Validation error: cityName: City name is mandatory, ", detail.getDetail());
    }

    @Test
    void shouldDeleteAirport(){
        Airport airportToDelete = new Airport("airportToDelete", ADDRESS, cityRepository.findAll().get(0));
        airportRepository.save(airportToDelete);
        testClient
                .delete()
                .uri("/airports/{name}",airportToDelete.getName())
                .headers(headersConsumer -> headersConsumer.setBasicAuth(ADMIN, PASSWORD))
                .exchange()
                .expectStatus().isOk();
        Assertions.assertFalse(airportRepository.findAll().contains(airportToDelete));
        airportRepository.delete(airportToDelete);
    }


}