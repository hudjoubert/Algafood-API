package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.CityRepository;
import com.algaworks.algafood.domain.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class CityRegistrationService {

  @Autowired
  private CityRepository cityRepository;
  @Autowired
  private StateRepository stateRepository;

  public City save(City city) {
    Long id = city.getState().getId();
    State state = stateRepository.find(id);

    if (ObjectUtils.isEmpty(state)) {
      throw new EntityNotFoundException(
          String.format("Não existe cadastro de cidade com código %d", id));
    }
    city.setState(state);

    return cityRepository.save(city);
  }

  public void remove(Long id) {
    try {
      cityRepository.remove(id);
    } catch (EmptyResultDataAccessException e) {
      throw new EntityNotFoundException("City not found!");
    } catch (DataIntegrityViolationException e) {
      throw new EntityInUseException("City cannot been removed!");
    }
  }

}
