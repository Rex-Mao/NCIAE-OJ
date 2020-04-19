package cn.edu.nciae.usercenter.common.mapper;

import cn.edu.nciae.usercenter.common.entity.UserInfo;
import cn.edu.nciae.usercenter.common.vo.UserInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author RexALun
 * @since 2020-04-04
 */
@Component
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * desc : list users and roles by paging
     * @param page - Page Object
     * @return IPage<UserVO>
     */
    IPage<UserInfoVO> listUserRolesByPaging(Page<UserInfoVO> page);
}
