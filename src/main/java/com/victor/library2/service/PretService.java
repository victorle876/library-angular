package com.victor.library2.service;

import com.victor.library2.model.dto.PretDTO;
import com.victor.library2.model.entity.Pret;
import com.victor.library2.model.entity.Utilisateur;
import com.victor.library2.repository.PretRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PretService {

    private PretDTO mapPretToPretDTO(Pret pret) {
        ModelMapper mapper = new ModelMapper();
        PretDTO pretDTO = mapper.map(pret, PretDTO.class);
        return pretDTO;
    }

    @Autowired
    PretRepository pretRepository;

    private static final Logger logger = LogManager.getLogger(PretService.class);

    /**
     * Méthode permet de lister toutes les Topos via ce service
     *
     * * @return la liste des topos
     */
    public List<PretDTO> getAllPrets()
    {
        List<Pret> pretList = pretRepository.findAll();
        logger.debug(pretList.size());
        if(pretList.size() > 0) {
            return pretList.stream()
                    .map(this::mapPretToPretDTO)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<PretDTO>();
        }
    }

    public List<PretDTO> getAllPretsDispo()
    {
        List<Pret> pretListDispo = pretRepository.findByDisponibleTrue();
        logger.debug(pretListDispo.size());
        if(pretListDispo.size() > 0) {
            return pretListDispo.stream()
                    .map(this::mapPretToPretDTO)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<PretDTO>();
        }
    }

    public List<PretDTO> getAllPretsRetourneOrProlonge()
    {
        List<Pret> pretListRetourneOrProlonge = pretRepository.findByEmprunteTrueOrRetourneTrueOrProlongeTrue();
        logger.debug(pretListRetourneOrProlonge.size());
        if(pretListRetourneOrProlonge.size() > 0) {
            return pretListRetourneOrProlonge.stream()
                    .map(this::mapPretToPretDTO)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<PretDTO>();
        }
    }

    public List<PretDTO> getAllPretsRecherchees(String chaineRecherchee)
    {
        List<Pret> pretListRecherchees = pretRepository.findByTitre(chaineRecherchee);
        logger.debug(pretListRecherchees.size());
        if(pretListRecherchees.size() > 0) {
            return pretListRecherchees.stream()
                    .map(this::mapPretToPretDTO)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<PretDTO>();
        }
    }

    /**
     * Méthode permet de consulter le Stock en fonction de l'id via ce service
     *
     * @param id
     * * @return le stock via id
     */
    public PretDTO getPretById(Long id)
    {
        Optional<Pret> pret= this.pretRepository.findById(id);
        if (!pret.isPresent()){
            return null;
        }
        return mapPretToPretDTO(pret.get());

    }

    /**
     * Méthode permet de sauvegarder le stock via ce service
     *
     * @param pret
     * * @return le stock sauvegardé
     */
    public PretDTO savePret(Pret pret)
    {
        Pret pretSauve= this.pretRepository.save(pret);
        return mapPretToPretDTO(pretSauve);

    }

    /**
     * Méthode permet d'effacer le stock en fonction de l'id via ce service
     *
     * @param id
     */
    public void deletePretById(Long id)
    {
        pretRepository.deleteById(id);
    }


}
