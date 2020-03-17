package cn.edu.nciae.contentcenter.controller;


import cn.edu.nciae.contentcenter.common.entity.Notice;
import cn.edu.nciae.contentcenter.common.vo.MessageVO;
import cn.edu.nciae.contentcenter.common.vo.NoticeListVO;
import cn.edu.nciae.contentcenter.service.INoticeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author RexALun
 * @since 2020-03-16
 */
@RestController
public class NoticeController {

    @Autowired
    private INoticeService noticeService;

    @GetMapping("/announcement")
    public MessageVO<NoticeListVO> getNoticeList(@RequestParam("offset") Integer offset,
                                                 @RequestParam("limit") Integer limit) {
        limit = limit <= 0 ? 10 : limit;
        return MessageVO.<NoticeListVO>builder()
                .error(null)
                .data(getNoticeListVO(offset, limit))
                .build();
    }

    @GetMapping("/admin/announcement")
    public MessageVO<NoticeListVO> getAdminNoticeListg(@RequestParam("paging") Boolean paging,
                                                       @RequestParam("offset") Integer offset,
                                                       @RequestParam("limit") Integer limit) {
        if (paging) {
            limit = limit <= 0 ? 10 : limit;
            return MessageVO.<NoticeListVO>builder()
                    .error(null)
                    .data(getNoticeListVO(offset, limit))
                    .build();
        } else {
            return MessageVO.<NoticeListVO>builder().error("No Announcements Data Returned...").build();
        }
    }

    /**
     * desc : get notice list by paging
     * @param offset - offset
     * @param limit - record num limit
     * @return NoticeListVO
     */
    private NoticeListVO getNoticeListVO(Integer offset, Integer limit) {
        Page<Notice> page = new Page<Notice>(offset / limit, limit);
        IPage<Notice> noticeList = noticeService.listNoticesByPaging(page);
        return NoticeListVO.builder()
                .results(noticeList.getRecords())
                .total(noticeList.getTotal())
                .build();
    }
}
