package cn.edu.nciae.contentcenter.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@TableName("competition")
public class Competition implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 竞赛ID
     */
    @TableId(value = "cid", type = IdType.AUTO)
    private Long cid;

    /**
     * 竞赛创建用户名
     */
    private String createUsername;

    /**
     * 竞赛标题
     */
    private String title;

    /**
     * 竞赛密码
     */
    private String password;

    /**
     * 竞赛开始时间
     */
    private Date startTime;

    /**
     * 竞赛结束时间
     */
    private Date endTime;

    /**
     * 竞赛描述
     */
    private String description;

    /**
     * 竞赛开放属性
     */
    @JsonProperty("cPublic")
    private Boolean cPublic;

    /**
     * 竞赛状态是否生效
     */
    @JsonProperty("cStatus")
    private Integer cStatus;

}
