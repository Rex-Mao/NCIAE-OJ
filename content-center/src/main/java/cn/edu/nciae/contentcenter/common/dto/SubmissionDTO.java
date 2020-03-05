package cn.edu.nciae.contentcenter.common.dto;

import cn.edu.nciae.contentcenter.common.entity.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/2/10 5:35 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionDTO {
    private Long userId;
    private Long problemId;
    private String submissionID;
    private Language language;
    private String code;
    private Long contestId;
    private String captcha;
}
