package cn.edu.nciae.usercenter.controller;


import cn.edu.nciae.usercenter.common.dto.UserInfoDTO;
import cn.edu.nciae.usercenter.common.entity.UserInfo;
import cn.edu.nciae.usercenter.common.vo.MessageVO;
import cn.edu.nciae.usercenter.common.vo.UserVO;
import cn.edu.nciae.usercenter.service.IUserInfoService;
import cn.edu.nciae.usercenter.utils.PasswordUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

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
public class UserInfoController {

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private PasswordUtils passwordUtils;

    @GetMapping("/userInfo/{userid}")
    public UserInfoDTO getUserInfo(@PathVariable("userid") Long uid) {
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUid, uid));
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .userid(userInfo.getUid())
                .regTime(userInfo.getRegtime())
                .solvedNum(userInfo.getSolvednum())
                .build();
        log.info("The handler was be used...");
        return userInfoDTO;
    }

    /**
     * desc : get the user view object
     * @param authentication - Authentication information
     * @return MessageVO<UserVO>
     */
    @GetMapping("/profile")
    public MessageVO<UserVO> getProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getNickname, user.getUsername()));
        userInfo.setPassword(null);
        return MessageVO.<UserVO>builder()
                .error(null)
                .data(UserVO.builder()
                        .user(userInfo)
                        .build())
                .build();
    }

    /**
     * desc : update the profile
     * @param authentication - Authentication information
     * @return MessageVO<String>
     */
    @PutMapping("/profile")
    public MessageVO<UserInfo> updateProfile(Authentication authentication,
                                           UserInfo changedInfo) {
        User user = (User) authentication.getPrincipal();
        UserInfo changed = userInfoService.updateUserProfile(changedInfo, user.getUsername());
        return MessageVO.<UserInfo>builder()
                .error(null)
                .data(changed)
                .build();
    }

    @PutMapping("/profile/password")
    public MessageVO<String> updateProfilePassword(Authentication authentication,
                                                   @RequestParam("old_password") String oldPassword,
                                                   @RequestParam("new_password") String newPassword) {
        User user = (User) authentication.getPrincipal();
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getNickname, user.getUsername()));
        if (!passwordUtils.matches(oldPassword.subSequence(0, oldPassword.length()), userInfo.getPassword())) {
            return MessageVO.<String>builder()
                    .error("403")
                    .data("Old password is not invalid.")
                    .build();
        } else {
            userInfo.setPassword(passwordUtils.encode(newPassword));
            userInfoService.update(userInfo, Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUid, userInfo.getUid()));
            return MessageVO.<String>builder()
                    .error(null)
                    .data("Change password is done.")
                    .build();
        }
    }

    @PutMapping("/profile/email")
    public MessageVO<String> updateProfileEmail(Authentication authentication,
                                                   @RequestParam("password") String password,
                                                   @RequestParam("old_email") String oldEmail,
                                                   @RequestParam("new_email") String newEmail) {
        User user = (User) authentication.getPrincipal();
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getNickname, user.getUsername()));
        if (!passwordUtils.matches(password.subSequence(0, password.length()), userInfo.getPassword()) ||
            !oldEmail.equals(userInfo.getEmail())) {
            return MessageVO.<String>builder()
                    .error("403")
                    .data("Authorization information is not invalid.")
                    .build();
        } else {
            userInfo.setEmail(newEmail);
            userInfoService.update(userInfo, Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUid, userInfo.getUid()));
            return MessageVO.<String>builder()
                    .error(null)
                    .data("Change email is done.")
                    .build();
        }
    }
}
