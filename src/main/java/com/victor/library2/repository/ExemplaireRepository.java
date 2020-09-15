package com.victor.library2.repository;

import com.victor.library2.model.ExemplaireDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExemplaireRepository extends JpaRepository<ExemplaireDTO, Long> {
}
