package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.StateRepository;
import com.algaworks.algafood.domain.service.StateRegistrationService;
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
@RequestMapping("/states")
public class StateController {

  @Autowired
  private StateRepository stateRepository;

  @Autowired
  private StateRegistrationService stateRegistrationService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<State> list() {
    return stateRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<State> find(@PathVariable Long id) {
    Optional<State> state = stateRepository.findById(id);

    if (state.isPresent()) {
      return ResponseEntity.ok(state.get());
    }

    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody State state) {
    try {
      state = stateRegistrationService.save(state);

      return ResponseEntity.status(HttpStatus.CREATED).body(state);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @RequestBody State state) {
    try {
      Optional<State> currentState = stateRepository.findById(id);

      if (!ObjectUtils.isEmpty(currentState)) {
        BeanUtils.copyProperties(state, currentState.get(), "id");

        State stateSaved = stateRegistrationService.save(currentState.get());
        return ResponseEntity.ok(stateSaved);
      }
      return ResponseEntity.notFound().build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<State> delete(@PathVariable Long id) {
    try {
      stateRegistrationService.remove(id);

      return ResponseEntity.noContent().build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (EntityInUseException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }
}
