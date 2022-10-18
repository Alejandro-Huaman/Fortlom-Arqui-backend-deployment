package com.fortlom.answer.interfaces.controllers;

import com.fortlom.answer.domain.AnswerAggregate.entity.childentity.Opinion;
import com.fortlom.answer.domain.AnswerAggregate.service.OpinionService;
import com.fortlom.answer.interfaces.dto.Opinion.CreateOpinionResource;
import com.fortlom.answer.interfaces.dto.Opinion.OpinionResource;
import com.fortlom.answer.interfaces.mapping.entity.OpinionMapper;
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
public class OpinionController {

    @Autowired
    private OpinionService opinionService;

    @Autowired
    private OpinionMapper mapper;

    @Autowired
    private ModelMapper mapping;

    @GetMapping("/opinions")
    public Page<OpinionResource> getAllOpinions(Pageable pageable) {
        return mapper.modelListToPage(opinionService.getAll(), pageable);
    }
    @GetMapping("/opinions/{opinionId}")
    public ResponseEntity<OpinionResource> getOpinionById(@PathVariable Long opinionId) {

        return ResponseEntity.ok(mapper.toResource(opinionService.getById(opinionId)));

    }
    @PostMapping("/user/{usersId}/content/{contentid}/opinions")
    public ResponseEntity<OpinionResource> createOpinion(@PathVariable Long usersId,@PathVariable Long contentid,@RequestBody CreateOpinionResource request) {
        Opinion opinion = mapping.map(request, Opinion.class);
        return ResponseEntity.ok(mapping.map(opinionService.create(usersId, contentid,opinion), OpinionResource.class));
    }
    @DeleteMapping("/opinions/{opinionId}")
    public ResponseEntity<?> deleteOpinion(@PathVariable Long opinionId) {
        return opinionService.delete(opinionId);
    }

    @GetMapping("/content/{contentid}/opinions")
    public ResponseEntity<Page<OpinionResource>> getAllOpinionsBycontentId(@PathVariable Long contentid,Pageable pageable) {

        return ResponseEntity.ok(mapper.modelListToPage(opinionService.getContentid(contentid), pageable));
    }

    @GetMapping("/users/{userid}/opinions")
    public ResponseEntity<Page<OpinionResource>> getAllOpinionsByuserId(@PathVariable Long userid,Pageable pageable) {

        return ResponseEntity.ok(mapper.modelListToPage(opinionService.getUserid(userid), pageable));
    }

    @GetMapping("/agreess/{boolagree}/opinions")
    public ResponseEntity<Page<OpinionResource>> getAllOpinionsByagree(@PathVariable boolean boolagree,Pageable pageable) {

        return ResponseEntity.ok(mapper.modelListToPage(opinionService.getByAgree(boolagree), pageable));
    }


    @GetMapping("/content/{contentid}/agreess/{boolagree}/opinions")
    public ResponseEntity<Page<OpinionResource>> getAllOpinionsByagreeandContentId(@PathVariable Long contentid,@PathVariable boolean boolagree,Pageable pageable) {

        return ResponseEntity.ok(mapper.modelListToPage(opinionService.getByContentidAndAgree(contentid,boolagree), pageable));
    }
    @GetMapping("/check/{contentid}/{Userid}")
    public boolean existsbycontentIdandUserId(@PathVariable("contentid") Long contentid,@PathVariable("Userid") Long Userid){
        return opinionService.existsByContentidAndUserid(contentid,Userid);
    }
    @GetMapping("/getby/{contentid}/{Userid}")
    public ResponseEntity<Page<OpinionResource>> getbycontentanduserid(@PathVariable("contentid") Long contentid,@PathVariable("Userid") Long Userid,Pageable pageable){

        return ResponseEntity.ok(mapper.modelListToPage(opinionService.findByUseridAndContentid(Userid,contentid), pageable));
    }
    @PutMapping("/update/{opinionId}/agree/{boolagree}")
    public OpinionResource updateOpinion(@PathVariable Long opinionId, @PathVariable boolean boolagree) {
        return mapper.toResource(opinionService.update(opinionId,boolagree));
    }

}
