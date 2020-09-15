package com.victor.library2.service;

import com.victor.library2.model.LivreDTO;
import com.victor.library2.repository.LivreRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LivreService {

    @Autowired
    LivreRepository livreRepository;


    private static final Logger logger = LogManager.getLogger(LivreService.class);

    /**
     * Méthode permet de lister toutes les Topos via ce service
     *
     * * @return la liste des topos
     */
    public List<LivreDTO> getAllLivres()
    {
        List<LivreDTO> LivreList = livreRepository.findAll();
        logger.debug(LivreList.size());
        if(LivreList.size() > 0) {
            return LivreList;
        } else {
            return new ArrayList<LivreDTO>();
        }
    }

    /**
     * Méthode permet de consulter le livre en fonction de l'id via ce service
     *
     * @param id
     * * @return le tivre via id
     */
    public LivreDTO getLivreById(Long id)
    {
        return this.livreRepository.findById(id).get();

    }

    /**
     * Méthode permet de sauvegarder le livre via ce service
     *
     * @param livre
     * * @return le topo sauvegardé
     */
    public LivreDTO saveLivre(LivreDTO livre)
    {
        return this.livreRepository.save(livre);

    }

    /**
     * Méthode permet d'effacer le livre en fonction de l'id via ce service
     *
     * @param id
     */
    public void deleteLivreById(Long id)
    {
        livreRepository.deleteById(id);
    }


}
