package cn.edu.nciae.contentcenter.rocketmq.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/3/7 11:20 PM
 */
public interface JudgeResultSink {

    String JUDGE_RESULT_INPUT = "JUDGE_RESULT_INPUT";

    @Input(JUDGE_RESULT_INPUT)
    SubscribableChannel input();

}
