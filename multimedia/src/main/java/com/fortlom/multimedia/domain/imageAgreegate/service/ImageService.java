package com.fortlom.multimedia.domain.imageAgreegate.service;

import com.fortlom.multimedia.domain.imageAgreegate.entity.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;
public interface ImageService {
    List<Image> getAll();
    Page<Image> getAll(Pageable pageable);
    Image getById(Long ForumId);
    Image createforuser(Long userId,Image image);
    Image createforpublication(Long publicationId,Image image);
    List<Image> getImageByUserId(Long userId);
    List<Image> getImageByPublicationId(Long PublicationId);
    Image createforalbum(Long albumId,Image image);
    List<Image> getImageByAlbumId(Long AlbumId);
    ResponseEntity<?> delete(Long PublicationId);
    boolean exists(Long id);
}
