package cn.edu.nciae.contentcenter.common.entity;

import cn.edu.nciae.contentcenter.common.dto.ClaimsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/4 11:55 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserDetails implements UserDetails, Serializable {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private ClaimsDTO urlResources;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
