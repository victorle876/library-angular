package com.victor.library2.repository;

import com.victor.library2.model.entity.Exemplaire;
import com.victor.library2.model.entity.Pret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PretRepository extends JpaRepository<Pret, Long> {
    @Query("select p from Pret p inner join Exemplaire e on e.id = p.exemplaire.id where p.emprunte = true")
    List<Pret> findByExemplaire (String chaineRecherche);
}
