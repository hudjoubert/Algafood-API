package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.domain.service.KitchenRegistrationService;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@JsonRootName("kitchen")
@RestController
@RequestMapping("/kitchens")
public class KitchenController {

  @Autowired
  private KitchenRepository kitchenRepository;

  @Autowired
  private KitchenRegistrationService kitchenRegistrationService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Kitchen> list() {
    return kitchenRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Kitchen> find(@PathVariable Long id) {
    Optional<Kitchen> kitchen = kitchenRepository.findById(id);

    if (kitchen.isPresent()) {
      return ResponseEntity.ok(kitchen.get());
    }

    return ResponseEntity.notFound().build();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Kitchen create(@RequestBody Kitchen kitchen) {

    return kitchenRegistrationService.save(kitchen);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Kitchen> update(@PathVariable Long id, @RequestBody Kitchen kitchen) {
    Optional<Kitchen> currentKitchen = kitchenRepository.findById(id);

    if (currentKitchen.isPresent()) {
      BeanUtils.copyProperties(kitchen, currentKitchen.get(), "id");

      Kitchen kitchenSaved = kitchenRegistrationService.save(currentKitchen.get());
      return ResponseEntity.ok(kitchenSaved);
    }

    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Kitchen> delete(@PathVariable Long id) {

    try {
      kitchenRegistrationService.remove(id);

      return ResponseEntity.noContent().build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (EntityInUseException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }
}
