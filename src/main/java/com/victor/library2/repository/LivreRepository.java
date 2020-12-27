package com.victor.library2.repository;

import com.victor.library2.model.entity.Livre;
import com.victor.library2.model.entity.Pret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {
    List<Livre> findByAuteurIsContaining(String Auteur);
    List<Livre> findByTitreIgnoreCaseContaining(String titre);
    List<Livre> findByTitreIgnoreCaseContainingOrAuteurIsContaining(String titre,String Auteur);
}
