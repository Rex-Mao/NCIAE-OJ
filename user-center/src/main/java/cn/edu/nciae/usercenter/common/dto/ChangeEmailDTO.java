package cn.edu.nciae.usercenter.common.dto;

import lombok.Data;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/5/30 10:28 AM
 */
@Data
public class ChangeEmailDTO {

    private String password;

    private String old_email;

    private String new_email;

}
