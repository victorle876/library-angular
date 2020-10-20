package com.victor.library2.controller;

import com.victor.library2.exception.ResourceNotFoundException;
import com.victor.library2.model.entity.Utilisateur;
import com.victor.library2.model.dto.UtilisateurDTO;
import com.victor.library2.repository.RoleRepository;
import com.victor.library2.repository.UtilisateurRepository;
import com.victor.library2.service.UtilisateurService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UtilisateurRepository utilisateurRepository;
    

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
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(utilisateurId);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<UtilisateurDTO> createUtilisateur(@Valid @RequestBody UtilisateurDTO utilisateurDTO) {
        utilisateurDTO.setRolesDTO(utilisateurDTO.getRolesDTO());
        utilisateurDTO.setPassword(utilisateurDTO.getPassword());
        utilisateurDTO.setToken(utilisateurDTO.getToken());
        Utilisateur utilisateur = convertToEntity(utilisateurDTO);
        UtilisateurDTO utilisateurSauve = this.utilisateurService.saveUser(utilisateur);
        return ResponseEntity.ok().body(utilisateurSauve);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<UtilisateurDTO> updateUtilisateur(@PathVariable(value = "id") Long utilisateurId,
                                              @Valid @RequestBody UtilisateurDTO utilisateurDetails)
                                                 {
        System.out.println("utilisateurDTO" + utilisateurDetails);
        Utilisateur utilisateur = convertToEntity(utilisateurDetails);
        if (utilisateurId == null){
         //   new ResourceNotFoundException("Employee not found for this id :: " + utilisateurId);
            return ResponseEntity.notFound().build();
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
