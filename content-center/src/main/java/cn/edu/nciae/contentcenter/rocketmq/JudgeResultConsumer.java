package cn.edu.nciae.contentcenter.rocketmq;

import cn.edu.nciae.contentcenter.common.dto.RecordDTO;
import cn.edu.nciae.contentcenter.rocketmq.sink.JudgeResultSink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/3/7 11:41 PM
 */
@Slf4j
@Component
public class JudgeResultConsumer {

    @StreamListener(JudgeResultSink.JUDGE_RESULT_INPUT)
    public void receive(RecordDTO recordDTO) {
        System.out.println(recordDTO);
    }
}
