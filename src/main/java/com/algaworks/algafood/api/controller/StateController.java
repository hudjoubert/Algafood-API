package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.StateRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/states")
public class StateController {

    @Autowired
    private StateRepository stateRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<State> list() { return stateRepository.list(); }

    @GetMapping("/{id}")
    public ResponseEntity<State> find(@PathVariable Long id) {
        State state = stateRepository.find(id);

        if (state != null) {
            return ResponseEntity.ok(state);
        }

        return ResponseEntity.notFound().build();
    }


}
