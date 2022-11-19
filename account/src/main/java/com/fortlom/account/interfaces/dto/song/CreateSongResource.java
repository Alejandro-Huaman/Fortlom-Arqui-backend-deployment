package com.fortlom.account.interfaces.dto.song;
import com.fortlom.account.domain.MusicAggregate.enumeration.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSongResource {
    private String name;


    private String musicUrl;



    private Category category;
}
