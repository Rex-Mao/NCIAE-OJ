package cn.edu.nciae.usercenter.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/19 7:29 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserListVO {
    private List<UserInfoVO> results;
    private Long total;
}
