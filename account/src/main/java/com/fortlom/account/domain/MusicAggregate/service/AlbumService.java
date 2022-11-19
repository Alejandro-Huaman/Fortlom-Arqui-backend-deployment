package com.fortlom.account.domain.MusicAggregate.service;

import com.fortlom.account.domain.MusicAggregate.entity.Album;

import java.util.List;

public interface AlbumService {
    List<Album> getAlbumsByArtistId(Long artistId);
    Album createAlbum(Long Artist,Album event);
    Album updateAlbum(Long Artist,Album event);
}
