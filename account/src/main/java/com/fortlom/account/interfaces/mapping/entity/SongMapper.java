package com.fortlom.account.interfaces.mapping.entity;
import com.fortlom.account.domain.MusicAggregate.entity.Song;
import com.fortlom.account.interfaces.dto.song.CreateSongResource;
import com.fortlom.account.interfaces.dto.song.SongResource;
import com.fortlom.account.interfaces.dto.song.UpdateSongResource;
import com.fortlom.account.interfaces.mapping.configuration.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
public class SongMapper {



    @Autowired
    EnhancedModelMapper mapper;


    public SongResource toResource(Song model) {
        return mapper.map(model, SongResource.class);
    }

    public Page<SongResource> modelListToPage(List<Song> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, SongResource.class), pageable, modelList.size());
    }
    public Song toModel(CreateSongResource resource) {
        return mapper.map(resource, Song.class);
    }

    public Song toModel(UpdateSongResource resource) {
        return mapper.map(resource, Song.class);
    }
















}
