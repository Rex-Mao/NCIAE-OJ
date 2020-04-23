package cn.edu.nciae.usercenter.common.vo;

import cn.edu.nciae.usercenter.common.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/24 2:00 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleListVO {
    private Collection<Role> roles;
}
