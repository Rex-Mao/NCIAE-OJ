package cn.edu.nciae.contentcenter.controller;


import cn.edu.nciae.contentcenter.common.entity.Notice;
import cn.edu.nciae.contentcenter.common.vo.MessageVO;
import cn.edu.nciae.contentcenter.common.vo.NoticeListVO;
import cn.edu.nciae.contentcenter.service.INoticeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


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

    @GetMapping("/public/announcement")
    public MessageVO<NoticeListVO> getNoticeList(@RequestParam("offset") Integer offset,
                                                 @RequestParam("limit") Integer limit) {
        String role = "User";
        limit = limit <= 0 ? 10 : limit;
        return MessageVO.<NoticeListVO>builder()
                .error(null)
                .data(getNoticeListVO(offset, limit, role))
                .build();
    }

    @GetMapping("/admin/announcement")
    public MessageVO<NoticeListVO> getAdminNoticeListg(@RequestParam("paging") Boolean paging,
                                                       @RequestParam("offset") Integer offset,
                                                       @RequestParam("limit") Integer limit) {
        String role = "Admin";
        if (paging) {
            limit = limit <= 0 ? 10 : limit;
            return MessageVO.<NoticeListVO>builder()
                    .error(null)
                    .data(getNoticeListVO(offset, limit, role))
                    .build();
        } else {
            return MessageVO.<NoticeListVO>builder().error("No Announcements Data Returned...").build();
        }
    }

    /**
     * desc : create a new announcement
     * @param notice -
     * @return MessageVO<Boolean>
     */
    @PostMapping("/admin/announcement")
    public MessageVO<Boolean> addAdminNotice(@RequestBody Notice notice) {
        notice.setCreateTime(new Date());
        notice.setLastUpdateTime(new Date());
        Boolean result = noticeService.save(notice);
        return MessageVO.<Boolean>builder()
                .error(null)
                .data(result)
                .build();
    }

    /**
     * desc : delete an announcement
     * @param nid - Notice id
     * @return MessageVO<Boolean>
     */
    @DeleteMapping("/admin/announcement/{nid}")
    public MessageVO<Boolean> deleteAdminNotice(@PathVariable("nid") Long nid) {
        Boolean result = noticeService.removeById(nid);
        return MessageVO.<Boolean>builder()
                .error(null)
                .data(result)
                .build();
    }

    /**
     * desc : update an announcement by notice id
     * @param notice - New notice body
     * @return MessageVO<Boolean>
     */
    @PutMapping("/admin/announcement")
    public MessageVO<Boolean> updateAdminNotice(@RequestBody Notice notice) {
        notice.setLastUpdateTime(new Date());
        Boolean result = noticeService.saveOrUpdate(notice, Wrappers.<Notice>lambdaQuery().eq(Notice::getNid, notice.getNid()));
        return MessageVO.<Boolean>builder()
                .error(null)
                .data(result)
                .build();
    }

    /**
     * desc : get notice list by paging
     * @param offset - offset
     * @param limit - record num limit
     * @return NoticeListVO
     */
    private NoticeListVO getNoticeListVO(Integer offset, Integer limit, String role) {
        Page<Notice> page = new Page<Notice>(offset / limit, limit);
        IPage<Notice> noticeList = noticeService.listNoticesByPaging(page, role);
        return NoticeListVO.builder()
                .results(noticeList.getRecords())
                .total(noticeList.getTotal())
                .build();
    }
}
