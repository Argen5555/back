package com.example.carservice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarRequestDto {
    private String brand;
    private String model;
    private Integer year;
    private String number;
    private Long ownerId;
}
