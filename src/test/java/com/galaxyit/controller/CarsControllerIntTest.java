package com.galaxyit.controller;

import com.galaxyit.model.Car;
import com.galaxyit.repository.CarRepository;
import com.galaxyit.service.CarService;
import com.galaxyit.service.WordsInterceptorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarsControllerIntTest {

    private static final String CARS_URL = "/api/v1/cars/";

    @Autowired
    private CarService carService;

    @Autowired
    private WordsInterceptorService wordsInterceptorService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RestTemplate testRestTemplate;

    @LocalServerPort
    private String port;

    @AfterEach
    public void tearDown() {
        carRepository.deleteAll();
    }

    @BeforeEach
    public void setUp() {
        carRepository.deleteAll();
    }

    @Test
    public void savesCarSuccessfully() throws URISyntaxException {
        Car c = new Car(null, "Tesla", "3", "White", 2021);
        ResponseEntity<Car> response = testRestTemplate.postForEntity(
                getUrl("/api/v1/cars"), c, Car.class);
        Car savedCar = response.getBody();
        assertThat(savedCar.getId()).isInstanceOf(Long.class);
        assertThat(savedCar.getMake()).isEqualTo("Tesla");
        assertThat(savedCar.getModel()).isNotBlank();
        assertThat(savedCar.getColour()).isEqualTo("White");
    }

    @Test
    public void fetchesCarByIdSuccessfully() throws URISyntaxException {
        List<Car> cars = insertFewCars();
        ResponseEntity<Car> responseGetForEntity = testRestTemplate.getForEntity(
                getUrl("/api/v1/cars/" + cars.get(0).getId()), Car.class);

        Car c = responseGetForEntity.getBody();
        assertThat(c.getId()).isEqualTo(cars.get(0).getId());
        assertThat(c.getMake()).isEqualTo("BMW");
        assertThat(c.getModel()).isNotBlank();
        assertThat(c.getColour()).isEqualTo("Red");
    }

    @Test
    public void carByIdIsNotFound() {
        HttpServerErrorException.InternalServerError internalServerError = assertThrows(HttpServerErrorException.InternalServerError.class,
                () -> testRestTemplate.getForEntity(
                        getUrl("/api/v1/cars/999999"), Car.class));

        assertThat(internalServerError.getLocalizedMessage()).contains("\"errorMessageDescription\":\"Car by Id [999999] not found\"");
    }

    @Test
    public void updatesCarSuccessfully() throws URISyntaxException {
        List<Car> cars = insertFewCars();
        Car car = cars.get(0);
        car.setColour("Unknown colour");

        testRestTemplate.put(
                getUrl(CARS_URL + car.getId()), car);
        ResponseEntity<Car> responseGetForEntity = testRestTemplate.getForEntity(
                getUrl("/api/v1/cars/" + car.getId()), Car.class);

        assertThat(responseGetForEntity.getBody().getColour()).isEqualTo("Unknown colour");
    }

    @Test
    public void removesTheCarByIdSuccessfully() throws URISyntaxException {

        List<Car> cars = insertFewCars();
        testRestTemplate.delete(getUrl(CARS_URL + cars.get(0).getId()));

        HttpServerErrorException.InternalServerError internalServerError = assertThrows(HttpServerErrorException.InternalServerError.class,
                () -> testRestTemplate.getForEntity(
                        getUrl("/api/v1/cars/" + cars.get(0).getId()), Car.class));
        assertThat(internalServerError.getLocalizedMessage()).contains("\"errorMessageDescription\":\"Car by Id [" + cars.get(0).getId() + "] not found\"");

    }

    private List<Car> insertFewCars() {
        Car c = new Car(null, "BMW", "2-Series", "Red", 2019);
        Car c1 = new Car(null, "BMW", "3-Series", "Black", 2020);
        List<Car> entities = Arrays.asList(c, c1);
        carRepository.saveAll(entities);
        return entities;
    }


    private URI getUrl(String s) throws URISyntaxException {
        return new URI("http://localhost:" + port + s);
    }
}