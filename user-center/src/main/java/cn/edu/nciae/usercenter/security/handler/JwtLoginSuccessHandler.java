package cn.edu.nciae.usercenter.security.handler;

import cn.edu.nciae.usercenter.common.vo.MessageVO;
import cn.edu.nciae.usercenter.utils.JwtTokenUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/9 6:58 PM
 */
@Slf4j
@Component
public class JwtLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtTokenUtils tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        log.debug("Authentication Success.");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter()
                .write(JSON.toJSONString(MessageVO.<String>builder()
                        .error(null)
                        .data(tokenProvider.createToken(authentication))
                        .build(), PrettyFormat, WriteMapNullValue));
    }
}
