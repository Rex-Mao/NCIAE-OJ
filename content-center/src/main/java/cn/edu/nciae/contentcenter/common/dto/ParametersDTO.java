package cn.edu.nciae.contentcenter.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author RexALun
 * @version 1.0
 * Annotation : query parameters
 * @date 2020/2/7 3:53 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParametersDTO {
    private String keyword;
    private String difficulty;
    private String tag;
    private Integer page;
}
