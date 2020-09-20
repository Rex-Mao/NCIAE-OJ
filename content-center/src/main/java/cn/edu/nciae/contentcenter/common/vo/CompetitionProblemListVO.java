package cn.edu.nciae.contentcenter.common.vo;

import cn.edu.nciae.contentcenter.common.entity.CompetitionProblem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/25 11:41 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionProblemListVO {
    private Long cid;
    private List<CompetitionProblem> results;
    private Long total;
}
