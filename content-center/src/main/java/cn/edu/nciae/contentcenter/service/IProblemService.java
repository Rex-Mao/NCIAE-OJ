package cn.edu.nciae.contentcenter.service;

import cn.edu.nciae.contentcenter.common.dto.ProblemDTO;
import cn.edu.nciae.contentcenter.common.dto.ProblemParametersDTO;
import cn.edu.nciae.contentcenter.common.entity.Problem;
import cn.edu.nciae.contentcenter.common.vo.ProblemVO;
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
public interface IProblemService extends IService<Problem> {
    /**
     * desc : list the problem by paging
     * @param page -
     * @return IPage<Problem>
     */
    IPage<ProblemVO> listProblemsByPaging(Page<ProblemVO> page, ProblemParametersDTO problemParametersDTO);


    /**
     * desc : get problem by pid
     * @param pid -
     * @return ProblemVO
     */
    ProblemVO getProblemVOByPid(Long pid);

    /**
     * desc : insert a new problem view object to database
     * @param problemVO -
     * @return ProblemVO
     */
    ProblemVO insertOneProblemVO(ProblemVO problemVO);

    /**
     * desc : insert a new problem data transfer object to database
     * @param problemDTO -
     * @return Boolean
     */
    Boolean insertOneProblemDTO(ProblemDTO problemDTO);

    /**
     * desc : update a problem
     * @param problemDTO -
     * @return Boolean
     */
    Boolean updateOneProblemDTO(ProblemDTO problemDTO);
}
