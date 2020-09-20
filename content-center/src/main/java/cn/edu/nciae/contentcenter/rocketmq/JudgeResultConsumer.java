package cn.edu.nciae.contentcenter.rocketmq;

import cn.edu.nciae.contentcenter.common.dto.RecordDTO;
import cn.edu.nciae.contentcenter.common.entity.Problem;
import cn.edu.nciae.contentcenter.common.entity.Record;
import cn.edu.nciae.contentcenter.rocketmq.sink.JudgeResultSink;
import cn.edu.nciae.contentcenter.service.IProblemService;
import cn.edu.nciae.contentcenter.service.IRecordService;
import cn.edu.nciae.contentcenter.utils.ClassUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IRecordService recordService;

    @Autowired
    private IProblemService problemService;

    @StreamListener(JudgeResultSink.JUDGE_RESULT_INPUT)
    public void receive(RecordDTO recordDTO) {
        log.info(String.format("Judge Result %s is delivered from the MQ ...", recordDTO.getRecordId()));
        if (recordDTO.getStatus() == 0) {
            Problem problem = problemService.getById(recordDTO.getPid());
            problem.setSolvedNum(problem.getSolvedNum() + 1);
            problemService.saveOrUpdate(problem);
        }
        recordService.save(ClassUtils.getSuperObjectFromSubObject(recordDTO, Record.class));
    }
}
