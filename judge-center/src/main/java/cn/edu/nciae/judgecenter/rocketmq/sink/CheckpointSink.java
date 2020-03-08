package cn.edu.nciae.judgecenter.rocketmq.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/3/8 1:33 PM
 */
public interface CheckpointSink {

    String CHECKPOINT_INPUT = "CHECKPOINT_INPUT";

    @Input(CHECKPOINT_INPUT)
    SubscribableChannel input();
}
