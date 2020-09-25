package com.victor.library2.controller;

import com.victor.library2.exception.ResourceNotFoundException;
import com.victor.library2.model.Livre;
import com.victor.library2.model.Utilisateur;
import com.victor.library2.model.UtilisateurDTO;
import com.victor.library2.service.LivreService;
import com.victor.library2.service.UtilisateurService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/listUtilisateur")
    public List<UtilisateurDTO> getAllUtilisateurs() {
        return this.utilisateurService.getAllUsers();
    }

    @GetMapping("/detailUtilisateur/{id}")
    public ResponseEntity<UtilisateurDTO> getUtilisateurById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        UtilisateurDTO utilisateurId = utilisateurService.getUserById(id);
        if (utilisateurId == null){
            new ResourceNotFoundException("Utilisateur not found for this id :: " + utilisateurId);
        }
        return ResponseEntity.ok().body(utilisateurId);
    }

    @PostMapping("/addUtilisateur")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UtilisateurDTO createUtilisateur(@RequestBody UtilisateurDTO utilisateurDTO) {
        Utilisateur utilisateur = convertToEntity(utilisateurDTO);
        return this.utilisateurService.saveUser(utilisateur);
    }

    @PutMapping("/updateUtilisateur/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<UtilisateurDTO> updateUtilisateur(@PathVariable(value = "id") Long utilisateurId,
                                              @RequestBody UtilisateurDTO utilisateurDetails)
                                                throws ResourceNotFoundException {
        UtilisateurDTO utilisateurDTO = utilisateurService.getUserById(utilisateurId);
        Utilisateur utilisateur = convertToEntity(utilisateurDTO);
        if (utilisateurId == null){
            new ResourceNotFoundException("Employee not found for this id :: " + utilisateurId);
        }
/*        utilisateur.setId(utilisateurDetails.getId());
        utilisateur.setAge(utilisateurDetails.getAge());
        utilisateur.setPrenom(utilisateurDetails.getPrenom());
        utilisateur.setUsername(utilisateurDetails.getUsername());
        utilisateur.setMail(utilisateurDetails.getMail());*/
        final UtilisateurDTO updatedUtilisateur = utilisateurService.saveUser(utilisateur);
        return ResponseEntity.ok(updatedUtilisateur);
    }

    @DeleteMapping("/deleteLivre/{id}")
    public Map<String, Boolean> deleteUtilisateur(@PathVariable(value = "id") Long utilisateurId)
            throws ResourceNotFoundException {
        UtilisateurDTO utilisateur= utilisateurService.getUserById(utilisateurId);
        if (utilisateurId == null){
            new ResourceNotFoundException("Utilisateur not found for this id :: " + utilisateurId);
        }
        this.utilisateurService.deleteUserById(utilisateurId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    private Utilisateur convertToEntity(UtilisateurDTO utilisateurDTO) throws ParseException {
        ModelMapper mapper = new ModelMapper();
        Utilisateur utilisateur = mapper.map(utilisateurDTO, Utilisateur.class);

        return utilisateur;
    }
}
