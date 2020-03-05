package cn.edu.nciae.judgecenter.rocketmq.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/2/15 7:19 PM
 */
public interface JudgeSubmissionSink {

    String JUDGE_SUBMISSION_INPUT = "JUDGE_SUBMISSION_INPUT";

    @Input(JUDGE_SUBMISSION_INPUT)
    SubscribableChannel input();
}
