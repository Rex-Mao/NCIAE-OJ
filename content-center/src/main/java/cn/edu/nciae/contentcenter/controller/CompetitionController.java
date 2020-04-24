package cn.edu.nciae.contentcenter.controller;

import cn.edu.nciae.contentcenter.common.dto.PageParametersDTO;
import cn.edu.nciae.contentcenter.common.dto.QueryParametersDTO;
import cn.edu.nciae.contentcenter.common.entity.Competition;
import cn.edu.nciae.contentcenter.common.entity.SystemUserDetails;
import cn.edu.nciae.contentcenter.common.vo.CompetitionListVO;
import cn.edu.nciae.contentcenter.common.vo.MessageVO;
import cn.edu.nciae.contentcenter.service.ICompetitionService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author RexALun
 * @since 2020-02-08
 */
@RestController
public class CompetitionController {

    @Autowired
    private ICompetitionService competitionService;

    /**
     * desc : create a new contest
     * @param competition -
     * @return MessageVO<Boolean>
     */
    @PostMapping("/admin/contest")
    public MessageVO<Long> createContest(Authentication authentication, @RequestBody Competition competition) {
        SystemUserDetails user = (SystemUserDetails) authentication.getPrincipal();
        setCompetitionStatus(competition);
        competition.setCreateUsername(user.getUsername());
        competitionService.save(competition);
        System.out.println(competition);
        return MessageVO.<Long>builder()
                .error(null)
                .data(competition.getCid())
                .build();
    }

    /**
     * desc : list contests with paging and parameters by admin
     * @param pageParametersDTO -
     * @return MessageVO<CompetitionListVO>
     */
    @GetMapping("/admin/contests")
    public MessageVO<CompetitionListVO> listAdminContests(PageParametersDTO pageParametersDTO,
                                                          QueryParametersDTO queryParametersDTO) {
        CompetitionListVO competitionListVO = getPagingCompetitionListVO(pageParametersDTO, queryParametersDTO, false);
        return MessageVO.<CompetitionListVO>builder()
                .error(null)
                .data(competitionListVO)
                .build();
    }

    /**
     * desc : list contests with paging and parameters
     * @param pageParametersDTO -
     * @return MessageVO<CompetitionListVO>
     */
    @GetMapping("/public/contests")
    public MessageVO<CompetitionListVO> listPublicContests(PageParametersDTO pageParametersDTO,
                                                           QueryParametersDTO queryParametersDTO) {
        CompetitionListVO competitionListVO = getPagingCompetitionListVO(pageParametersDTO, queryParametersDTO, true);
        return MessageVO.<CompetitionListVO>builder()
                .error(null)
                .data(competitionListVO)
                .build();
    }

    /**
     * desc : get contest by cid
     * @param cid -
     * @return MessageVO<Competition>
     */
    @GetMapping("/admin/contest/{cid}")
    public MessageVO<Competition> getAdminContest(@PathVariable("cid") Long cid) {
        Competition competition = competitionService.getById(cid);
        if (competition != null) {
            setCompetitionStatus(competition);
        }
        return MessageVO.<Competition>builder()
                .error(null)
                .data(competition)
                .build();
    }

    /**
     * desc : update contest by new contest information
     * @param authentication -
     * @param competition - new competition
     * @return MessageVO<Boolean>
     */
    @PutMapping("/admin/contest")
    public MessageVO<Boolean> updateAdminContest(Authentication authentication, @RequestBody Competition competition) {
        Competition competitionOrigin = competitionService.getById(competition.getCid());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Boolean isSuperAdmin = false;
        for (GrantedAuthority authority : authorities) {
            if ("ROLE_SUPERADMIN".equals(authority.getAuthority())) {
                isSuperAdmin = true;
            }
        }
        // if it is not super admin and not the creator then no authority to update competition
        if (!isSuperAdmin &&
                ((SystemUserDetails) authentication.getPrincipal()).getUsername().equals(competition.getCreateUsername())) {
            return MessageVO.<Boolean>builder()
                    .error("Sorry, you don't have access to update it...")
                    .data(false)
                    .build();
        }
        if (competition.getTitle() != null) {
            BeanUtils.copyProperties(competition, competitionOrigin);
        }
        setCompetitionStatus(competitionOrigin);
        competitionService.saveOrUpdate(competitionOrigin);
        return MessageVO.<Boolean>builder()
                .error(null)
                .data(true)
                .build();
    }

    /**
     * desc : get user list view object with paging.
     * @param pageParametersDTO - page parameters
     * @param queryParametersDTO - query parameters
     * @param justPublic - just get the public without the not online
     * @return ProblemListVO
     */
    private CompetitionListVO getPagingCompetitionListVO(PageParametersDTO pageParametersDTO,
                                                         QueryParametersDTO queryParametersDTO,
                                                         Boolean justPublic) {
        Page<Competition> page;
        IPage<Competition> competitions = new Page<>();
        if (pageParametersDTO.getPage() != null){
            page = new Page<>(pageParametersDTO.getPage(), pageParametersDTO.getLimit());
        } else {
            page = new Page<>(1, pageParametersDTO.getLimit());
        }
        // just return the contest without cStatus = -2 means not online
        if (justPublic) {
            competitions = competitionService.listPublicContestsByPagingWithQueryParameters(page, queryParametersDTO);
        } else {
            competitions = competitionService.listAdminContestsByPagingWithQueryParameters(page, queryParametersDTO);
        }
        competitions.getRecords().forEach(item -> {
            item.setPassword(null);
        });
        return CompetitionListVO.builder()
                .results(competitions.getRecords())
                .total(competitions.getTotal())
                .build();
    }

    /**
     * desc : change the date status of competition
     * (1,not start ; 0,underway ; -1, ended)
     * @param competition -
     */
    private void setCompetitionStatus(Competition competition) {
        if (competition.getCStatus() == -2) {
            return;
        }
        Date date = new Date();
        if (date.before(competition.getStartTime())) {
            competition.setCStatus(1);
        } else if (date.before(competition.getEndTime())) {
            competition.setCStatus(0);
        } else {
            competition.setCStatus(-1);
        }
    }
}
