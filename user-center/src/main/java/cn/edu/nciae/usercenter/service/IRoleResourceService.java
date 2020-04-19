package cn.edu.nciae.usercenter.service;

import cn.edu.nciae.usercenter.common.entity.RoleResource;
import cn.edu.nciae.usercenter.common.vo.RoleResourceVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author RexALun
 * @since 2019-12-26
 */
public interface IRoleResourceService extends IService<RoleResource> {
    /**
     * desc : get all resources of Role by role id
     * @param roleId - role id
     * @return List<RoleResourceVO>
     */
    List<RoleResourceVO> listRoleResoucesByRoleId(Long roleId);
}
