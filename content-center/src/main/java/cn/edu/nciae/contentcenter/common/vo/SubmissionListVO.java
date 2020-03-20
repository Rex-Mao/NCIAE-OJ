package cn.edu.nciae.contentcenter.common.vo;

import cn.edu.nciae.contentcenter.common.entity.Record;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/3/19 12:16 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionListVO {
    private List<Record> results;
    private Long total;
}
