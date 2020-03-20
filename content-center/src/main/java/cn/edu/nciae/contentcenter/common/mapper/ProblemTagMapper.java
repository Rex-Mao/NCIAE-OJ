package cn.edu.nciae.contentcenter.common.mapper;

import cn.edu.nciae.contentcenter.common.entity.ProblemTag;
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
public interface ProblemTagMapper extends BaseMapper<ProblemTag> {
    /**
     * desc : get the problem list by tag and paging
     * @param page
     * @return
     */
    IPage<ProblemVO> listProblemVOByTag(Page<ProblemVO> page, String tagName);
}
