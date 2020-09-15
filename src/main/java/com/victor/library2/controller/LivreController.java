package com.victor.library2.controller;

import com.victor.library2.exception.ResourceNotFoundException;
import com.victor.library2.model.LivreDTO;
import com.victor.library2.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/livre")
public class LivreController {

    @Autowired
    private LivreService livreService;

    @GetMapping("/listLivre")
    public List<LivreDTO> getAllLivres() {
        return livreService.getAllLivres();
    }

    @GetMapping("/detailLivre/{id}")
    public ResponseEntity<LivreDTO> getlivreById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        LivreDTO livreId = livreService.getLivreById(id);
        if (livreId == null){
            new ResourceNotFoundException("Employee not found for this id :: " + livreId);
        }
        return ResponseEntity.ok().body(livreId);
    }

    @PostMapping("/addLivre")
    public LivreDTO createLivre(@RequestBody LivreDTO livre) {

        return this.livreService.saveLivre(livre);
    }

    @PutMapping("/updateLivre/{id}")
    public ResponseEntity<LivreDTO> updatelivre(@PathVariable(value = "id") Long livreId,
                                                @RequestBody LivreDTO livreDetails) throws ResourceNotFoundException {
        LivreDTO livre = livreService.getLivreById(livreId);
        if (livreId == null){
                 new ResourceNotFoundException("Employee not found for this id :: " + livreId);
        }
/*        livre.setId(livreDetails.getId());
        livre.setAuteur(livreDetails.getAuteur());
        livre.setCollection(livreDetails.getCollection());
        livre.setCategorie(livreDetails.getCategorie());
        livre.setDescription(livreDetails.getDescription());*/
        final LivreDTO updatedLivre = livreService.saveLivre(livre);
        return ResponseEntity.ok(updatedLivre);
    }

    @DeleteMapping("/deleteLivre/{id}")
    public Map<String, Boolean> deleteLivre(@PathVariable(value = "id") Long livreId)
            throws ResourceNotFoundException {
        LivreDTO livre = livreService.getLivreById(livreId);
        if (livreId == null){
                 new ResourceNotFoundException("Livre not found for this id :: " + livreId);
        }
        this.livreService.deleteLivreById(livreId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
