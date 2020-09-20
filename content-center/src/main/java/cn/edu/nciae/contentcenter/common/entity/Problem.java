package cn.edu.nciae.contentcenter.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
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
 * @since 2020-02-08
 */
@Data
//@Builder
@AllArgsConstructor
@NoArgsConstructor
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
     * 添加者用户名
     */
    private String addUsername;

    /**
     * 题目名称
     */
    private String title;

    /**
     * 题目描述
     */
    private String description;

    /**
     * 输入格式
     */
    @JsonProperty("fInput")
    private String fInput;

    /**
     * 输出格式
     */
    @JsonProperty("fOutput")
    private String fOutput;

    /**
     * 时间限制(MS)
     */
    private Double timeLimit;

    /**
     * 内存限制(MB)
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

    /**
     * 特殊判题默认否0 是1
     */
    private Integer specialJudge;

    /**
     * 提示
     */
    private String hint;

    /**
     * 状态0 public， 1 private
     */
    private Integer status;
}
