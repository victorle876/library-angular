package com.victor.library2.model.dto;

import com.victor.library2.model.entity.ERole;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class RoleDTO {
    private Integer id;

    private ERole name;
}
