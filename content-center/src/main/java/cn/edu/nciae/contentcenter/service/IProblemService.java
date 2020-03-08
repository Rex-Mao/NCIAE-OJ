package cn.edu.nciae.contentcenter.service;

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
     * @param page
     * @return IPage<Problem>
     */
    IPage<ProblemVO> getProblemListPage(Page<ProblemVO> page);

    /**
     * @param pid
     * @return
     */
    ProblemVO getProblemVOByPid(Long pid);

    /**
     * desc : insert a new problem view object to database
     * @param problemVO
     * @return
     */
    ProblemVO insertOneProblemVO(ProblemVO problemVO);
}
