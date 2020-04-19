package cn.edu.nciae.contentcenter.security.handler;

import cn.edu.nciae.contentcenter.common.vo.MessageVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/19 6:18 AM
 */
@Slf4j
@Component
public class AuthorityAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        String errorCode = null;
        if (accessDeniedException.getMessage() != null) {
            errorCode = "403";
        }
        response.getWriter().write(
                JSON.toJSONString(MessageVO.<String>builder()
                        .error(errorCode)
                        .data(accessDeniedException.getMessage())
                        .build(), SerializerFeature.WriteMapNullValue));
    }
}
