package cn.edu.nciae.usercenter.service;

import cn.edu.nciae.usercenter.common.entity.UserInfo;
import cn.edu.nciae.usercenter.common.vo.UserInfoVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    /**
     * desc : list users by paging
     * @param page - Page Object
     * @return IPage<UserInfoVO>
     */
    IPage<UserInfoVO> listUsersByPaging(Page<UserInfoVO> page);

    /**
     * desc : list users by paging with keyword
     * @param page -
     * @param keyword -
     * @return IPage<UserInfoVO>
     */
    IPage<UserInfoVO> listUsersByPagingWithKeyword(Page<UserInfoVO> page, String keyword);
}
