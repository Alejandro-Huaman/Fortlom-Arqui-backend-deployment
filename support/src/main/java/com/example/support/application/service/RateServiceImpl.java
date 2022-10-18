package com.example.support.application.service;

import com.example.support.application.exception.ResourceNotFoundException;
import com.example.support.application.exception.ResourcePerzonalized;
import com.example.support.domain.followAgreegate.persistence.FollowRepository;
import com.example.support.domain.rateAgreegate.entity.Rate;
import com.example.support.domain.rateAgreegate.persistence.RateRepository;
import com.example.support.domain.rateAgreegate.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class RateServiceImpl implements RateService {


    private static final String ENTITY = "Follow";

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Rate> getAllRates() {
        return rateRepository.findAll();
    }

    @Override
    public Page<Rate> getAllRates(Pageable pageable) {
        return rateRepository.findAll(pageable);
    }

    @Override
    public Rate getRateById(Long rateId) {
        return rateRepository.findById(rateId).orElseThrow(() -> new ResourceNotFoundException(ENTITY, rateId));
    }

    @Override
    public Rate createRate(Long artistId,Long fanaticId, Rate request) {
        boolean check1 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/check/" + artistId,boolean.class);
        boolean check2 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/fanatics/check/" + fanaticId,boolean.class);
        if(check1 && check2) {
            request.setArtistid(artistId);
            request.setFanaticid(fanaticId);
            return rateRepository.save(request);
        }else {
            throw  new ResourcePerzonalized("id inexistente");

        }

    }

    @Override
    public List<Rate> getRateByartistId(Long artistId) {
        return rateRepository.findByArtistid(artistId);
    }

    @Override
    public List<Rate> getRateByfanaticId(Long fanaticId) {
        return rateRepository.findByFanaticid(fanaticId);
    }

    @Override
    public ResponseEntity<?> delete(Long rateId) {
        return rateRepository.findById(rateId).map(Forum -> {
            rateRepository.delete(Forum);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, rateId));
    }

    @Override
    public boolean existsByArtistidAndFanaticid(Long artistid, Long fanaticId) {
        return rateRepository.existsByArtistidAndFanaticid(artistid,fanaticId);
    }

    @Override
    public Rate update(Long rateId, Rate review) {
        return rateRepository.findById(rateId).map(opinion -> {
            opinion.setReview(review.getReview());
            rateRepository.save(opinion);
            return opinion;
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, rateId));
    }

    @Override
    public List<Rate> findByArtistidAndFanaticid(Long artistid, Long fanaticId) {
        return rateRepository.findByArtistidAndFanaticid(artistid,fanaticId);
    }
}
