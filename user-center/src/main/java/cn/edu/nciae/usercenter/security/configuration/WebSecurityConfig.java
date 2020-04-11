package cn.edu.nciae.usercenter.security.configuration;

import cn.edu.nciae.usercenter.security.entrypoint.JwtAuthenticationFailureEntryPoint;
import cn.edu.nciae.usercenter.security.filter.JwtAuthenticationFilter;
import cn.edu.nciae.usercenter.security.filter.JwtLoginFilter;
import cn.edu.nciae.usercenter.security.handler.JwtLoginSuccessHandler;
import cn.edu.nciae.usercenter.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/4 5:48 PM
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String AUTHORIZATION_TOKEN = "access_token";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtLoginSuccessHandler jwtLoginSuccessHandler;

    @Autowired
    private JwtAuthenticationFailureEntryPoint jwtAuthenticationFailureEntryPoint;

    @Autowired
    private JwtTokenUtils tokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter(authenticationManager());
        jwtLoginFilter.setAuthenticationSuccessHandler(jwtLoginSuccessHandler);
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(tokenProvider);
        http.addFilterBefore(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests()
            .anyRequest().authenticated().and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationFailureEntryPoint).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .csrf().disable();
        // disable cache
        http.headers().cacheControl();
    }
}
