package cn.edu.nciae.usercenter.security.provider;

import cn.edu.nciae.usercenter.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/6 2:12 PM
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordUtils passwordUtils;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        UserDetails userDetails = null;
        if(username != null) {
            // get the user information from service
            userDetails = userDetailsService.loadUserByUsername(username);
        }

        if(userDetails == null) {
            throw new UsernameNotFoundException("Username or Password is invalid");
        }else if (!userDetails.isEnabled()){
            throw new DisabledException("User is locked.");
        }else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("Account is expired.");
        }
        if (!passwordUtils.matches(password.subSequence(0, password.length()), userDetails.getPassword())) {
            throw new BadCredentialsException("Username or Password is invalid.");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password,userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
