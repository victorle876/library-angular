package com.victor.library2.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
//@Data
@Getter
@Setter
@Table(name = "Pret")
public class Pret {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "exemplaire_id", referencedColumnName = "id")
    private Exemplaire exemplaire;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="utilisateur_id", referencedColumnName = "id")
    @JsonBackReference
    private Utilisateur utilisateur;

    private Boolean emprunte;

    private Boolean prolonge;

    private Boolean retourne;

    private Boolean disponible;

    private Integer nombreProlonge;

    @CreatedDate
    @Column(name = "createdAt")
    private Date createdAt;


    @LastModifiedDate
    @Column(name = "updatedAt")
    private Date updatedAt;

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
