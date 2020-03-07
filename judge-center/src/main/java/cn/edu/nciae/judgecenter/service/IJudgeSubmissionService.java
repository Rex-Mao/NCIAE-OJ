package cn.edu.nciae.judgecenter.service;

import cn.edu.nciae.judgecenter.common.dto.RecordDTO;
import cn.edu.nciae.judgecenter.common.dto.SubmissionDTO;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/3/7 12:37 PM
 */
public interface IJudgeSubmissionService {

    RecordDTO judgeSubmission(SubmissionDTO submissionDTO);

}
