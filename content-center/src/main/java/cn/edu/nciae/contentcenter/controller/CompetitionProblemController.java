package cn.edu.nciae.contentcenter.controller;


import cn.edu.nciae.contentcenter.common.dto.CompetitionProblemDTO;
import cn.edu.nciae.contentcenter.common.dto.PageParametersDTO;
import cn.edu.nciae.contentcenter.common.entity.CompetitionProblem;
import cn.edu.nciae.contentcenter.common.entity.SystemUserDetails;
import cn.edu.nciae.contentcenter.common.vo.CompetitionProblemListVO;
import cn.edu.nciae.contentcenter.common.vo.MessageVO;
import cn.edu.nciae.contentcenter.service.ICompetitionProblemService;
import cn.edu.nciae.contentcenter.service.IUserCompetitionService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author RexALun
 * @since 2020-02-08
 */
@RestController
public class CompetitionProblemController {

    @Autowired
    private ICompetitionProblemService competitionProblemService;

    @Autowired
    private IUserCompetitionService userCompetitionService;

    /**
     * desc : get competition problems by cid
     * @param pageParametersDTO -
     * @param cid -
     * @return MessageVO<CompetitionProblemListVO>
     */
    @GetMapping("/admin/contest/problems/{cid}")
    public MessageVO<CompetitionProblemListVO> getAdminCompetitionProblems(PageParametersDTO pageParametersDTO,
                                                                           @PathVariable("cid") Long cid) {
        return MessageVO.<CompetitionProblemListVO>builder()
                .error(null)
                .data(getPagingCompetitionProblemListVO(pageParametersDTO, cid))
                .build();
    }

    /**
     * desc : get competition problem by cpid
     * @param cpid -
     * @return MessageVO<CompetitionProblem>
     */
    @GetMapping("/admin/contest/problem/{cpid}")
    public MessageVO<CompetitionProblem> getAdminCompetitionProblem(@PathVariable("cpid") Long cpid) {

        CompetitionProblem competitionProblem = competitionProblemService.getCompetitionProblemByCpid(cpid);
        return MessageVO.<CompetitionProblem>builder()
                .error(null)
                .data(competitionProblem)
                .build();
    }

    /**
     * desc : create competition problem
     * @param authentication -
     * @param competitionProblemDTO -
     * @return MessageVO<String>
     */
    @PostMapping("/admin/contest/problem")
    public MessageVO<String> createCompetitionProblem(Authentication authentication,
                                                       @RequestBody CompetitionProblemDTO competitionProblemDTO) {
        SystemUserDetails userDetails = (SystemUserDetails) authentication.getPrincipal();
        // TODO : change the addUid when you rebuild the project
        competitionProblemDTO.setAddUsername(userDetails.getUsername());
        Boolean result = competitionProblemService.insertCompetitonProblemDTO(competitionProblemDTO);
        return MessageVO.<String>builder()
                .error(null)
                .data(result.toString())
                .build();
    }

    /**
     * desc : update competition problem
     * @param authentication -
     * @param competitionProblemDTO -
     * @return MessageVO<Boolean>
     */
    @PutMapping("/admin/contest/problem")
    public MessageVO<String> updateCompetitionProblem(Authentication authentication,
                                                       @RequestBody CompetitionProblemDTO competitionProblemDTO) {
        SystemUserDetails userDetails = (SystemUserDetails) authentication.getPrincipal();
        // TODO : change the addUid when you rebuild the project
        competitionProblemDTO.setAddUsername(userDetails.getUsername());
        Boolean result = competitionProblemService.updateCompetitionProblemDTO(competitionProblemDTO);
        return MessageVO.<String>builder()
                .error(null)
                .data(result.toString())
                .build();
    }

    /**
     * desc : delete competition problem
     * @param cpid - competition problem id
     * @return MessageVO<String>
     */
    @DeleteMapping("/admin/contest/problem/{cpid}")
    public MessageVO<String> deleteCompetitionProblem(@PathVariable("cpid") Long cpid){
        Boolean result = competitionProblemService.removeById(cpid);
        return MessageVO.<String>builder()
                .error(null)
                .data(result.toString())
                .build();
    }

    /**
     * desc : create competition problem from public problem
     * @param competitionProblem -
     * @return MessageVO<Boolean>
     */
    @PostMapping("/admin/contest/from/public")
    public MessageVO<String> createCompetitionProblemFromPublicProblem(@RequestBody CompetitionProblem competitionProblem){
        competitionProblem.setScore(0);
        competitionProblem.setSolvedNum(0);
        competitionProblem.setSubmitNum(0);
        Boolean result = competitionProblemService.save(competitionProblem);
        return MessageVO.<String>builder()
                .error(null)
                .data(result.toString())
                .build();
    }

    @GetMapping("/contest/problems/{cid}")
    public MessageVO<CompetitionProblemListVO> getCompetitionProblems(Authentication authentication,
                                                                     PageParametersDTO pageParametersDTO,
                                                                     @PathVariable("cid") Long cid) {
        if (!userCompetitionService.checkUserContestAuthority(authentication, cid)) {
            return MessageVO.<CompetitionProblemListVO>builder()
                    .error("403")
                    .data(null)
                    .build();
        }

        return MessageVO.<CompetitionProblemListVO>builder()
                .error(null)
                .data(getPagingCompetitionProblemListVO(pageParametersDTO, cid))
                .build();
    }

    @GetMapping("/contest/problem/{cpid}")
    public MessageVO<CompetitionProblem> getCompetitionProblem(Authentication authentication,
                                                               @PathVariable("cpid") Long cpid) {
        CompetitionProblem competitionProblem = competitionProblemService.getCompetitionProblemByCpid(cpid);
        if (competitionProblem == null ||
                !userCompetitionService.checkUserContestAuthority(authentication, competitionProblem.getCid())) {
            return MessageVO.<CompetitionProblem>builder()
                    .error("403")
                    .data(null)
                    .build();
        }
        return MessageVO.<CompetitionProblem>builder()
                .error(null)
                .data(competitionProblem)
                .build();
    }


    private CompetitionProblemListVO getPagingCompetitionProblemListVO(PageParametersDTO pageParametersDTO, Long cid) {
        Page<CompetitionProblem> page;
        if (pageParametersDTO.getPage() != null){
            page = new Page<>(pageParametersDTO.getPage(), pageParametersDTO.getLimit());
        } else {
            page = new Page<>(1, pageParametersDTO.getLimit());
        }
        IPage<CompetitionProblem> competitionProblems = competitionProblemService.pageCompetitionProblems(page, cid);
        return CompetitionProblemListVO.builder()
                .cid(cid)
                .results(competitionProblems.getRecords())
                .total(competitionProblems.getTotal())
                .build();
    }

}
