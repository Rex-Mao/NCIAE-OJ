package cn.edu.nciae.contentcenter.service;

import cn.edu.nciae.contentcenter.common.dto.SubmissionDTO;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/2/11 7:45 AM
 */
public interface ISubmissionService {
    /**
     * desc : Create the submission and take the submission to the Message Queue
     * @return The UUID of the submissionID
     */
    String createSubmissionAndJudge(SubmissionDTO submissionDTO);
}
