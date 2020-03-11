package cn.edu.nciae.contentcenter.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
//@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("languages")
public class Language implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "language_id", type = IdType.AUTO)
    private Integer languageId;

    private String languageSlug;

    private String languageName;

    private String languageCompileCommand;

    private String languageRunCommand;

    private String languageSuffix;

}
