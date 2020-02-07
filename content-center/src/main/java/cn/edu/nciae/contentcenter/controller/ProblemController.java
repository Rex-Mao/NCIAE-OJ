package cn.edu.nciae.contentcenter.controller;


import cn.edu.nciae.contentcenter.common.dto.ParametersDTO;
import cn.edu.nciae.contentcenter.common.entity.Problem;
import cn.edu.nciae.contentcenter.common.vo.MessageVO;
import cn.edu.nciae.contentcenter.common.vo.ProblemListVO;
import cn.edu.nciae.contentcenter.service.IProblemService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author RexALun
 * @since 2019-12-26
 */
@Controller
@RequestMapping("/api")
public class ProblemController {

    @Autowired
    public IProblemService problemService;

    @GetMapping("/problem")
    public MessageVO<ProblemListVO> getProblemList(@RequestParam("paging") Boolean paging,
                                                   @RequestParam("offset") Integer offset,
                                                   @RequestParam("limit") Integer limit,
                                                   ParametersDTO parametersDTO) {
        if (paging) {
            Page<Problem> page;
            if (parametersDTO.getPage() != null){
                page = new Page<Problem>(parametersDTO.getPage(), limit);
            } else {
                page = new Page<Problem>(1, limit);
            }
            IPage<Problem> problems = problemService.getProblemListPage(page);
            ProblemListVO problemListVO = ProblemListVO.builder()
                    .results(problems.getRecords())
                    .total(problems.getTotal())
                    .build();
            return MessageVO.<ProblemListVO>builder()
                    .error(null)
                    .data(problemListVO)
                    .build();
        }
        return MessageVO.<ProblemListVO>builder().error("No Problem Data Returned...").build();
    }

}
