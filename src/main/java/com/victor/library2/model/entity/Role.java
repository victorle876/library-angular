package com.victor.library2.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

/*    @ManyToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Utilisateur> utilisateurs = new HashSet<>();*/

}
