package cn.edu.nciae.judgecenter.rocketmq.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/3/7 11:30 PM
 */
public interface JudgeResultSouce {

    String JUDGE_RESULT_OUTPUT = "JUDGE_RESULT_OUTPUT";

    @Output(JUDGE_RESULT_OUTPUT)
    MessageChannel output();
}
