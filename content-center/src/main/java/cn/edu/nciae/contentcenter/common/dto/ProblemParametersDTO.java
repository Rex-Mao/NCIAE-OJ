package cn.edu.nciae.contentcenter.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author RexALun
 * @version 1.0
 * Annotation : query parameters of problems
 * @date 2020/2/7 3:53 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemParametersDTO {
    private String keyword;
    private String difficulty;
    private String tag;
    private Long contestId;
}
