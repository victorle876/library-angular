package com.victor.library2.controller;

import com.victor.library2.model.dto.UtilisateurDTO;
import com.victor.library2.model.entity.Utilisateur;
import com.victor.library2.repository.UtilisateurRepository;
import com.victor.library2.service.UtilisateurService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String pwd) {
        UtilisateurDTO utilisateurExistant = this.utilisateurService.getUserByUsername(username);
        if (utilisateurExistant == null) {
            System.out.println("utilisateur non existant");
        }
        String token = getJWTToken(username);
        System.out.println(username);
        System.out.println(pwd);
        System.out.println(passwordEncoder.matches(pwd, utilisateurExistant.getPassword()));
        System.out.println(utilisateurExistant);
        if (passwordEncoder.matches(pwd, utilisateurExistant.getPassword()) == true) {
            Utilisateur utilisateur = convertToEntity(utilisateurExistant);
            System.out.println(token);
            utilisateur.setUsername(username);
            utilisateur.setToken(token);
        }
        return token;
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        System.out.println(token);

        return "Bearer " + token;
    }

    @PostMapping("/logout")
    private boolean logout(@RequestParam("username") String username) {
        Boolean utilisateurConnecte = this.utilisateurRepository.existsByUsername(username);
        return true;
    }

    private Utilisateur convertToEntity(UtilisateurDTO utilisateurDTO) throws ParseException {
        ModelMapper mapper = new ModelMapper();
        Utilisateur utilisateur = mapper.map(utilisateurDTO, Utilisateur.class);
        return utilisateur;
    }
}
