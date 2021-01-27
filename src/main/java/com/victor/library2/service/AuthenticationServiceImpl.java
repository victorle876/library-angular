package com.victor.library2.service;

import com.victor.library2.model.entity.AuthenticationRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Authentication authenticate(AuthenticationRequest authenticationRequest) {
        System.out.println("authenticationRequest :" +authenticationRequest);
        UsernamePasswordAuthenticationToken usernameAuthentication = new UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password);
        System.out.println("usernameAuthentication :" +usernameAuthentication);
        return authenticationManager.authenticate(usernameAuthentication);
    }

    @Override
    public Authentication getAuthentication(Jws<Claims> token) {
        return new UsernamePasswordAuthenticationToken(token.getBody().getSubject(), "PROTECTED",
                AuthorityUtils.commaSeparatedStringToAuthorityList(token.getBody().get("roles", String.class)));
    }
}
