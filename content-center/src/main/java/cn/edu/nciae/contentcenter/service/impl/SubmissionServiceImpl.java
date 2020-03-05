package cn.edu.nciae.contentcenter.service.impl;

import cn.edu.nciae.contentcenter.common.dto.SubmissionDTO;
import cn.edu.nciae.contentcenter.rocketmq.source.JudgeSubmissionSource;
import cn.edu.nciae.contentcenter.service.ISubmissionService;
import cn.edu.nciae.contentcenter.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/2/11 7:47 AM
 */
@Service
public class SubmissionServiceImpl implements ISubmissionService {

    @Autowired
    private JudgeSubmissionSource judgeSubmissionSource;
    /**
     * desc : Create the submission and take the submission to the Message Queue
     * @return The UUID of the submissionID
     */
    public String createSubmissionAndJudge(SubmissionDTO submissionDTO) {
        String submissionId = UUIDUtils.getUUID();
        submissionDTO.setSubmissionID(submissionId);
        judgeSubmissionSource.output().send(
                MessageBuilder.withPayload(submissionDTO).build()
                //use to transfer some parameter
                //        .setHeader()
        );
        return submissionId;
    }
}
