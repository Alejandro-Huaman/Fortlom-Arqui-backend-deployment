package com.fortlom.account.interfaces.mapping.entity;
import com.fortlom.account.domain.MusicAggregate.entity.Album;
import com.fortlom.account.interfaces.dto.album.AlbumResource;
import com.fortlom.account.interfaces.dto.album.CreateAlbumResource;
import com.fortlom.account.interfaces.dto.album.UpdateAlbumResource;
import com.fortlom.account.interfaces.mapping.configuration.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
public class AlbumMapper {
    @Autowired
    EnhancedModelMapper mapper;


    public AlbumResource toResource(Album model) {
        return mapper.map(model, AlbumResource.class);
    }

    public Page<AlbumResource> modelListToPage(List<Album> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, AlbumResource.class), pageable, modelList.size());
    }

    public Album toModel(CreateAlbumResource resource) {
        return mapper.map(resource, Album.class);
    }

    public Album toModel(UpdateAlbumResource resource) {
        return mapper.map(resource, Album.class);

    }



}