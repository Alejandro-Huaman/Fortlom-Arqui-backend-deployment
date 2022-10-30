package com.fortlom.report.application.service;

import com.fortlom.report.application.exception.ResourceNotFoundException;
import com.fortlom.report.application.exception.ResourcePerzonalized;
import com.fortlom.report.domain.complaintAgregate.entity.Complaint;
import com.fortlom.report.domain.complaintAgregate.persistence.ComplaintRepository;
import com.fortlom.report.domain.complaintAgregate.service.ComplaintService;
import com.fortlom.report.domain.complaintAgregate.valueobject.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class ComplaintServiceImpl implements ComplaintService {

    private static final String ENTITY = "Complaint";

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public List<Complaint> getAll() {

        List<Complaint>complaints=complaintRepository.findAll();
        for (Complaint complaint:complaints){
            UserAccount userAccountmain= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+complaint.getUserMain(),UserAccount.class);
            UserAccount userAccountreported= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+complaint.getUserReported(),UserAccount.class);

            complaint.setUserAccountMain(userAccountmain);
            complaint.setUserAccountReported(userAccountreported);

        }
        return complaints;
    }

    @Override
    public Page<Complaint> getAll(Pageable pageable) {

        Page<Complaint>complaints=complaintRepository.findAll(pageable);
        for (Complaint complaint:complaints){
            UserAccount userAccountmain= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+complaint.getUserMain(),UserAccount.class);
            UserAccount userAccountreported= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+complaint.getUserReported(),UserAccount.class);

            complaint.setUserAccountMain(userAccountmain);
            complaint.setUserAccountReported(userAccountreported);

        }
        return complaints;
    }

    @Override
    public Complaint getById(Long reportId) {
        Complaint complaint=complaintRepository.findById(reportId).orElseThrow(() -> new ResourceNotFoundException(ENTITY, reportId));
        UserAccount userAccountmain= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+complaint.getUserMain(),UserAccount.class);
        UserAccount userAccountreported= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+complaint.getUserReported(),UserAccount.class);
        complaint.setUserAccountMain(userAccountmain);
        complaint.setUserAccountReported(userAccountreported);
        return complaint;
    }

    @Override
    public Complaint createforpublication(Long UserMainId, Long UserReportedId,Long PublicationId ,Complaint request) {
        boolean check1 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/check/" + UserMainId,boolean.class);
        boolean check2 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/fanatics/check/" + UserMainId,boolean.class);
        boolean check3 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/check/" + UserReportedId,boolean.class);
        boolean check4 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/fanatics/check/" + UserReportedId,boolean.class);
        if((check1||check2)&&(check3||check4)) {
            request.setUserMain(UserMainId);
            request.setUserReported(UserReportedId);
            request.setPublicationId(PublicationId);
            return complaintRepository.save(request);
        }else {
            throw  new ResourcePerzonalized("id inexistente");

        }

    }

    @Override
    public Complaint createforcomment(Long UserMainId, Long UserReportedId, Long CommentId, Complaint request) {
        boolean check1 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/check/" + UserMainId,boolean.class);
        boolean check2 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/fanatics/check/" + UserMainId,boolean.class);
        boolean check3 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/check/" + UserReportedId,boolean.class);
        boolean check4 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/fanatics/check/" + UserReportedId,boolean.class);
        if((check1||check2)&&(check3||check4)) {
            request.setUserMain(UserMainId);
            request.setUserReported(UserReportedId);
            request.setCommentId(CommentId);
            return complaintRepository.save(request);
        }else {
            throw  new ResourcePerzonalized("id inexistente");

        }
    }

    @Override
    public Complaint createforforumt(Long UserMainId, Long UserReportedId, Long ForumId, Complaint request) {
        boolean check1 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/check/" + UserMainId,boolean.class);
        boolean check2 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/fanatics/check/" + UserMainId,boolean.class);
        boolean check3 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/check/" + UserReportedId,boolean.class);
        boolean check4 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/fanatics/check/" + UserReportedId,boolean.class);
        if((check1||check2)&&(check3||check4)) {
            request.setUserMain(UserMainId);
            request.setUserReported(UserReportedId);
            request.setForumId(ForumId);
            return complaintRepository.save(request);
        }else {
            throw  new ResourcePerzonalized("id inexistente");

        }
    }

    @Override
    public List<Complaint> findByUserMainId(Long UserMainId) {


            List<Complaint>complaints=complaintRepository.findByUserMain(UserMainId);
            for (Complaint complaint:complaints){
                UserAccount userAccountmain=     restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+complaint.getUserMain(),UserAccount.class);
                UserAccount userAccountreported= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+complaint.getUserReported(),UserAccount.class);

                complaint.setUserAccountMain(userAccountmain);
                complaint.setUserAccountReported(userAccountreported);

            }
            return complaints;

    }

    @Override
    public List<Complaint> findByUserReportedId(Long UserReportedId) {
        List<Complaint>complaints=complaintRepository.findByUserReported(UserReportedId);
        for (Complaint complaint:complaints){
            UserAccount userAccountmain=     restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+complaint.getUserMain(),UserAccount.class);
            UserAccount userAccountreported= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+complaint.getUserReported(),UserAccount.class);

            complaint.setUserAccountMain(userAccountmain);
            complaint.setUserAccountReported(userAccountreported);

        }
        return complaints;
    }

    @Override
    public List<Complaint> findByPublicationId(Long UserReportedId) {
        List<Complaint>complaints=complaintRepository.findByPublicationId(UserReportedId);
        for (Complaint complaint:complaints){
            UserAccount userAccountmain=     restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+complaint.getUserMain(),UserAccount.class);
            UserAccount userAccountreported= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+complaint.getUserReported(),UserAccount.class);

            complaint.setUserAccountMain(userAccountmain);
            complaint.setUserAccountReported(userAccountreported);

        }
        return complaints;
    }

    @Override
    public List<Complaint> findByforumId(Long UserReportedId) {
        List<Complaint>complaints=complaintRepository.findByForumId(UserReportedId);
        for (Complaint complaint:complaints){
            UserAccount userAccountmain=     restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+complaint.getUserMain(),UserAccount.class);
            UserAccount userAccountreported= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+complaint.getUserReported(),UserAccount.class);

            complaint.setUserAccountMain(userAccountmain);
            complaint.setUserAccountReported(userAccountreported);

        }
        return complaints;
    }

    @Override
    public List<Complaint> findBycommentId(Long UserReportedId) {
        List<Complaint>complaints=complaintRepository.findByCommentId(UserReportedId);
        for (Complaint complaint:complaints){
            UserAccount userAccountmain=     restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+complaint.getUserMain(),UserAccount.class);
            UserAccount userAccountreported= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/users/"+complaint.getUserReported(),UserAccount.class);

            complaint.setUserAccountMain(userAccountmain);
            complaint.setUserAccountReported(userAccountreported);

        }
        return complaints;
    }

    @Override
    public ResponseEntity<?> delete(Long rateId) {
        return complaintRepository.findById(rateId).map(Forum -> {
            complaintRepository.delete(Forum);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, rateId));
    }






}
