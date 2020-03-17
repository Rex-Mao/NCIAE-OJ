package cn.edu.nciae.contentcenter.service.impl;

import cn.edu.nciae.contentcenter.common.dto.SubmissionDTO;
import cn.edu.nciae.contentcenter.common.entity.Problem;
import cn.edu.nciae.contentcenter.common.mapper.ProblemMapper;
import cn.edu.nciae.contentcenter.rocketmq.source.JudgeSubmissionSource;
import cn.edu.nciae.contentcenter.service.ISubmissionService;
import cn.edu.nciae.contentcenter.utils.SnowflakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/2/11 7:47 AM
 */
@Slf4j
@Service
public class SubmissionServiceImpl implements ISubmissionService {

    @Autowired
    private JudgeSubmissionSource judgeSubmissionSource;

    @Autowired
    private ProblemMapper problemMapper;


    /**
     * desc : Create the submission and take the submission to the Message Queue
     * @return The UUID of the submissionID
     */
    @Override
    public Long createSubmissionAndJudge(SubmissionDTO submissionDTO) {
        SnowflakeUtil snowflakeUtil = new SnowflakeUtil(1L,1L);
        Long submissionId = snowflakeUtil.nextId();
        submissionDTO.setSubmissionId(submissionId);
        submissionDTO.setCommitTime(new Date());
        Problem problem = problemMapper.selectById(submissionDTO.getProblemId());
        submissionDTO.setTimeLimit(problem.getTimeLimit());
        submissionDTO.setMemoryLimit(problem.getMemoryLimit());
        judgeSubmissionSource.output().send(
                MessageBuilder.withPayload(submissionDTO).build()
                //use to transfer some parameter
                //        .setHeader()
        );
        log.info(String.format("Submission %s is delivered to the MQ ...", submissionDTO.getSubmissionId()));
        return submissionId;
    }
}
