package com.fortlom.account.interfaces.controllers;
import com.fortlom.account.domain.MusicAggregate.entity.Album;
import com.fortlom.account.domain.MusicAggregate.service.AlbumService;
import com.fortlom.account.interfaces.dto.album.AlbumResource;
import com.fortlom.account.interfaces.dto.album.CreateAlbumResource;
import com.fortlom.account.interfaces.dto.album.UpdateAlbumResource;
import com.fortlom.account.interfaces.mapping.entity.AlbumMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/userservice/albums")
public class AlbumController {



    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private ModelMapper mapping;


    @PostMapping("/artist/{artistId}/newAlbum")
    public AlbumResource createAlbum(@PathVariable Long artistId, @RequestBody CreateAlbumResource request) {

        Album album = mapping.map(request, Album.class);
        return mapping.map(albumService.createAlbum(artistId, album), AlbumResource.class);
    }
    @GetMapping("/artist/{artistId}/albums")
    public Page<AlbumResource> getAllalbumsByArtistId(@PathVariable Long artistId, Pageable pageable) {
        return albumMapper.modelListToPage(albumService.getAlbumsByArtistId(artistId), pageable);
    }

    @PutMapping("/album/{albumId}/")
    public AlbumResource update(@PathVariable Long albumId, @RequestBody UpdateAlbumResource request){
        return albumMapper.toResource(albumService.updateAlbum(albumId,albumMapper.toModel(request)));
    }
































}
