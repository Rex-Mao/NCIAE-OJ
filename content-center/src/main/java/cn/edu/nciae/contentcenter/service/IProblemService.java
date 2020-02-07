package cn.edu.nciae.contentcenter.service;

import cn.edu.nciae.contentcenter.common.entity.Problem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author RexALun
 * @since 2019-12-26
 */
public interface IProblemService extends IService<Problem> {

    /**
     * @param page
     * @return IPage<Problem>
     */
    IPage<Problem> getProblemListPage(Page<Problem> page);

}
