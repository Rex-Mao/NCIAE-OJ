package cn.edu.nciae.contentcenter.service;

import cn.edu.nciae.contentcenter.common.entity.Notice;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author RexALun
 * @since 2020-03-16
 */
public interface INoticeService extends IService<Notice> {
    /**
     * desc : get the notice list by paging
     * @param page - Page<Notice>
     * @param role - User or Admin in case sensitive
     * @return IPage
     */
    IPage<Notice> listNoticesByPaging(Page<Notice> page, String role);
}
