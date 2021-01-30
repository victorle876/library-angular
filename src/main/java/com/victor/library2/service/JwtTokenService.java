package com.victor.library2.service;

import com.victor.library2.model.dto.UserDto;
import com.victor.library2.model.entity.JwtTokens;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Calendar;

@Service
public class JwtTokenService {
    private static final String USER_SECRET = "userSecret";

    @Autowired
    private UserDetailsServiceImpl userService;

    @Value("${token.secret}")
    private String secret;

    public JwtTokens createTokens(Authentication authentication) {

        String token;
        String refreshToken;

        UserDto user = (UserDto) authentication.getPrincipal();
        System.out.println("user test:  " + user);
        token = createToken(user);
        System.out.println("token cree:  " + token);
        refreshToken = createRefreshToken(user);
        System.out.println("token refresh:  " + refreshToken);
        return new JwtTokens(token, refreshToken);
    }

    public String createToken(UserDto user) {

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secret)
                .setClaims(buildUserClaims(user))
                .setExpiration(getTokenExpirationDate(false))
                .setIssuedAt(new Date())
                .compact();
    }

    public String createRefreshToken(UserDto user) {

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secret)
                .setClaims(buildUserClaims(user))
                .setExpiration(getTokenExpirationDate(true))
                .setIssuedAt(new Date())
                .compact();
    }

    public Jws<Claims> validateJwtToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
    }

    public JwtTokens refreshJwtToken(String refreshToken) {
        Jws<Claims> claims = validateJwtRefreshToken(refreshToken);
        String newToken = createTokenFromClaims(claims);
        return new JwtTokens(newToken, refreshToken);
    }

    private String createTokenFromClaims(Jws<Claims> jws) {

        return Jwts.builder()
                .setClaims(jws.getBody())
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(getTokenExpirationDate(false))
                .setIssuedAt(new Date())
                .compact();
    }

    private Jws<Claims> validateJwtRefreshToken(String token) {
        JwtParser parser = Jwts.parser().setSigningKey(secret);
        Jws<Claims> claims = parser.parseClaimsJws(token);

        UserDto user = (UserDto) userService.loadUserByUsername(claims.getBody().getSubject());
        System.out.println("token refresh2:  " + token);

        return parser.require(USER_SECRET, user.getUserSecret()).parseClaimsJws(token);
    }

    private Date getTokenExpirationDate(boolean refreshToken) {
        Calendar calendar = Calendar.getInstance();

        if(refreshToken) {
            calendar.add(Calendar.MONTH, 1);
        } else {
            calendar.add(Calendar.MINUTE, 5);
        }

        return calendar.getTime();
    }

    private Claims buildUserClaims(UserDto user) {
        Claims claims = new DefaultClaims();

        claims.setSubject(String.valueOf(user.getId()));
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("roles", String.join(",", AuthorityUtils.authorityListToSet(user.getAuthorities())));
        claims.put(USER_SECRET, user.getUserSecret());

        return claims;
    }

}
