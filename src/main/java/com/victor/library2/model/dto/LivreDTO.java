package com.victor.library2.model.dto;

import com.victor.library2.model.entity.Exemplaire;
import com.victor.library2.model.entity.Livre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LivreDTO {
    private Long id;

    private String titre;

    private String auteur;

    private String categorie;

    private Date createdAt;

    private Date updatedAt;

   // private Date dateParution;

    private List<Exemplaire> exemplaires;

}
