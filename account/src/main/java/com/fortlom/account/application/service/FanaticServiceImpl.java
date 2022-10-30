package com.fortlom.account.application.service;


import com.fortlom.account.application.exception.ResourceNotFoundException;
import com.fortlom.account.application.exception.ResourcePerzonalized;
import com.fortlom.account.domain.UserAggregate.entity.Rol;
import com.fortlom.account.domain.UserAggregate.entity.childentity.Fanatic;
import com.fortlom.account.domain.UserAggregate.enumeration.Rolname;
import com.fortlom.account.domain.UserAggregate.repository.FanaticRepository;
import com.fortlom.account.domain.UserAggregate.service.FanaticService;
import com.fortlom.account.domain.UserAggregate.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FanaticServiceImpl implements FanaticService {

    private static final String ENTITY = "Fanatic";

    @Autowired
    private FanaticRepository fanaticRepository;

    @Autowired
    RolService rolService;
    @Override
    public List<Fanatic> getAll() {
        return fanaticRepository.findAll();
    }

    @Override
    public Page<Fanatic> getAll(Pageable pageable) {
        return fanaticRepository.findAll(pageable);
    }

    @Override
    public Fanatic getById(Long fanaticId) {
        return fanaticRepository.findById(fanaticId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, fanaticId));
    }

    @Override
    public Fanatic update(Long fanaticId, Fanatic request) {
        return fanaticRepository.findById(fanaticId).map(dueño ->
                fanaticRepository.save(
                        dueño.withFanaticalias(request.getFanaticalias())
                )
        ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, fanaticId));
    }

    @Override
    public ResponseEntity<?> delete(Long artistId) {
        return fanaticRepository.findById(artistId).map(post -> {
            fanaticRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }

    @Override
    public Fanatic getbyUsername(String Username) {
        return fanaticRepository.findByUsername(Username)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, (long)1));
    }

    @Override
    public Fanatic getbyNameandLastname(String name, String lastname) {
        return fanaticRepository.findByRealnameAndLastname(name,lastname)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, (long)1));
    }

    @Override
    public boolean existsByUsername(String Username) {
        return fanaticRepository.existsByUsername(Username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return fanaticRepository.existsByEmail(email);

    }

    @Override
    public Optional<Fanatic> getbyUsernameOrEmail(String UsernameOremail) {
        return fanaticRepository.findByUsernameOrEmail(UsernameOremail,UsernameOremail);
    }

    @Override
    public Fanatic create(Fanatic artist) {
        if(fanaticRepository.existsByUsername(artist.getUsername()))
            throw  new ResourcePerzonalized("ya exsite este nombre de usuario");
        if (fanaticRepository.existsByEmail(artist.getEmail()))
            throw  new ResourcePerzonalized("ya exsite este correo electronico");

        return fanaticRepository.save(artist);
    }

    @Override
    public Fanatic banFanatic(Long artistId) {
        return fanaticRepository.findById(artistId).map(post->{
            Set<Rol> roles = new HashSet<>();
            roles.add(rolService.findByName(Rolname.Role_Ban_Fanatic).get());
            post.setRoles(roles);
            fanaticRepository.save(post);
            return post;

        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }


    @Override
    public void save(Fanatic fanatic) {
         fanaticRepository.save(fanatic);
    }

    @Override
    public boolean existsfanatic(Long fanaticid) {
        return fanaticRepository.existsById(fanaticid);
    }
}
