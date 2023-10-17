package com.algaworks.algafood.infrasctructure.repository;

import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurant> list() {
        return manager.createQuery("from Restaurant", Restaurant.class)
                .getResultList();
    }

    @Override
    public Restaurant find(Long id) {
        return manager.find(Restaurant.class, id);
    }

    @Transactional
    @Override
    public Restaurant save(Restaurant restaurant) {
        return manager.merge(restaurant);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        Restaurant restaurant = find(id);
        if (restaurant == null) {
            throw new EmptyResultDataAccessException(1);
        }
        manager.remove(restaurant);
    }
}
