package cn.edu.nciae.usercenter.security.filter;

import cn.edu.nciae.usercenter.security.configuration.WebSecurityConfig;
import cn.edu.nciae.usercenter.utils.JwtTokenUtils;
import org.springframework.security.core.Authentication;
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

    public JwtAuthenticationFilter(JwtTokenUtils jwtTokenUtils) {
        super();
        this.tokenProvider = jwtTokenUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String jsonWebToken = resolveToken(httpServletRequest);
        if (jsonWebToken != null && !"".equals(jsonWebToken.trim()) && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (this.tokenProvider.validateToken(jsonWebToken)) {
                 // get auth info
                Authentication authentication = this.tokenProvider.getAuthentication(jsonWebToken);
                 // save user info to securityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * desc : get the token from header or data
     * @param request -
     * @return String jsonWebToken
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(WebSecurityConfig.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(7, bearerToken.length());
        }
        String jsonWebToken = request.getParameter(WebSecurityConfig.AUTHORIZATION_TOKEN);
        if (StringUtils.hasText(jsonWebToken)) {
            return jsonWebToken;
        }
        return null;
    }
}
