package com.example.carservice.dto.mapper;

public interface RequestDtoMapper<D, T> {
    T mapToModel(D dto);
}
