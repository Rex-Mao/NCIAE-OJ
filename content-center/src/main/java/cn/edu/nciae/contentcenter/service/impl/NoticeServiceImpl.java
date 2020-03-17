package cn.edu.nciae.contentcenter.service.impl;

import cn.edu.nciae.contentcenter.common.entity.Notice;
import cn.edu.nciae.contentcenter.common.mapper.NoticeMapper;
import cn.edu.nciae.contentcenter.service.INoticeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
 * @since 2020-03-16
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * desc : get the notice list by paging
     * @param page - Page<Notice>
     * @return IPage
     */
    @Override
    public IPage<Notice> listNoticesByPaging(Page<Notice> page) {
        return noticeMapper.listNoticesByPaging(page);
    }
}
