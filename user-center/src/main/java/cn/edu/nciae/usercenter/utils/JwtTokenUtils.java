package cn.edu.nciae.usercenter.utils;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;


/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/4 1:42 PM
 */
@Slf4j
@Component
public class JwtTokenUtils {

    private static final String AUTHORITIES_KEY = "auth";

    /**
     * secret key
     */
    private static final String SECRET_STRING = "SecretKey012345678901234567890123456789012345678901234567890123456789";

    private SecretKey secretKey;

    /**
     * Token validity time(ms)
     */
    private long tokenValidityInMilliseconds;

    public JwtTokenUtils() {
        this.secretKey = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());
        this.tokenValidityInMilliseconds = 1000 * 60 * 30L;
    }

    /**
     * desc : Create token
     * @param authentication auth info
     * @return token
     */
    public String createToken(Authentication authentication) {
        //Current time
        long now = (new Date()).getTime();
        Date validity;
        validity = new Date(now + this.tokenValidityInMilliseconds);

         // create token
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setHeaderParam("typ", "JWT")
                .claim(AUTHORITIES_KEY, "")
                .setIssuedAt(new Date(now))
                .setExpiration(validity)
                .signWith(secretKey)
                .compact();
    }

    /**
     * desc : Get auth Info
     * @param token token
     * @return auth info
     */
    public Authentication getAuthentication(String token) {
        // parse the payload of token
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get(AUTHORITIES_KEY));
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    /**
     * desc : validate token
     * @param token token
     * @return whether valid
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }
}
