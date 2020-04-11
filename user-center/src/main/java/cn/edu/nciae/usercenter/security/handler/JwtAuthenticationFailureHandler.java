package cn.edu.nciae.usercenter.security.handler;

import cn.edu.nciae.usercenter.common.vo.MessageVO;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/9 6:11 AM
 */
@Component
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String failureJson = JSON.toJSONString(MessageVO.<String>builder()
                                        .error("401")
                                        .data("Please Login... Authentication Failed...")
                                        .build());
        httpServletResponse.getWriter().write(failureJson);
    }
}
