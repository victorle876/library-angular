package com.victor.library2.service;

import com.victor.library2.model.entity.AuthenticationRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    Authentication getAuthentication(Jws<Claims> request);
    Authentication authenticate(AuthenticationRequest authenticationRequest);
}
