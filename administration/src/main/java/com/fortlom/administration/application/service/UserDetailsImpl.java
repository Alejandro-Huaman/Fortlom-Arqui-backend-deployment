package com.fortlom.administration.application.service;
import com.fortlom.administration.domain.adminAgreegate.entity.Admin;
import com.fortlom.administration.domain.adminAgreegate.service.AdminService;
import com.fortlom.administration.domain.adminAgreegate.valueObject.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsImpl implements UserDetailsService{

    @Autowired
    AdminService adminService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin>value=adminService.getbyNombreUsuario(username);
        if (value.isPresent()){
            return PrincipalUser.build(value.get());
        }
        throw new UsernameNotFoundException("Error");
    }




}
