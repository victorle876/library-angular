package com.victor.library2.controller;

import com.victor.library2.exception.ResourceNotFoundException;
import com.victor.library2.model.Pret;
import com.victor.library2.model.PretDTO;
import com.victor.library2.model.Utilisateur;
import com.victor.library2.model.UtilisateurDTO;
import com.victor.library2.service.PretService;
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
@RequestMapping("/api/pret")
public class PretController {

    @Autowired
    private PretService pretService;

    @GetMapping("/listPret")
    public List<PretDTO> getAllPrets() {
        return this.pretService.getAllPrets();
    }

    @GetMapping("/detailPret/{id}")
    public ResponseEntity<PretDTO> getPretById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        PretDTO pretId = pretService.getPretById(id);
        if (pretId == null){
            new ResourceNotFoundException("Pret not found for this id :: " + pretId);
        }
        return ResponseEntity.ok().body(pretId);
    }

    @PostMapping("/addPret")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public PretDTO createPret(@RequestBody PretDTO pretDTO) {
        Pret pret = convertToEntity(pretDTO);
        return this.pretService.savePret(pret);
    }

    @PutMapping("/updatePret/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<PretDTO> updateUtilisateur(@PathVariable(value = "id") Long pretId,
                                                @RequestBody PretDTO pretDetails) throws ResourceNotFoundException {
        PretDTO pretDTO = pretService.getPretById(pretId);
        Pret pret = convertToEntity(pretDTO);
        if (pretId == null){
            new ResourceNotFoundException("Employee not found for this id :: " + pretId);
        }
/*        utilisateur.setId(utilisateurDetails.getId());
        utilisateur.setAge(utilisateurDetails.getAge());
        utilisateur.setPrenom(utilisateurDetails.getPrenom());
        utilisateur.setUsername(utilisateurDetails.getUsername());
        utilisateur.setMail(utilisateurDetails.getMail());*/
        final PretDTO updatedPret = pretService.savePret(pret);
        return ResponseEntity.ok(updatedPret);
    }

    @DeleteMapping("/deletePret/{id}")
    public Map<String, Boolean> deletePret(@PathVariable(value = "id") Long pretId)
            throws ResourceNotFoundException {
        PretDTO pret= pretService.getPretById(pretId);
        if (pretId == null){
            new ResourceNotFoundException("Utilisateur not found for this id :: " + pretId);
        }
        this.pretService.deletePretById(pretId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    private Pret convertToEntity(PretDTO pretDTO) throws ParseException {
        ModelMapper mapper = new ModelMapper();
        Pret pret = mapper.map(pretDTO, Pret.class);

        return pret;
    }
}
