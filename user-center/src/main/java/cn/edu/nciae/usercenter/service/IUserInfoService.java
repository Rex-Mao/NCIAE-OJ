package cn.edu.nciae.usercenter.service;

import cn.edu.nciae.usercenter.common.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/4 11:14 PM
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * desc : update the profile by parameters
     * @param changed - changed profile (Language, Blog, Mood, Github)
     * @param username - username
     * @return UserInfo
     */
    UserInfo updateUserProfile(UserInfo changed, String username);
}
