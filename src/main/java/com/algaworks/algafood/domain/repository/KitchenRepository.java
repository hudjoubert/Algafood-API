package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Kitchen;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitchenRepository extends JpaRepository<Kitchen, Long> {
    List<Kitchen> findByName(String name);

}
