package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class RestaurantRegistrationService {

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Autowired
  private KitchenRepository kitchenRepository;

  public Restaurant save(Restaurant restaurant) {
    Long id = restaurant.getKitchen().getId();
    Kitchen kitchen = kitchenRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(
            String.format("Não existe cadastro de cozinha com código %d", id)));

    restaurant.setKitchen(kitchen);

    return restaurantRepository.save(restaurant);
  }

  public void remove(Long id) {
    try {
      restaurantRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new EntityNotFoundException("Restaurant not found!");
    } catch (DataIntegrityViolationException e) {
      throw new EntityInUseException("Restaurant cannot been removed!");
    }
  }


}
