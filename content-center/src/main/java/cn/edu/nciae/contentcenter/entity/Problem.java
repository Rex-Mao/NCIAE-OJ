package cn.edu.nciae.contentcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author RexALun
 * @since 2019-12-26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Repository
@TableName("problem")
public class Problem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 题目ID
     */
    @TableId(value = "pid", type = IdType.AUTO)
    private Long pid;

    /**
     * 添加者ID
     */
    private Long addUid;

    /**
     * 题目名称
     */
    private String title;

    /**
     * 题目描述
     */
    private String description;

    /**
     * 题目输入
     */
    private String pInput;

    /**
     * 题目输出
     */
    private String pOutput;

    /**
     * 样例输入
     */
    private String sInput;

    /**
     * 样例输出
     */
    private String sOutput;

    /**
     * 时间限制(MS)
     */
    private Double timeLimit;

    /**
     * 内存限制(MS)
     */
    private Double memoryLimit;

    /**
     * 提交次数
     */
    private Integer submitNum;

    /**
     * 解决人数
     */
    private Integer solvedNum;

    /**
     * 题目作者
     */
    private String author;

}
