package be.digitalcity.projetspringrest.utils;
//???????????????????

import be.digitalcity.projetspringrest.models.dtos.TokenDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@Slf4j
@Component
public class JwtProvider {

    private final JwtProperties properties;
    private final UserDetailsService service;

    public JwtProvider(JwtProperties properties, UserDetailsService service) {
        this.properties = properties;
        this.service = service;
    }

    public TokenDto createToken(Authentication auth){
        return new TokenDto(JWT.create()
                // Declarer les claims du payload
                .withExpiresAt( Instant.now().plus(properties.getExpiresAt(), ChronoUnit.DAYS) )
                .withSubject(auth.getName())
                .withClaim(
                        "roles",
                        auth.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList()
                )
                // Declarer la signature
                .sign( Algorithm.HMAC512(properties.getSecret()) )
        );
    }
    public String extractToken(HttpServletRequest request){
        String authHeader = request.getHeader(properties.getHeaderKey());
        return authHeader == null ? null : authHeader.replace(properties.getHeaderPrefix(), "");
    }
    public boolean validate(String token) {
        try{
            DecodedJWT decodedJWT = JWT.require( Algorithm.HMAC512(properties.getSecret()) )
                    .acceptExpiresAt(properties.getExpiresAt())
                    .build().verify(token);
            return true;
        }
        catch (JWTVerificationException ex){
            log.warn(ex.getMessage());
            return false;
        }

    }
    public Authentication generateAuth(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);

        UserDetails user = service.loadUserByUsername( decodedJWT.getSubject() );

        return new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(),null);
    }
}