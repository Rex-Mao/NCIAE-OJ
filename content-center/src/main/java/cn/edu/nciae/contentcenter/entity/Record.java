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
import java.sql.Date;

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
@TableName("record")
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 解题记录ID
     */
    @TableId(value = "record_id", type = IdType.AUTO)
    private Long recordId;

    /**
     * 解题用户ID
     */
    private Long commitUid;

    /**
     * 题目ID
     */
    private Long pid;

    /**
     * 所属竞赛ID
     */
    private Long cid;

    /**
     * 提交源码
     */
    private String sourceCode;

    /**
     * 提交时间
     */
    private Date commitTime;

    /**
     * 判题状态
     */
    private Integer status;

    /**
     * 运行时间(MS)
     */
    private Double usedTime;

    /**
     * 运行所需内存大小(MB)
     */
    private Double usedMemory;

}
