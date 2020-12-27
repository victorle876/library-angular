package com.victor.library2.controller;

import com.victor.library2.exception.ResourceNotFoundException;
import com.victor.library2.model.dto.ExemplaireDTO;
import com.victor.library2.model.entity.Pret;
import com.victor.library2.model.dto.PretDTO;
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
@RequestMapping("/api/pret")
public class PretController {

    @Autowired
    private PretService pretService;

    @GetMapping("/list")
    public ResponseEntity<List<PretDTO>> getAllPrets() {
        List<PretDTO> ListPretsDto = this.pretService.getAllPrets();
        return ResponseEntity.ok().body(ListPretsDto);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<PretDTO> getPretById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        PretDTO pretId = pretService.getPretById(id);
        if (pretId == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(pretId);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<PretDTO> updatePret(@PathVariable(value = "id") Long pretId, @Valid @RequestBody PretDTO pretDetails) throws ResourceNotFoundException {
         PretDTO pretID = pretService.getPretById(pretId);
        System.out.print(pretID);
         ExemplaireDTO exemplaireId = pretID.getExemplaire();
         pretDetails.setExemplaire(exemplaireId);
        Pret pret = convertToEntity(pretDetails);
        if (pretDetails == null){
            return ResponseEntity.notFound().build();
        //    new ResourceNotFoundException("Pret not found for this id :: " + pretId);
        }
        final PretDTO updatedPret = pretService.savePret(pret);
        return ResponseEntity.ok(updatedPret);
    }

    @PutMapping("/makePretEmprunte/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<PretDTO> makePretEmprunte(@PathVariable(value = "id") Long pretId, @RequestBody PretDTO pretDetails) throws ResourceNotFoundException {
        System.out.print("pretDetails:" + pretDetails);
        Pret pret = convertToEntity(pretDetails);
        if (pretDetails == null){
            return ResponseEntity.notFound().build();
        }
        final PretDTO pretEmprunte = pretService.savePret(pret);
        System.out.println("pretEmprunte:" + pretEmprunte);
        return ResponseEntity.ok(pretEmprunte);
    }

    @PutMapping("/makePretRetourne/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<PretDTO> makePretRetourne(@PathVariable(value = "id") Long pretId,@RequestBody PretDTO pretDetails) throws ResourceNotFoundException {
        System.out.print(pretDetails);
        Pret pret = convertToEntity(pretDetails);
        if (pretDetails == null){
            return ResponseEntity.notFound().build();
            //    new ResourceNotFoundException("Pret not found for this id :: " + pretId);
        }
        final PretDTO pretRetourne = pretService.savePret(pret);
        return ResponseEntity.ok(pretRetourne);
    }

    @PutMapping("/makePretProlonge/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<PretDTO> makePretProlonge(@PathVariable(value = "id") Long pretId,@RequestBody PretDTO pretDetails) throws ResourceNotFoundException {
        System.out.print(pretDetails);
        Pret pret = convertToEntity(pretDetails);
        if (pret == null){
            return ResponseEntity.notFound().build();
            //    new ResourceNotFoundException("Pret not found for this id :: " + pretId);
        }
        System.out.print(pretDetails.getNombreProlonge());
        final PretDTO pretProlonge = pretService.savePret(pret);
        return ResponseEntity.ok(pretProlonge);
    }

    @GetMapping("/listPretDispo")
    public ResponseEntity<List<PretDTO>> getAllPretsDispo() {
        List<PretDTO> ListPretsDto = this.pretService.getAllPretsDispo();
        return ResponseEntity.ok().body(ListPretsDto);
    }


    @GetMapping("/listPretRetourneOrProlonge")
    public ResponseEntity<List<PretDTO>> getAllPretsRetourneOrProlonge() {
        List<PretDTO> ListPretsDto = this.pretService.getAllPretsRetourneOrProlonge();
        return ResponseEntity.ok().body(ListPretsDto);
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> deletePret(@PathVariable(value = "id") Long pretId)
            throws ResourceNotFoundException {
        PretDTO pret= pretService.getPretById(pretId);
        if (pretId == null){
            new ResourceNotFoundException("Pret not found for this id :: " + pretId);
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
