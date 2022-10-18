package com.fortlom.answer.application.service;

import com.fortlom.answer.application.exception.ResourceNotFoundException;
import com.fortlom.answer.application.exception.ResourcePerzonalized;
import com.fortlom.answer.domain.AnswerAggregate.entity.childentity.Opinion;
import com.fortlom.answer.domain.AnswerAggregate.repository.OpinionRepository;
import com.fortlom.answer.domain.AnswerAggregate.service.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class OpinionServiceImpl implements OpinionService {


    private static final String ENTITY = "Opinion";

    @Autowired
    private OpinionRepository opinionRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public List<Opinion> getAll() {
        return opinionRepository.findAll();
    }

    @Override
    public Page<Opinion> getAll(Pageable pageable) {
        return opinionRepository.findAll(pageable);
    }

    @Override
    public Opinion getById(Long commentId) {
        return opinionRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(ENTITY, commentId));
    }

    @Override
    public ResponseEntity<?> delete(Long commentId) {
        return opinionRepository.findById(commentId).map(opinion -> {
            opinionRepository.delete(opinion);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, commentId));
    }

    @Override
    public Opinion create(Long Userid, Long contentid,Opinion request) {
        boolean check1 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/check/" + Userid,boolean.class);
        boolean check2 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/fanatics/check/" + Userid,boolean.class);
        if(check1 || check2){
            request.setUserid(Userid);
            request.setContentid(contentid);
            return opinionRepository.save(request);
        }else {
            throw  new ResourcePerzonalized("id inexistente");
        }

    }

    @Override
    public Opinion update(Long commentId, boolean agree) {
        return opinionRepository.findById(commentId).map(opinion -> {
              opinion.setAgree(agree);
              opinionRepository.save(opinion);
              return opinion;
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, commentId));
    }

    @Override
    public List<Opinion> getUserid(Long Userid) {
        boolean check1 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/check/" + Userid,boolean.class);
        boolean check2 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/fanatics/check/" + Userid,boolean.class);
        if(check1 || check2){

            return opinionRepository.findByUserid(Userid);
        }else {
            throw  new ResourcePerzonalized("id inexistente");
        }
    }

    @Override
    public List<Opinion> getContentid(Long contentid) {
        boolean check1 = restTemplate.getForObject("https://fortlom-content.herokuapp.com/api/v1/contentservice/events/check/" + contentid,boolean.class);
        boolean check2 = restTemplate.getForObject("https://fortlom-content.herokuapp.com/api/v1/contentservice/publications/check/" + contentid,boolean.class);
        if(check1 || check2){

            return opinionRepository.findByUserid(contentid);
        }else {
            throw  new ResourcePerzonalized("id inexistente");
        }
    }

    @Override
    public List<Opinion> getByAgree(boolean agree) {
        return opinionRepository.findByAgree(agree);
    }

    @Override
    public List<Opinion> getByContentidAndAgree(Long contentid, boolean agree) {
        return opinionRepository.findByContentidAndAgree(contentid,agree);
    }

    @Override
    public List<Opinion> findByUseridAndContentid(Long Userid, Long contentid) {
        return opinionRepository.findByUseridAndContentid(Userid,contentid);
    }

    @Override
    public boolean existsByContentidAndUserid(Long contentid, Long Userid) {
        return opinionRepository.existsByContentidAndUserid(contentid,Userid);
    }
}
