package cn.edu.nciae.contentcenter.common.vo;

import cn.edu.nciae.contentcenter.common.entity.Record;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/2/10 6:21 PM
 */
@Data
@Builder
@AllArgsConstructor
//@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SubmissionVO extends Record {
}
