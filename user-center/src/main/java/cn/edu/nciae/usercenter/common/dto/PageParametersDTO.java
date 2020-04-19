package cn.edu.nciae.usercenter.common.dto;

import lombok.Data;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/19 7:33 PM
 */
@Data
public class PageParametersDTO {
    private Boolean paging;
    private Integer offset;
    private Integer limit;
    private Integer page;
}
