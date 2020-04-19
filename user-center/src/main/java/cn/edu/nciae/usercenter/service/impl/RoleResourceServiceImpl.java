package cn.edu.nciae.usercenter.service.impl;

import cn.edu.nciae.usercenter.common.entity.RoleResource;
import cn.edu.nciae.usercenter.common.mapper.RoleResourceMapper;
import cn.edu.nciae.usercenter.common.vo.RoleResourceVO;
import cn.edu.nciae.usercenter.service.IRoleResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author RexALun
 * @since 2019-12-26
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements IRoleResourceService {

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    /**
     * desc : get all resources of Role by role id
     * @param roleId - role id
     * @return List<RoleResourceVO>
     */
    @Override
    public List<RoleResourceVO> listRoleResoucesByRoleId(Long roleId) {
        return roleResourceMapper.listRoleResourceVOByRoleId(roleId);
    }
}
