package cn.edu.nciae.contentcenter.controller;


import cn.edu.nciae.contentcenter.common.dto.ProblemDTO;
import cn.edu.nciae.contentcenter.common.dto.ProblemParametersDTO;
import cn.edu.nciae.contentcenter.common.entity.Checkpoint;
import cn.edu.nciae.contentcenter.common.entity.Record;
import cn.edu.nciae.contentcenter.common.vo.MessageVO;
import cn.edu.nciae.contentcenter.common.vo.ProblemListVO;
import cn.edu.nciae.contentcenter.common.vo.ProblemVO;
import cn.edu.nciae.contentcenter.common.vo.SolvedProblemListVO;
import cn.edu.nciae.contentcenter.service.IProblemService;
import cn.edu.nciae.contentcenter.service.IRecordService;
import cn.edu.nciae.contentcenter.utils.FPSUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author RexALun
 * @since 2020-02-08
 */
@RestController
public class ProblemController {

    @Autowired
    public IProblemService problemService;

    @Autowired
    private IRecordService recordService;

    /**
     * desc : get all problems by user
     * @param paging -
     * @param offset -
     * @param limit -
     * @param problemParametersDTO -
     * @return MessageVO<ProblemListVO>
     */
    @GetMapping("/public/problems")
    public MessageVO<ProblemListVO> getProblemList(@RequestParam("paging") Boolean paging,
                                                   @RequestParam("offset") Integer offset,
                                                   @RequestParam("limit") Integer limit,
                                                   ProblemParametersDTO problemParametersDTO) {
        if (paging) {
            return MessageVO.<ProblemListVO>builder()
                    .error(null)
                    .data(getPagingProblemListVO(problemParametersDTO, limit))
                    .build();
        }
        return MessageVO.<ProblemListVO>builder().error("No Problem Data Returned...").build();
    }

    /**
     * desc : get the problem by pid
     * @param pid -
     * @return MessageVO<ProblemVO>
     */
    @GetMapping("/public/problem/{pid}")
    public MessageVO<ProblemVO> getProblemByPid(@PathVariable("pid") Long pid) {
        ProblemVO problemVO = problemService.getProblemVOByPid(pid);
        if (problemVO != null) {
            return MessageVO.<ProblemVO>builder()
                    .error(null)
                    .data(problemVO)
                    .build();
        } else {
            return MessageVO.<ProblemVO>builder().error("No choosed problem find...").build();
        }
    }

    /**
     * desc : get all the problem the user has solved
     * @param authentication -
     * @return MessageVO<ProblemListVO>
     */
    @GetMapping("/problem/solved")
    public MessageVO<SolvedProblemListVO> getSovledProblem(Authentication authentication) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        List<Record> records = recordService.list(Wrappers.<Record>lambdaQuery().eq(Record::getCommitNickname, username));
        Set<Long> problemSetIds = new HashSet<>();
        for (Record record : records) {
            problemSetIds.add(record.getPid());
        }
        List<Long> problemIds = new ArrayList<>(problemSetIds);
        return MessageVO.<SolvedProblemListVO>builder()
                .error(null)
                .data(SolvedProblemListVO.builder()
                        .total((long) problemIds.size())
                        .results(problemIds)
                        .build())
                .build();
    }

    /**
     * desc : get the problem by pid with admin role
     * @param pid -
     * @return MessageVO<ProblemVO>
     */
    @GetMapping("/admin/problem/{pid}")
    public MessageVO<ProblemVO> getAdminProblemByPid(@PathVariable("pid") Long pid) {
        ProblemVO problemVO = problemService.getProblemVOByPid(pid);
        if (problemVO != null) {
            return MessageVO.<ProblemVO>builder()
                    .error(null)
                    .data(problemVO)
                    .build();
        } else {
            return MessageVO.<ProblemVO>builder().error("No choosed problem find...").build();
        }
    }

    /**
     * desc : get problem list by admin
     * @param offset -
     * @param limit -
     * @param problemParametersDTO -
     * @return MessageVO<ProblemListVO>
     */
    @GetMapping("/admin/problems")
    public MessageVO<ProblemListVO> getAdminProblemList(@RequestParam("offset") Integer offset,
                                                        @RequestParam("limit") Integer limit,
                                                        ProblemParametersDTO problemParametersDTO) {
        return MessageVO.<ProblemListVO>builder()
                .error(null)
                .data(getPagingProblemListVO(problemParametersDTO, limit))
                .build();
    }

    /**
     * desc : create a new problem
     * @param problemDTO -
     * @return MessageVO<Boolean>
     */
    @PostMapping("/admin/problem")
    public MessageVO<Boolean> addSingleProblem(@RequestBody ProblemDTO problemDTO) {
        Boolean result = problemService.insertOneProblemDTO(problemDTO);
        return MessageVO.<Boolean>builder()
                .error(null)
                .data(result)
                .build();
    }

    /**
     * desc : update a new problem
     * @param problemDTO -
     * @return MessageVO<Boolean>
     */
    @PutMapping("/admin/problem")
    public MessageVO<Boolean> editSingeProblem(@RequestBody ProblemDTO problemDTO) {
        Boolean result = problemService.updateOneProblemDTO(problemDTO);
        return MessageVO.<Boolean>builder()
                .error(null)
                .data(result)
                .build();
    }

    /**
     * desc : get the problems from free problem set file
     * @return MessageVO<ProblemListVO>
     */
    @PostMapping("/admin/problems")
    public MessageVO<ProblemListVO> addProblems() {
        List<ProblemVO> problemVOList = FPSUtils.fps2ProblemVO(Long.valueOf("1"), "/Users/rexmao/Documents/RexStudio/NCIAE-OJ/Doc/standard-test-fps.xml");
        for (ProblemVO problemVO : problemVOList) {
            problemService.insertOneProblemVO(problemVO);
        }
        return MessageVO.<ProblemListVO>builder().error(null)
                .data(ProblemListVO.builder()
                        .results(problemVOList)
                        .total((long)problemVOList.size())
                        .build())
                .build();
    }

    /**
     * desc : get problem list view object with paging.
     * @param problemParametersDTO - parameters
     * @param limit - limit
     * @return ProblemListVO
     */
    private ProblemListVO getPagingProblemListVO(ProblemParametersDTO problemParametersDTO, Integer limit) {
        Page<ProblemVO> page;
        if (problemParametersDTO.getPage() != null){
            page = new Page<ProblemVO>(problemParametersDTO.getPage(), limit);
        } else {
            page = new Page<ProblemVO>(1, limit);
        }
        IPage<ProblemVO> problems = problemService.listProblemsByPaging(page, problemParametersDTO);
        for (ProblemVO problemVO : problems.getRecords()) {
            problemVO.setCheckpoints(new ArrayList<Checkpoint>());
        }
        return ProblemListVO.builder()
                .results(problems.getRecords())
                .total(problems.getTotal())
                .build();
    }
}
