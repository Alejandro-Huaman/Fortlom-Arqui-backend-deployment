package com.fortlom.administration.application.service;
import com.fortlom.administration.application.exception.Message;
import com.fortlom.administration.application.jwt.jwtProvider;
import com.fortlom.administration.domain.adminAgreegate.entity.Admin;
import com.fortlom.administration.domain.adminAgreegate.entity.Rol;
import com.fortlom.administration.domain.adminAgreegate.enumeration.Rolname;
import com.fortlom.administration.domain.adminAgreegate.service.AdminService;
import com.fortlom.administration.domain.adminAgreegate.service.RolService;
import com.fortlom.administration.interfaces.dto.authetication.LoginUser;
import com.fortlom.administration.interfaces.dto.authetication.RegisterUser;
import com.fortlom.administration.interfaces.dto.authetication.jwtDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    jwtProvider jwtProvider;

    @Autowired
    AdminService adminService;

    @Autowired
    RolService rolService;

    public Rol getrole() throws Message {

        Optional<Rol>value=rolService.findByName(Rolname.Role_Admin);
        if(value.isPresent()){
            return value.get();
        }
        throw new Message("Error");
    }

    public Admin register(RegisterUser request, BindingResult bindingResult) throws Message {
        if (bindingResult.hasErrors()){
            throw new Message("campos mal puestos o email invalido");
        }
        if(adminService.existsByUsername(request.getUsername())){
            throw new Message("ya existe nombre");
        }
        Admin admin = new Admin();
        admin.setUsername(request.getUsername());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Rol> roles = new HashSet<>();
        roles.add(getrole());
        admin.setRoles(roles);
        return admin;

    }
    public ResponseEntity<jwtDto>login(LoginUser loginUser, BindingResult bindingResult){

        if(bindingResult.hasErrors())
            return new ResponseEntity(new Message("campos mal puestos"), HttpStatus.BAD_REQUEST);
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getNombreUsuario(), loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        jwtDto jwtDto = new jwtDto(jwt);
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }

}
