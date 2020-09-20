package cn.edu.nciae.usercenter.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/5/12 1:10 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsernameEmailValidateVO {
    private Boolean nickname;
    private Boolean email;
}
