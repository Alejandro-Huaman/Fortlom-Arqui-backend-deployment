package com.fortlom.account.application.service;

import com.fortlom.account.application.exception.ResourceNotFoundException;
import com.fortlom.account.domain.MusicAggregate.entity.Song;
import com.fortlom.account.domain.MusicAggregate.repository.AlbumRepository;
import com.fortlom.account.domain.MusicAggregate.repository.SongRepository;
import com.fortlom.account.domain.MusicAggregate.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {
    private static final String ENTITY2 = "Album";
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private SongRepository songRepository;

    @Override
    public List<Song> getSongByAlbumId(Long Album) {
        return songRepository.findByAlbumId(Album);
    }

    @Override
    public Song createSong(Long Album, Song request) {

        return albumRepository.findById(Album)
                .map(album -> {

                    request.setAlbum(album);
                    return songRepository.save(request);
                })
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY2, Album));
    }

    @Override
    public Song updateSong(Long Album, Song event) {
        return songRepository.findById(Album).map(song ->{
            song.setMusicUrl(event.getMusicUrl());
            song.setName(event.getName());
            songRepository.save(song);
            return song;


        }).orElseThrow(() -> new ResourceNotFoundException("Album", Album));
    }
}
