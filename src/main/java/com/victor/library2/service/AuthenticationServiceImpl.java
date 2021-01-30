package com.victor.library2.service;

import com.victor.library2.controller.AuthController;
import com.victor.library2.model.entity.AuthenticationRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(AuthenticationServiceImpl.class);

    @Override
    public Authentication authenticate(AuthenticationRequest authenticationRequest) {
        logger.info("authenticationRequest :" +authenticationRequest);
        UsernamePasswordAuthenticationToken usernameAuthentication = new UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password);
        logger.info("usernameAuthentication :" +usernameAuthentication);
        return authenticationManager.authenticate(usernameAuthentication);
    }

    @Override
    public Authentication getAuthentication(Jws<Claims> token) {
        return new UsernamePasswordAuthenticationToken(token.getBody().getSubject(), "PROTECTED",
                AuthorityUtils.commaSeparatedStringToAuthorityList(token.getBody().get("roles", String.class)));
    }
}
