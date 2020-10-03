package com.victor.library2.controller;

import com.victor.library2.exception.ResourceNotFoundException;
import com.victor.library2.model.dto.LivreDTO;
import com.victor.library2.model.dto.PretDTO;
import com.victor.library2.model.dto.RoleDTO;
import com.victor.library2.model.entity.ERole;
import com.victor.library2.model.entity.Pret;
import com.victor.library2.model.entity.Role;
import com.victor.library2.model.entity.Utilisateur;
import com.victor.library2.model.dto.UtilisateurDTO;
import com.victor.library2.payload.request.LoginRequest;
import com.victor.library2.payload.request.SignupRequest;
import com.victor.library2.payload.response.JwtResponse;
import com.victor.library2.payload.response.MessageResponse;
import com.victor.library2.repository.RoleRepository;
import com.victor.library2.repository.UtilisateurRepository;
import com.victor.library2.security.jwt.JwtUtils;
import com.victor.library2.service.UserDetailsImpl;
import com.victor.library2.service.UtilisateurService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

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
    public ResponseEntity<UtilisateurDTO> createUtilisateur(@Valid @RequestBody UtilisateurDTO utilisateurDTO) {
        utilisateurDTO.setRolesDTO(utilisateurDTO.getRolesDTO());
        utilisateurDTO.setPassword(encoder.encode(utilisateurDTO.getPassword()));
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

    @PostMapping("/connect")
    public ResponseEntity<?> ConnectUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> signUpUtilisateur(@Valid @RequestBody SignupRequest signUpRequest) {
        if ((utilisateurRepository.existsByUsername(signUpRequest.getUsername())) &&
                (utilisateurRepository.existsByPassword(signUpRequest.getPassword()))) {
           System.out.println("l'utilisateur existe bien");

        }
       return ResponseEntity.ok().body(new MessageResponse("L'utilisateur s'est déconnecté"));
    }


    private Utilisateur convertToEntity(UtilisateurDTO utilisateurDTO) throws ParseException {
        ModelMapper mapper = new ModelMapper();
        Utilisateur utilisateur = mapper.map(utilisateurDTO, Utilisateur.class);
        return utilisateur;
    }
}
