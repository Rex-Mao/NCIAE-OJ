package cn.edu.nciae.usercenter.common.entity;

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
@TableName("user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "uid", type = IdType.AUTO)
    private Long uid;

    /**
     * 用户名
     */
    private String nickname;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 注册时间
     */
    private Date regtime;

    /**
     * 解题数量
     */
    private Integer solvednum;

    /**
     * 界面语言
     */
    private String language;

    /**
     * 座右铭
     */
    private String mood;

    /**
     * 博客
     */
    private String blog;

    /**
     * Github url
     */
    private String github;

    /**
     * Base64头像
     */
    private String avatar;

    /**
     * 头像URL
     */
    private String avatarUrl;
}
