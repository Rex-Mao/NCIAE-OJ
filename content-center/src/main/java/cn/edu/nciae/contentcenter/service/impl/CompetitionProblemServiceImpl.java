package cn.edu.nciae.contentcenter.service.impl;

import cn.edu.nciae.contentcenter.common.dto.CompetitionProblemDTO;
import cn.edu.nciae.contentcenter.common.entity.Checkpoint;
import cn.edu.nciae.contentcenter.common.entity.CompetitionProblem;
import cn.edu.nciae.contentcenter.common.entity.Problem;
import cn.edu.nciae.contentcenter.common.entity.Sample;
import cn.edu.nciae.contentcenter.common.mapper.CompetitionProblemMapper;
import cn.edu.nciae.contentcenter.common.mapper.ProblemMapper;
import cn.edu.nciae.contentcenter.common.mapper.SampleMapper;
import cn.edu.nciae.contentcenter.service.ICompetitionProblemService;
import cn.edu.nciae.contentcenter.utils.ClassUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author RexALun
 * @since 2020-02-08
 */
@Service
public class CompetitionProblemServiceImpl extends ServiceImpl<CompetitionProblemMapper, CompetitionProblem> implements ICompetitionProblemService {

    @Autowired
    private CompetitionProblemMapper competitionProblemMapper;

    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private SampleMapper sampleMapper;

    /**
     * desc : page the competition problems
     * @param page -
     * @param cid -
     * @return IPage<CompetitionProblem>
     */
    @Override
    public IPage<CompetitionProblem> pageCompetitionProblems(Page<CompetitionProblem> page, Long cid) {
        return competitionProblemMapper.selectCompetitionProblems(page, cid);
    }

    /**
     * desc : get the competition problem by cpid
     * @param cpid -
     * @return CompetitionProblem
     */
    @Override
    public CompetitionProblem getCompetitionProblemByCpid(Long cpid) {
        return competitionProblemMapper.selectCompetitionProblemByCpid(cpid);
    }

    /**
     * desc : insert a problem with samples and checkpoints to database
     * @param competitionProblemDTO -
     * @return Boolean
     */
    @Override
    public Boolean insertCompetitonProblemDTO(CompetitionProblemDTO competitionProblemDTO) {
        Problem problem = ClassUtils.getSuperObjectFromSubObject(competitionProblemDTO, Problem.class);
        problem.setSubmitNum(0);
        problem.setSolvedNum(0);
        problem.setStatus(1);
        problemMapper.insert(problem);
        competitionProblemDTO.setPid(problem.getPid());
        competitionProblemMapper.insert(competitionProblemDTO.abstractCompetitionProblem());
        Boolean result = setThingsWithProblem(competitionProblemDTO, problem.getPid());
        return result;
    }

    /**
     * desc : update a competition problem with samples and checkpoints to database
     * @param competitionProblemDTO -
     * @return Boolean
     */
    @Override
    public Boolean updateCompetitionProblemDTO(CompetitionProblemDTO competitionProblemDTO) {
        Problem problem = ClassUtils.getSuperObjectFromSubObject(competitionProblemDTO, Problem.class);
        problemMapper.update(problem, Wrappers.<Problem>lambdaQuery().eq(Problem::getPid, problem.getPid()));
        sampleMapper.delete(Wrappers.<Sample>lambdaQuery().eq(Sample::getPid, problem.getPid()));
        // TODO : add the checkpoint opreation when you rebuild the project
        Boolean result = setThingsWithProblem(competitionProblemDTO, problem.getPid());
        return null;
    }

    /**
     * desc : set the samples, problemTags with problemDTO
     * @param competitionProblemDTO -
     * @param pid -
     * @return Boolean
     */
    private Boolean setThingsWithProblem(CompetitionProblemDTO competitionProblemDTO, Long pid) {
        for (Sample sample : competitionProblemDTO.getSamples()) {
            sample.setPid(pid);
            sampleMapper.insert(sample);
        }
        for (Checkpoint checkpoint : competitionProblemDTO.getCheckpoints()) {
            // TODO : change to hand out the checkpoints to the judge center
            System.out.println(checkpoint);
        }
        return true;
    }
}
