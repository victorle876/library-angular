package com.victor.library2.controller;

import com.victor.library2.exception.ResourceNotFoundException;
import com.victor.library2.model.dto.ExemplaireDTO;
import com.victor.library2.model.dto.UtilisateurDTO;
import com.victor.library2.model.entity.Exemplaire;
import com.victor.library2.model.entity.Livre;
import com.victor.library2.model.dto.LivreDTO;
import com.victor.library2.service.ExemplaireService;
import com.victor.library2.service.LivreService;
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
@RequestMapping("/api/livre")
public class LivreController {

    @Autowired
    private LivreService livreService;

    @Autowired
    private ExemplaireService exemplaireService;

    @GetMapping("/list")
    public ResponseEntity<List<LivreDTO>> getAllLivres() {
        List<LivreDTO> ListLivresDto = this.livreService.getAllLivres();
        return ResponseEntity.ok().body(ListLivresDto);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<LivreDTO> getLivreById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        LivreDTO livreId = livreService.getLivreById(id);
        if (livreId == null){
            new ResourceNotFoundException("Livre not found for this id :: " + livreId);
        }
        return ResponseEntity.ok().body(livreId);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<LivreDTO> createLivre(@Valid @RequestBody LivreDTO livreDTO) {
        Livre livre = convertToEntity(livreDTO);
        LivreDTO livreSauve = this.livreService.saveLivre(livre);
        return ResponseEntity.ok().body(livreSauve);
    }

    @PostMapping("/addExemplaire/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<ExemplaireDTO> createExemplaire(@Valid @RequestBody ExemplaireDTO exemplaireDTO,@PathVariable(value = "id") Long livreId) {
        LivreDTO livreDTO = this.livreService.getLivreById(livreId);
        exemplaireDTO.setLivreDTO(livreDTO);
        Exemplaire exemplaire = convertToEntity(exemplaireDTO);
        ExemplaireDTO exemplaireSauveDTO = this.exemplaireService.saveExemplaire(exemplaire);
        return ResponseEntity.ok().body(exemplaireSauveDTO);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<LivreDTO> updatelivre(@PathVariable(value = "id") Long livreId,
                                                @RequestBody LivreDTO livreDetails) throws ResourceNotFoundException {
        Livre livre = convertToEntity(livreDetails);
        if (livreId == null){
                 new ResourceNotFoundException("Livre not found for this id :: " + livreId);
        }
        final LivreDTO updatedLivre = livreService.saveLivre(livre);
        return ResponseEntity.ok(updatedLivre);
    }

    @DeleteMapping("/delete/{id}")
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

    private Livre convertToEntity(LivreDTO livreDTO) throws ParseException {
        ModelMapper mapper = new ModelMapper();
        Livre livre = mapper.map(livreDTO, Livre.class);

        return livre;
    }

    private Exemplaire convertToEntity(ExemplaireDTO exemplaireDTO) throws ParseException {
        ModelMapper mapper = new ModelMapper();
        Exemplaire exemplaire = mapper.map(exemplaireDTO, Exemplaire.class);

        return exemplaire;
    }
}
