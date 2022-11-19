package com.fortlom.account.domain.MusicAggregate.service;

import com.fortlom.account.domain.MusicAggregate.entity.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AlbumService {
    List<Album> getAlbumsByArtistId(Long artistId);
    Album createAlbum(Long Artist,Album event);
    Album updateAlbum(Long Artist,Album event);
    List<Album> getall();
    Page<Album> getall(Pageable pageable);
}
