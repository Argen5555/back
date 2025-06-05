package com.example.carservice.service;

import com.example.carservice.model.Goods;
import java.util.List;

public interface GoodsService {
    List<Goods> getAll();

    Goods get(Long id);

    Goods add(Goods goods);

    Goods update(Goods goods);
}
