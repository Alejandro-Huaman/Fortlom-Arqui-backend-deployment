package com.fortlom.account.domain.UserAggregate.service;

import com.fortlom.account.domain.UserAggregate.entity.childentity.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;
public interface ArtistService {

    List<Artist> getAll();
    Page<Artist> getAll(Pageable pageable);
    Artist getById(Long artistId);
    ResponseEntity<?> delete(Long artistId);
    boolean existsByUsername(String Username);
    Artist update(Long artistId, Artist request);
    boolean existsByEmail(String email);
    void save(Artist artist);
    Artist getbyUsername(String Username);
    Artist getbyNameandLastname(String nombre,String lastname);
    Artist setInstagramAccount(Long artistId,Artist request);
    Artist setFacebookAccount(Long artistId,Artist request);
    Artist setTwitterAccount(Long artistId,Artist request);
    Optional<Artist>getbyUsernameOrEmail(String nameOremail);
    Artist create(Artist artist);
    boolean existsartist(Long artistId);
    boolean ispremium(Long artistId);
    Artist upgradeartist(Long artistId);
    Artist banArtist(Long artistId);
    Artist setAboutMe(Long artistId,Artist request);

}
