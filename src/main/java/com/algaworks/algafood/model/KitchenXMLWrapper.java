package com.algaworks.algafood.model;

import com.algaworks.algafood.domain.model.Kitchen;
import java.util.List;
import lombok.Data;
import lombok.NonNull;


@Data
public class KitchenXMLWrapper {

  @NonNull
  private List<Kitchen> kitchens;

}
