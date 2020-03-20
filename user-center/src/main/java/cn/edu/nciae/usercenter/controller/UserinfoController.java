package cn.edu.nciae.usercenter.controller;


import cn.edu.nciae.usercenter.common.dto.UserinfoDTO;
import cn.edu.nciae.usercenter.common.entity.Userinfo;
import cn.edu.nciae.usercenter.common.vo.MessageVO;
import cn.edu.nciae.usercenter.service.IUserinfoService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author RexALun
 * @since 2019-12-26
 */
@Slf4j
@RestController
public class UserinfoController {

    @Autowired
    private IUserinfoService userinfoService;

    @GetMapping("/userinfo/{userid}")
    public UserinfoDTO getUserinfo(@PathVariable("userid") Long uid) {
        Userinfo userinfo = userinfoService.getOne(Wrappers.<Userinfo>lambdaQuery().eq(Userinfo::getUid, uid));
        UserinfoDTO userinfoDTO = UserinfoDTO.builder()
                .userid(userinfo.getUid())
                .regTime(userinfo.getRegtime())
                .solvedNum(userinfo.getSolvednum())
                .build();
        log.info("The handler was be used...");
        return userinfoDTO;
    }

    @GetMapping("/profile")
    public MessageVO<Userinfo> getProfile() {
        return MessageVO.<Userinfo>builder()
                .error(null)
                .data(null)
                .build();
    }
}
