package cn.edu.nciae.usercenter.common.dto;

import lombok.Data;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/5/30 10:25 AM
 */
@Data
public class ChangePasswordDTO {

    private String oldPassword;

    private String newPassword;
}
