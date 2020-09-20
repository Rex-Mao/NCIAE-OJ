package cn.edu.nciae.contentcenter.service;

import cn.edu.nciae.contentcenter.common.dto.CompetitionProblemDTO;
import cn.edu.nciae.contentcenter.common.entity.CompetitionProblem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author RexALun
 * @since 2020-02-08
 */
public interface ICompetitionProblemService extends IService<CompetitionProblem> {

    /**
     * desc : page the competition problems
     * @param page -
     * @param cid -
     * @return IPage<CompetitionProblem>
     */
    IPage<CompetitionProblem> pageCompetitionProblems(Page<CompetitionProblem> page, Long cid);

    /**
     * desc : get the competition problem by cpid
     * @param cpid -
     * @return CompetitionProblem
     */
    CompetitionProblem getCompetitionProblemByCpid(Long cpid);

    /**
     * desc : insert a problem with samples and checkpoints to database
     * @param competitionProblemDTO -
     * @return Boolean
     */
    Boolean insertCompetitonProblemDTO(CompetitionProblemDTO competitionProblemDTO);

    /**
     * desc : update a competition problem with samples and checkpoints to database
     * @param competitionProblemDTO -
     * @return Boolean
     */
    Boolean updateCompetitionProblemDTO(CompetitionProblemDTO competitionProblemDTO);
}
