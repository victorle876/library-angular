package com.victor.library2.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<Utilisateur> utilisateurs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List <Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List <Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
}
