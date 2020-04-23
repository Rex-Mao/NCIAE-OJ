package cn.edu.nciae.usercenter.service;

import cn.edu.nciae.usercenter.common.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author RexALun
 * @since 2019-12-26
 */
public interface IUserRoleService extends IService<UserRole> {

//    /**
//     * desc : list users and roles by paging
//     * @param page - Page Object
//     * @return IPage<UserVO>
//     */
//    IPage<UserVO> listUserRolesByPaging(Page<UserVO> page);

    /**
     * desc : delete all user role by uids
     * @param uids -
     * @return Boolean
     */
    Boolean removeByUids(ArrayList<Long> uids);
}
