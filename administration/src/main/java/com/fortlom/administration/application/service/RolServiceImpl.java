package com.fortlom.administration.application.service;
import com.fortlom.administration.domain.adminAgreegate.entity.Rol;
import com.fortlom.administration.domain.adminAgreegate.enumeration.Rolname;
import com.fortlom.administration.domain.adminAgreegate.persistence.RolRepository;
import com.fortlom.administration.domain.adminAgreegate.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {


    @Autowired
    RolRepository rolRepository;
    private static String[] DEFAULT_ROLES = { "Role_Admin"};


    @Override
    public Optional<Rol> findByName(Rolname name) {
        return rolRepository.findByName(name);
    }

    @Override
    public void seed() {
        Arrays.stream(DEFAULT_ROLES).forEach(name -> {
            Rolname roleName = Rolname.valueOf(name);
            if(!rolRepository.existsByName(roleName)) {
                rolRepository.save((new Rol()).withName(roleName));
            }
        } );
    }

    @Override
    public List<Rol> getAll() {
        return rolRepository.findAll();
    }
}
