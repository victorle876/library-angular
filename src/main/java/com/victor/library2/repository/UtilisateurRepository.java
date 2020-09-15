package com.victor.library2.repository;

import com.victor.library2.model.UtilisateurDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<UtilisateurDTO, Long> {

    Optional<UtilisateurDTO> findByMail(String mail);

}
