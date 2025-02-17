package cn.edu.nciae.contentcenter.common.vo;

import cn.edu.nciae.contentcenter.common.entity.Record;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/2/10 6:21 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionVO {
    private Record data;
    private Integer result;
}
