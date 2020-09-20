package cn.edu.nciae.contentcenter.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author RexALun
 * @since 2020-02-08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("competition_problem")
public class CompetitionProblem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 竞赛问题关系ID
     */
    @TableId(value = "cpid", type = IdType.AUTO)
    private Long cpid;

    /**
     * 竞赛ID
     */
    private Long cid;

    /**
     * 问题ID
     */
    private Long pid;

    /**
     * 问题在竞赛中所占分数
     */
    private Integer score;

    /**
     * 问题在竞赛中显示的ID
     */
    private String displayId;

    /**
     * 问题在竞赛中的提交次数
     */
    private Integer submitNum;

    /**
     * 问题在竞赛中的解决次数
     */
    private Integer solvedNum;

    /**
     * 问题
     */
    @TableField(value = "pid", exist = false)
    private Problem problem;
}
