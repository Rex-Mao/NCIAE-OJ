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
@TableName("problem_tag")
public class ProblemTag implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 问题标签关系主键
     */
    @TableId(value = "ptid", type = IdType.AUTO)
    private Long ptid;

    /**
     * 问题id
     */
    private Long pid;

    /**
     * 标签id
     */
    private Long tid;

}
