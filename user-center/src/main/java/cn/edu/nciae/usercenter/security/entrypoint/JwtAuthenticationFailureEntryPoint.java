package cn.edu.nciae.usercenter.security.entrypoint;

import cn.edu.nciae.usercenter.common.vo.MessageVO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/6 4:38 PM
 */
@Slf4j
@Component
public class JwtAuthenticationFailureEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        log.info("Authentication Failure.");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter()
                .write(JSON.toJSONString(MessageVO.<String>builder()
                        .error("401")
                        .data("Please Login... Authentication Failed...")
                        .build()));
    }
}
