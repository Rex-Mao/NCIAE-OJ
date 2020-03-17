package cn.edu.nciae.contentcenter.controller;


import cn.edu.nciae.contentcenter.common.dto.ParametersDTO;
import cn.edu.nciae.contentcenter.common.vo.MessageVO;
import cn.edu.nciae.contentcenter.common.vo.ProblemListVO;
import cn.edu.nciae.contentcenter.common.vo.ProblemVO;
import cn.edu.nciae.contentcenter.service.IProblemService;
import cn.edu.nciae.contentcenter.utils.FPSUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/problem")
    public MessageVO<ProblemListVO> getProblemList(@RequestParam("paging") Boolean paging,
                                                   @RequestParam("offset") Integer offset,
                                                   @RequestParam("limit") Integer limit,
                                                   ParametersDTO parametersDTO) {
        if (paging) {
            return MessageVO.<ProblemListVO>builder()
                    .error(null)
                    .data(getPagingProblemListVO(parametersDTO, limit))
                    .build();
        }
        return MessageVO.<ProblemListVO>builder().error("No Problem Data Returned...").build();
    }

    @GetMapping("/problem/{problem_id}")
    public MessageVO<ProblemVO> getProblemByPid(@PathVariable("problem_id") Long pid) {
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

    @GetMapping("/admin/problem")
    public MessageVO<ProblemListVO> getAdminProblemList(@RequestParam("offset") Integer offset,
                                                        @RequestParam("limit") Integer limit,
                                                        ParametersDTO parametersDTO) {
        return MessageVO.<ProblemListVO>builder()
                .error(null)
                .data(getPagingProblemListVO(parametersDTO, limit))
                .build();
    }

    @PutMapping("/admin/problem")
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
     * @param parametersDTO - parameters
     * @param limit - limit
     * @return ProblemListVO
     */
    private ProblemListVO getPagingProblemListVO(ParametersDTO parametersDTO, Integer limit) {
        Page<ProblemVO> page;
        if (parametersDTO.getPage() != null){
            page = new Page<ProblemVO>(parametersDTO.getPage(), limit);
        } else {
            page = new Page<ProblemVO>(1, limit);
        }
        IPage<ProblemVO> problems = problemService.listProblemsByPaging(page);
        return ProblemListVO.builder()
                .results(problems.getRecords())
                .total(problems.getTotal())
                .build();
    }
}
