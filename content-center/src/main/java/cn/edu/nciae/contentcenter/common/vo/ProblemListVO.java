package cn.edu.nciae.contentcenter.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/2/6 4:58 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemListVO {
    /**
     * Result set
     */
    private List<ProblemVO> results;

    /**
     * Total Problem Num
     */
    private Long total;
}
