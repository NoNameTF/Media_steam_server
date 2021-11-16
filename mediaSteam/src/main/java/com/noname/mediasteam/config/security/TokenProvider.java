package com.noname.mediasteam.config.security;

import com.noname.mediasteam.config.properties.AppProperties;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Component
public class TokenProvider {

    private final AppProperties appProperties;

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String tokenSecret = appProperties.getAuth().getTokenSecret();

        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime expiryDateTime = nowDateTime.plusNanos(appProperties.getAuth().getTokenExpirationMsec());

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(Timestamp.valueOf(nowDateTime))
                .setExpiration(Timestamp.valueOf(expiryDateTime))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        String tokenSecret = appProperties.getAuth().getTokenSecret();

        Claims claims = Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        String tokenSecret = appProperties.getAuth().getTokenSecret();

        try {
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT Token.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT Token.");
        } catch (IllegalArgumentException e) {
            log.error("JWT Claims string is empty.");
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }

        return false;
    }

}
