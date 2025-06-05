package com.example.carservice.dto.mapper.impl;

import com.example.carservice.dto.mapper.RequestDtoMapper;
import com.example.carservice.dto.mapper.ResponseDtoMapper;
import com.example.carservice.dto.request.GoodsRequestDto;
import com.example.carservice.dto.response.GoodsResponseDto;
import com.example.carservice.model.Goods;
import org.springframework.stereotype.Component;

@Component
public class GoodsMapper implements RequestDtoMapper<GoodsRequestDto, Goods>,
        ResponseDtoMapper<GoodsResponseDto, Goods> {
    @Override
    public Goods mapToModel(GoodsRequestDto dto) {
        Goods goods = new Goods();
        goods.setName(dto.getName());
        goods.setPrice(dto.getPrice());
        return goods;
    }

    @Override
    public GoodsResponseDto mapToDto(Goods goods) {
        GoodsResponseDto dto = new GoodsResponseDto();
        dto.setId(goods.getId());
        dto.setName(goods.getName());
        dto.setPrice(goods.getPrice());
        return dto;
    }
}
