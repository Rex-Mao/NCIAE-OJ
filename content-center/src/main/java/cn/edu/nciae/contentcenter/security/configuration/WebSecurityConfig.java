package cn.edu.nciae.contentcenter.security.configuration;

import cn.edu.nciae.contentcenter.security.entrypoint.JwtAuthenticationFailureEntryPoint;
import cn.edu.nciae.contentcenter.security.filter.JwtAuthenticationFilter;
import cn.edu.nciae.contentcenter.security.handler.JwtAuthenticationFailureHandler;
import cn.edu.nciae.contentcenter.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/4 5:48 PM
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String AUTHORIZATION_TOKEN = "access_token";

    @Autowired
    private JwtAuthenticationFailureEntryPoint jwtAuthenticationFailureEntryPoint;

    @Autowired
    private JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;

    @Autowired
    private JwtTokenUtils tokenProvider;

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().antMatchers("/public/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(tokenProvider, jwtAuthenticationFailureHandler);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests()
            .anyRequest().access("@rbacauthorityservice.hasPermission(httpServletRequest, authentication)")
            .anyRequest().authenticated().and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationFailureEntryPoint).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .csrf().disable();
        // disable cache
        http.headers().cacheControl();
    }
}
