package com.fortlom.account.interfaces.dto.song;
import com.fortlom.account.domain.MusicAggregate.enumeration.Category;
import com.fortlom.account.interfaces.dto.album.AlbumResource;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SongResource {
    private Long id;


    private String name;


    private String musicUrl;



    private Category category;



    private AlbumResource album;
}
