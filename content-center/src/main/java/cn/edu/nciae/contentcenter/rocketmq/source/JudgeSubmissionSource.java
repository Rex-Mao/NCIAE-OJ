package cn.edu.nciae.contentcenter.rocketmq.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/2/15 4:51 PM
 */
public interface JudgeSubmissionSource {

    String JUDGE_SUBMISSION_OUTPUT = "JUDGE_SUBMISSION_OUTPUT";

    @Output(JUDGE_SUBMISSION_OUTPUT)
    MessageChannel output();
}
