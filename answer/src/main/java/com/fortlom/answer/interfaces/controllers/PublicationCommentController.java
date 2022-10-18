package com.fortlom.answer.interfaces.controllers;



import com.fortlom.answer.domain.AnswerAggregate.entity.childentity.ForumComment;
import com.fortlom.answer.domain.AnswerAggregate.entity.childentity.PublicationComment;
import com.fortlom.answer.domain.AnswerAggregate.service.PublicationCommentService;


import com.fortlom.answer.interfaces.dto.PublicationComment.CreatePublicationCommentResource;
import com.fortlom.answer.interfaces.dto.PublicationComment.PublicationCommentResource;
import com.fortlom.answer.interfaces.mapping.entity.PublicationCommentMapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin
@RequestMapping("/api/v1/answerservice")
public class PublicationCommentController {

    @Autowired
    private PublicationCommentService publicationCommentService;

    @Autowired
    private PublicationCommentMapper mapper;

    @Autowired
    private ModelMapper mapping;

    @GetMapping("/publicationcomments")
    public Page<PublicationCommentResource> getAllPublicationComments(Pageable pageable) {
        return mapper.modelListToPage(publicationCommentService.getAll(), pageable);
    }
    @GetMapping("publicationcomments/{publicationcommentsId}")
    public ResponseEntity<PublicationCommentResource> getPublicationCommentById(@PathVariable Long publicationcommentsId) {

        return ResponseEntity.ok(mapper.toResource(publicationCommentService.getById(publicationcommentsId)));

    }

    @PostMapping("user/{usersId}/publications/{publicationId}/publicationcomments")
    public ResponseEntity<PublicationCommentResource> createpublicationComment(@PathVariable Long usersId, @PathVariable Long publicationId, @RequestBody CreatePublicationCommentResource request) {
        PublicationComment publicationComment = mapping.map(request, PublicationComment.class);
        return ResponseEntity.ok(mapping.map(publicationCommentService.create(usersId, publicationId,publicationComment), PublicationCommentResource.class));
    }
    @GetMapping("publications/{publicationId}/publicationcomments")
    public ResponseEntity<Page<PublicationCommentResource>> getPublicationCommentByPublicationId(@PathVariable Long publicationId,Pageable pageable) {

        return ResponseEntity.ok(mapper.modelListToPage(publicationCommentService.getCommentByPublicationId(publicationId), pageable));
    }
    @DeleteMapping("/publicationcomments/{publicationcommentsId}")
    public ResponseEntity<?> delete(@PathVariable Long publicationcommentsId) {
        return publicationCommentService.delete(publicationcommentsId);
    }
}
