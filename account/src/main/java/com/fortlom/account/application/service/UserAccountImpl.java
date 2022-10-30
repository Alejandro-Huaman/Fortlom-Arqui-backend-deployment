package com.fortlom.account.application.service;
import com.fortlom.account.application.exception.ResourceNotFoundException;
import com.fortlom.account.domain.UserAggregate.entity.UserAccount;
import com.fortlom.account.domain.UserAggregate.repository.UserAccounRepository;
import com.fortlom.account.domain.UserAggregate.service.UserAccountService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.util.*;

@Service
public class UserAccountImpl implements UserAccountService {
    @Autowired
    private UserAccounRepository userAccounRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserAccountImpl.class);

    @Override
    public Optional<UserAccount> getbyNombreUsuarioOrEmail(String nombreOremail) {
        return userAccounRepository.findByUsernameOrEmail(nombreOremail,nombreOremail);
    }

    @Override
    public void save(UserAccount usuario) {
        userAccounRepository.save(usuario);
    }

    @Override
    public Optional<UserAccount> getByTokenPassword(String tokenPassword) {
        return userAccounRepository.findByTokenpassword(tokenPassword);
    }

    @Override
    public UserAccount getById(Long userId) {
        return userAccounRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
    }

    @Override
    public boolean existsById(Long id) {
        return userAccounRepository.existsById(id);
    }

    @Override
    public UserAccount updateprofile(Long userId, UserAccount request) {
        return userAccounRepository.findById(userId).map(user ->{
            user.setRealname(request.getRealname());
            user.setLastname(request.getLastname());
            user.setEmail(request.getEmail());
            userAccounRepository.save(user);
            return user;


        }).orElseThrow(() -> new ResourceNotFoundException("Person", userId));
    }

    @Override
    public UserAccount updatepassword(Long userId, UserAccount request) {
        PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();

        return userAccounRepository.findById(userId).map(user ->{
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userAccounRepository.save(user);
            return user;


        }).orElseThrow(() -> new ResourceNotFoundException("userAccount", userId));
    }

    @Override
    public UserAccount getByUsername(String Username) {
        return userAccounRepository.findByUsername(Username)  .orElseThrow(() -> new ResourceNotFoundException("User", (long)0));
    }


}
