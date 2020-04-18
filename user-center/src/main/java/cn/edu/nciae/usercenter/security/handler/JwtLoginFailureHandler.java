package cn.edu.nciae.usercenter.security.handler;

import cn.edu.nciae.usercenter.common.vo.MessageVO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
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
 * @date 2020/4/19 4:04 AM
 */
@Slf4j
@Component
public class JwtLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.debug("Authentication Fail.");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(
                JSON.toJSONString(MessageVO.<String>builder()
                        .error("401")
                        .data(e.getMessage())
                        .build())
        );
    }
}
