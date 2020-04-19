package cn.edu.nciae.usercenter.common.mapper;

import cn.edu.nciae.usercenter.common.entity.RoleResource;
import cn.edu.nciae.usercenter.common.vo.RoleResourceVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author RexALun
 * @since 2019-12-26
 */
@Component
public interface RoleResourceMapper extends BaseMapper<RoleResource> {

    /**
     * desc : list RoleResourceVO by role id
     * @param roleId -
     * @return List<RoleResourceVO>
     */
    List<RoleResourceVO> listRoleResourceVOByRoleId(@Param("roleId") Long roleId);

}
