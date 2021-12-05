package com.galaxyit.service;

import com.galaxyit.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

    Car createCar(Car car);

    Optional<Car> fetchCarById(Long carId);

    void removeCar(Car car);

    Car updateCar(Car car);

    Iterable<Car> fetchAllCars();

    void saveAll(List<Car> cars);
}