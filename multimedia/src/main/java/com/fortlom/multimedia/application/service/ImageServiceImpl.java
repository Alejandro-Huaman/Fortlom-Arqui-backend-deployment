package com.fortlom.multimedia.application.service;

import com.fortlom.multimedia.application.exception.ResourceNotFoundException;
import com.fortlom.multimedia.application.exception.ResourcePerzonalized;
import com.fortlom.multimedia.domain.imageAgreegate.entity.Image;
import com.fortlom.multimedia.domain.imageAgreegate.persistence.ImageRepository;
import com.fortlom.multimedia.domain.imageAgreegate.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class ImageServiceImpl implements ImageService {


    private static final String ENTITY = "Image";
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public List<Image> getAll() {
        return imageRepository.findAll();
    }

    @Override
    public Page<Image> getAll(Pageable pageable) {
        return imageRepository.findAll(pageable);
    }

    @Override
    public Image getById(Long ImageID) {
        return imageRepository.findById(ImageID)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, ImageID));
    }

    @Override
    public Image createforuser(Long userId, Image request) {
        boolean check1 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/check/" + userId,boolean.class);
        boolean check2 = restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/fanatics/check/" + userId,boolean.class);
        if(check1||check2) {
            request.setUserid(userId);
            return imageRepository.save(request);
        }else {
            throw  new ResourcePerzonalized("id inexistente");

        }


    }

    @Override
    public Image createforpublication(Long publicationId, Image request) {
        boolean check = restTemplate.getForObject("https://fortlom-content.herokuapp.com/api/v1/contentservice/publications/check/" + publicationId,boolean.class);
       if(check){
           request.setPublicationid(publicationId);
           return imageRepository.save(request);
       }else{
           throw  new ResourcePerzonalized("id inexistente");
       }


    }

    @Override
    public List<Image> getImageByUserId(Long userId) {
        return imageRepository.findByUserid(userId);
    }

    @Override
    public List<Image> getImageByPublicationId(Long PublicationId) {
        return imageRepository.findByPublicationid(PublicationId);
    }

    @Override
    public Image createforalbum(Long albumId, Image image) {
        image.setAlbumid(albumId);
        return imageRepository.save(image);
    }

    @Override
    public List<Image> getImageByAlbumId(Long AlbumId) {
        return imageRepository.findByAlbumid(AlbumId);
    }

    @Override
    public ResponseEntity<?> delete(Long PublicationId) {
        return imageRepository.findById(PublicationId).map(Publication -> {
            imageRepository.delete(Publication);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, PublicationId));
    }

    @Override
    public boolean exists(Long id) {
        return imageRepository.existsById(id);
    }
}
