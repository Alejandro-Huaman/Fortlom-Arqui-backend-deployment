package com.fortlom.account.interfaces.controllers;
import com.fortlom.account.domain.MusicAggregate.entity.Song;
import com.fortlom.account.domain.MusicAggregate.service.SongService;
import com.fortlom.account.interfaces.dto.song.CreateSongResource;
import com.fortlom.account.interfaces.dto.song.SongResource;
import com.fortlom.account.interfaces.dto.song.UpdateSongResource;
import com.fortlom.account.interfaces.mapping.entity.SongMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/userservice/songs")
public class SongController {




    @Autowired
    private SongService songService;

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private ModelMapper mapping;



    @PostMapping("/album/{albumId}/newSong")
    public SongResource createSong(@PathVariable Long albumId, @RequestBody CreateSongResource request) {

        Song song = mapping.map(request, Song.class);
        return mapping.map(songService.createSong(albumId, song), SongResource.class);
    }
    @GetMapping("/album/{albumId}/songs")
    public Page<SongResource> getAllsongByAlbumId(@PathVariable Long albumId, Pageable pageable) {
        return songMapper.modelListToPage(songService.getSongByAlbumId(albumId), pageable);
    }

    @PutMapping("/song/{songId}/")
    public SongResource update(@PathVariable Long songId, @RequestBody UpdateSongResource request){
        return songMapper.toResource(songService.updateSong(songId,songMapper.toModel(request)));
    }


















}
