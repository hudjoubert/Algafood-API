package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.PaymentMethod;

import java.util.List;

public interface PaymentMethodRepository {

    List<PaymentMethod> list();
    PaymentMethod find(Long id);
    PaymentMethod save(PaymentMethod paymentMethod);
    void remove(PaymentMethod paymentMethod);
}
