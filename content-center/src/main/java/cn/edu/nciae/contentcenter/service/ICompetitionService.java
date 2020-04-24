package cn.edu.nciae.contentcenter.service;

import cn.edu.nciae.contentcenter.common.dto.QueryParametersDTO;
import cn.edu.nciae.contentcenter.common.entity.Competition;
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
public interface ICompetitionService extends IService<Competition> {

    IPage<Competition> listPublicContestsByPagingWithQueryParameters(Page<Competition> page, QueryParametersDTO queryParametersDTO);

    IPage<Competition> listAdminContestsByPagingWithQueryParameters(Page<Competition> page, QueryParametersDTO queryParametersDTO);

}
