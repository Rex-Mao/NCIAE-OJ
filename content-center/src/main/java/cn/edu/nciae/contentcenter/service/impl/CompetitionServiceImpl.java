package cn.edu.nciae.contentcenter.service.impl;

import cn.edu.nciae.contentcenter.common.dto.QueryParametersDTO;
import cn.edu.nciae.contentcenter.common.entity.Competition;
import cn.edu.nciae.contentcenter.common.mapper.CompetitionMapper;
import cn.edu.nciae.contentcenter.service.ICompetitionService;
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
public class CompetitionServiceImpl extends ServiceImpl<CompetitionMapper, Competition> implements ICompetitionService {

    @Autowired
    private CompetitionMapper competitionMapper;

    /**
     * desc : list Public Contests By Paging With Query Parameters
     * @param page -
     * @param queryParametersDTO -
     * @return IPage<Competition>
     */
    @Override
    public IPage<Competition> listPublicContestsByPagingWithQueryParameters(Page<Competition> page, QueryParametersDTO queryParametersDTO) {
        IPage<Competition> competitions;
        if (queryParametersDTO.getKeyword() != null && !"".equals(queryParametersDTO.getKeyword())) {
            if (queryParametersDTO.getStatus() != null) {
                competitions = competitionMapper.listContestsByPagingWithKeywordAndStatus(page, queryParametersDTO.getKeyword(), queryParametersDTO.getStatus());
            } else {
                competitions = competitionMapper.listContestsByPagingWithKeyword(page, queryParametersDTO.getKeyword());
            }
        } else {
            if (queryParametersDTO.getStatus() != null) {
                competitions = competitionMapper.selectPage(page,
                        Wrappers.<Competition>lambdaQuery().eq(Competition::getCStatus, queryParametersDTO.getStatus()));
            } else {
                competitions = competitionMapper.selectPage(page, Wrappers.<Competition>lambdaQuery().ne(Competition::getCStatus, -2));
            }
        }
        return competitions;
    }

    /**
     * desc : list Admin Contests By Paging With Query Parameters
     * @param page -
     * @param queryParametersDTO -
     * @return IPage<Competition>
     */
    @Override
    public IPage<Competition> listAdminContestsByPagingWithQueryParameters(Page<Competition> page, QueryParametersDTO queryParametersDTO) {
        IPage<Competition> competitions;
        if (queryParametersDTO.getKeyword() != null && !"".equals(queryParametersDTO.getKeyword())) {
            if (queryParametersDTO.getStatus() != null) {
                competitions = competitionMapper.listContestsByPagingWithKeywordAndStatus(page, queryParametersDTO.getKeyword(), queryParametersDTO.getStatus());
            } else {
                competitions = competitionMapper.listContestsByPagingWithKeyword(page, queryParametersDTO.getKeyword());
            }
        } else {
            if (queryParametersDTO.getStatus() != null) {
                competitions = competitionMapper.selectPage(page,
                        Wrappers.<Competition>lambdaQuery().eq(Competition::getCStatus, queryParametersDTO.getStatus()));
            } else {
                competitions = page(page);
            }
        }
        return competitions;
    }
}
