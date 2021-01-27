package com.victor.library2.controller;

import antlr.BaseAST;
import com.victor.library2.exception.ResourceNotFoundException;
import com.victor.library2.model.dto.ExemplaireDTO;
import com.victor.library2.model.dto.UtilisateurDTO;
import com.victor.library2.model.entity.AuthenticationRequest;
import com.victor.library2.model.entity.JwtTokens;
import com.victor.library2.model.entity.RefreshRequest;
import com.victor.library2.model.entity.Utilisateur;
import com.victor.library2.repository.UtilisateurRepository;
import com.victor.library2.service.JwtTokenService;
import com.victor.library2.service.UtilisateurService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private AuthenticationManager authenticationManager;

 //   private PasswordEncoder passwordEncoder;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private JwtTokenService jwtTokenService;


    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity authenticate(@RequestBody UtilisateurDTO utilisateurDTO, AuthenticationRequest authenticationRequest) {

        System.out.println("utilisateurDTO:" + utilisateurDTO);
        System.out.println("mail:" + utilisateurDTO.getMail());
        System.out.println("pwd:" + utilisateurDTO.getPassword());
        UtilisateurDTO utilisateurExistant = this.utilisateurService.getUserByMail(utilisateurDTO.getMail());
        if (utilisateurExistant != null) {
            System.out.println("utilisateurExistant:" + utilisateurExistant);
            authenticationRequest.username = utilisateurExistant.getUsername();
            System.out.println("authenticationRequest.username:" + authenticationRequest.username);
            authenticationRequest.password = passwordEncoder.encode(utilisateurExistant.getPassword());
            System.out.println("authenticationRequest.password:" + authenticationRequest.password);
        }

        //$2a$10$T0sv9bnVdj6jDhTvtQbyYeiQORlJtlR/RA2eli8POjD8zdL25GCyO
        //$2a$10$T0sv9bnVdj6jDhTvtQbyYeiQORlJtlR/RA2eli8POjD8zdL25GCyO

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password);
        System.out.println("usernamePasswordAuthenticationToken:" + usernamePasswordAuthenticationToken);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        System.out.println("authentication:" + authentication);
        if (authentication != null && authentication.isAuthenticated() && utilisateurExistant != null) {
                System.out.println("ici4");
                if (passwordEncoder.matches(utilisateurDTO.getPassword(), utilisateurExistant.getPassword()) == true) {
                    JwtTokens tokens = jwtTokenService.createTokens(authentication);
                    System.out.println(tokens);
                    return ResponseEntity.ok().body(tokens);
            }
        }
        System.out.println("ici5");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }

    @PostMapping(value = "/logout")
    public ResponseEntity refreshToken(@RequestBody RefreshRequest refreshRequest) {
        try {
            JwtTokens tokens = jwtTokenService.refreshJwtToken(refreshRequest.refreshToken);
            return ResponseEntity.ok().body(tokens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }
    }

    private Utilisateur convertToEntity(UtilisateurDTO utilisateurDTO) throws ParseException {
        ModelMapper mapper = new ModelMapper();
        Utilisateur utilisateur = mapper.map(utilisateurDTO, Utilisateur.class);
        return utilisateur;
    }
}
