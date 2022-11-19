package com.fortlom.account.domain.MusicAggregate.entity;

import com.fortlom.account.domain.UserAggregate.entity.childentity.Artist;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @NotBlank
    @Size(max = 20)
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 200)
    private String Description;

    @ManyToOne(targetEntity = Artist.class)
    @JoinColumn(name = "artistid")
    private Artist artist;

    @OneToMany(targetEntity = Song.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "albumid",referencedColumnName = "id")
    private List<Song> songs;
}
