package com.fortlom.report.interfaces.controllers;
import com.fortlom.report.domain.complaintAgregate.entity.Complaint;
import com.fortlom.report.domain.complaintAgregate.service.ComplaintService;
import com.fortlom.report.interfaces.dto.complaint.ComplaintResource;
import com.fortlom.report.interfaces.dto.complaint.CreateComplaintResource;
import com.fortlom.report.interfaces.mapping.entity.ComplaintMapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin
@RequestMapping("/api/v1/reportservice")
public class ComplaintController {


    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private ComplaintMapper mapper;

    @Autowired
    private ModelMapper mapping;


    @GetMapping("/complaints")
    public Page<ComplaintResource> getAllComplaints(Pageable pageable) {
        return mapper.modelListToPage(complaintService.getAll(), pageable);
    }
    @GetMapping("/complaints/{complaintId}")
    public ResponseEntity<ComplaintResource> getById(@PathVariable Long complaintId) {

        return ResponseEntity.ok(mapper.toResource(complaintService.getById(complaintId)));

    }
    @PostMapping("/usersmains/{UserMainId}/usersreports/{UserReportedId}/publications/{publicationId}/complaints")
    public ResponseEntity<ComplaintResource> createforpublication(@PathVariable Long UserMainId, @PathVariable Long UserReportedId, @PathVariable Long publicationId,@RequestBody CreateComplaintResource request) {
        Complaint complaint = mapping.map(request, Complaint.class);
        return ResponseEntity.ok(mapping.map(complaintService.createforpublication(UserMainId, UserReportedId,publicationId ,complaint), ComplaintResource.class));
    }
    @PostMapping("/usersmains/{UserMainId}/usersreports/{UserReportedId}/comments/{commentId}/complaints")
    public ResponseEntity<ComplaintResource> createforcomment(@PathVariable Long UserMainId, @PathVariable Long UserReportedId, @PathVariable Long commentId,@RequestBody CreateComplaintResource request) {
        Complaint complaint = mapping.map(request, Complaint.class);
        return ResponseEntity.ok(mapping.map(complaintService.createforcomment(UserMainId, UserReportedId,commentId ,complaint), ComplaintResource.class));
    }
    @PostMapping("/usersmains/{UserMainId}/usersreports/{UserReportedId}/forums/{forumId}/complaints")
    public ResponseEntity<ComplaintResource> createforforum(@PathVariable Long UserMainId, @PathVariable Long UserReportedId, @PathVariable Long forumId,@RequestBody CreateComplaintResource request) {
        Complaint complaint = mapping.map(request, Complaint.class);
        return ResponseEntity.ok(mapping.map(complaintService.createforforumt(UserMainId, UserReportedId,forumId ,complaint), ComplaintResource.class));
    }
    @GetMapping("/usersmains/{UserMainId}/complaints")
    public Page<ComplaintResource> getAllByMainId(@PathVariable Long UserMainId,Pageable pageable) {
        return mapper.modelListToPage(complaintService.findByUserMainId(UserMainId), pageable);
    }
    @GetMapping("/usersreports/{UserReportedId}/complaints")
    public Page<ComplaintResource> getAllByReportedId(@PathVariable Long UserReportedId,Pageable pageable) {
        return mapper.modelListToPage(complaintService.findByUserReportedId(UserReportedId), pageable);
    }
    @DeleteMapping("/complaints/{complaintId}")
    public ResponseEntity<?> deleteComplaint(@PathVariable Long complaintId) {
        return complaintService.delete(complaintId);
    }
    @GetMapping("/publications/{publicationId}/complaints")
    public Page<ComplaintResource> getAllByPublicationId(@PathVariable Long publicationId,Pageable pageable) {
        return mapper.modelListToPage(complaintService.findByPublicationId(publicationId), pageable);
    }
    @GetMapping("/comments/{commentId}/complaints")
    public Page<ComplaintResource> getAllBycommentId(@PathVariable Long commentId,Pageable pageable) {
        return mapper.modelListToPage(complaintService.findBycommentId(commentId), pageable);
    }
    @GetMapping("/forums/{forumId}/complaints")
    public Page<ComplaintResource> getAllByforumId(@PathVariable Long forumId,Pageable pageable) {
        return mapper.modelListToPage(complaintService.findByforumId(forumId), pageable);
    }



}
