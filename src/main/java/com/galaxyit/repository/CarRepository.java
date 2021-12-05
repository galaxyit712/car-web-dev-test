package com.galaxyit.repository;

import com.galaxyit.model.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface CarRepository extends CrudRepository<Car, Long> {
}
