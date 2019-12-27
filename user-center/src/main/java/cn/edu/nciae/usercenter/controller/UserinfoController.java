package cn.edu.nciae.usercenter.controller;


import cn.edu.nciae.usercenter.dto.UserinfoDTO;
import cn.edu.nciae.usercenter.entity.Userinfo;
import cn.edu.nciae.usercenter.service.IUserinfoService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author RexALun
 * @since 2019-12-26
 */
@RestController
@Slf4j
@RequestMapping("/userinfo")
public class UserinfoController {

    @Autowired
    private IUserinfoService userinfoService;

    QueryWrapper<Userinfo> queryWrapper = new QueryWrapper<Userinfo>();

    @GetMapping("/{userid}")
    public UserinfoDTO getUserinfo(@PathVariable("userid") Long uid) {
        Userinfo userinfo = userinfoService.getOne((Wrapper<Userinfo>) queryWrapper.gt("uid", uid), false);
        UserinfoDTO userinfoDTO = UserinfoDTO.builder()
                .userid(userinfo.getUid())
                .regTime(userinfo.getRegtime())
                .solvedNum(userinfo.getSolvednum())
                .build();
        log.info("The handler was be used...");
        return userinfoDTO;
    }

}
