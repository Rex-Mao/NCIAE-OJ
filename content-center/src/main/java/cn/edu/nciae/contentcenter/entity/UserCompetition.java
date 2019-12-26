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
     * 竞赛ID
     */
    private Long cid;

    /**
     * 参与时间
     */
    private Date joinTime;

}
