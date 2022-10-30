package com.fortlom.administration.domain.adminAgreegate.service;


import com.fortlom.administration.domain.adminAgreegate.entity.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    Admin getById(Long adminId);
    Admin create(Admin admin);
    Optional<Admin> getbyNombreUsuario(String userName);
    Admin getbyusername(String userName);
    Boolean existsByUsername(String username);
     void save(Admin admin);
}
