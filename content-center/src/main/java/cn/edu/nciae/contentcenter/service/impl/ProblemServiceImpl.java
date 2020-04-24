package cn.edu.nciae.contentcenter.service.impl;

import cn.edu.nciae.contentcenter.common.dto.ProblemDTO;
import cn.edu.nciae.contentcenter.common.dto.ProblemParametersDTO;
import cn.edu.nciae.contentcenter.common.entity.*;
import cn.edu.nciae.contentcenter.common.mapper.ProblemMapper;
import cn.edu.nciae.contentcenter.common.mapper.ProblemTagMapper;
import cn.edu.nciae.contentcenter.common.mapper.SampleMapper;
import cn.edu.nciae.contentcenter.common.mapper.TagMapper;
import cn.edu.nciae.contentcenter.common.vo.ProblemVO;
import cn.edu.nciae.contentcenter.rocketmq.source.CheckpointSource;
import cn.edu.nciae.contentcenter.service.IProblemService;
import cn.edu.nciae.contentcenter.utils.ClassUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
    private ProblemMapper problemMapper;

    @Autowired
    private SampleMapper sampleMapper;

    @Autowired
    private ProblemTagMapper problemTagMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    public CheckpointSource checkpointSource;

    /**
     * @param page - page object
     * @return IPage<Problem>
     */
    @Override
    public IPage<ProblemVO> listProblemsByPaging(Page<ProblemVO> page, ProblemParametersDTO problemParametersDTO) {
        if (problemParametersDTO.getTag() != null) {
            return problemTagMapper.listProblemVOByTag(page, problemParametersDTO.getTag());
        }
        if (problemParametersDTO.getKeyword() != null && !"".equals(problemParametersDTO.getKeyword())) {
            return problemMapper.listProblemsByPagingWithKeyword(page, problemParametersDTO.getKeyword());
        }
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

    /**
     * desc : insert a problem with samples and checkpoints to database
     * @param problemDTO -
     * @return Boolean
     */
    @Override
    public Boolean insertOneProblemDTO(ProblemDTO problemDTO) {
        Problem problem = ClassUtils.getSuperObjectFromSubObject(problemDTO, Problem.class);
        problem.setSubmitNum(0);
        problem.setSolvedNum(0);
        problemMapper.insert(problem);
        Boolean result = setThingsWithProblem(problemDTO, problem.getPid());
        return result;
    }

    /**
     * desc : update a problem
     * @param problemDTO -
     * @return Boolean
     */
    @Override
    public Boolean updateOneProblemDTO(ProblemDTO problemDTO) {
        Problem problem = ClassUtils.getSuperObjectFromSubObject(problemDTO, Problem.class);
        sampleMapper.delete(Wrappers.<Sample>lambdaQuery().eq(Sample::getPid, problem.getPid()));
        problemTagMapper.delete(Wrappers.<ProblemTag>lambdaQuery().eq(ProblemTag::getPid, problem.getPid()));
        problemMapper.update(problem, Wrappers.<Problem>lambdaQuery().eq(Problem::getPid, problem.getPid()));
        Boolean result = setThingsWithProblem(problemDTO, problem.getPid());
        return result;
    }

    /**
     * desc : set the tags ,samples, problemTags with problemDTO
     * @param problemDTO -
     * @param pid -
     * @return Boolean
     */
    private Boolean setThingsWithProblem(ProblemDTO problemDTO, Long pid) {
        for (Sample sample : problemDTO.getSamples()) {
            sample.setPid(pid);
            sampleMapper.insert(sample);
        }
        for (Tag tag : problemDTO.getTags()) {
            Tag tmp = tagMapper.selectOne(Wrappers.<Tag>lambdaQuery().eq(Tag::getTName, tag.getTName()));
            if (tmp == null) {
                tmp = Tag.builder()
                        .tName(tag.getTName())
                        .tDescription(tag.getTName())
                        .build();
                tagMapper.insert(tmp);
            }
            ProblemTag problemTag = ProblemTag.builder()
                    .pid(pid)
                    .tid(tmp.getTid())
                    .build();
            problemTagMapper.insert(problemTag);
        }
        for (Checkpoint checkpoint : problemDTO.getCheckpoints()) {
            System.out.println(checkpoint);
        }
        return true;
    }

}
