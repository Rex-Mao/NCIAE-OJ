package cn.edu.nciae.contentcenter.security.filter;

import cn.edu.nciae.contentcenter.security.configuration.WebSecurityConfig;
import cn.edu.nciae.contentcenter.security.handler.JwtAuthenticationFailureHandler;
import cn.edu.nciae.contentcenter.utils.JwtTokenUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/6 2:42 PM
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer ";

    private JwtTokenUtils tokenProvider;

    private JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;

    public JwtAuthenticationFilter(JwtTokenUtils jwtTokenUtils, JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler) {
        super();
        this.tokenProvider = jwtTokenUtils;
        this.jwtAuthenticationFailureHandler = jwtAuthenticationFailureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            String jsonWebToken = resolveToken(httpServletRequest);
            if (jsonWebToken != null && !"".equals(jsonWebToken.trim()) && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (this.tokenProvider.validateToken(jsonWebToken)) {
                    // get auth info
                    Authentication authentication = this.tokenProvider.getAuthentication(jsonWebToken);
                    // save user info to securityContext
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (AuthenticationServiceException ex) {
            SecurityContextHolder.clearContext();
            jwtAuthenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, ex);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * desc : get the token from header or data
     * @param request -
     * @return String jsonWebToken
     */
    private String resolveToken(HttpServletRequest request) throws AuthenticationException {
        String bearerToken = request.getHeader(WebSecurityConfig.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(7, bearerToken.length());
        }
        String jsonWebToken = request.getParameter(WebSecurityConfig.AUTHORIZATION_TOKEN);
        if (StringUtils.hasText(jsonWebToken)) {
            return jsonWebToken;
        }
        throw new AuthenticationServiceException("Please Login... You can request the resources when it finished !");
    }
}
