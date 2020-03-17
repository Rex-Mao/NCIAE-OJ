package cn.edu.nciae.contentcenter.common.mapper;

import cn.edu.nciae.contentcenter.common.entity.Notice;
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
 * @since 2020-03-16
 */
@Component
public interface NoticeMapper extends BaseMapper<Notice> {

    /**
     * desc : get the notice list by paging
     * @param page - Page<Notice>
     * @return IPage
     */
    IPage<Notice> listNoticesByPaging(Page<Notice> page);
}
