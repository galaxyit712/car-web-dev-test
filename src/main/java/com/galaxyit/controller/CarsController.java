package com.galaxyit.controller;

import com.galaxyit.exception.CarNotFoundException;
import com.galaxyit.model.Car;
import com.galaxyit.service.CarService;
import com.galaxyit.service.WordsInterceptorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/cars")
public class CarsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarsController.class);

    private final CarService carService;

    private final WordsInterceptorService wordsInterceptor;

    public CarsController(@Autowired CarService carService, @Autowired WordsInterceptorService wordsInterceptor) {
        this.carService = carService;
        this.wordsInterceptor = wordsInterceptor;
    }

    @PostMapping
    public Car createCar(@Valid @RequestBody Car car) {
        LOGGER.info("Going to save car [{}]...", car);
        Car newCar = carService.createCar(car);
        LOGGER.info("Car [{}] created successfully", car);
        return newCar;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> fetchCarById(@PathVariable(value = "id") Long carId) {
        LOGGER.info("Going to fetch car by carId [{}]...", carId);
        Car car = getCar(carId);
        car.setModel(wordsInterceptor.getLikeMindedWordsForGivenModel(car.getModel()));
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCarById(@PathVariable(value = "id") Long carId) {
        Car car = getCar(carId);

        carService.removeCar(car);
        final String msg = "Car by Id " + carId + " deleted successfully";
        LOGGER.info(msg);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(
            @PathVariable(value = "id") Long carId, @Valid @RequestBody Car carDetails) {

        Car car = getCar(carId);

        carDetails.setId(car.getId());

        final Car updatedCar = carService.updateCar(carDetails);
        LOGGER.info("Car updated successfully. new Car [{}]", updatedCar);
        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }


    private Car getCar(@PathVariable("id") Long carId) {
        return carService
                .fetchCarById(carId)
                .orElseThrow(() -> new CarNotFoundException("Car by Id [" + carId + "] not found"));
    }
}