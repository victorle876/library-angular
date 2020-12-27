package com.victor.library2.controller;

import com.victor.library2.exception.ResourceNotFoundException;
import com.victor.library2.model.dto.ExemplaireDTO;
import com.victor.library2.model.dto.LivreDTO;
import com.victor.library2.model.dto.PretDTO;
import com.victor.library2.model.dto.UtilisateurDTO;
import com.victor.library2.model.entity.Exemplaire;
import com.victor.library2.model.entity.Pret;
import com.victor.library2.model.entity.Utilisateur;
import com.victor.library2.service.ExemplaireService;
import com.victor.library2.service.PretService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exemplaire")
public class ExemplaireController {

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private PretService pretService;

    @GetMapping("/list")
    public ResponseEntity<List<ExemplaireDTO>> getAllExemplaires() {
        List<ExemplaireDTO> ListExemplairesDto = this.exemplaireService.getAllExemplaires();
        return ResponseEntity.ok().body(ListExemplairesDto);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ExemplaireDTO> getExemplaireById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        ExemplaireDTO exemplaireId = exemplaireService.getExemplaireById(id);
        if (exemplaireId == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(exemplaireId);
    }

/*    @PostMapping("/addPret/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<PretDTO> createPret(@Valid @RequestBody PretDTO pretDTO, @PathVariable(value = "id") Long exemplaireId) {
        ExemplaireDTO exemplaireDTO = this.exemplaireService.getExemplaireById(exemplaireId);
        pretDTO.setExemplaire(exemplaireDTO);
        pretDTO.setDisponible(Boolean.FALSE);
        pretDTO.setEmprunte(Boolean.FALSE);
        Pret pret = convertToEntity(pretDTO);
        PretDTO pretSauveDTO = this.pretService.savePret(pret);
        return ResponseEntity.ok().body(pretSauveDTO);
    }*/

    @PostMapping("/addPretDispo/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<PretDTO> addPretDispo(@Valid @RequestBody PretDTO pretDTO, @PathVariable(value = "id") Long exemplaireId) {
        ExemplaireDTO exemplaireDTO = this.exemplaireService.getExemplaireById(exemplaireId);
/*        pretDTO.setExemplaire(exemplaireDTO);
        pretDTO.setDisponible(Boolean.TRUE);
        pretDTO.setEmprunte(Boolean.FALSE);*/
        pretDTO.setExemplaire(exemplaireDTO);
        Pret pret = convertToEntity(pretDTO);
        PretDTO pretSauveDTO = this.pretService.savePret(pret);
        return ResponseEntity.ok().body(pretSauveDTO);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<ExemplaireDTO> saveExemplaire(@Valid @RequestBody ExemplaireDTO exemplaireDetails)
    {
        Exemplaire exemplaire = convertToEntity(exemplaireDetails);
        final ExemplaireDTO updatedExemplaire = exemplaireService.saveExemplaire(exemplaire);
        return ResponseEntity.ok(updatedExemplaire);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ExemplaireDTO> updateStock(@PathVariable(value = "id") Long exemplaireId,
                                                   @RequestBody ExemplaireDTO exemplaireDetails) throws ResourceNotFoundException {
        ExemplaireDTO exemplaireID = this.exemplaireService.getExemplaireById(exemplaireId);
        LivreDTO livreId = exemplaireID.getLivre();
        exemplaireDetails.setLivre(livreId);
        Exemplaire exemplaire = convertToEntity(exemplaireDetails);
        if (exemplaireId == null){
             //    new ResourceNotFoundException("Exemplaire not found for this id :: " + exemplaireId);
            return ResponseEntity.notFound().build();
        }

        final ExemplaireDTO updatedExemplaire = exemplaireService.saveExemplaire(exemplaire);
        return ResponseEntity.ok(updatedExemplaire);
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> deleteExemplaire(@PathVariable(value = "id") Long exemplaireId)
            throws ResourceNotFoundException {
        ExemplaireDTO exemplaire = exemplaireService.getExemplaireById(exemplaireId);
        if (exemplaireId == null){
                 new ResourceNotFoundException("Employee not found for this id :: " + exemplaireId);
        }
        this.exemplaireService.deleteExemplaireById(exemplaireId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


    private Exemplaire convertToEntity(ExemplaireDTO exemplaireDTO) throws ParseException {
        ModelMapper mapper = new ModelMapper();
        Exemplaire exemplaire = mapper.map(exemplaireDTO, Exemplaire.class);

        return exemplaire;
    }

    private Pret convertToEntity(PretDTO pretDTO) throws ParseException {
        ModelMapper mapper = new ModelMapper();
        Pret pret = mapper.map(pretDTO, Pret.class);

        return pret;
    }
}
