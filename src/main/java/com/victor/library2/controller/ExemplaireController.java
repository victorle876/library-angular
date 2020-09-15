package com.victor.library2.controller;

import com.victor.library2.exception.ResourceNotFoundException;
import com.victor.library2.model.ExemplaireDTO;
import com.victor.library2.service.ExemplaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/stock")
public class ExemplaireController {

    @Autowired
    private ExemplaireService exemplaireService;

    @GetMapping("/listExemplaire")
    public List<ExemplaireDTO> getAllStocks() {
        return exemplaireService.getAllExemplaires();
    }

    @GetMapping("/detailExemplaire/{id}")
    public ResponseEntity<ExemplaireDTO> getExemplaireById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        ExemplaireDTO exemplaireId = exemplaireService.getExemplaireById(id);
        if (exemplaireId == null){
            new ResourceNotFoundException("Stock not found for this id :: " + exemplaireId);
        }
        return ResponseEntity.ok().body(exemplaireId);
    }

    @PostMapping("/addStock")
    public ExemplaireDTO createExemplaire(@RequestBody ExemplaireDTO exemplaire) {
        return this.exemplaireService.saveExemplaire(exemplaire);
    }

    @PutMapping("/updateStock/{id}")
    public ResponseEntity<ExemplaireDTO> updateStock(@PathVariable(value = "id") Long stockId,
                                                  @RequestBody ExemplaireDTO exemplaireDetails) throws ResourceNotFoundException {
        ExemplaireDTO exemplaire = exemplaireService.getExemplaireById(stockId);
        if (stockId == null){
                 new ResourceNotFoundException("Stock not found for this id :: " + stockId);
        }

        final ExemplaireDTO updatedExemplaire = exemplaireService.saveExemplaire(exemplaire);
        return ResponseEntity.ok(updatedExemplaire);
    }

    @DeleteMapping("/deleteStock/{id}")
    public Map<String, Boolean> deleteExemplaire(@PathVariable(value = "id") Long exemplaireId)
            throws ResourceNotFoundException {
        ExemplaireDTO exemplaire = exemplaireService.getExemplaireById(exemplaireId);
        if (exemplaireId == null){
                 new ResourceNotFoundException("Employee not found for this id :: " + exemplaireId);
        }
        this.exemplaireService.deleteStockById(exemplaireId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
