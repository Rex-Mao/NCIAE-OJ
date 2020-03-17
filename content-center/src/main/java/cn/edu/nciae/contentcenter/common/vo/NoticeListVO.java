package cn.edu.nciae.contentcenter.common.vo;

import cn.edu.nciae.contentcenter.common.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/3/16 3:39 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeListVO {

    /**
     * Result set
     */
    private List<Notice> results;

    /**
     * Total Problem Num
     */
    private Long total;
}
