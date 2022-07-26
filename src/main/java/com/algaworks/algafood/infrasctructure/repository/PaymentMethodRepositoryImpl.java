package com.algaworks.algafood.infrasctructure.repository;

import com.algaworks.algafood.domain.model.PaymentMethod;
import com.algaworks.algafood.domain.repository.PaymentMethodRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class PaymentMethodRepositoryImpl implements PaymentMethodRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<PaymentMethod> list() {
        return manager.createQuery("from PaymentMethod", PaymentMethod.class)
                .getResultList();
    }

    @Override
    public PaymentMethod find(Long id) {
        return manager.find(PaymentMethod.class, id);
    }

    @Transactional
    @Override
    public PaymentMethod save(PaymentMethod paymentMethod) {
        return manager.merge(paymentMethod);
    }

    @Transactional
    @Override
    public void remove(PaymentMethod paymentMethod) {
        paymentMethod = find(paymentMethod.getId());
        manager.remove(paymentMethod);
    }
}
