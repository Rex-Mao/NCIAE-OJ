package cn.edu.nciae.contentcenter.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author RexALun
 * @version 1.0
 * Annotation : query parameters of submission
 * @date 2020/3/19 12:05 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionParametersDTO {
    private Integer myself;
    private String result;
    private String username;
    private Integer page;
    private Integer limit;
    private Integer offset;
}
