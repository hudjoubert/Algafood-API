package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.domain.service.RestaurantRegistrationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Autowired
  private RestaurantRegistrationService restaurantRegistrationService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Restaurant> list() {
    return restaurantRepository.list();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Restaurant> find(@PathVariable Long id) {
    Restaurant restaurant = restaurantRepository.find(id);

    if (restaurant != null) {
      return ResponseEntity.ok(restaurant);
    }

    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody Restaurant restaurant) {

    try {
      restaurant = restaurantRegistrationService.save(restaurant);

      return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}
