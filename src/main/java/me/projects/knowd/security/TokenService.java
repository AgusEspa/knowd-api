package me.projects.knowd.security;

import me.projects.knowd.entities.Token;
import me.projects.knowd.exceptions.InvalidTokenException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import me.projects.knowd.repositories.TokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Service
public class TokenService {

    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    public TokenService(UserDetailsService userDetailsService, TokenRepository tokenRepository) {
        this.userDetailsService = userDetailsService;
        this.tokenRepository = tokenRepository;
    }

    public Token addTokenToBlacklist(Token token) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        verifier.verify(token.getTokenString());
        return tokenRepository.save(token);
    }

    public void verifyTokenNotBlacklisted(String token) {
        List<Token> tokensFetched = tokenRepository.findAll();
        for (Token tk : tokensFetched) {
            if (tk.getTokenString().equals(token))
                throw new InvalidTokenException("Revoked token");
        }
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);

                verifyTokenNotBlacklisted(refresh_token);

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.HOUR, 1);
                String username = decodedJWT.getSubject();
                UserDetails user = userDetailsService.loadUserByUsername(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(cal.getTime())
                        .withClaim("roles", user.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    public String generatePasswordToken(String username) {
        UserDetails user = userDetailsService.loadUserByUsername(username);
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        String passwordToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withClaim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);

        return passwordToken;
    }
}
