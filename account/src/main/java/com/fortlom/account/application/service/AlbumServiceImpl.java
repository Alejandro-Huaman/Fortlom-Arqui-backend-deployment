package com.fortlom.account.application.service;


import com.fortlom.account.application.exception.ResourceNotFoundException;
import com.fortlom.account.domain.MusicAggregate.entity.Album;
import com.fortlom.account.domain.MusicAggregate.repository.AlbumRepository;
import com.fortlom.account.domain.MusicAggregate.service.AlbumService;
import com.fortlom.account.domain.UserAggregate.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AlbumServiceImpl implements AlbumService {



    private static final String ENTITY2 = "Artist";
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public List<Album> getAlbumsByArtistId(Long artistId) {
        return albumRepository.findByArtistId(artistId);
    }

    @Override
    public Album createAlbum(Long Artist, Album request) {
        return artistRepository.findById(Artist)
                .map(artists -> {
                    request.setArtist(artists);
                    return albumRepository.save(request);
                })
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY2, Artist));
    }

    @Override
    public Album updateAlbum(Long Artist, Album event) {
        return albumRepository.findById(Artist).map(album ->{
            album.setDescription(event.getDescription());
            album.setName(event.getName());
            albumRepository.save(album);
            return album;


        }).orElseThrow(() -> new ResourceNotFoundException("Album", Artist));
    }






}
