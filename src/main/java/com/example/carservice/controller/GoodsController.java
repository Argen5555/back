package com.example.carservice.controller;

import com.example.carservice.dto.mapper.RequestDtoMapper;
import com.example.carservice.dto.mapper.ResponseDtoMapper;
import com.example.carservice.dto.request.GoodsRequestDto;
import com.example.carservice.dto.response.GoodsResponseDto;
import com.example.carservice.model.Goods;
import com.example.carservice.service.GoodsService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    private final GoodsService goodsService;
    private final RequestDtoMapper<GoodsRequestDto, Goods> requestDtoMapper;
    private final ResponseDtoMapper<GoodsResponseDto, Goods> responseDtoMapper;

    public GoodsController(GoodsService goodsService,
                           RequestDtoMapper<GoodsRequestDto, Goods> requestDtoMapper,
                           ResponseDtoMapper<GoodsResponseDto, Goods> responseDtoMapper) {
        this.goodsService = goodsService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
    }

    @GetMapping
    @ApiOperation("Get all goods")
    public List<GoodsResponseDto> getAll() {
        return goodsService.getAll()
                .stream()
                .map(responseDtoMapper::mapToDto)
                .toList();
    }

    @GetMapping("/{id}")
    @ApiOperation("Get goods by id")
    public GoodsResponseDto get(@PathVariable Long id) {
        Goods goods = goodsService.get(id);
        return responseDtoMapper.mapToDto(goods);
    }

    @PostMapping
    @ApiOperation("Add a new goods")
    public GoodsResponseDto create(@RequestBody GoodsRequestDto requestDto) {
        Goods goods = goodsService.add(requestDtoMapper.mapToModel(requestDto));
        return responseDtoMapper.mapToDto(goods);
    }

    @PostMapping("/{id}")
    @ApiOperation("Update goods by id")
    public GoodsResponseDto update(@PathVariable Long id,
                                   @RequestBody GoodsRequestDto requestDto) {
        Goods goods = requestDtoMapper.mapToModel(requestDto);
        goods.setId(id);
        return responseDtoMapper.mapToDto(goodsService.update(goods));
    }
}
