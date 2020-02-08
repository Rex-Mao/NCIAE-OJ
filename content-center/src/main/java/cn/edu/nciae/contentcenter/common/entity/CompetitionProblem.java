package cn.edu.nciae.contentcenter.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
}
