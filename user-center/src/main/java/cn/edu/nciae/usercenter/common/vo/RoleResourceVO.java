package cn.edu.nciae.usercenter.common.vo;

import cn.edu.nciae.usercenter.common.entity.Resource;
import cn.edu.nciae.usercenter.common.entity.Role;
import cn.edu.nciae.usercenter.common.entity.RoleResource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/20 3:16 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResourceVO extends RoleResource {
    private Role role;
    private Resource resource;
}
