package cn.edu.nciae.usercenter.security.endpoint;

import cn.edu.nciae.usercenter.common.vo.MessageVO;
import cn.edu.nciae.usercenter.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/9 1:12 AM
 */
@RestController
public class WebTokenEndpoint {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    /**
     * desc : login
     * @param authentication -
     * @return MessageVO<String>
     */
    @PostMapping("/token/refresh")
    public MessageVO<String> refreshToken(Authentication authentication) throws Exception {
        return MessageVO.<String>builder()
                .error(null)
                .data(jwtTokenUtils.createToken(authentication))
                .build();
    }
}
