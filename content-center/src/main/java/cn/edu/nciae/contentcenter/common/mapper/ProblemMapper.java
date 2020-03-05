package cn.edu.nciae.contentcenter.common.mapper;

import cn.edu.nciae.contentcenter.common.entity.Problem;
import cn.edu.nciae.contentcenter.common.vo.ProblemVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author RexALun
 * @since 2020-02-08
 */
@Component
public interface ProblemMapper extends BaseMapper<Problem> {
    /**
     * desc : get the problemVO list by paging
     * @param page
     * @return
     */
    IPage<ProblemVO> selectProblemVOListPage(Page<ProblemVO> page);

    /**
     * desc : get ProblemVO by Pid
     * @param pid
     * @return
     */
    ProblemVO selectProblemVOByPid(Long pid);
}
