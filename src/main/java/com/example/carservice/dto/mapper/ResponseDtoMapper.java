package com.example.carservice.dto.mapper;

public interface ResponseDtoMapper<D, T> {
    D mapToDto(T t);
}
