package cn.edu.nciae.usercenter.service.impl;

import cn.edu.nciae.usercenter.common.entity.UserInfo;
import cn.edu.nciae.usercenter.common.mapper.RoleMapper;
import cn.edu.nciae.usercenter.common.mapper.UserInfoMapper;
import cn.edu.nciae.usercenter.common.mapper.UserRoleMapper;
import cn.edu.nciae.usercenter.common.vo.UserInfoVO;
import cn.edu.nciae.usercenter.service.IUserInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/4 11:14 PM
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * desc : update the profile by parameters
     * @param changed - changed profile (Language, Blog, Mood, Github)
     * @param username - username
     * @return UserInfo
     */
    @Override
    public UserInfo updateUserProfile(UserInfo changed, String username) {
        UserInfo userInfo = userInfoMapper.selectOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getNickname, username));
        if (changed.getLanguage() != null) {
            userInfo.setLanguage(changed.getLanguage());
        }
        if (changed.getBlog() != null) {
            userInfo.setBlog(changed.getBlog());
        }
        if (changed.getMood() != null) {
            userInfo.setMood(changed.getMood());
        }
        if (changed.getGithub() != null) {
            userInfo.setGithub(changed.getGithub());
        }
        userInfoMapper.updateById(userInfo);
        return userInfo;
    }

    /**
     * desc : list users by paging
     * @param page - Page Object
     * @return IPage<UserInfoVO>
     */
    @Override
    public IPage<UserInfoVO> listUsersByPaging(Page<UserInfoVO> page) {
        return userInfoMapper.listUserRolesByPaging(page);
    }
}
