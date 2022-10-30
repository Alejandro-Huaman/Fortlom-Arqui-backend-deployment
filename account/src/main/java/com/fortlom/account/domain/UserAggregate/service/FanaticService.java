package com.fortlom.account.domain.UserAggregate.service;


import com.fortlom.account.domain.UserAggregate.entity.childentity.Fanatic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
public interface FanaticService {
    List<Fanatic> getAll();
    Page<Fanatic> getAll(Pageable pageable);
    Fanatic getById(Long artistId);
    Fanatic update(Long artistId, Fanatic request);
    ResponseEntity<?> delete(Long artistId);
    Fanatic getbyUsername(String Username);
    Fanatic getbyNameandLastname(String nombre,String lastname);
    boolean existsByUsername(String Username);
    boolean existsByEmail(String email);
    Optional<Fanatic>getbyUsernameOrEmail(String UsernameOremail);
    Fanatic create(Fanatic artist);
    Fanatic banFanatic(Long artistId);
    void save(Fanatic fanatic);
    boolean existsfanatic(Long fanaticid);
}
