package com.victor.library2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Livres")
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String titre;

    private String auteur;

    private String categorie;

/*    @ManyToOne
    @JoinColumn(name="exemplaire_id", referencedColumnName = "id")
    private Exemplaire exemplaire;

    @OneToOne
    @JoinColumn(name = "pret_id", referencedColumnName = "id")
    private Pret pret;*/

    @CreatedDate
    @Column(name = "createdAt")
    private Date createdAt;


    @LastModifiedDate
    @Column(name = "updatedAt")
    private Date updatedAt;

    private Date dateParution;

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
