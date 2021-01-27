package com.victor.library2.model.dto;

import com.fasterxml.jackson.annotation.*;
import com.victor.library2.model.entity.Exemplaire;
import com.victor.library2.model.entity.Livre;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.modelmapper.internal.bytebuddy.build.ToStringPlugin;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

//@Data
@Getter
@Setter
public class LivreDTO {
    private Long id;

    private String titre;

    private String auteur;

    private String categorie;

    @JsonProperty("createdAt")
    private Date createdAt;

    @JsonProperty("updatedAt")
    private Date updatedAt;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy="livre")
    @JsonManagedReference
    private List<ExemplaireDTO> exemplaires;

}
