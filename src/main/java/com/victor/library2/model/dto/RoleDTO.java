package com.victor.library2.model.dto;

import com.victor.library2.model.entity.ERole;
import com.victor.library2.model.entity.Utilisateur;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Data
public class RoleDTO {
    private Integer id;

    private ERole name;

/*    @ManyToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude*
    private Set<Utilisateur> utilisateurs = new HashSet<>();*/
}
