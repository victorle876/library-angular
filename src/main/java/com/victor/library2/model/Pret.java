package com.victor.library2.model;

import com.sun.istack.NotNull;
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
@Table(name = "Pret")
public class Pret {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

/*    @OneToOne
    @JoinColumn(name = "livre_id", referencedColumnName = "id")
    private Livre livre;

    @ManyToOne
    @JoinColumn(name="utilisateur_id", referencedColumnName = "id")
    private Utilisateur utilisateur;*/

    private Boolean emprunte;

    private Boolean prolonge;

    private Boolean retourne;

    private Integer nombreProlonge;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
