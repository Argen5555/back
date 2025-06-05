package com.example.carservice.service;

import com.example.carservice.model.Car;
import java.util.List;

public interface CarService {
    List<Car> getAll();

    Car get(Long id);

    Car add(Car car);

    Car update(Car car);

    void delete(Car car);
}
