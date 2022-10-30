package com.fortlom.administration.application.service;

import com.fortlom.administration.application.exception.ResourceNotFoundException;
import com.fortlom.administration.domain.adminAgreegate.entity.Admin;
import com.fortlom.administration.domain.adminAgreegate.persistence.AdminRepository;
import com.fortlom.administration.domain.adminAgreegate.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {


    private static final String ENTITY = "Admin";

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Admin getById(Long adminId) {
        return adminRepository.findById(adminId).orElseThrow(() -> new ResourceNotFoundException(ENTITY, adminId));
    }

    @Override
    public Admin create(Admin admin) {


        return  adminRepository.save(admin);
    }

    @Override
    public Optional<Admin> getbyNombreUsuario(String userName) {
        System.out.println(userName);
        return adminRepository.findByUsername(userName);
    }

    @Override
    public Admin getbyusername(String userName) {
        return adminRepository.findByUsername(userName).get();
    }

    @Override
    public Boolean existsByUsername(String username) {
        return adminRepository.existsByUsername(username);
    }

    @Override
    public void save(Admin admin) {
        adminRepository.save(admin);
    }
}
