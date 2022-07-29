package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

@RestController
@RequestMapping("/kitchens")
public class KitchenController {

  @Autowired private KitchenRepository kitchenRepository;

  @GetMapping
  public List<Kitchen> list() {
    return kitchenRepository.list();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Kitchen> find(@PathVariable Long id) {
    Kitchen kitchen = kitchenRepository.find(id);

    if (kitchen != null) {
      return ResponseEntity.status(HttpStatus.OK).body(kitchen);
    }

    return ResponseEntity.notFound().build();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Kitchen create(@RequestBody Kitchen kitchen) {
    return kitchenRepository.save(kitchen);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Kitchen> update(@PathVariable Long id, @RequestBody Kitchen kitchen) {
    Kitchen currentKitchen = kitchenRepository.find(id);

    if (currentKitchen != null) {
      BeanUtils.copyProperties(kitchen, currentKitchen, "id");
      kitchenRepository.save(currentKitchen);

      return ResponseEntity.ok(currentKitchen);
    }

    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Kitchen> delete(@PathVariable Long id) {

    try {

      Kitchen kitchen = kitchenRepository.find(id);

      if (kitchen != null) {
        kitchenRepository.remove(kitchen);
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.notFound().build();
    } catch (DataIntegrityViolationException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }
}
