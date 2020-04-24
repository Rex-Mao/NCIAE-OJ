package cn.edu.nciae.contentcenter.common.mapper;

import cn.edu.nciae.contentcenter.common.entity.Competition;
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
public interface CompetitionMapper extends BaseMapper<Competition> {

    /**
     * desc : list Contests By Paging With Keyword And Status
     * @param page -
     * @param keyword -
     * @param status -
     * @return IPage<Competition>
     */
    IPage<Competition> listContestsByPagingWithKeywordAndStatus(Page<Competition> page,
                                                                @Param("keyword") String keyword,
                                                                @Param("status") Integer status);

    /**
     * desc : list Contests By Paging With Keyword
     * @param page -
     * @param keyword -
     * @return IPage<Competition>
     */
    IPage<Competition> listContestsByPagingWithKeyword(Page<Competition> page,
                                                       @Param("keyword") String keyword);
}
