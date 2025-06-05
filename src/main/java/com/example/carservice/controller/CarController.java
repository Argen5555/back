package com.example.carservice.controller;

import com.example.carservice.dto.mapper.RequestDtoMapper;
import com.example.carservice.dto.mapper.ResponseDtoMapper;
import com.example.carservice.dto.request.CarRequestDto;
import com.example.carservice.dto.response.CarResponseDto;
import com.example.carservice.model.Car;
import com.example.carservice.service.CarService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final RequestDtoMapper<CarRequestDto, Car> requestDtoMapper;
    private final ResponseDtoMapper<CarResponseDto, Car> responseDtoMapper;

    public CarController(CarService carService,
                         RequestDtoMapper<CarRequestDto, Car> requestDtoMapper,
                         ResponseDtoMapper<CarResponseDto, Car> responseDtoMapper) {
        this.carService = carService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
    }

    @GetMapping
    @ApiOperation("Get all cars")
    public List<CarResponseDto> getAll() {
        return carService.getAll()
                .stream()
                .map(responseDtoMapper::mapToDto)
                .toList();
    }

    @GetMapping("/{id}")
    @ApiOperation("Get car by id")
    public CarResponseDto get(@PathVariable Long id) {
        Car car = carService.get(id);
        return responseDtoMapper.mapToDto(car);
    }

    @PostMapping
    @ApiOperation("Add a new car")
    public CarResponseDto create(@RequestBody CarRequestDto requestDto) {
        Car car = carService.add(requestDtoMapper.mapToModel(requestDto));
        return responseDtoMapper.mapToDto(car);
    }

    @PostMapping("/{id}")
    @ApiOperation("Update car by id")
    public CarResponseDto update(@PathVariable Long id,
                                 @RequestBody CarRequestDto requestDto) {
        Car car = requestDtoMapper.mapToModel(requestDto);
        car.setId(id);
        return responseDtoMapper.mapToDto(carService.update(car));
    }
}
