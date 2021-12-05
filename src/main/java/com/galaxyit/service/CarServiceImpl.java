package com.galaxyit.service;

import com.galaxyit.model.Car;
import com.galaxyit.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarServiceImpl.class);

    private final CarRepository carRepository;

    public CarServiceImpl(@Autowired CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Optional<Car> fetchCarById(Long carId) {
        return carRepository.findById(carId);
    }

    @Override
    public void removeCar(Car car) {
        carRepository.delete(car);
    }

    @Override
    public Car updateCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Iterable<Car> fetchAllCars() {
        return carRepository.findAll();
    }

    @Override
    public void saveAll(List<Car> cars) {
        carRepository.saveAll(cars);
    }
}