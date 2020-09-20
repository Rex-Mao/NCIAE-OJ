package cn.edu.nciae.contentcenter.common.dto;

import cn.edu.nciae.contentcenter.common.entity.Checkpoint;
import cn.edu.nciae.contentcenter.common.entity.CompetitionProblem;
import cn.edu.nciae.contentcenter.common.entity.Problem;
import cn.edu.nciae.contentcenter.common.entity.Sample;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/5/3 4:40 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionProblemDTO extends Problem {

    /**
     * 竞赛问题关系ID
     */
    private Long cpid;

    /**
     * 竞赛ID
     */
    private Long cid;

    /**
     * 问题在竞赛中所占分数
     */
    private Integer score;

    /**
     * 问题在竞赛中显示的ID
     */
    private String displayId;

    private List<Sample> samples;

    private List<Checkpoint> checkpoints;

    public CompetitionProblem abstractCompetitionProblem(){
        return CompetitionProblem.builder()
                .cpid(this.cpid)
                .cid(this.cid)
                .pid(this.getPid())
                .solvedNum(0)
                .submitNum(0)
                .displayId(this.displayId)
                .score(this.score)
                .build();
    }

}
