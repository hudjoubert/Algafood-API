package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.domain.service.RestaurantRegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    return restaurantRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Restaurant> find(@PathVariable Long id) {
    Optional<Restaurant> restaurant = restaurantRepository.findById(id);

    if (restaurant.isPresent()) {
      return ResponseEntity.ok(restaurant.get());
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

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Restaurant restaurant) {

    try {
      Optional<Restaurant> currentRestaurant = restaurantRepository.findById(id);

      if (currentRestaurant.isPresent()) {
        BeanUtils.copyProperties(restaurant, currentRestaurant.get(), "id");

        Restaurant restaurantSaved = restaurantRegistrationService.save(currentRestaurant.get());
        return ResponseEntity.ok(restaurantSaved);
      }
      return ResponseEntity.notFound().build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> halfUpdate(@PathVariable Long id, @RequestBody Map<String, Object> fields) {

    Optional<Restaurant> currentRestaurant = restaurantRepository.findById(id);

    if (currentRestaurant.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    merge(fields, currentRestaurant.get());

    return update(id, currentRestaurant.get());
  }

  private void merge(Map<String, Object> fields, Restaurant destinationRestaurant) {
    ObjectMapper objectMapper = new ObjectMapper();
    Restaurant originRestaurant = objectMapper.convertValue(fields, Restaurant.class);

    System.out.println(originRestaurant);

    fields.forEach((prop, value) -> {
      Field field = ReflectionUtils.findField(Restaurant.class, prop);
      field.setAccessible(true);

      Object newValue = ReflectionUtils.getField(field, originRestaurant);

      System.out.println(prop + " = " + value + " = " + newValue);

      ReflectionUtils.setField(field, destinationRestaurant, newValue);

    });
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Restaurant> delete(@PathVariable Long id) {
    try {
      restaurantRegistrationService.remove(id);

      return ResponseEntity.noContent().build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (EntityInUseException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }

}
