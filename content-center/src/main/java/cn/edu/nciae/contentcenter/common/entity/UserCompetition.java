package cn.edu.nciae.contentcenter.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

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
@TableName("user_competition")
public class UserCompetition implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户竞赛关系ID
     */
    @TableId(value = "ucid", type = IdType.AUTO)
    private Long ucid;

    /**
     * 用户ID
     */
    private Long uid;

    /**
     * 用户名
     */
    private String nickname;

    /**
     * 竞赛ID
     */
    private Long cid;

    /**
     * 参与时间
     */
    private Date joinTime;
}
