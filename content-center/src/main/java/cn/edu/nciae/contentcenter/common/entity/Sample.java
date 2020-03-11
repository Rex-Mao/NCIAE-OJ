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
@TableName("sample")
public class Sample implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 样例id
     */
    @TableId(value = "sid", type = IdType.AUTO)
    private Long sid;

    /**
     * 题目id
     */
    private Long pid;

    /**
     * 样例输入
     */
    private String input;

    /**
     * 样例输出
     */
    private String output;

}
