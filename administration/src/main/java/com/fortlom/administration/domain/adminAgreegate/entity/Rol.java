package com.fortlom.administration.domain.adminAgreegate.entity;

import com.fortlom.administration.domain.adminAgreegate.enumeration.Rolname;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Rolname name;
}

