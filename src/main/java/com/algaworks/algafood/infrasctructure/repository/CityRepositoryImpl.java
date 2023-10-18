package com.algaworks.algafood.infrasctructure.repository;

import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.repository.CityRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import org.springframework.util.ObjectUtils;

@Component
public class CityRepositoryImpl implements CityRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<City> list() {
        return manager.createQuery("from City", City.class)
                .getResultList();
    }

    @Override
    public City find(Long id) {
        return manager.find(City.class, id);
    }

    @Transactional
    @Override
    public City save(City city) {
        return manager.merge(city);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        City city = find(id);
        if (ObjectUtils.isEmpty(city)) {
            throw new EmptyResultDataAccessException(1);
        }
        manager.remove(city);
    }
}
