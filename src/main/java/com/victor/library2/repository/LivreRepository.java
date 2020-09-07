package com.victor.library2.repository;

import com.victor.library2.model.Livre;
import com.victor.library2.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {
}
