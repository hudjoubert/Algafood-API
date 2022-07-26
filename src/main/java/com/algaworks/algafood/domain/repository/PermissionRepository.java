package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Permission;

import java.util.List;

public interface PermissionRepository {

    List<Permission> list();
    Permission find(Long id);
    Permission save(Permission permission);
    void remove(Permission permission);
}
