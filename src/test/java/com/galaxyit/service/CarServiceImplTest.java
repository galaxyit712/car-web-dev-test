package com.galaxyit.service;

import com.galaxyit.model.Car;
import com.galaxyit.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class CarServiceImplTest {

    @MockBean
    private CarRepository mockCarRepository;

    @Autowired
    private CarService carService;

    @Test
    void createsCarSuccessfully() {
        Car c = new Car(Long.MAX_VALUE, "BMW", "3-Series", "White", 2021);
        when(mockCarRepository.save(c)).thenReturn(c);
        Car savedCar = carService.createCar(c);

        assertThat(savedCar.getId()).isEqualTo(Long.MAX_VALUE);
        assertThat(savedCar.getMake()).isEqualTo("BMW");
        assertThat(savedCar.getModel()).isEqualTo("3-Series");
        assertThat(savedCar.getColour()).isEqualTo("White");
        assertThat(savedCar.getYear()).isEqualTo(2021);
    }

    @Test
    void updatesCarSuccessfully() {
        Car c = new Car(null, "BMW", "3-Series", "White", 2021);
        Car updatedCar = new Car(9999L, "BMW", "3-Series", "White", 2021);
        when(mockCarRepository.save(c)).thenReturn(updatedCar);

        Car savedCar = carService.updateCar(c);
        assertThat(savedCar.getId()).isEqualTo(9999L);
        assertThat(savedCar.getMake()).isEqualTo("BMW");
        assertThat(savedCar.getModel()).isEqualTo("3-Series");
        assertThat(savedCar.getColour()).isEqualTo("White");
        assertThat(savedCar.getYear()).isEqualTo(2021);
    }

    @Test
    void fetchesCarByIdSuccessfully() {
        Car c = new Car(9999L, "BMW", "3-Series", "White", 2021);
        when(mockCarRepository.findById(9999L)).thenReturn(Optional.of(c));

        Car car = carService.fetchCarById(9999L).get();
        assertThat(car.getId()).isEqualTo(9999L);
        assertThat(car.getMake()).isEqualTo("BMW");
        assertThat(car.getModel()).isEqualTo("3-Series");
        assertThat(car.getColour()).isEqualTo("White");
        assertThat(car.getYear()).isEqualTo(2021);
    }

    @Test
    void removesCarByIdSuccessfully() {
        Car c = new Car(9999L, "BMW", "3-Series", "White", 2021);
        doNothing().when(mockCarRepository).delete(c);
        when(mockCarRepository.findById(9999L)).thenReturn(Optional.empty());
        carService.removeCar(c);

        Optional<Car> optionalCar = carService.fetchCarById(c.getId());

        assertThat(optionalCar).isEqualTo(Optional.empty());
    }
}