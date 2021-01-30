package com.victor.library2.service;

import com.victor.library2.model.dto.UserDto;
import com.victor.library2.model.dto.UtilisateurDTO;
import com.victor.library2.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UtilisateurService utilisateurService;

    @Override
    public UserDto loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("username: " + username);
        UtilisateurDTO utilisateurTrouve = this.utilisateurService.getUserByUsername(username);
        System.out.println("utilisateurTrouve: " + utilisateurTrouve);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));

        return new UserDto(1L,utilisateurTrouve.getUsername(),utilisateurTrouve.getPassword(), utilisateurTrouve.getMail(),
                authorities, true, LocalDate.now(), "saltquisertarien");
    }
}
