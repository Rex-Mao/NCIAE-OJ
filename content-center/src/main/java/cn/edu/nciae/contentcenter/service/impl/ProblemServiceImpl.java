package cn.edu.nciae.contentcenter.service.impl;

import cn.edu.nciae.contentcenter.common.entity.Checkpoint;
import cn.edu.nciae.contentcenter.common.entity.Problem;
import cn.edu.nciae.contentcenter.common.entity.Sample;
import cn.edu.nciae.contentcenter.common.mapper.ProblemMapper;
import cn.edu.nciae.contentcenter.common.mapper.SampleMapper;
import cn.edu.nciae.contentcenter.common.vo.ProblemVO;
import cn.edu.nciae.contentcenter.rocketmq.source.CheckpointSource;
import cn.edu.nciae.contentcenter.service.IProblemService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author RexALun
 * @since 2020-02-08
 */
@Slf4j
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements IProblemService {

    @Autowired
    public ProblemMapper problemMapper;

    @Autowired
    public SampleMapper sampleMapper;

    @Autowired
    public CheckpointSource checkpointSource;

    /**
     * @param page - page object
     * @return IPage<Problem>
     */
    @Override
    public IPage<ProblemVO> listProblemsByPaging(Page<ProblemVO> page) {
        return problemMapper.listProblemVOByPaging(page);
    }

    /**
     * @param pid - problem id
     * @return ProblemVO
     */
    @Override
    public ProblemVO getProblemVOByPid(Long pid) {
        return problemMapper.selectProblemVOByPid(pid);
    }

    /**
     * desc : insert a new problem view object into database
     * @param problemVO -
     * @return ProblemVO
     */
    @Override
    public ProblemVO insertOneProblemVO(ProblemVO problemVO) {
        Problem problem = problemVO.unzipProblemVO();
        problemMapper.insert(problem);
        for (Sample sample : problemVO.getSamples()) {
            sample.setPid(problem.getPid());
            sampleMapper.insert(sample);
        }
        for (Checkpoint checkpoint : problemVO.getCheckpoints()) {
            checkpoint.setPid(problem.getPid());
        }
        checkpointSource.ouput().send(MessageBuilder
                .withPayload(problemVO.getCheckpoints())
                .build()
        );
        log.info(String.format("Checkpoints of Problem %s is delivered to the MQ ...", problem.getPid()));
        System.out.println(problem.getPid());
        problemVO.zipProblem(problem);
        return problemVO;
    }
}
