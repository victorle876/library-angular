package com.victor.library2.controller;

import com.victor.library2.exception.ResourceNotFoundException;
import com.victor.library2.model.dto.LivreDTO;
import com.victor.library2.model.entity.Utilisateur;
import com.victor.library2.model.dto.UtilisateurDTO;
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

    @GetMapping("/list")
    public ResponseEntity<List<UtilisateurDTO>> getAllUtilisateurs() {
        List<UtilisateurDTO> ListUtilisateursDto = this.utilisateurService.getAllUsers();
        return ResponseEntity.ok().body(ListUtilisateursDto);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<UtilisateurDTO> getUtilisateurById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        UtilisateurDTO utilisateurId = utilisateurService.getUserById(id);
        if (utilisateurId == null){
            new ResourceNotFoundException("Utilisateur not found for this id :: " + utilisateurId);
        }
        return ResponseEntity.ok().body(utilisateurId);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<UtilisateurDTO> createUtilisateur(@RequestBody UtilisateurDTO utilisateurDTO) {
        Utilisateur utilisateur = convertToEntity(utilisateurDTO);
        UtilisateurDTO utilisateurSauve = this.utilisateurService.saveUser(utilisateur);
        return ResponseEntity.ok().body(utilisateurSauve);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<UtilisateurDTO> updateUtilisateur(@PathVariable(value = "id") Long utilisateurId,
                                              @RequestBody UtilisateurDTO utilisateurDetails)
                                                 {
        System.out.println("utilisateurDTO" + utilisateurDetails);
        Utilisateur utilisateur = convertToEntity(utilisateurDetails);
        if (utilisateurId == null){
            new ResourceNotFoundException("Employee not found for this id :: " + utilisateurId);
        }
        System.out.println("utilisateur" + utilisateur);
        final UtilisateurDTO updatedUtilisateur = utilisateurService.saveUser(utilisateur);
        return ResponseEntity.ok(updatedUtilisateur);
    }

    @DeleteMapping("/delete/{id}")
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
