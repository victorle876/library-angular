package com.victor.library2.model.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.victor.library2.model.entity.Pret;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

//@Data
@Getter
@Setter
public class ExemplaireDTO {

    private Long id;

    private String collection;

    private String editeur ;

    private String description;

    private String isbn;

    @JsonProperty("createdAt")
    private Date createdAt;

    @JsonProperty("updatedAt")
    private Date updatedAt;

    private Integer nombre;

    @JoinColumn(name="livre_id", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private LivreDTO livre;

    private Date dateParution;

    private Pret pret;

}
