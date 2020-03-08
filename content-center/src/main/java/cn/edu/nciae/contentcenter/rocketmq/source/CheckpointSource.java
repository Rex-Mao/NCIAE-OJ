package cn.edu.nciae.contentcenter.rocketmq.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/3/8 1:21 PM
 */
public interface CheckpointSource {

    String CHICKPOINT_OUTPUT = "CHICKPOINT_OUTPUT";

    @Output(CHICKPOINT_OUTPUT)
    MessageChannel ouput();

}
