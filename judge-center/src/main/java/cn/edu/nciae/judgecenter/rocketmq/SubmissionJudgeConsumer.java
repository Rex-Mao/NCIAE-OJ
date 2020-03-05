package cn.edu.nciae.judgecenter.rocketmq;

import cn.edu.nciae.judgecenter.common.dto.SubmissionDTO;
import cn.edu.nciae.judgecenter.core.Dispatcher;
import cn.edu.nciae.judgecenter.rocketmq.sink.JudgeSubmissionSink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/2/11 11:09 AM
 */
@Service
@Slf4j
public class SubmissionJudgeConsumer {

    @Autowired
    private Dispatcher dispatcher;

    @StreamListener(JudgeSubmissionSink.JUDGE_SUBMISSION_INPUT)
    public void receive(SubmissionDTO submissionDTO) {
        System.out.println(submissionDTO);
    }
}
