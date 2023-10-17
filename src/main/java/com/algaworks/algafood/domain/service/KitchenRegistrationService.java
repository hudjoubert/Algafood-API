package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class KitchenRegistrationService {

  @Autowired
  private KitchenRepository kitchenRepository;

  public Kitchen save(Kitchen kitchen) {
    return kitchenRepository.save(kitchen);
  }

  public void remove(Long id) {
    try {
      kitchenRepository.remove(id);

    } catch (EmptyResultDataAccessException e ) {
      throw new EntityNotFoundException("Kitchen not found!");

    } catch (DataIntegrityViolationException e) {
      throw new EntityInUseException("Kitchen cannot been removed!");
    }
  }
}
