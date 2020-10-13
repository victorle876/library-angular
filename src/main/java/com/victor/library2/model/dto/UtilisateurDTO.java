package com.victor.library2.model.dto;

import com.victor.library2.model.entity.Pret;
import com.victor.library2.model.entity.Role;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UtilisateurDTO {

    private Long id;

    private String username;

    private String prenom;

    private String mail;

    private String password;

    private Integer age;

    private String statut;

    private Date createdAt;

    private Date updatedAt;

    private List<PretDTO> prets;

    private Set<RoleDTO> rolesDTO = new HashSet<>();
}


