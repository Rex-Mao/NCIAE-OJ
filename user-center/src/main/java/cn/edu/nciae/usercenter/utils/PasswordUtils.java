package cn.edu.nciae.usercenter.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/5 12:25 AM
 */
@Component
public class PasswordUtils implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return new BCryptPasswordEncoder().encode(charSequence);
    }

    /**
     * desc : match the password
     * @param charSequence - a char sequence of unencoded password
     * @param s - encoded password
     * @return boolean
     */
    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return new BCryptPasswordEncoder().matches(charSequence, s);
    }
}
