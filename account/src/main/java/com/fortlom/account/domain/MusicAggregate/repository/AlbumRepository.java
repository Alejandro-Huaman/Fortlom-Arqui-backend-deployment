package com.fortlom.account.domain.MusicAggregate.repository;

import com.fortlom.account.domain.MusicAggregate.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album,Long> {

    List<Album> findByArtistId(Long id);
    List<Album> findByName(String name);





}
