package cn.edu.nciae.contentcenter.common.entity;

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
 * @since 2020-03-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Checkpoint implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 检查点ID
     */
    private Integer cpid;

    /**
     * 题目ID与内容中心同步
     */
    private Long pid;

    /**
     * 标准输入
     */
    private String input;

    /**
     * 标准输出
     */
    private String output;

}
