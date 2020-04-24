package cn.edu.nciae.usercenter.common.dto;

import lombok.Data;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/24 9:42 AM
 */
@Data
public class QueryParametersDTO {
    private String keyword;
    private Integer status;
}
