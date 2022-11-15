package com.fortlom.account.domain.UserAggregate.entity.childentity;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fortlom.account.domain.UserAggregate.entity.Tag;
import com.fortlom.account.domain.UserAggregate.entity.UserAccount;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@With
@AllArgsConstructor
@Table(name="artists")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Artist extends UserAccount {

    @NotNull
    private Long artistfollowers;

    private String instagramLink;

    private String facebookLink;

    private String twitterLink;

    private String aboutMe;

    @OneToMany(targetEntity = Tag.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "artistid",referencedColumnName = "id")
    private List<Tag> tags;
}
