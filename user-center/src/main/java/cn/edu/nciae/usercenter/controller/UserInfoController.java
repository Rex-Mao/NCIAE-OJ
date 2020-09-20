package cn.edu.nciae.usercenter.controller;


import cn.edu.nciae.usercenter.common.dto.PageParametersDTO;
import cn.edu.nciae.usercenter.common.dto.UserInfoDTO;
import cn.edu.nciae.usercenter.common.entity.Role;
import cn.edu.nciae.usercenter.common.entity.UserInfo;
import cn.edu.nciae.usercenter.common.entity.UserRole;
import cn.edu.nciae.usercenter.common.vo.*;
import cn.edu.nciae.usercenter.service.IRoleService;
import cn.edu.nciae.usercenter.service.IUserInfoService;
import cn.edu.nciae.usercenter.service.IUserRoleService;
import cn.edu.nciae.usercenter.utils.PasswordUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  Controller
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
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private PasswordUtils passwordUtils;

    /**
     * desc : get user info by inner system
     * @param uid -
     * @return UserInfoDTO
     */
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
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getNickname, username));
        filterSensitiveInformation(userInfo);
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
    public MessageVO<UserVO> updateProfile(Authentication authentication,
                                           UserInfo changedInfo) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        UserInfo changed = userInfoService.updateUserProfile(changedInfo, username);
        filterSensitiveInformation(changed);
        return MessageVO.<UserVO>builder()
                .error(null)
                .data(UserVO.builder()
                        .user(changed)
                        .build())
                .build();
    }

    /**
     * desc : update profile password
     * @param authentication -
     * @param oldPassword -
     * @param newPassword -
     * @return MessageVO<String>
     */
    @PutMapping("/profile/password")
    public MessageVO<String> updateProfilePassword(Authentication authentication,
                                                   @RequestParam("old_password") String oldPassword,
                                                   @RequestParam("new_password") String newPassword) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getNickname, username));
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

    /**
     * desc : update profile email
     * @param authentication -
     * @param password -
     * @param oldEmail -
     * @param newEmail -
     * @return MessageVO<String>
     */
    @PutMapping("/profile/email")
    public MessageVO<String> updateProfileEmail(Authentication authentication,
                                                   @RequestParam("password") String password,
                                                   @RequestParam("old_email") String oldEmail,
                                                   @RequestParam("new_email") String newEmail) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getNickname, username));
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

    /**
     * desc : get user list
     * @param pageParametersDTO - page
     * @return MessageVO<UserListVO>
     */
    @GetMapping("/admin/users")
    public MessageVO<UserListVO> getAdminUserList(PageParametersDTO pageParametersDTO) {
        UserListVO users = getPagingProblemListVO(pageParametersDTO);
        return MessageVO.<UserListVO>builder()
                .error(null)
                .data(users)
                .build();
    }

    /**
     * desc : get user info by admin
     * @param uid -
     * @return MessageVO<UserVO>
     */
    @GetMapping("/admin/user/{uid}")
    public MessageVO<UserVO> getAdminUserByUid(@PathVariable("uid") Long uid) {
        UserInfo userInfo = userInfoService.getById(uid);
        userInfo.setPassword(null);
        userInfo.setAvatar(null);
        userInfo.setAvatarUrl(null);
        List<UserRole> userRoles = userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUid, userInfo.getUid()));
        Set<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        List<Role> roles = null;
        if (roleIds.size() != 0) {
            roles = roleService.listByIds(roleIds);
        }
        return MessageVO.<UserVO>builder()
                .error(null)
                .data(UserVO.builder()
                    .user(userInfo)
                    .roles(roles)
                    .build())
                .build();
    }

    /**
     * desc : check username or email exist
     * @param userInfo -
     * @return MessageVO<Boolean>
     */
    @PostMapping("/public/check_username_or_email")
    public MessageVO<UsernameEmailValidateVO> checkUsernameOrEmailExist(@RequestBody UserInfo userInfo) {
        UsernameEmailValidateVO result = UsernameEmailValidateVO.builder().nickname(false).email(false).build();
        if (userInfo.getNickname() != null) {
            UserInfo userTemp = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getNickname, userInfo.getNickname()));
            if (userTemp != null) {
                result.setNickname(true);
            }
        }
        if (userInfo.getEmail() != null) {
            UserInfo userTemp = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getEmail, userInfo.getEmail()));
            if (userTemp != null) {
                result.setEmail(true);
            }
        }
        return MessageVO.<UsernameEmailValidateVO>builder()
                .error(null)
                .data(result)
                .build();
    }

    /**
     * desc : registry
     * @param userInfo -
     * @return MessageVO<Boolean>
     */
    @PostMapping("/public/register")
    public MessageVO<Long> register(@RequestBody UserInfo userInfo) {
        UserInfo registerUser = userInfoService.saveRegisterUserInfo(userInfo);
        return MessageVO.<Long>builder()
                .error(null)
                .data(registerUser.getUid())
                .build();
    }

    /**
     * desc : get user list view object with paging.
     * @param pageParametersDTO - parameters
     * @return ProblemListVO
     */
    private UserListVO getPagingProblemListVO(PageParametersDTO pageParametersDTO) {
        Page<UserInfoVO> page;
        IPage<UserInfoVO> users = new Page<>();
        if (pageParametersDTO.getPage() != null){
            page = new Page<UserInfoVO>(pageParametersDTO.getPage(), pageParametersDTO.getLimit());
        } else {
            page = new Page<UserInfoVO>(1, pageParametersDTO.getLimit());
        }
        if (pageParametersDTO.getKeyword() != null && !"".equals(pageParametersDTO.getKeyword())) {
            users = userInfoService.listUsersByPagingWithKeyword(page, pageParametersDTO.getKeyword());
        } else {
            users = userInfoService.listUsersByPaging(page);
        }
        users.getRecords().forEach(this::filterSensitiveInformation);
        return UserListVO.builder()
                .results(users.getRecords())
                .total(users.getTotal())
                .build();
    }

    /**
     * desc : filter sensitive information
     * @param userInfo -
     * @return UserInfo
     */
    private UserInfo filterSensitiveInformation(UserInfo userInfo) {
        userInfo.setPassword(null);
        return userInfo;
    }

}
