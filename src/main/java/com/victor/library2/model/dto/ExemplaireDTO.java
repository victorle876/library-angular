package com.victor.library2.model.dto;

import com.victor.library2.model.entity.Pret;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Data
public class ExemplaireDTO {

    private Long id;

    private String collection;

    private String editeur ;

    private String description;

    private String isbn;

    private Date createdAt;

    private Date updatedAt;

    private Integer nombre;

    private LivreDTO livreDTO;

    private Date dateParution;

    private Pret pret;

}
