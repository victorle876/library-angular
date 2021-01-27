package com.victor.library2.model.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "Livres")
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String titre;

    @NotBlank
    private String auteur;

    @NotBlank
    private String categorie;

    @JsonProperty("createdAt")
    @CreatedDate
    @Column(name = "createdAt")
    private Date createdAt;

    @JsonProperty("updatedAt")
    @LastModifiedDate
    @Column(name = "updatedAt")
    private Date updatedAt;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy="livre")
    @JsonManagedReference
    private List<Exemplaire> exemplaires;

    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = new Date();
        if (this.updatedAt == null) updatedAt = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Date();
    }

}
