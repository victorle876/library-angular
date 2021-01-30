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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(AuthController.class);

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity authenticate(@RequestBody UtilisateurDTO utilisateurDTO) {

        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        logger.info("utilisateurDTO:" + utilisateurDTO);
        logger.info("mail:" + utilisateurDTO.getMail());
        logger.info("pwd:" + utilisateurDTO.getPassword());
/*        UtilisateurDTO utilisateurId = this.utilisateurService.getUtilisateurConnected();
        logger.info("user connected:" + utilisateurId );*/
        UtilisateurDTO utilisateurExistant = this.utilisateurService.getUserByMail(utilisateurDTO.getMail());
        if (utilisateurExistant != null) {
            logger.info("utilisateurExistant:" + utilisateurExistant);
            authenticationRequest.username = utilisateurExistant.getUsername();
            logger.info("authenticationRequest.username:" + authenticationRequest.username);
            authenticationRequest.password = utilisateurDTO.getPassword();
            logger.info("authenticationRequest.password:" + authenticationRequest.password);
        }


        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password);
        logger.info("usernamePasswordAuthenticationToken:" + usernamePasswordAuthenticationToken);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        logger.info("authentication:" + authentication);
        if (authentication != null && authentication.isAuthenticated() && utilisateurExistant != null) {
                logger.info("ici4");
                if (passwordEncoder.matches(utilisateurDTO.getPassword(), utilisateurExistant.getPassword()) == true) {
                    JwtTokens tokens = jwtTokenService.createTokens(authentication);
                    logger.info("tokens: " + tokens);
                    return ResponseEntity.ok().body(tokens);
            }
        }
        logger.info("ici5");
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
