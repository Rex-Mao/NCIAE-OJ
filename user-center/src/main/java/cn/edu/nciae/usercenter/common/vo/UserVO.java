package cn.edu.nciae.usercenter.common.vo;

import cn.edu.nciae.usercenter.common.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/10 1:48 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private UserInfo user;
//    private List<Role> roleList;
}
