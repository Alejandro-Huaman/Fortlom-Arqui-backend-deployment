package com.fortlom.account.domain.MusicAggregate.service;

import com.fortlom.account.domain.MusicAggregate.entity.Song;

import java.util.List;

public interface SongService {

    List<Song> getSongByAlbumId(Long Album);
    Song createSong(Long Album,Song event);
    Song updateSong(Long Album, Song event);
}
