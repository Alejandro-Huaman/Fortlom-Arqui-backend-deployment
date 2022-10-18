package com.example.support.interfaces.controllers;
import com.example.support.domain.followAgreegate.entity.Follow;
import com.example.support.domain.followAgreegate.service.FollowService;

import com.example.support.interfaces.dto.Follow.FollowResource;
import com.example.support.interfaces.dto.Rate.RateResource;
import com.example.support.interfaces.mapping.entity.FollowMapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin
@RequestMapping("/api/v1/supportservice")
public class FollowController {

    @Autowired
    private FollowService followService;

    @Autowired
    private FollowMapper mapper;

    @Autowired
    private ModelMapper mapping;


    @GetMapping("/follows")
    public Page<FollowResource> getAllfollows(Pageable pageable) {
        return mapper.modelListToPage(followService.getAllForums(), pageable);
    }
    @GetMapping("/follows/{followId}")
    public ResponseEntity<FollowResource> getFollowById(@PathVariable Long followId) {

        return ResponseEntity.ok(mapper.toResource(followService.getFollowById(followId)));

    }
    @GetMapping("/artists/{artistId}/follows")
    public ResponseEntity<Page<FollowResource>> getFollowByartistId(@PathVariable Long artistId,Pageable pageable) {

        return ResponseEntity.ok(mapper.modelListToPage(followService.getFollowByartistId(artistId), pageable));
    }

    @PostMapping("/artists/{artistId}/fanatics/{fanaticId}/boolfollow/{followbool}/follows")
    public ResponseEntity<FollowResource> createFollow(@PathVariable Long artistId,@PathVariable Long fanaticId,@PathVariable boolean followbool) {
        return ResponseEntity.ok(mapper.toResource(followService.createFollow(fanaticId,artistId,followbool)));
    }
    @GetMapping("/check/{artistoid}/{agree}")
    public boolean existbyartistoidandagree(@PathVariable("artistoid") Long artistoid,@PathVariable("agree") boolean agree){
        return followService.existsByArtistidAndAgree(artistoid,agree);
    }
    @GetMapping("/check/{artistoid}/fanatics/{fanaticid}")
    public boolean existbyartistoidandfanaticid(@PathVariable("artistoid") Long artistoid,@PathVariable("fanaticid") Long fanaticid){
        return followService.existsByArtistidAndFanaticid(artistoid,fanaticid);
    }
    @GetMapping("/artists/{artistid}/agreess/{boolagree}/opinions")
    public ResponseEntity<Page<FollowResource>> geybyartistoidandboolagree(@PathVariable Long artistid,@PathVariable boolean boolagree,Pageable pageable) {
        return ResponseEntity.ok(mapper.modelListToPage(followService.getByArtistidAndAgree(artistid,boolagree), pageable));
    }
    @GetMapping("/artists/{artistid}/fanatics/{fanaticid}/follows")
    public ResponseEntity<Page<FollowResource>> geybyartistoidandfanaticid(@PathVariable Long artistid,@PathVariable Long fanaticid,Pageable pageable) {
        return ResponseEntity.ok(mapper.modelListToPage(followService.findByArtistidAndFanaticid(artistid,fanaticid), pageable));
    }

    @DeleteMapping("/follows/{followId}")
    public ResponseEntity<?> delete(@PathVariable Long followId) {
        return followService.delete(followId);
    }
    @PutMapping("/update/{followId}/follow/{boolfollow}")
    public FollowResource updateFollow(@PathVariable Long followId, @PathVariable boolean boolfollow) {
        return mapper.toResource(followService.update(followId,boolfollow));
    }
}
