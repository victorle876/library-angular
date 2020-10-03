package com.victor.library2.service;

import com.victor.library2.model.entity.Utilisateur;
import com.victor.library2.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
    public class UserDetailsServiceImpl implements UserDetailsService {
        @Autowired
        UtilisateurRepository utilisateurRepository;

        @Override
        @Transactional
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Utilisateur utilisateur = utilisateurRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

            return UserDetailsImpl.build(utilisateur);
        }
}
