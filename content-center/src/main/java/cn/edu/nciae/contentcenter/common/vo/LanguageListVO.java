package cn.edu.nciae.contentcenter.common.vo;

import cn.edu.nciae.contentcenter.common.entity.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/3/5 2:38 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LanguageListVO {
    /**
     * Result set
     */
    private List<Language> results;
}
