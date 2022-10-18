package com.fortlom.forum.application.service;
import com.fortlom.forum.application.exception.ResourceNotFoundException;
import com.fortlom.forum.application.exception.ResourcePerzonalized;
import com.fortlom.forum.domain.ForumAggregate.entity.Forum;
import com.fortlom.forum.domain.ForumAggregate.persistence.ForumRepository;
import com.fortlom.forum.domain.ForumAggregate.service.ForumService;
import com.fortlom.forum.domain.ForumAggregate.valueobject.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.Validator;
import java.util.List;

@Service
public class ForumServiceImpl implements ForumService {


    private static final String ENTITY = "Forum";

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Forum> getAllForums() {
        List<Forum>forums=forumRepository.findAll();
        for (Forum forum:forums){
            UserAccount userAccount= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+forum.getUserid(),UserAccount.class);
            forum.setUserAccount(userAccount);
        }
        return forums;
    }

    @Override
    public Page<Forum> getAllForums(Pageable pageable) {
        Page<Forum>forums=forumRepository.findAll(pageable);
        for (Forum forum:forums){
            UserAccount userAccount= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+forum.getUserid(),UserAccount.class);
            forum.setUserAccount(userAccount);
        }



        return forums;
    }

    @Override
    public Forum getForumById(Long ForumId) {
        Forum forum=forumRepository.findById(ForumId).orElseThrow(() -> new ResourceNotFoundException(ENTITY, ForumId));
        UserAccount userAccount= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+forum.getUserid(),UserAccount.class);
        forum.setUserAccount(userAccount);
        return forum;
    }

    @Override
    public Forum createForum(Long userId, Forum request) {

        boolean check1 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/check/" + userId,boolean.class);
        boolean check2 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/fanatics/check/" + userId,boolean.class);

        if(check1||check2) {
            request.setUserid(userId);
            return forumRepository.save(request);
        }else {
            throw  new ResourcePerzonalized("id inexistente");

        }
    }

    @Override
    public List<Forum> getForumsByUserId(Long userId) {
        boolean check1 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/check/" + userId,boolean.class);
        boolean check2 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/fanatics/check/" + userId,boolean.class);

        if(check1||check2) {
             List<Forum>forums=forumRepository.findByUserid(userId);
             for (Forum forum:forums){
                UserAccount userAccount= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+forum.getUserid(),UserAccount.class);
                forum.setUserAccount(userAccount);
             }
             return forums;
        }else {
            throw  new ResourcePerzonalized("id inexistente");

        }

    }

    @Override
    public ResponseEntity<?> deleteForum(Long forumId) {
        return forumRepository.findById(forumId).map(Forum -> {
            forumRepository.delete(Forum);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, forumId));
    }

    @Override
    public boolean existsforum(Long forumId) {
        return forumRepository.existsById(forumId);
    }

    @Override
    public Forum UpdateRules(Long forumId,Forum request) {
        return forumRepository.findById(forumId).map(post->{
            post.setForumrules(request.getForumrules());
            forumRepository.save(post);
            return  post;
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, forumId));
    }
}
