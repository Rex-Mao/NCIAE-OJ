package cn.edu.nciae.usercenter.service.impl;

import cn.edu.nciae.usercenter.common.entity.UserRole;
import cn.edu.nciae.usercenter.common.mapper.UserRoleMapper;
import cn.edu.nciae.usercenter.service.IUserRoleService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author RexALun
 * @since 2019-12-26
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * desc : delete all user role by uids
     * @param uids -
     * @return Boolean
     */
    @Override
    public Boolean removeByUids(ArrayList<Long> uids) {
        userRoleMapper.delete(Wrappers.<UserRole>lambdaQuery().in(UserRole::getUid, uids));
        return true;
    }
}
