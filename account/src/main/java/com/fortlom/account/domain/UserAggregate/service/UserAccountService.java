package com.fortlom.account.domain.UserAggregate.service;

import com.fortlom.account.domain.UserAggregate.entity.UserAccount;
import com.fortlom.account.domain.UserAggregate.entity.childentity.Artist;

import java.util.Optional;

public interface UserAccountService {
    Optional<UserAccount> getbyNombreUsuarioOrEmail(String nombreOremail);

    void save(UserAccount usuario);

    Optional<UserAccount> getByTokenPassword(String tokenPassword);
    UserAccount getById(Long userId);
    boolean existsById(Long id);

    UserAccount updateprofile(Long userId, UserAccount request);

    UserAccount updatepassword(Long userId, UserAccount request);

    UserAccount getByUsername(String Username);



}
