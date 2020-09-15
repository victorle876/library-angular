package com.victor.library2.service;

import com.victor.library2.model.ExemplaireDTO;
import com.victor.library2.repository.ExemplaireRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExemplaireService {

    @Autowired
    ExemplaireRepository exemplaireRepository;


    private static final Logger logger = LogManager.getLogger(ExemplaireService.class);

    /**
     * Méthode permet de lister toutes les Topos via ce service
     *
     * * @return la liste des topos
     */
    public List<ExemplaireDTO> getAllExemplaires()
    {
        List<ExemplaireDTO> exemplaireList = exemplaireRepository.findAll();
        logger.debug(exemplaireList.size());
        if(exemplaireList.size() > 0) {
            return exemplaireList;
        } else {
            return new ArrayList<ExemplaireDTO>();
        }
    }

    /**
     * Méthode permet de consulter le Stock en fonction de l'id via ce service
     *
     * @param id
     * * @return le stock via id
     */
    public ExemplaireDTO getExemplaireById(Long id)
    {
        return this.exemplaireRepository.findById(id).get();

    }

    /**
     * Méthode permet de sauvegarder le stock via ce service
     *
     * @param exemplaire
     * * @return le stock sauvegardé
     */
    public ExemplaireDTO saveExemplaire(ExemplaireDTO exemplaire)
    {
        return this.exemplaireRepository.save(exemplaire);

    }

    /**
     * Méthode permet d'effacer le stock en fonction de l'id via ce service
     *
     * @param id
     */
    public void deleteStockById(Long id)
    {
        exemplaireRepository.deleteById(id);
    }


}
