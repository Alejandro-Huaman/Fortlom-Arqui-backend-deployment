package com.fortlom.account.domain.MusicAggregate.entity;
import com.fortlom.account.domain.MusicAggregate.enumeration.Category;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 20)
    private String name;

    @NotNull
    @NotBlank
    private String musicUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(targetEntity = Album.class)
    @JoinColumn(name = "albumid")
    private Album album;











}
