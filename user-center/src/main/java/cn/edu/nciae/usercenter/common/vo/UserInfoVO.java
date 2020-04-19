package cn.edu.nciae.usercenter.common.vo;

import cn.edu.nciae.usercenter.common.entity.Role;
import cn.edu.nciae.usercenter.common.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/20 2:21 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO extends UserInfo {
    private List<Role> roles;
}
