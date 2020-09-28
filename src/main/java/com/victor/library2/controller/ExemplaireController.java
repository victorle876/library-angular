package com.victor.library2.controller;

import com.victor.library2.exception.ResourceNotFoundException;
import com.victor.library2.model.dto.ExemplaireDTO;
import com.victor.library2.model.entity.Exemplaire;
import com.victor.library2.service.ExemplaireService;
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
@RequestMapping("/api/exemplaire")
public class ExemplaireController {

    @Autowired
    private ExemplaireService exemplaireService;

    @GetMapping("/list")
    public List<ExemplaireDTO> getAllExemplairess() {
        return exemplaireService.getAllExemplaires();
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ExemplaireDTO> getExemplaireById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        ExemplaireDTO exemplaireId = exemplaireService.getExemplaireById(id);
        if (exemplaireId == null){
            new ResourceNotFoundException("Exemplaire not found for this id :: " + exemplaireId);
        }
        return ResponseEntity.ok().body(exemplaireId);
    }

/*    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ExemplaireDTO createExemplaire(@RequestBody ExemplaireDTO exemplaireDTO) {
        Exemplaire exemplaire = convertToEntity(exemplaireDTO);
        return this.exemplaireService.saveExemplaire(exemplaire);
    }*/

    @PutMapping("/update/{id}")
    public ResponseEntity<ExemplaireDTO> updateStock(@PathVariable(value = "id") Long stockId,
                                                  @RequestBody ExemplaireDTO exemplaireDetails) throws ResourceNotFoundException {
        Exemplaire exemplaire = convertToEntity(exemplaireDetails);
        if (stockId == null){
                 new ResourceNotFoundException("Exemplaire not found for this id :: " + stockId);
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
}
