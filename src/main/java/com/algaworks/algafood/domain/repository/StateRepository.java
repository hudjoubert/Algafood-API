package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.State;

import java.util.List;

public interface StateRepository {

    List<State> list();
    State find(Long id);
    State save(State state);
    void remove(State state);
}
