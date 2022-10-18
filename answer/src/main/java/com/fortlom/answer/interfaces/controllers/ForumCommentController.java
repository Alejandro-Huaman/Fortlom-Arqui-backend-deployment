package com.fortlom.answer.interfaces.controllers;
import com.fortlom.answer.domain.AnswerAggregate.entity.childentity.ForumComment;
import com.fortlom.answer.domain.AnswerAggregate.service.ForumCommentService;
import com.fortlom.answer.interfaces.dto.ForumComment.CreateForumCommentResource;
import com.fortlom.answer.interfaces.dto.ForumComment.ForumCommentResource;
import com.fortlom.answer.interfaces.mapping.entity.ForumCommentMapper;
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
public class ForumCommentController {

    @Autowired
    private ForumCommentService forumCommentService;

    @Autowired
    private ForumCommentMapper mapper;

    @Autowired
    private ModelMapper mapping;

    @GetMapping("/forumcomments")
    public Page<ForumCommentResource> getAllForumComments(Pageable pageable) {
        return mapper.modelListToPage(forumCommentService.getAll(), pageable);
    }
    @GetMapping("forumcomments/{forumcommentId}")
    public ResponseEntity<ForumCommentResource> getForumCommentById(@PathVariable Long forumcommentId) {

        return ResponseEntity.ok(mapper.toResource(forumCommentService.getById(forumcommentId)));

    }
    @PostMapping("user/{usersId}/forums/{forumId}/forumcomments")
    public ResponseEntity<ForumCommentResource> createForumComment(@PathVariable Long usersId,@PathVariable Long forumId,@RequestBody CreateForumCommentResource request) {
        ForumComment forumComment = mapping.map(request, ForumComment.class);
        return ResponseEntity.ok(mapping.map(forumCommentService.create(usersId, forumId,forumComment), ForumCommentResource.class));
    }
    @GetMapping("forums/{forumId}/forumcomments")
    public ResponseEntity<Page<ForumCommentResource>> getForumCommentByForumId(@PathVariable Long forumId,Pageable pageable) {

        return ResponseEntity.ok(mapper.modelListToPage(forumCommentService.getForumCommentByForumId(forumId), pageable));
    }
    @DeleteMapping("/forumcomments/{forumcommentId}")
    public ResponseEntity<?> delete(@PathVariable Long forumcommentId) {
        return forumCommentService.delete(forumcommentId);
    }
}
