package cn.edu.nciae.contentcenter.common.mapper;

import cn.edu.nciae.contentcenter.common.entity.CompetitionProblem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
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
public interface CompetitionProblemMapper extends BaseMapper<CompetitionProblem> {

    /**
     * desc : select competition problem by cid
     * @param cid -
     * @return IPage<CompetitionProblem>
     */
    IPage<CompetitionProblem> selectCompetitionProblems(Page<CompetitionProblem> page, @Param("cid") Long cid);

    /**
     * desc : select competition problem by cpid
     * @param cpid competition problem id
     * @return CompetitionProblem
     */
    CompetitionProblem selectCompetitionProblemByCpid(Long cpid);
}
