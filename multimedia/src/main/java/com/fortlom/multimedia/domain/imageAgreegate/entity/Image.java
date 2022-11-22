package com.fortlom.multimedia.domain.imageAgreegate.entity;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@With
@AllArgsConstructor
@Table(name="images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String imagenUrl;

    private String imagenId;
    private Long userid;

    private Long publicationid;

    private Long albumid;


}
