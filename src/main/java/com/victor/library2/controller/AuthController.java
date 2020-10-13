package com.victor.library2.controller;

import com.victor.library2.payload.request.LoginRequest;
import com.victor.library2.payload.request.SignupRequest;
import com.victor.library2.payload.response.JwtResponse;
import com.victor.library2.payload.response.MessageResponse;
import com.victor.library2.repository.UtilisateurRepository;
import com.victor.library2.security.jwt.JwtUtils;
import com.victor.library2.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

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
}
