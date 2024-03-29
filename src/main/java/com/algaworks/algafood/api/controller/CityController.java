package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.repository.CityRepository;
import com.algaworks.algafood.domain.service.CityRegistrationService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cities")
public class CityController {

  @Autowired
  private CityRepository cityRepository;

  @Autowired
  private CityRegistrationService cityRegistrationService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<City> list() {
    return cityRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<City> find(@PathVariable Long id) {
    Optional<City> city = cityRepository.findById(id);

    if (city.isPresent()) {
      return ResponseEntity.ok(city.get());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody City city) {
    try {
      city = cityRegistrationService.save(city);

      return ResponseEntity.status(HttpStatus.CREATED).body(city);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @RequestBody City city) {
    try {
      Optional<City> currentCity = cityRepository.findById(id);

      if (currentCity.isPresent()) {
        BeanUtils.copyProperties(city, currentCity.get(), "id");

        City citySaved = cityRegistrationService.save(currentCity.get());
        return ResponseEntity.ok(citySaved);
      }
      return ResponseEntity.notFound().build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<City> delete(@PathVariable Long id) {
    try {
      cityRegistrationService.remove(id);

      return ResponseEntity.noContent().build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (EntityInUseException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }

}
