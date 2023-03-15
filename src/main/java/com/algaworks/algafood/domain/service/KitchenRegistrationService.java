package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KitchenRegistrationService {

  @Autowired
  private KitchenRepository kitchenRepository;

  public Kitchen save(Kitchen kitchen){
    return kitchenRepository.save(kitchen);
  }

  public void remove(Kitchen kitchen){
    kitchenRepository.remove(kitchen.getId());
  }
}
