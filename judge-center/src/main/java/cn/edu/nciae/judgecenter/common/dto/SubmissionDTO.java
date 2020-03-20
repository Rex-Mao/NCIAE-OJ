package cn.edu.nciae.judgecenter.common.dto;

import cn.edu.nciae.judgecenter.common.entity.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private Long submissionId;
    private String userNickname;
    private Language language;
    private Double timeLimit;
    private Double memoryLimit;
    private String code;
    private Date commitTime;
    private Long contestId;
    private String captcha;
}
