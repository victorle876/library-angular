package com.victor.library2.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.victor.library2.model.entity.Utilisateur;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
public class PretDTO {

    private Long id;

    private Boolean emprunte;

    private Boolean prolonge;

    private Boolean retourne;

    private Integer nombreProlonge;

    private Date createdAt;

    private Date updatedAt;

    private Boolean disponible;

 //   @JsonIgnore
    private UtilisateurDTO utilisateur;
    private ExemplaireDTO exemplaire;

}
