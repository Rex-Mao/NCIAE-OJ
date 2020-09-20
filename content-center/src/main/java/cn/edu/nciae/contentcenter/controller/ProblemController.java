package cn.edu.nciae.contentcenter.controller;

import cn.edu.nciae.contentcenter.common.dto.PageParametersDTO;
import cn.edu.nciae.contentcenter.common.dto.ProblemDTO;
import cn.edu.nciae.contentcenter.common.dto.ProblemParametersDTO;
import cn.edu.nciae.contentcenter.common.entity.ProblemTag;
import cn.edu.nciae.contentcenter.common.entity.Record;
import cn.edu.nciae.contentcenter.common.vo.MessageVO;
import cn.edu.nciae.contentcenter.common.vo.ProblemListVO;
import cn.edu.nciae.contentcenter.common.vo.ProblemVO;
import cn.edu.nciae.contentcenter.common.vo.SolvedProblemListVO;
import cn.edu.nciae.contentcenter.service.IFileService;
import cn.edu.nciae.contentcenter.service.IProblemService;
import cn.edu.nciae.contentcenter.service.IProblemTagService;
import cn.edu.nciae.contentcenter.service.IRecordService;
import cn.edu.nciae.contentcenter.utils.FPSUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private IProblemTagService problemTagService;

    @Autowired
    private IFileService fileService;

    /**
     * desc : get all problems by user
     * @param pageParametersDTO -
     * @return MessageVO<ProblemListVO>
     */
    @GetMapping("/public/problems")
    public MessageVO<ProblemListVO> getProblemList(PageParametersDTO pageParametersDTO,
                                                   ProblemParametersDTO problemParametersDTO) {
        if (pageParametersDTO.getPaging()) {
            return MessageVO.<ProblemListVO>builder()
                    .error(null)
                    .data(getPagingProblemListVO(problemParametersDTO, pageParametersDTO, 0))
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
     * @param pageParametersDTO -
     * @param problemParametersDTO -
     * @return MessageVO<ProblemListVO>
     */
    @GetMapping("/admin/problems")
    public MessageVO<ProblemListVO> getAdminProblemList(PageParametersDTO pageParametersDTO,
                                                        ProblemParametersDTO problemParametersDTO) {
        return MessageVO.<ProblemListVO>builder()
                .error(null)
                .data(getPagingProblemListVO(problemParametersDTO, pageParametersDTO, -1))
                .build();
    }

    /**
     * desc : create a new problem
     * @param problemDTO -
     * @return MessageVO<Boolean>
     */
    @PostMapping("/admin/problem")
    public MessageVO<Boolean> createProblem(Authentication authentication,
                                            @RequestBody ProblemDTO problemDTO) {
        problemDTO.setAddUsername(((UserDetails)authentication.getPrincipal()).getUsername());
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
     * desc : delete problem by pid
     * @param pid -
     * @return MessageVO<Boolean>
     */
    @DeleteMapping("/admin/problem/{pid}")
    public MessageVO<Boolean> deleteProblemByPid(@PathVariable("pid") Long pid) {
        try {
            problemTagService.remove(Wrappers.<ProblemTag>lambdaQuery().eq(ProblemTag::getPid, pid));
            Boolean result = problemService.removeById(pid);
            return MessageVO.<Boolean>builder()
                    .error(null)
                    .data(result)
                    .build();
        } catch (Exception e) {
            return MessageVO.<Boolean>builder()
                    .error("Sorry, The problem is using by somewhere...")
                    .data(false)
                    .build();
        }
    }

    /**
     * desc : get the problems from free problem set file
     * @param authentication -
     * @param multipartFile -
     * @return MessageVO<ProblemListVO>
     */
    @PostMapping("/admin/problem/import_from_fps")
    public MessageVO<Boolean> createBatchProblemsFromFps(Authentication authentication,
                                                         @RequestParam("file") MultipartFile multipartFile) {
        String absolutePath = fileService.saveLocalFile(multipartFile, "FreeProblemSet");
        String addUsername = ((UserDetails) authentication.getPrincipal()).getUsername();
        List<ProblemDTO> problemDTOList = FPSUtils.fps2ProblemDTO(Long.valueOf("1"), absolutePath);
        problemDTOList.forEach(item -> {
            item.setAddUsername(addUsername);
            item.setAuthor("外部导入");
        });
        for (ProblemDTO problemDTO : problemDTOList) {
            problemService.insertOneProblemDTO(problemDTO);
        }
        return MessageVO.<Boolean>builder()
                .error(null)
                .data(true)
                .build();
    }

    /**
     * desc : get problem list view object with paging.
     * @param problemParametersDTO - parameters
     * @param status - means if it is a public problem
     * @return ProblemListVO
     */
    private ProblemListVO getPagingProblemListVO(ProblemParametersDTO problemParametersDTO, PageParametersDTO pageParametersDTO, Integer status) {
        Page<ProblemVO> page;
        if (pageParametersDTO.getPage() != null){
            page = new Page<>(pageParametersDTO.getPage(), pageParametersDTO.getLimit());
        } else {
            page = new Page<>(1, pageParametersDTO.getLimit());
        }
        IPage<ProblemVO> problems = problemService.listProblemsByPaging(page, problemParametersDTO);
        // set the rule for role user
        // TODO : decline the level of the filter to the database level
        List<ProblemVO> tmps = problems.getRecords();
        if (status == 0) {
            tmps = tmps.stream().filter(tmp -> tmp.getStatus() == 0).collect(Collectors.toList());
        }
        problems.setRecords(tmps);
        for (ProblemVO problemVO : problems.getRecords()) {
            problemVO.setCheckpoints(new ArrayList<>());
        }
        return ProblemListVO.builder()
                .results(problems.getRecords())
                .total(problems.getTotal())
                .build();
    }
}
