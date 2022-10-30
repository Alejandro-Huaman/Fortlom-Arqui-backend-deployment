package com.fortlom.account.application.service;
import com.fortlom.account.domain.UserAggregate.entity.Rol;
import com.fortlom.account.domain.UserAggregate.enumeration.Rolname;
import com.fortlom.account.domain.UserAggregate.repository.RolRepository;
import com.fortlom.account.domain.UserAggregate.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {
    @Autowired
    RolRepository rolRepository;
    private static String[] DEFAULT_ROLES = { "Role_Fanatic","Role_Artist","Role_Upgrade_Artist", "Role_Ban_Fanatic","Role_Ban_Artist","Role_Admin"};
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
