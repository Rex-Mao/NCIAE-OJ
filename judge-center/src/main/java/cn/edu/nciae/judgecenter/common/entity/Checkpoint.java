package cn.edu.nciae.judgecenter.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
@TableName("checkpoint")
public class Checkpoint implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 检查点ID
     */
    @TableId(value = "cpid", type = IdType.AUTO)
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
