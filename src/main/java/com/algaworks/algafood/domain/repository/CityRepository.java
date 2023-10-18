package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.City;

import java.util.List;

public interface CityRepository {

    List<City> list();
    City find(Long id);
    City save(City city);
    void remove(Long id);
}
