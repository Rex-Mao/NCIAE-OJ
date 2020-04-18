package cn.edu.nciae.contentcenter.utils;

import cn.edu.nciae.contentcenter.common.dto.ClaimsDTO;
import cn.edu.nciae.contentcenter.common.entity.SystemUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/4 1:42 PM
 */
@Slf4j
@Component
public class JwtTokenUtils {

    @Autowired
    UrlUtils urlUtils;

    private static final String AUTHORITIES_KEY = "auth";

    private static final String RESOURCEES_KEY = "public";

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
    public String createToken(Authentication authentication) throws Exception {
        // Current time
        long now = (new Date()).getTime();
        Date validity;
        validity = new Date(now + this.tokenValidityInMilliseconds);

        // Resource url decode
        List<String> resources = (List<String>) ((SystemUserDetails) authentication.getPrincipal()).getUrlResources().getUrlResources();
        List<String> encodedUrls = resourceEncoder(resources);
        ClaimsDTO claimsDTO = ClaimsDTO.builder().urlResources(encodedUrls).build();

        // Create token
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setHeaderParam("typ", "JWT")
                .claim(AUTHORITIES_KEY, authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(", ")))
                .claim(RESOURCEES_KEY, claimsDTO)
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
        ClaimsDTO claimsDTO = ClaimsDTO.builder()
                .urlResources((List)((LinkedHashMap) claims.get(RESOURCEES_KEY)).get("urlResources"))
                .build();
        SystemUserDetails principal = new SystemUserDetails(claims.getSubject(), "", authorities, claimsDTO);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    /**
     * desc : validate token
     * @param token token
     * @return whether valid
     */
    public boolean validateToken(String token) throws AuthenticationServiceException{
        try {
            Jwts.parserBuilder().setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.info("Invalid JWT signature.");
            throw new AuthenticationServiceException("Please login again... Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            throw new AuthenticationServiceException("Please login again... Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            throw new AuthenticationServiceException("Please login again... Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            throw new AuthenticationServiceException("Please login again... Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            throw new AuthenticationServiceException("Please login again... JWT token compact of handler are invalid.");
        }
    }

    /**
     * desc : encode the resource
     * @param resources - Resource
     * @return List<String>
     */
    private List<String> resourceEncoder(List<String> resources) throws Exception {
        List<String> results = new ArrayList<>();
        for (String resource : resources) {
            results.add(urlUtils.decrypt(resource));
        }
        return results;
    }

    /**
     * desc : encode the resource
     * @param resources - Resource
     * @return List<String>
     */
    private List<String> resourceDecoder(List<String> resources) throws Exception {
        List<String> results = new ArrayList<>();
        for (String resource : resources) {
            results.add(urlUtils.encrypt(resource));
        }
        return results;
    }
}
