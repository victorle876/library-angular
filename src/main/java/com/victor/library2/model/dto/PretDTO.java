package com.victor.library2.model.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.victor.library2.model.entity.Utilisateur;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

//@Data
@Getter
@Setter
public class PretDTO {

    private Long id;

    private Boolean emprunte;

    private Boolean prolonge;

    private Boolean retourne;

    private Integer nombreProlonge;

    private Date createdAt;

    private Date updatedAt;

    private Boolean disponible;

//    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    private UtilisateurDTO utilisateur;

    private ExemplaireDTO exemplaire;

}
