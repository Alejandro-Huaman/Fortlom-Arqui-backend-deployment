package com.fortlom.administration.domain.adminAgreegate.service;

import com.fortlom.administration.domain.adminAgreegate.entity.Rol;
import com.fortlom.administration.domain.adminAgreegate.enumeration.Rolname;

import java.util.List;
import java.util.Optional;
public interface RolService {
    Optional<Rol> findByName(Rolname name);

    void seed();

    List<Rol> getAll();
}

