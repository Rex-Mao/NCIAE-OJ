package cn.edu.nciae.contentcenter.common.vo;

import cn.edu.nciae.contentcenter.common.entity.Competition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/24 7:31 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionListVO {
    private List<Competition> results;
    private Long total;
}
