package com.fortlom.answer.application.service;

import com.fortlom.answer.application.exception.ResourceNotFoundException;
import com.fortlom.answer.application.exception.ResourcePerzonalized;
import com.fortlom.answer.domain.AnswerAggregate.entity.childentity.ForumComment;
import com.fortlom.answer.domain.AnswerAggregate.entity.childentity.PublicationComment;
import com.fortlom.answer.domain.AnswerAggregate.repository.ForumCommentRepository;
import com.fortlom.answer.domain.AnswerAggregate.repository.PublicationCommentRepository;
import com.fortlom.answer.domain.AnswerAggregate.service.PublicationCommentService;
import com.fortlom.answer.domain.AnswerAggregate.valueobject.Forum;
import com.fortlom.answer.domain.AnswerAggregate.valueobject.Publication;
import com.fortlom.answer.domain.AnswerAggregate.valueobject.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PublicationCommentServiceImpl implements PublicationCommentService {
    private static final String ENTITY = "PublicationComment";

    @Autowired
    private PublicationCommentRepository publicationCommentRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<PublicationComment> getAll() {
        List<PublicationComment>publicationComments=publicationCommentRepository.findAll();
        for(PublicationComment publicationComment:publicationComments){
            Publication publication=restTemplate.getForObject("https://fortlom-content.herokuapp.com/api/v1/contentservice/publications/"+publicationComment.getPublicationid(),Publication.class);
            publicationComment.setPublication(publication);


        }
        return publicationComments;
    }

    @Override
    public Page<PublicationComment> getAll(Pageable pageable) {
        Page<PublicationComment>publicationComments=publicationCommentRepository.findAll(pageable);
        for(PublicationComment publicationComment:publicationComments){
            Publication publication=restTemplate.getForObject("https://fortlom-content.herokuapp.com/api/v1/contentservice/publications/"+publicationComment.getPublicationid(),Publication.class);
            publicationComment.setPublication(publication);


        }
        return publicationComments;
    }

    @Override
    public PublicationComment getById(Long commentId) {
        PublicationComment publicationComment= publicationCommentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(ENTITY, commentId));
        Publication publication=restTemplate.getForObject("https://fortlom-content.herokuapp.com/api/v1/contentservice/publications/"+publicationComment.getPublicationid(),Publication.class);
        publicationComment.setPublication(publication);
        return publicationComment;
    }

    @Override
    public PublicationComment create(Long userId, Long publicationId, PublicationComment request) {
        boolean check1 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/check/" + userId,boolean.class);
        boolean check2 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/fanatics/check/" + userId,boolean.class);
        boolean check3 = restTemplate.getForObject("https://fortlom-content.herokuapp.com/api/v1/contentservice/publications/check/" + publicationId,boolean.class);
        if((check1 || check2) && check3) {
            request.setUserid(userId);
            request.setPublicationid(publicationId);
            return publicationCommentRepository.save(request);
        }else {
            throw  new ResourcePerzonalized("id inexistente");

        }


    }

    @Override
    public List<PublicationComment> getCommentByPublicationId(Long publicationId) {
        boolean check = restTemplate.getForObject("https://fortlom-content.herokuapp.com/api/v1/contentservice/publications/check/" + publicationId,boolean.class);
        if(check){
            List<PublicationComment>publicationComments=publicationCommentRepository.findByPublicationid(publicationId);
            for(PublicationComment publicationComment:publicationComments){
                Publication publication=restTemplate.getForObject("https://fortlom-content.herokuapp.com/api/v1/contentservice/publications/"+publicationComment.getPublicationid(),Publication.class);
                UserAccount userAccount=restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+publicationComment.getUserid(),UserAccount.class);
                publicationComment.setUserAccount(userAccount);
                publicationComment.setPublication(publication);



            }
            return publicationComments;
        }else {
            throw  new ResourcePerzonalized("id inexistente");

        }
    }

    @Override
    public ResponseEntity<?> delete(Long commentId) {
        return publicationCommentRepository.findById(commentId).map(Publication -> {
            publicationCommentRepository.delete(Publication);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, commentId));
    }
}
