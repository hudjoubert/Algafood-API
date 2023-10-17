package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class StateRegistrationService {

  @Autowired
  private StateRepository stateRepository;

  public State save(State state){

    return stateRepository.save(state);

  }

  public void remove(Long id) {
    try {
      stateRepository.remove(id);
    } catch (EmptyResultDataAccessException e ) {
      throw new EntityNotFoundException("State not found!");
    } catch (DataIntegrityViolationException e) {
      throw new EntityInUseException("State cannot been removed!");
    }
  }

}
