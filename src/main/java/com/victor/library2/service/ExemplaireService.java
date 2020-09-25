package com.victor.library2.service;

import com.victor.library2.model.*;
import com.victor.library2.repository.ExemplaireRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExemplaireService {

    private ExemplaireDTO mapExemplaireToExemplaireDTO(Exemplaire exemplaire) {
        ModelMapper mapper = new ModelMapper();
        ExemplaireDTO exemplaireDTO = mapper.map(exemplaire, ExemplaireDTO.class);
        return exemplaireDTO;
    }

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
        List<Exemplaire> exemplaireList = exemplaireRepository.findAll();
        logger.debug(exemplaireList.size());
        if(exemplaireList.size() > 0) {
            return exemplaireList.stream()
                    .map(this::mapExemplaireToExemplaireDTO)
                    .collect(Collectors.toList());
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
        Exemplaire exemplaireId= this.exemplaireRepository.findById(id).get();
        return mapExemplaireToExemplaireDTO(exemplaireId);

    }

    /**
     * Méthode permet de sauvegarder le stock via ce service
     *
     * @param exemplaire
     * * @return le stock sauvegardé
     */
    public ExemplaireDTO saveExemplaire(Exemplaire exemplaire)
    {
        Exemplaire exemplaireSauve= this.exemplaireRepository.save(exemplaire);
        return mapExemplaireToExemplaireDTO(exemplaireSauve);

    }

    /**
     * Méthode permet d'effacer le stock en fonction de l'id via ce service
     *
     * @param id
     */
    public void deleteExemplaireById(Long id)
    {
        exemplaireRepository.deleteById(id);
    }


}
