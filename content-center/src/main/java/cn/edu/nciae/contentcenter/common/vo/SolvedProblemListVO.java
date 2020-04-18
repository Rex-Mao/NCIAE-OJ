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
 * @date 2020/4/12 5:17 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolvedProblemListVO {

    private List<Long> results;

    private Long total;
}
