package com.victor.library2.service;

import com.victor.library2.model.dto.UtilisateurDTO;
import com.victor.library2.model.entity.Livre;
import com.victor.library2.model.entity.Utilisateur;
import com.victor.library2.repository.UtilisateurRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UtilisateurService {

    private static final Logger logger = LogManager.getLogger(UtilisateurService.class);

    private UtilisateurDTO mapUtilisateurToUtilisateurDTO(Utilisateur utilisateur) {
        ModelMapper mapper = new ModelMapper();
        UtilisateurDTO utilisateurDTO = mapper.map(utilisateur, UtilisateurDTO.class);
        return utilisateurDTO;
    }

    @Autowired
    UtilisateurRepository utilisateurRepository;

    /**
     * Méthode permet de lister tous les utilisateurs via ce service
     *
     * * @return la liste des utilisateurs
     */
    public List<UtilisateurDTO> getAllUsers()
    {
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();

        if(utilisateurList.size() > 0) {
            return utilisateurList.stream()
                    .map(this::mapUtilisateurToUtilisateurDTO)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<UtilisateurDTO>();
        }
    }

    /**
     * Méthode permet de consulter l'utilisateur en fonction de l'id via ce service
     *
     * @param id
     * * @return l'utilisateur via id
     */
    public UtilisateurDTO getUserById(Long id)
    {
        Optional<Utilisateur> utilisateur= this.utilisateurRepository.findById(id);
        if (!utilisateur.isPresent()){
            return null;
        }
        return mapUtilisateurToUtilisateurDTO(utilisateur.get());
    }

    public UtilisateurDTO getUserByUsername(String username)
    {
        Optional<Utilisateur> utilisateur= this.utilisateurRepository.findByUsername(username);
        logger.info(utilisateur);
        if (!utilisateur.isPresent()){
            return null;
        }
        return mapUtilisateurToUtilisateurDTO(utilisateur.get());
    }

    public UtilisateurDTO getUserByMail(String mail)
    {
        Optional<Utilisateur> utilisateur= this.utilisateurRepository.findByMail(mail);
        logger.info(utilisateur);
        if (!utilisateur.isPresent()){
            return null;
        }
        return mapUtilisateurToUtilisateurDTO(utilisateur.get());
    }

    public UtilisateurDTO getUtilisateurConnected (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUtilisateur = authentication.getName();
        logger.info("mail connecte: " + emailUtilisateur);
        return mapUtilisateurToUtilisateurDTO(this.utilisateurRepository.findByMail(emailUtilisateur).get());
    }

    public UtilisateurDTO getUserByUsernameAndPassword(String username, String password)
    {
        Optional<Utilisateur> utilisateur= this.utilisateurRepository.findByUsernameAndPassword(username,password);
        logger.info(utilisateur);
        if (!utilisateur.isPresent()){
            return null;
        }
        return mapUtilisateurToUtilisateurDTO(utilisateur.get());
    }

    /**
     * Méthode permet de sauvegarder l'utilisateur via ce service
     *
     * @param utilisateur
     * * @return la voie sauvegardée
     */
    public UtilisateurDTO saveUser(Utilisateur utilisateur)
    {
        Utilisateur utilisateurSauve= this.utilisateurRepository.save(utilisateur);
        return mapUtilisateurToUtilisateurDTO(utilisateurSauve);
    }

    /**
     * Méthode permet d'effacer l'utilisateur en fonction de l'id via ce service
     *
     * @param id
     */
    public void deleteUserById(Long id)
    {
        utilisateurRepository.deleteById(id);
    }

}
