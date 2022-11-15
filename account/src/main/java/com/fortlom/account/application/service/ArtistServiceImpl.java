package com.fortlom.account.application.service;


import com.fortlom.account.application.exception.ResourceNotFoundException;
import com.fortlom.account.application.exception.ResourcePerzonalized;
import com.fortlom.account.domain.UserAggregate.entity.Rol;
import com.fortlom.account.domain.UserAggregate.entity.childentity.Artist;
import com.fortlom.account.domain.UserAggregate.enumeration.Rolname;
import com.fortlom.account.domain.UserAggregate.repository.ArtistRepository;
import com.fortlom.account.domain.UserAggregate.service.ArtistService;
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
public class ArtistServiceImpl implements ArtistService {
    private static final String ENTITY = "Artist";
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    RolService rolService;
    @Override
    public List<Artist> getAll() {
        return artistRepository.findAll();
    }

    @Override
    public Page<Artist> getAll(Pageable pageable) {
        return artistRepository.findAll(pageable);
    }

    @Override
    public Artist getById(Long artistId) {
        return artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }

    @Override
    public ResponseEntity<?> delete(Long artistId) {
        return artistRepository.findById(artistId).map(post -> {
            artistRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }

    @Override
    public boolean existsByUsername(String Username) {
        return artistRepository.existsByUsername(Username);
    }

    @Override
    public Artist update(Long artistId, Artist request) {
        return artistRepository.findById(artistId).map(post->{
            post.setArtistfollowers(request.getArtistfollowers());
            artistRepository.save(post);
            return  post;
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }

    @Override
    public boolean existsByEmail(String email) {
        return artistRepository.existsByEmail(email);
    }

    @Override
    public void save(Artist artist) {
        artistRepository.save(artist);
    }

    @Override
    public Artist getbyUsername(String Username) {
        return artistRepository.findByUsername(Username)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, (long)1));
    }

    @Override
    public Artist getbyNameandLastname(String nombre, String lastname) {
        return artistRepository.findByRealnameAndLastname(nombre,lastname)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, (long)1));
    }

    @Override
    public Artist setInstagramAccount(Long artistId, Artist request) {
        return artistRepository.findById(artistId).map(post->{
            post.setInstagramLink(request.getInstagramLink());
            artistRepository.save(post);
            return  post;
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }

    @Override
    public Artist setFacebookAccount(Long artistId, Artist request) {
        return artistRepository.findById(artistId).map(post->{
            post.setFacebookLink(request.getFacebookLink());
            artistRepository.save(post);
            return  post;
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }

    @Override
    public Artist setTwitterAccount(Long artistId, Artist request) {
        return artistRepository.findById(artistId).map(post->{
            post.setTwitterLink(request.getTwitterLink());
            artistRepository.save(post);
            return  post;
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }

    @Override
    public Optional<Artist> getbyUsernameOrEmail(String nameOremail) {
        return artistRepository.findByUsernameOrEmail(nameOremail,nameOremail);
    }

    @Override
    public Artist create(Artist artist) {
        if(artistRepository.existsByUsername(artist.getUsername()))
            throw  new ResourcePerzonalized("ya exsite este nombre de usuario");
        if (artistRepository.existsByEmail(artist.getEmail()))
            throw  new ResourcePerzonalized("ya exsite este correo electronico");

        return artistRepository.save(artist);
    }

    @Override
    public boolean existsartist(Long artistId) {
        return artistRepository.existsById(artistId);
    }

    @Override
    public boolean ispremium(Long artistId) {
        return artistRepository.findById(artistId).map(post->{

            for (Rol rol:post.getRoles()){
                String r=rol.getName().name();

                if (r.equals("Role_Upgrade_Artist")){
                    return true;
                }
            }
            return false;


        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }

    @Override
    public Artist upgradeartist(Long artistId) {
        return artistRepository.findById(artistId).map(post->{
            Set<Rol> roles = new HashSet<>();
            roles.add(rolService.findByName(Rolname.Role_Upgrade_Artist).get());
            post.setRoles(roles);
            artistRepository.save(post);
            return post;

        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }

    @Override
    public Artist banArtist(Long artistId) {
        return artistRepository.findById(artistId).map(post->{
            Set<Rol> roles = new HashSet<>();
            roles.add(rolService.findByName(Rolname.Role_Ban_Artist).get());
            post.setRoles(roles);
            artistRepository.save(post);
            return post;

        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }

    @Override
    public Artist setAboutMe(Long artistId, Artist request) {
        return artistRepository.findById(artistId).map(post->{
            post.setAboutMe(request.getAboutMe());
            artistRepository.save(post);
            return  post;
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }
}
