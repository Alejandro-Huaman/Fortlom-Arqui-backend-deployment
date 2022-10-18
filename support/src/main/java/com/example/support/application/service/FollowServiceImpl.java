package com.example.support.application.service;


import com.example.support.application.exception.ResourceNotFoundException;
import com.example.support.application.exception.ResourcePerzonalized;
import com.example.support.domain.followAgreegate.entity.Follow;
import com.example.support.domain.followAgreegate.persistence.FollowRepository;
import com.example.support.domain.followAgreegate.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {


    private static final String ENTITY = "Follow";


    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Follow> getAllForums() {
        return followRepository.findAll();
    }

    @Override
    public Page<Follow> getAllForums(Pageable pageable) {
        return followRepository.findAll(pageable);
    }

    @Override
    public Follow getFollowById(Long FollowId) {

        return followRepository.findById(FollowId).orElseThrow(() -> new ResourceNotFoundException(ENTITY, FollowId));
    }

    @Override
    public Follow createFollow(Long fanaticId,Long artistId,boolean boolfollow) {
         Follow follow = new Follow();
        boolean check1 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/check/" + artistId,boolean.class);
        boolean check2 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/fanatics/check/" + fanaticId,boolean.class);
        if(check1 && check2) {
            follow.setArtistid(artistId);
            follow.setFanaticid(fanaticId);
            follow.setAgree(boolfollow);
            return followRepository.save(follow);
        }else {
            throw  new ResourcePerzonalized("id inexistente");

        }



    }

    @Override
    public Follow update(Long followid, boolean boolfollow) {
        return followRepository.findById(followid).map(opinion -> {
            opinion.setAgree(boolfollow);
            followRepository.save(opinion);
            return opinion;
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, followid));
    }

    @Override
    public List<Follow> getFollowByartistId(Long artistId) {
        return followRepository.findByArtistid(artistId);
    }

    @Override
    public ResponseEntity<?> delete(Long followId) {

        return followRepository.findById(followId).map(Forum -> {
            followRepository.delete(Forum);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, followId));
    }

    @Override
    public boolean existsByArtistidAndAgree(Long artistoid, boolean fanaticId) {
        return followRepository.existsByArtistidAndAgree(artistoid,fanaticId);
    }

    @Override
    public boolean existsByArtistidAndFanaticid(Long artistid, Long fanaticId) {
        return followRepository.existsByArtistidAndFanaticid(artistid,fanaticId);
    }

    @Override
    public List<Follow> getByArtistidAndAgree(Long artistId, boolean fanaticId) {
        return followRepository.findByArtistidAndAgree(artistId,fanaticId);
    }

    @Override
    public List<Follow> findByArtistidAndFanaticid(Long artistid, Long fanaticId) {
        return followRepository.findByArtistidAndFanaticid( artistid,  fanaticId);
    }
}
