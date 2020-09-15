package com.victor.library2.service;

import com.victor.library2.model.UtilisateurDTO;
import com.victor.library2.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtilisateurService {

    @Autowired
    UtilisateurRepository utilisateurRepository;

    /**
     * Méthode permet de lister tous les utilisateurs via ce service
     *
     * * @return la liste des utilisateurs
     */
    public List<UtilisateurDTO> getAllUsers()
    {
        List<UtilisateurDTO> utilisateurList = utilisateurRepository.findAll();

        if(utilisateurList.size() > 0) {
            return utilisateurList;
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
        return this.utilisateurRepository.findById(id).get();
    }

    /**
     * Méthode permet de sauvegarder l'utilisateur via ce service
     *
     * @param utilisateur
     * * @return la voie sauvegardée
     */
    public UtilisateurDTO saveUser(UtilisateurDTO utilisateur)
    {
        return this.utilisateurRepository.save(utilisateur);
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

    /**
     * Méthode permet de vérifier l'existence lors de la connexion via ce service
     *
     * @return l'utilisateur
     */
    public UtilisateurDTO getUtilisateurConnected (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUtilisateur = authentication.getName();
        return this.utilisateurRepository.findByMail(emailUtilisateur).get();
    }
}
