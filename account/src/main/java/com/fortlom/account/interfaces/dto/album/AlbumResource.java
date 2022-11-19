package com.fortlom.account.interfaces.dto.album;

import com.fortlom.account.interfaces.dto.artist.ArtistResource;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AlbumResource {


    private Long id;



    private String name;


    private String Description;


    private ArtistResource artist;

}
