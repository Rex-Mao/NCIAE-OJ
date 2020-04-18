package cn.edu.nciae.contentcenter.security.service;

import cn.edu.nciae.contentcenter.common.entity.SystemUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/18 10:31 PM
 */
@Component("rbacauthorityservice")
public class RbacAuthorityService {
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object userInfo = authentication.getPrincipal();

        boolean hasPermission  = false;

        if (userInfo instanceof SystemUserDetails) {

            SystemUserDetails systemUserDetails = (SystemUserDetails) userInfo;
            String username = systemUserDetails.getUsername();

            //get the resource and filter
            Set<String> urlResources = (Set<String>) systemUserDetails.getUrlResources().getUrlResources();
            AntPathMatcher antPathMatcher = new AntPathMatcher();
            for (String url : urlResources) {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }
            return hasPermission;
        } else {
            return false;
        }
    }
}

