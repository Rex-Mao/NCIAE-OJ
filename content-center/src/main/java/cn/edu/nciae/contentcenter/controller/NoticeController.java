package cn.edu.nciae.contentcenter.controller;


import cn.edu.nciae.contentcenter.common.dto.PageParametersDTO;
import cn.edu.nciae.contentcenter.common.entity.Notice;
import cn.edu.nciae.contentcenter.common.vo.MessageVO;
import cn.edu.nciae.contentcenter.common.vo.NoticeListVO;
import cn.edu.nciae.contentcenter.service.INoticeService;
import cn.edu.nciae.contentcenter.service.IUserCompetitionService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private IUserCompetitionService userCompetitionService;

    /**
     * desc : get public notices
     * @param offset -
     * @param limit -
     * @return MessageVO<NoticeListVO>
     */
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

    /**
     * desc : get admin notices
     * @param offset -
     * @param limit -
     * @return MessageVO<NoticeListVO>
     */
    @GetMapping("/admin/announcement")
    public MessageVO<NoticeListVO> getAdminNoticeList(@RequestParam("paging") Boolean paging,
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
        notice.setCid(0L);
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
     * desc : get contest notices
     * @param authentication -
     * @param pageParametersDTO -
     * @param cid -
     * @return MessageVO<NoticeListVO>
     */
    @GetMapping("/admin/contest/announcement/{cid}")
    public MessageVO<NoticeListVO> getAdminContestNoticeList(Authentication authentication,
                                                             PageParametersDTO pageParametersDTO,
                                                             @PathVariable("cid") Long cid) {
        return MessageVO.<NoticeListVO>builder()
                .error(null)
                .data(getContestNoticeListVO(pageParametersDTO, cid))
                .build();
    }

    /**
     * desc : create a new announcement
     * @param notice -
     * @return MessageVO<String>
     */
    @PostMapping("/admin/contest/announcement")
    public MessageVO<String> createAdminContestNotice(Authentication authentication,
                                                  @RequestBody Notice notice) {
        if (!userCompetitionService.checkAdminContestAuthority(authentication, notice.getCid())) {
            return MessageVO.<String>builder()
                    .error("403")
                    .data("Action forbidden")
                    .build();
        }
        notice.setCreateTime(new Date());
        notice.setLastUpdateTime(new Date());
        Boolean result = noticeService.save(notice);
        return MessageVO.<String>builder()
                .error(null)
                .data(result.toString())
                .build();
    }

    /**
     * desc : delete an announcement
     * @param nid - Notice id
     * @return MessageVO<String>
     */
    @DeleteMapping("/admin/contest/announcement/{nid}")
    public MessageVO<String> deleteAdminContestNotice(Authentication authentication,
                                                  @PathVariable("nid") Long nid) {
        Notice notice = noticeService.getById(nid);
        if (notice != null &&
                !userCompetitionService.checkAdminContestAuthority(authentication, notice.getCid())) {
            return MessageVO.<String>builder()
                    .error("403")
                    .data("Action forbidden")
                    .build();
        }
        Boolean result = noticeService.removeById(nid);
        return MessageVO.<String>builder()
                .error(null)
                .data(result.toString())
                .build();
    }

    /**
     * desc : update an announcement by notice id
     * @param notice - New notice body
     * @return MessageVO<String>
     */
    @PutMapping("/admin/contest/announcement")
    public MessageVO<String> updateAdminContestNotice(Authentication authentication,
                                                  @RequestBody Notice notice) {
        if (!userCompetitionService.checkAdminContestAuthority(authentication, notice.getCid())) {
            return MessageVO.<String>builder()
                    .error("403")
                    .data("Action forbidden")
                    .build();
        }
        notice.setLastUpdateTime(new Date());
        Boolean result = noticeService.saveOrUpdate(notice, Wrappers.<Notice>lambdaQuery().eq(Notice::getNid, notice.getNid()));
        return MessageVO.<String>builder()
                .error(null)
                .data(result.toString())
                .build();
    }

    /**
     * desc : get contest notices
     * @param authentication -
     * @param pageParametersDTO -
     * @param cid -
     * @return MessageVO<NoticeListVO>
     */
    @GetMapping("/contest/announcement/{cid}")
    public MessageVO<NoticeListVO> getContestNoticeList(Authentication authentication,
                                                     PageParametersDTO pageParametersDTO,
                                                     @PathVariable("cid") Long cid) {
        if (!userCompetitionService.checkUserContestAuthority(authentication, cid)) {
            return MessageVO.<NoticeListVO>builder()
                    .error("403")
                    .data(null)
                    .build();
        }
        return MessageVO.<NoticeListVO>builder()
                .error(null)
                .data(getContestNoticeListVO(pageParametersDTO, cid))
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

    /**
     * desc : get contest notice list by paging
     * @param pageParametersDTO
     * @return NoticeListVO
     */
    private NoticeListVO getContestNoticeListVO(PageParametersDTO pageParametersDTO, Long cid) {
        int offset = ( pageParametersDTO.getOffset() == null || pageParametersDTO.getOffset() == 0)  ? 1 : pageParametersDTO.getOffset();
        int limit = ( pageParametersDTO.getLimit() == null || pageParametersDTO.getLimit() == 0 ) ? 10 : pageParametersDTO.getLimit();
        Page<Notice> page = new Page<Notice>(offset / limit, limit);
        IPage<Notice> noticeList = noticeService.page(page, Wrappers.<Notice>lambdaQuery().eq(Notice::getCid, cid));
        return NoticeListVO.builder()
                .results(noticeList.getRecords())
                .total(noticeList.getTotal())
                .build();
    }
}
