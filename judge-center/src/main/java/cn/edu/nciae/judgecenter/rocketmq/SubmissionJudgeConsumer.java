package cn.edu.nciae.judgecenter.rocketmq;

import cn.edu.nciae.judgecenter.common.dto.SubmissionDTO;
import cn.edu.nciae.judgecenter.rocketmq.sink.JudgeSubmissionSink;
import cn.edu.nciae.judgecenter.service.IJudgeSubmissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/2/11 11:09 AM
 */
@Slf4j
@Component
public class SubmissionJudgeConsumer {

    @Autowired
    private IJudgeSubmissionService judgeSubmissionService;

    @StreamListener(JudgeSubmissionSink.JUDGE_SUBMISSION_INPUT)
    public void receive(SubmissionDTO submissionDTO) {
        log.info(String.format("Submission %s is delivered from the MQ ...", submissionDTO.getSubmissionId()));
        judgeSubmissionService.judgeSubmission(submissionDTO);
    }
}
